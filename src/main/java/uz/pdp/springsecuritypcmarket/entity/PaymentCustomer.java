package uz.pdp.springsecuritypcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "payment_customer")
public class PaymentCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private Double price;

    @ManyToMany
    private Set<Product> products;
}
