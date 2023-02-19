package company.tap.java.assessment.utils.email;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailAddressVerify {

    final String regexPattern = "^(.+)@(\\S+)$";

    public boolean isValidEmailAddress(String emailAddress) {
        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }

}
