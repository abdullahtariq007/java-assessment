package company.tap.java.assessment.mapper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
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
        MerchantDto merchantDto = merchantMapper.convertToDto(merchant);
        Assert.assertEquals(merchantDto.getEmailAddress(),merchant.getEmailAddress());
        Assert.assertEquals(merchantDto.getIdentificationNumber(),merchant.getIdentificationNumber());
        Assert.assertEquals(merchantDto.getLicenseNumber(),merchant.getLicenseNumber());
    }

    @Test
    void convertToEntity() {
        MerchantDto merchantDto = new MerchantDto();
        merchantDto.setEmailAddress("testemail@test.email");
        merchantDto.setLicenseNumber("test license number");
        merchantDto.setIdentificationNumber("123456 id number");
        Merchant merchant = merchantMapper.convertToEntity(merchantDto);
        Assert.assertEquals(merchant.getEmailAddress(),merchantDto.getEmailAddress());
        Assert.assertEquals(merchant.getIdentificationNumber(),merchantDto.getIdentificationNumber());
        Assert.assertEquals(merchant.getLicenseNumber(),merchantDto.getLicenseNumber());
    }
}