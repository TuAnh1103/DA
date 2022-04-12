package com.viuniteam.socialviuni.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.service.ImageService;
import com.viuniteam.socialviuni.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Service
@AllArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final Cloudinary cloudinary;
    private final ImageService imageService;
    private final ImageReponseMapper imageReponseMapper;
    @Override
    public ResponseEntity<?> getLinkUploadListFile(MultipartFile[] files) {
        if(files.length <= 10){
            List<ImageResponse> imageResponses = new ArrayList<>();
            for(MultipartFile file : files){// check format and size file
                String formatFile = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
                if(!formatFile.equals("jpg") && !formatFile.equals("png") && !formatFile.equals("jpeg"))
                    throw new BadRequestException("Định dạng file phải là JPG hoặc PNG");
                if(file.getSize()>5242880)
                    throw new BadRequestException("Kích thước file không được vượt quá 5MB");
            }
            for(MultipartFile file : files){
                ImageResponse imageResponse = getImageResponse(file);
                if(imageResponse!=null)
                    imageResponses.add(imageResponse);
            }
            return ResponseEntity.ok(imageResponses);
        }
        throw new BadRequestException("Upload tối đa 10 ảnh");
    }

    @Override
    public ImageResponse getLinkUploadFile(MultipartFile file) {
        return getImageResponse(file);
    }

    public ImageResponse getImageResponse(MultipartFile file){
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
            String link = (String) result.get("secure_url");
            Image image = new Image();
            image.setLinkImage(link);
            return imageReponseMapper.from(imageService.save(image));
        } catch (IOException e) {
            return null;
        }
    }
}
