package uz.pdp.springsecuritypcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springsecuritypcmarket.payload.CategoryDTO;
import uz.pdp.springsecuritypcmarket.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getOne(@PathVariable("categoryId") Integer id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CategoryDTO dto) {
        return service.addCategory(dto);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> update(@PathVariable("categoryId") Integer id, @Valid @RequestBody CategoryDTO dto) {
        return service.editCategory(id, dto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable("categoryId") Integer id) {
        return service.deleteCategory(id);
    }
}
