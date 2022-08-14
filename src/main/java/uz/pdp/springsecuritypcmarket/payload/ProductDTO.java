package uz.pdp.springsecuritypcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ProductDTO {
    @NotBlank(message = "name must not be null")
    private String name;
    private String description;
    private boolean active;
    @NotNull(message = "price must not be null")
    private Double price;
    @NotNull(message = "amount must not be null")
    private Double amount;
    @NotNull(message = "currencyId must not be null")
    private Integer currencyId;
    @NotNull(message = "measurementId must not be null")
    private Integer measurementId;
    @NotNull(message = "categoryId must not be null")
    private Integer categoryId;
    @NotNull(message = "photosId must not be null")
    private Set<Integer> photosId;
}
