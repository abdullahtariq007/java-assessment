package company.tap.java.assessment.controller;

import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.dto.OtpDto;
import company.tap.java.assessment.exception.MerchantException;
import company.tap.java.assessment.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController("/v1/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/addMerchant")
    public ResponseEntity<MerchantDto> addMerchant(@RequestBody MerchantDto merchantDto) {
        try {
            return new ResponseEntity<>(merchantService.addMerchant(merchantDto), HttpStatus.OK);
        } catch (MerchantException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllMerchants")
    public ResponseEntity<List<MerchantDto>> getAllMerchants(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        List<MerchantDto> merchantDtoList = merchantService.getAllMerchants(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(merchantDtoList, HttpStatus.OK);
    }

    @GetMapping("/getMerchant/{emailAddress}")
    public ResponseEntity<MerchantDto> getMerchant(@PathVariable String emailAddress) throws MerchantException {
        return new ResponseEntity<>(merchantService.getMerchantByEmail(emailAddress), HttpStatus.OK);
    }

    @DeleteMapping("/deleteMerchant/{emailAddress}")
    public ResponseEntity<Boolean> deleteMerchant(@PathVariable String emailAddress) throws MerchantException {
        boolean status = merchantService.deleteMerchant(emailAddress) == 1;
        return new ResponseEntity<>(status, status ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateMerchant")
    public ResponseEntity<MerchantDto> updateMerchant(@RequestBody MerchantDto merchantDto) throws MerchantException {
        return new ResponseEntity<>(merchantService.updateMerchant(merchantDto), HttpStatus.OK);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<Boolean> verifyOtp(@RequestBody OtpDto otpDto) {
        boolean status = merchantService.verifyOtp(otpDto);
        return new ResponseEntity<>(status, status ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/resendOtp/{emailAddress}")
    public ResponseEntity<Boolean> resendOtp(@PathVariable String emailAddress) throws MerchantException {
        boolean status = merchantService.resendOtp(emailAddress);
        return new ResponseEntity<>(status, status ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("history/{emailAddress}")
    public ResponseEntity<List<MerchantDto>> getLicenseHistory(@PathVariable String emailAddress) {
        return new ResponseEntity<>(merchantService.getLicenseNumberHistoryById(emailAddress), HttpStatus.OK);
    }

}
