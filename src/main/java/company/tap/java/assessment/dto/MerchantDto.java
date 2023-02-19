package company.tap.java.assessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import company.tap.java.assessment.utils.enums.StatusType;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantDto {
    private String identificationNumber;
    private String licenseNumber;
    private String emailAddress;
    private StatusType identificationNumberStatus = StatusType.PENDING;
    private StatusType licenseNumberStatus = StatusType.PENDING;

    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String webhookUrl;

}
