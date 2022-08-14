package uz.pdp.springsecuritypcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.springsecuritypcmarket.entity.template.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "product")
public class Product extends AbsEntity {

    private String description;

    private Double price;

    private Double amount;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Attachment> attachment;

    @ManyToOne(optional = false)
    private Currency currency;

    @ManyToOne(optional = false)
    private Measurement measurement;

    @ManyToOne(optional = false)
    private Category category;

    public Product(Integer id, String name, boolean active, String description, Double price, Double amount, Set<Attachment> attachment, Currency currency, Measurement measurement) {
        super(id, name, active);
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.attachment = attachment;
        this.currency = currency;
        this.measurement = measurement;
    }
}
