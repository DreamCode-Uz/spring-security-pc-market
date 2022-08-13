package uz.pdp.springsecuritypcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.springsecuritypcmarket.entity.ProductProp;
import uz.pdp.springsecuritypcmarket.projection.ProductPropProjection;

@RepositoryRestResource(path = "product-prop", excerptProjection = ProductPropProjection.class)
public interface ProductPropertiesRepository extends JpaRepository<ProductProp, Integer> {
}
