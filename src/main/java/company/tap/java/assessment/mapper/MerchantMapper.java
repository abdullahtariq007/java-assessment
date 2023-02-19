package company.tap.java.assessment.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.model.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MerchantMapper {


    public MerchantDto convertToDto(Merchant merchantEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String merchantString = objectMapper.writeValueAsString(merchantEntity);
            MerchantDto merchantDto = objectMapper.readValue(merchantString, MerchantDto.class);
            merchantDto.setPassword(null);
            return merchantDto;
        } catch (IOException exception) {
            log.info("Exception while converting Merchant Entity to Dto: {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }


    public Merchant convertToEntity(MerchantDto merchantDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String merchantString = objectMapper.writeValueAsString(merchantDto);
            return objectMapper.readValue(merchantString, Merchant.class);
        } catch (IOException exception) {
            log.info("Exception while converting Merchant Dto to Entity: {}", exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }
}
