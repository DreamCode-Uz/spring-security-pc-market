package uz.pdp.springsecuritypcmarket.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.springsecuritypcmarket.entity.ProductProp;

@Projection(types = {ProductProp.class})
public interface ProductPropProjection {
    Integer getId();

    String getColor();

    Double getWeight();
}
