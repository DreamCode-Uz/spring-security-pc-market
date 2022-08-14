package uz.pdp.springsecuritypcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.springsecuritypcmarket.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    private final AttachmentService service;

    @Autowired
    public AttachmentController(AttachmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", defaultValue = "20") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{attachmentId}")
    public ResponseEntity<?> getOne(@PathVariable("attachmentId") Integer id) {
        return service.getOne(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> save(MultipartHttpServletRequest request) {
        return service.saveFile(request);
    }

    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<?> update(@PathVariable("attachmentId") Integer id, HttpServletResponse response) {
        return service.downloadFile(id, response);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<?> delete(@PathVariable("attachmentId") Integer id) {
        return service.deleteFile(id);
    }
}
