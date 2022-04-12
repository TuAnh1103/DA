package com.viuniteam.socialviuni.controller.utils;

import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.service.UploadFileService;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Callable;

public class ProcessMultipartFile implements Callable<ImageResponse> {

    private UploadFileService uploadFileService;

    private MultipartFile multipartFile;

    public ProcessMultipartFile(MultipartFile multipartFile,UploadFileService uploadFileService){
        this.multipartFile = multipartFile;
        this.uploadFileService = uploadFileService;
    }
    @Override
    public ImageResponse call() throws Exception {
        return uploadFileService.getLinkUploadFile(multipartFile);
    }
}
