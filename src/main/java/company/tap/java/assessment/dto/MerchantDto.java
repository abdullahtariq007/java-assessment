package company.tap.java.assessment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MerchantDto {
    private String identificationNumber;
    private String licenseNumber;
    private String emailAddress;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
