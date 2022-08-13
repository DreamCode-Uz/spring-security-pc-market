package uz.pdp.springsecuritypcmarket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.springsecuritypcmarket.entity.template.AbsEntity;

import javax.persistence.Entity;

@NoArgsConstructor
@Data
@Entity(name = "currency")
@EqualsAndHashCode(callSuper = true)
public class Currency extends AbsEntity {

    public Currency(Integer id, String name, boolean active) {
        super(id, name, active);
    }
}
