package uz.pdp.springsecuritypcmarket.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class CustomerDTO {

    @NotBlank(message = "Name should not be null and also empty")
    private String fullName;

    @NotNull(message = "phoneNumber must not be null")
    //    bu pattern uchun aynan mos variantlar (+99 890 123 4567,  +99-890-123-4567, +99.890.123.4567)
    @Pattern(regexp = "^\\+\\d{2}[- .]?(\\d{3}[- .]?){2}\\d{4}", message = "Please enter the phone number in the appropriate form (Example: +99 890 123 4567)")
    private String phoneNumber;

    @NotNull(message = "email must not be null")
    @Email(message = "Invalid email address entered.")
    private String email;

    @NotNull(message = "productsId must not be null")
    private Set<Integer> productsId;
}
