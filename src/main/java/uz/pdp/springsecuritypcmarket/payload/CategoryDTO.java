package uz.pdp.springsecuritypcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDTO {
    @NotBlank(message = "name must not be null or empty.")
    private String name;
    private boolean active;
    private Integer categoryId;
}
