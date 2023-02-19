package company.tap.java.assessment.utils.email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/*
 *This class will contain code to send email to client
 *  */
@Getter
@Setter
@Slf4j
@Service
@NoArgsConstructor
public class EmailManager {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(to);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        log.info("Sending Email....");
        try {
            mailSender.send(message);
        } catch (MailException exception) {
            log.info("Unable to send email due to following error: {}", exception.getMessage());
        }
    }
}
