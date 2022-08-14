package uz.pdp.springsecuritypcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springsecuritypcmarket.entity.Category;
import uz.pdp.springsecuritypcmarket.exception.BadRequestException;
import uz.pdp.springsecuritypcmarket.exception.NotFoundException;
import uz.pdp.springsecuritypcmarket.payload.CategoryDTO;
import uz.pdp.springsecuritypcmarket.repository.CategoryRepository;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) throw new NotFoundException("Category not found");
        return ok(optionalCategory.get());
    }

    public ResponseEntity<?> addCategory(CategoryDTO dto) {
        Category category = checkCategory(dto);
        return status(HttpStatus.CREATED).body(repository.save(category));
    }

    public ResponseEntity<?> editCategory(Integer id, CategoryDTO dto) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) throw new NotFoundException("Category not found");
        Category category = checkCategory(dto);
        category.setId(id);
        return ok(repository.save(category));
    }

    public ResponseEntity<?> deleteCategory(Integer id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) throw new NotFoundException("Category not found");
        try {
            repository.delete(optionalCategory.get());
        } catch (Exception e) {
            throw new BadRequestException("Could not delete category");
        }
        return noContent().build();
    }

    //    ACTION METHOD
    public Category checkCategory(CategoryDTO dto) {
        Category category = new Category(null, dto.getName(), dto.isActive(), null);
        if (dto.getCategoryId() != null) {
            Optional<Category> parentCategory = repository.findById(dto.getCategoryId());
            if (!parentCategory.isPresent()) throw new NotFoundException("Parent category not found");
            category.setCategory(parentCategory.get());
        }
        return category;
    }
}
