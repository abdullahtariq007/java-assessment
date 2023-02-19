package company.tap.java.assessment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OtpDto {
    String emailAddress;
    int otp;
}
