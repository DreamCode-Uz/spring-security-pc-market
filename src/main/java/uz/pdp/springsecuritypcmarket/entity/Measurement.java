package uz.pdp.springsecuritypcmarket.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.springsecuritypcmarket.entity.template.AbsEntity;

import javax.persistence.Entity;

@NoArgsConstructor
@Data
@Entity(name = "measurement")
@EqualsAndHashCode(callSuper = true)
public class Measurement extends AbsEntity {

    public Measurement(Integer id, String name, boolean active) {
        super(id, name, active);
    }
}
