package uz.pdp.springsecuritypcmarket.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.springsecuritypcmarket.entity.Attachment;
import uz.pdp.springsecuritypcmarket.entity.AttachmentContent;
import uz.pdp.springsecuritypcmarket.exception.BadRequestException;
import uz.pdp.springsecuritypcmarket.exception.NotFoundException;
import uz.pdp.springsecuritypcmarket.payload.response.AttachmentResponse;
import uz.pdp.springsecuritypcmarket.repository.AttachmentContentRepository;
import uz.pdp.springsecuritypcmarket.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@Service
public class AttachmentService {
    private final AttachmentRepository repository;
    private final AttachmentContentRepository contentRepository;

    @Value(value = "${attachment.download.url}")
    private String baseUrl;

    @Autowired
    public AttachmentService(AttachmentRepository repository, AttachmentContentRepository contentRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 20);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Attachment> optionalAttachment = repository.findById(id);
        if (!optionalAttachment.isPresent()) throw new NotFoundException("Attachment Not Found");
        return ok(optionalAttachment.get());
    }

    public ResponseEntity<?> saveFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file == null) throw new NotFoundException("File empty");
        Attachment attachment = new Attachment(null, file.getOriginalFilename(), file.getContentType(), file.getSize());
        attachment = repository.save(attachment);
        try {
            AttachmentContent content = new AttachmentContent(null, file.getBytes(), attachment);
            contentRepository.save(content);
        } catch (IOException e) {
            repository.delete(attachment);
            throw new BadRequestException("There was a problem writing the file");
        }
        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(baseUrl).path(attachment.getId().toString()).toUriString();
        return ok(new AttachmentResponse(file.getOriginalFilename(), file.getContentType(), file.getSize(), downloadUrl));
    }

    @SneakyThrows
    public ResponseEntity<?> downloadFile(Integer id, HttpServletResponse response) {
        Optional<Attachment> optional = repository.findById(id);
        if (!optional.isPresent()) throw new NotFoundException("Attachment not found");
        Attachment attachment = optional.get();
        Optional<AttachmentContent> optionalContent = contentRepository.findByAttachmentId(id);
        if (!optionalContent.isPresent()) throw new NotFoundException("Attachment content not found");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"");
        response.setContentType(attachment.getContentType());
        FileCopyUtils.copy(optionalContent.get().getBytes(), response.getOutputStream());
        return ok().build();
    }

    public ResponseEntity<?> deleteFile(Integer id) {
        Optional<Attachment> optionalAttachment = repository.findById(id);
        if (!optionalAttachment.isPresent()) throw new NotFoundException("Attachment not found");
        Optional<AttachmentContent> contentOptional = contentRepository.findByAttachmentId(id);
        contentOptional.ifPresent(contentRepository::delete);
        repository.delete(optionalAttachment.get());
        return noContent().build();
    }
}
