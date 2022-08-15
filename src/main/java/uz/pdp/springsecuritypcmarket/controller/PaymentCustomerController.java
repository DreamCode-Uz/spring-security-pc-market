package uz.pdp.springsecuritypcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecuritypcmarket.payload.PayCusDTO;
import uz.pdp.springsecuritypcmarket.service.PaymentCustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/payment")
public class PaymentCustomerController {
    private final PaymentCustomerService service;

    @Autowired
    public PaymentCustomerController(PaymentCustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getOne(@PathVariable("paymentId") Integer id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody PayCusDTO dto) {
        return service.addPaymentCustomer(dto);
    }

//    @PutMapping("/{paymentId}")
//    public ResponseEntity<?> update(@PathVariable("paymentId") Integer id, @Valid @RequestBody PayCusDTO dto) {
//        return service.editPaymentCustomer(id, dto);
//    }
}
