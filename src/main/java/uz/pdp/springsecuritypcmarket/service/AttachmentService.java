package uz.pdp.springsecuritypcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.springsecuritypcmarket.repository.AttachmentContentRepository;
import uz.pdp.springsecuritypcmarket.repository.AttachmentRepository;

@Service
public class AttachmentService {
    private final AttachmentRepository repository;
    private final AttachmentContentRepository contentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository repository, AttachmentContentRepository contentRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
    }
}
