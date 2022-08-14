package uz.pdp.springsecuritypcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.springsecuritypcmarket.entity.Customer;

@RepositoryRestResource(path = "customer")
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
