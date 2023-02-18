package company.tap.java.assessment.mapper;

import org.junit.Assert;
import org.junit.Test;
import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.model.Merchant;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MerchantMapperTest  {

    private final MerchantMapper merchantMapper = new MerchantMapper();

    @Test
    public void convertToDto() {
        Merchant merchant = new Merchant();
        merchant.setId(10);
        merchant.setEmailAddress("testemail@test.email");
        merchant.setIdentificationNumber("TestIdentificationNumber");
        merchant.setLicenseNumber("Merchant Sample license number");
//        merchant.setOtpVerified(true);

        MerchantDto merchantDto = merchantMapper.convertToDto(merchant);
        Assert.assertEquals(merchantDto.getEmailAddress(),merchant.getEmailAddress());
        Assert.assertEquals(merchantDto.getIdentificationNumber(),merchant.getIdentificationNumber());
        Assert.assertEquals(merchantDto.getLicenseNumber(),merchant.getLicenseNumber());
    }

//    @Test
//    void convertToEntity() {
//    }
}