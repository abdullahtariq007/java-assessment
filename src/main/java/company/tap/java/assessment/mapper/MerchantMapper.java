package company.tap.java.assessment.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import company.tap.java.assessment.dto.MerchantDto;
import company.tap.java.assessment.model.Merchant;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class MerchantMapper {

    @SneakyThrows
    public MerchantDto convertToDto(Merchant merchantEntity)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String merchantString = objectMapper.writeValueAsString(merchantEntity);
        return objectMapper.readValue(merchantString,MerchantDto.class);
    }
    @SneakyThrows
    public Merchant convertToEntity(MerchantDto merchantDto)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String merchantString = objectMapper.writeValueAsString(merchantDto);
        return objectMapper.readValue(merchantString, Merchant.class);
    }
}
