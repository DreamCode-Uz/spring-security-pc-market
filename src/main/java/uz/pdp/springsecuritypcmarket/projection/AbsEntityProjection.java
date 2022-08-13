package uz.pdp.springsecuritypcmarket.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.springsecuritypcmarket.entity.Currency;

@Projection(types = {Currency.class})
public interface AbsEntityProjection {
    Integer getId();

    String getName();

    boolean isActive();
}
