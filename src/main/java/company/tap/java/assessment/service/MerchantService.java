package company.tap.java.assessment.service;

import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.dto.OtpDto;
import company.tap.java.assessment.exception.MerchantException;
import company.tap.java.assessment.mapper.MerchantMapper;
import company.tap.java.assessment.model.Merchant;
import company.tap.java.assessment.repository.MerchantRepository;
import company.tap.java.assessment.utils.client.rest.MerchantWebhookClient;
import company.tap.java.assessment.utils.email.EmailAddressVerify;
import company.tap.java.assessment.utils.email.EmailManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class MerchantService {

    MerchantRepository merchantRepository;
    MerchantMapper merchantMapper;
    OtpGenerator otpGenerator;
    EmailAddressVerify emailAddressVerifier;
    EmailManager emailManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MerchantWebhookClient merchantWebhookClient;

    public List<MerchantDto> getAllMerchants(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Merchant> page = merchantRepository.findAll(pageable);
        List<Merchant> merchantList = page.getContent();
        List<MerchantDto> merchantDtoList = new ArrayList<>();
        merchantList.forEach(merchant -> merchantDtoList.add(merchantMapper.convertToDto(merchant)));
        return merchantDtoList;
    }

    public MerchantDto getMerchantByEmail(String emailAddress) throws MerchantException {
        Merchant merchant = merchantRepository.findByEmailAddress(emailAddress);
        if (merchant.getEmailAddress().isEmpty()) {
            throw new MerchantException("Merchant Does not Exists with this email...!!!");
        }
        return merchantMapper.convertToDto(merchant);
    }

    public MerchantDto addMerchant(@NotNull MerchantDto merchantDto) throws MerchantException {

        if (!emailAddressVerifier.isValidEmailAddress(merchantDto.getEmailAddress())) {
            throw new MerchantException("Email Address Not Valid. Please provide a valid email address");
        }
        if (!merchantRepository.existsByEmailAddress(merchantDto.getEmailAddress())) {
            Merchant merchantEntity = merchantMapper.convertToEntity(merchantDto);
            merchantEntity.setEmailAddress(merchantEntity.getEmailAddress().toLowerCase());
            merchantEntity.setPassword(bCryptPasswordEncoder.encode(merchantEntity.getPassword()));
            Merchant merchant = merchantRepository.save(merchantEntity);
            createAndSendOtp(merchantEntity.getEmailAddress());
            MerchantDto merchantDtoResponse = merchantMapper.convertToDto(merchant);
            merchantWebhookClient.sendWebhook(merchantDtoResponse);
            return merchantDtoResponse;

        } else {
            throw new MerchantException("This merchant already exists");
        }

    }

    public long deleteMerchant(String emailAddress) throws MerchantException {
        if (merchantRepository.existsByEmailAddress(emailAddress)) {
            return merchantRepository.deleteByEmailAddress(emailAddress);
        } else {
            throw new MerchantException("Merchant Does not Exists with this email...!!!");
        }
    }

    public MerchantDto updateMerchant(@NotNull MerchantDto merchantDto) throws MerchantException {
        if (merchantRepository.existsByEmailAddress(merchantDto.getEmailAddress())) {
            Merchant merchant = merchantRepository.findByEmailAddress(merchantDto.getEmailAddress());
            merchant.setIdentificationNumber(StringUtils.isNotEmpty(merchantDto.getIdentificationNumber()) ? merchantDto.getIdentificationNumber() : merchant.getIdentificationNumber());
            merchant.setLicenseNumber(StringUtils.isNotEmpty(merchantDto.getLicenseNumber()) ? merchantDto.getLicenseNumber() : merchant.getLicenseNumber());
            return merchantMapper.convertToDto(merchantRepository.save(merchant));
        } else {
            throw new MerchantException("Merchant Does not Exists with this email...!!!");
        }
    }

    public boolean verifyOtp(OtpDto otpDto) {
        Integer otpFromCache = otpGenerator.getOPTByKey(otpDto.getEmailAddress());
        if (otpFromCache.equals(otpDto.getOtp())) {
            Merchant merchant = merchantRepository.findByEmailAddress(otpDto.getEmailAddress());
            merchant.setVerified(true);
            merchantRepository.save(merchant);
            otpGenerator.clearOTPFromCache(otpDto.getEmailAddress());
            return true;
        }
        return false;
    }

    public boolean resendOtp(String emailAddress) {
        if (merchantRepository.existsByEmailAddress(emailAddress)) {
            createAndSendOtp(emailAddress);
            return true;
        }
        return false;
    }

    private void createAndSendOtp(String emailAddress) {
        otpGenerator.generateOTP(emailAddress);
        log.info("Generated OTP is: {}", otpGenerator.getOPTByKey(emailAddress).toString());
        sendOtpEmail(emailAddress);
    }

    public void sendOtpEmail(String emailAddress) throws NullPointerException {
        try {
            emailManager.sendEmail(emailAddress, "Tap Payments OTP", "Your OTP for Tap Payments is: " + otpGenerator.getOPTByKey(emailAddress).toString());
        } catch (Exception exception) {
            log.info("Unable to send email due to following error: {}", exception.getMessage());
        }
    }

    public List<MerchantDto> getLicenseNumberHistoryById(String emailAddress) {
        List<Merchant> merchantList = merchantRepository.getLicenseNumberHistoryByEmail(emailAddress);
        return merchantList.stream().map(merchant -> merchantMapper.convertToDto(merchant)).collect(Collectors.toList());
    }
}
