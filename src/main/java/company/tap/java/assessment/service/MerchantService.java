package company.tap.java.assessment.service;

import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.dto.OtpDto;
import company.tap.java.assessment.exception.MerchantException;
import company.tap.java.assessment.mapper.MerchantMapper;
import company.tap.java.assessment.model.Merchant;
import company.tap.java.assessment.repository.MerchantRepository;

import company.tap.java.assessment.utils.email.EmailAddressVerify;
import company.tap.java.assessment.utils.email.EmailManager;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    OtpGenerator otpGenerator;

    public List<MerchantDto> getAllMerchants(int pageNumber,int pageSize,String sortBy)
    {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Merchant> page = merchantRepository.findAll(pageable);
        List<Merchant> merchantList =  page.getContent();
        List<MerchantDto> merchantDtoList = new ArrayList<>();
        merchantList.forEach(merchant -> {
            merchantDtoList.add(merchantMapper.convertToDto(merchant));
        });
        return merchantDtoList;
    }
    public MerchantDto getMerchantByEmail(String emailAddress) throws MerchantException {
        Merchant merchant= merchantRepository.findByEmailAddress(emailAddress);
        if(merchant.getEmailAddress().isEmpty())
        {
                throw new MerchantException("Merchant Doesnot Exists with this email...!!!");
        }
        MerchantDto merchantDto = merchantMapper.convertToDto(merchant);
        return merchantDto;
    }

    public ResponseEntity addMerchant(@NotNull MerchantDto merchantDto) throws MerchantException {
        EmailAddressVerify emailAddressVerifier = new EmailAddressVerify();
        if(!emailAddressVerifier.isValidEmailAddress(merchantDto.getEmailAddress()))
        {throw new MerchantException("Email Address Not Valid. Please provide a valid email address");}

        if(!merchantRepository.existsByEmailAddress(merchantDto.getEmailAddress()))
        {
            Merchant merchant = merchantMapper.convertToEntity(merchantDto);
            merchant.setEmailAddress(merchant.getEmailAddress().toLowerCase());
            merchantRepository.save(merchant);
            createAndSendOtp(merchant.getEmailAddress());
            //Now Background service here to send email
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            throw new MerchantException("This merchant already exists");
        }

    }
    public ResponseEntity deleteMerchant(String emailAddress) throws MerchantException {
        if(merchantRepository.existsByEmailAddress(emailAddress))
        {
            merchantRepository.deleteByEmailAddress(emailAddress);
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            throw new MerchantException("Merchant Doesnot Exists with this email...!!!");
        }
    }
    public ResponseEntity updateMerchant(@NotNull MerchantDto merchantDto) throws MerchantException {
        if(merchantRepository.existsByEmailAddress(merchantDto.getEmailAddress()))
        {
            Merchant merchant = merchantRepository.findByEmailAddress(merchantDto.getEmailAddress());
            merchant = merchantMapper.convertToEntity(merchantDto);
            merchantRepository.save(merchant);
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            throw new MerchantException("Merchant Doesnot Exists with this email...!!!");
        }
    }

    public ResponseEntity verifyDto(OtpDto otpDto)
    {
        Integer otpFromCache = otpGenerator.getOPTByKey(otpDto.getEmailAddress());
        if(otpFromCache.equals(otpDto.getOtp()))
        {
            Merchant merchant = merchantRepository.findByEmailAddress(otpDto.getEmailAddress());
            merchant.setVerified(true);
            merchantRepository.save(merchant);
            otpGenerator.clearOTPFromCache(otpDto.getEmailAddress());
            return new ResponseEntity(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity resendOtp(String emailAddress) throws MerchantException {
        if(merchantRepository.existsByEmailAddress(emailAddress))
        {
            createAndSendOtp(emailAddress);
            return new ResponseEntity(HttpStatus.OK);
        }
        throw new MerchantException("Merchant Doesnot Exists with this email...!!!");
    }
    private void createAndSendOtp(String emailAddress)
    {
        otpGenerator.generateOTP(emailAddress);
        log.info("Generated OTP is: {}",otpGenerator.getOPTByKey(emailAddress).toString());
        sendOtpEmail(emailAddress);
    }
    private void sendOtpEmail(String emailAddress) throws NullPointerException
    {
        EmailManager emailManager = null;
        emailManager.sendEmail(emailAddress,"Tap Payments OTP","Your OTP for Tap Payments is: "+otpGenerator.getOPTByKey(emailAddress).toString());
    }
}
