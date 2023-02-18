package company.tap.java.assessment.repository;

import company.tap.java.assessment.model.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MerchantRepository extends MongoRepository<Merchant,Integer> {
boolean existsByEmailAddress(String emailAddress);
long deleteByEmailAddress(String emailAddress);
Merchant findByEmailAddress(String emailAddress);

}
