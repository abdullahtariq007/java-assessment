package company.tap.java.assessment.repository;

import company.tap.java.assessment.model.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends MongoRepository<Merchant, Integer> {
    boolean existsByEmailAddress(String emailAddress);

    long deleteByEmailAddress(String emailAddress);

    Merchant findByEmailAddress(String emailAddress);

    @Query(value = "{'emailAddress': ?0}", fields = "{'licenseNumber': 1}")
    List<Merchant> getLicenseNumberHistoryByEmail(String emailAddress);

}
