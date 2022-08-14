package uz.pdp.springsecuritypcmarket.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentResponse {
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String downloadUrl;
}
