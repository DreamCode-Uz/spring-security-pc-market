package uz.pdp.springsecuritypcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.springsecuritypcmarket.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "category")
@EqualsAndHashCode(callSuper = true)
public class Category extends AbsEntity {
    @ManyToOne
    private Category category;

    public Category(Integer id, @NotBlank String name, boolean active, Category category) {
        super(id, name, active);
        this.category = category;
    }
}
