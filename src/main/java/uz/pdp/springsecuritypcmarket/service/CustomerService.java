package uz.pdp.springsecuritypcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springsecuritypcmarket.entity.Customer;
import uz.pdp.springsecuritypcmarket.entity.Product;
import uz.pdp.springsecuritypcmarket.exception.BadRequestException;
import uz.pdp.springsecuritypcmarket.exception.ConflictException;
import uz.pdp.springsecuritypcmarket.exception.NotFoundException;
import uz.pdp.springsecuritypcmarket.payload.CustomerDTO;
import uz.pdp.springsecuritypcmarket.repository.CustomerRepository;
import uz.pdp.springsecuritypcmarket.repository.ProductRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final ProductRepository productRepository;

    @Autowired

    public CustomerService(CustomerRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        return ok(optionalCustomer.get());
    }

    public ResponseEntity<?> addCustomer(CustomerDTO dto) {
        Customer customer = checkCustomer(dto);
        return status(HttpStatus.CREATED).body(repository.save(customer));
    }

    public ResponseEntity<?> editCustomer(Integer id, CustomerDTO dto) {
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        Customer customer = checkCustomer(dto);
        customer.setId(id);
        return ok(repository.save(customer));
    }

    public ResponseEntity<?> deleteCustomer(Integer id) {
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        try {
            repository.delete(optionalCustomer.get());
            return noContent().build();
        } catch (Exception e){
            return badRequest().build();
        }
    }

    //  ACTION
    private Customer checkCustomer(CustomerDTO dto) {
        if (repository.existsByEmail(dto.getEmail()))
            throw new ConflictException("This email has already been registered.");
        if (repository.existsByPhoneNumber(dto.getPhoneNumber()))
            throw new ConflictException("This phone number has already been registered.");
        Set<Product> products = new HashSet<>();
        for (Integer productId : dto.getProductsId()) {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            optionalProduct.ifPresent(products::add);
        }
        if (products.size() == 0) throw new BadRequestException("No products available.");
        return new Customer(dto.getFullName(), dto.getPhoneNumber(), dto.getEmail(), products);
    }
}
