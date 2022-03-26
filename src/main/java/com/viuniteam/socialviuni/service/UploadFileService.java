package com.viuniteam.socialviuni.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    ResponseEntity<?> getLinkUploadListFile(MultipartFile[] file);
}
