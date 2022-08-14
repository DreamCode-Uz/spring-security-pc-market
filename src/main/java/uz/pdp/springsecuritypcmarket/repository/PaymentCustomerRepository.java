package uz.pdp.springsecuritypcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springsecuritypcmarket.entity.PaymentCustomer;

@Repository
public interface PaymentCustomerRepository extends JpaRepository<PaymentCustomer, Integer> {
}
