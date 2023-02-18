package company.tap.java.assessment.utils.email;



import java.util.regex.Pattern;

public class EmailAddressVerify {

    final String regexPattern = "^(.+)@(\\S+)$";
    public boolean isValidEmailAddress(String emailAddress)
    {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
