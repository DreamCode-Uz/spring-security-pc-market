package uz.pdp.springsecuritypcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CustomerDTO {
    @NotBlank(message = "Name should not be null and also empty")
    private String fullName;
    @NotNull(message = "phoneNumber must not be null")
    private String phoneNumber;
    @NotNull(message = "email must not be null")
    private String email;
    @NotNull(message = "productsId must not be null")
    private Set<Integer> productsId;
}
