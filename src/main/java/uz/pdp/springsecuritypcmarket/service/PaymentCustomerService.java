package uz.pdp.springsecuritypcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springsecuritypcmarket.entity.Customer;
import uz.pdp.springsecuritypcmarket.entity.PaymentCustomer;
import uz.pdp.springsecuritypcmarket.entity.Product;
import uz.pdp.springsecuritypcmarket.exception.BadRequestException;
import uz.pdp.springsecuritypcmarket.exception.NotFoundException;
import uz.pdp.springsecuritypcmarket.payload.PayCusDTO;
import uz.pdp.springsecuritypcmarket.repository.CustomerRepository;
import uz.pdp.springsecuritypcmarket.repository.PaymentCustomerRepository;
import uz.pdp.springsecuritypcmarket.repository.ProductRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@Service
public class PaymentCustomerService {
    private final PaymentCustomerRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PaymentCustomerService(PaymentCustomerRepository repository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<PaymentCustomer> opc = repository.findById(id);
        if (!opc.isPresent()) throw new NotFoundException("Payment Customer not found");
        return ok(opc.get());
    }

    public ResponseEntity<?> addPaymentCustomer(PayCusDTO dto) {
        PaymentCustomer paymentCustomer = checkPayCustomer(dto);
        return status(HttpStatus.CREATED).body(repository.save(paymentCustomer));
    }
//    PAYMENT CUSTOMER YANI CHIQIM MA'LUMOTLARINI O'ZGARTIRISH  VA O'CHIRISH MUMKIN EMAS
/*
    public ResponseEntity<?> editPaymentCustomer(Integer id, PayCusDTO dto) {
        Optional<PaymentCustomer> optionalPaymentCustomer = repository.findById(id);
        if (!optionalPaymentCustomer.isPresent()) throw new NotFoundException("Payment Customer Not Found");
        PaymentCustomer paymentCustomer = checkPayCustomer(dto);
        paymentCustomer.setId(id);
        return status(HttpStatus.CREATED).body(repository.save(paymentCustomer));
    }
    */

    //     ACTION METHOD
    public PaymentCustomer checkPayCustomer(PayCusDTO dto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(dto.getCustomerId());
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        Set<Product> products = new HashSet<>();
        for (Integer id : dto.getProductsId()) {
            Optional<Product> optionalProduct = productRepository.findById(id);
            optionalProduct.ifPresent(products::add);
        }
        if (products.size() == 0) throw new BadRequestException(null);
        return new PaymentCustomer(null, optionalCustomer.get(), new Date(), dto.getPrice(), products);
    }
}

