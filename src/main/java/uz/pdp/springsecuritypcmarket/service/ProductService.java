package uz.pdp.springsecuritypcmarket.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.springsecuritypcmarket.entity.*;
import uz.pdp.springsecuritypcmarket.exception.NotFoundException;
import uz.pdp.springsecuritypcmarket.payload.ProductDTO;
import uz.pdp.springsecuritypcmarket.repository.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;
    private final CurrencyRepository currencyRepository;
    private final MeasurementRepository measurementRepository;

    public ProductService(ProductRepository repository, AttachmentRepository attachmentRepository, CategoryRepository categoryRepository, CurrencyRepository currencyRepository, MeasurementRepository measurementRepository) {
        this.repository = repository;
        this.attachmentRepository = attachmentRepository;
        this.categoryRepository = categoryRepository;
        this.currencyRepository = currencyRepository;
        this.measurementRepository = measurementRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) throw new NotFoundException("Product not found");
        return ok(optionalProduct.get());
    }

    public ResponseEntity<?> addProduct(ProductDTO dto) {
        Product product = checkProduct(dto, new Product());
        return status(HttpStatus.CREATED).body(repository.save(product));
    }

    public ResponseEntity<?> editProduct(Integer id, ProductDTO dto) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) throw new NotFoundException("Product not found");
        Product product = checkProduct(dto, optionalProduct.get());
        return ok(repository.save(product));
    }

    //    ACTION FOR POST AND PUT
    private Product checkProduct(ProductDTO dto, Product product) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (!optionalCategory.isPresent()) throw new NotFoundException("Category not found");
        Optional<Currency> optionalCurrency = currencyRepository.findById(dto.getCurrencyId());
        if (!optionalCurrency.isPresent()) throw new NotFoundException("Currency not found");
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) throw new NotFoundException("Measurement not found");
        Set<Attachment> attachments = new HashSet<>();
        for (Integer photoId : dto.getPhotosId()) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(photoId);
            optionalAttachment.ifPresent(attachments::add);
        }
        if (attachments.size() == 0) throw new NotFoundException("No attachments available.");
        product.setCategory(optionalCategory.get());
        product.setAttachment(attachments);
        product.setCurrency(optionalCurrency.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setName(dto.getName());
        product.setActive(dto.isActive());
        product.setAmount(dto.getAmount());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        return product;
    }

    public ResponseEntity<?> deleteProduct(Integer id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) throw new NotFoundException("Product Not Found");
        repository.delete(optionalProduct.get());
        return noContent().build();
    }
}
