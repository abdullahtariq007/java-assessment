package company.tap.java.assessment.utils.email;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailAddressVerifyTest {

    @Test
    void isValidEmailAddress() {
        EmailAddressVerify emailAddressVerify = new EmailAddressVerify();
        Assert.assertEquals(emailAddressVerify.isValidEmailAddress("test@test.test"),true);
        Assert.assertEquals(emailAddressVerify.isValidEmailAddress("aasda.asda.asd"),false);
    }
}