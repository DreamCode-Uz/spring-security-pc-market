package uz.pdp.springsecuritypcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecuritypcmarket.payload.CustomerDTO;
import uz.pdp.springsecuritypcmarket.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getOne(@PathVariable("customerId") Integer id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CustomerDTO dto) {
        return service.addCustomer(dto);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> update(@PathVariable("customerId") Integer id, @Valid @RequestBody CustomerDTO dto) {
        return service.editCustomer(id, dto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> delete(@PathVariable("customerId") Integer id) {
        return service.deleteCustomer(id);
    }
}
