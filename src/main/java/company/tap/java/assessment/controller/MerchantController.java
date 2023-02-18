package company.tap.java.assessment.controller;

import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.dto.OtpDto;
import company.tap.java.assessment.exception.MerchantException;
import company.tap.java.assessment.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/addMerchant")
    public ResponseEntity addMerchant(@RequestBody MerchantDto merchantDto)
    {
        try {
            return merchantService.addMerchant(merchantDto);
        } catch (MerchantException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllMerchants")
    public ResponseEntity<List<MerchantDto>> getAllMerchants(@RequestParam(defaultValue = "0") Integer pageNo,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "id") String sortBy)
    {
        List<MerchantDto> merchantDtoList = merchantService.getAllMerchants(pageNo,pageSize,sortBy);
        return new ResponseEntity<>(merchantDtoList, HttpStatus.OK);
    }

    @GetMapping("/getMerchant/{emailAddress}")
    public Optional<MerchantDto> getMerchant(@PathVariable String emailAddress) throws MerchantException {
        return Optional.ofNullable(merchantService.getMerchantByEmail(emailAddress));
    }

    @DeleteMapping("/deleteMerchant/{emailAddress}")
    public ResponseEntity deleteMerchant(@PathVariable String emailAddress) throws MerchantException {
        return merchantService.deleteMerchant(emailAddress);
    }
    @PutMapping("/updateMerchant")
    public ResponseEntity updateMerchant( @RequestBody MerchantDto merchantDto) throws MerchantException {
        return merchantService.updateMerchant(merchantDto);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity verifyOtp(@RequestBody OtpDto otpDto) {
            return merchantService.verifyDto(otpDto);
        }
    @GetMapping("/resendOtp/{emailAddress}")
    public ResponseEntity resendOtp(@PathVariable String emailAddress) throws MerchantException
    {
        return merchantService.resendOtp(emailAddress);
    }




}
