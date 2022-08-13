package uz.pdp.springsecuritypcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.springsecuritypcmarket.entity.Currency;
import uz.pdp.springsecuritypcmarket.projection.AbsEntityProjection;

@RepositoryRestResource(path = "currency", excerptProjection = AbsEntityProjection.class)
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
}
