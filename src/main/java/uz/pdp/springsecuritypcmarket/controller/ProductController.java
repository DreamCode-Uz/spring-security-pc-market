package uz.pdp.springsecuritypcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecuritypcmarket.payload.ProductDTO;
import uz.pdp.springsecuritypcmarket.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getOne(@PathVariable("productId") Integer id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProductDTO dto) {
        return service.addProduct(dto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> update(@PathVariable("productId") Integer id, @Valid @RequestBody ProductDTO dto) {
        return service.editProduct(id, dto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") Integer id) {
        return service.deleteProduct(id);
    }
}
