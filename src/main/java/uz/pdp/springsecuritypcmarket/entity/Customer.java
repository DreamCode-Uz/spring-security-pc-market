package uz.pdp.springsecuritypcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;
    @ManyToMany
    private Set<Product> products;

    public Customer(String fullName, String phoneNumber, String email, Set<Product> products) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.products = products;
    }
}
