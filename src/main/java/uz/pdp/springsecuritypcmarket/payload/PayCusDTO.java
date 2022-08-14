package uz.pdp.springsecuritypcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class PayCusDTO {
    @NotNull(message = "customerId must not be null")
    private Integer customerId;
    @NotNull(message = "price must not be null")
    private Double price;
    @NotNull(message = "productsId must not be null")
    private Set<Integer> productsId;
}
