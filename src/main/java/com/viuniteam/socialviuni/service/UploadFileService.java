package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    ResponseEntity<?> getLinkUploadListFile(MultipartFile[] file);
    ImageResponse getLinkUploadFile(MultipartFile file);
}
