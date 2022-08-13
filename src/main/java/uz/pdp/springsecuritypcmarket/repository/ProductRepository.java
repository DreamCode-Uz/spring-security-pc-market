package uz.pdp.springsecuritypcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springsecuritypcmarket.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
