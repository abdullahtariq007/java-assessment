package company.tap.java.assessment.events;


import company.tap.java.assessment.model.Merchant;
import company.tap.java.assessment.model.User;
import company.tap.java.assessment.service.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class UserModelListener extends AbstractMongoEventListener<User> {

    private SequenceGenerator sequenceGenerator;

    @Autowired
    public UserModelListener(SequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId((int) sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        }
    }


}