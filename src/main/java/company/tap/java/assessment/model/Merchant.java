package company.tap.java.assessment.model;

import company.tap.java.assessment.utils.enums.StatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;

@Getter
@Setter
@ToString
@Document(collection = "Merchant")
@Entity
@Audited
public class Merchant {
    @Transient
    public static final String SEQUENCE_NAME = "merchant_sequence";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String identificationNumber;
    private StatusType identificationNumberStatus = StatusType.PENDING;
    private String licenseNumber;
    private StatusType licenseNumberStatus = StatusType.PENDING;
    private String emailAddress;
    private boolean verified = false;
    private String password;
    @Version
    private Long version;
    private String webhookUrl;
}
