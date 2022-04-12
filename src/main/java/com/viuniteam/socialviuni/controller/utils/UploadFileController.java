package com.viuniteam.socialviuni.controller.utils;
import com.viuniteam.socialviuni.dto.response.image.ImageResponse;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@RestController
@AllArgsConstructor
public class UploadFileController {

    private final UploadFileService uploadFileService;

    @PostMapping("/upload")
    public List<ImageResponse> upload(@RequestParam(value = "files", required = false) MultipartFile[] files) throws ExecutionException, InterruptedException {
        if(files.length <= 10){
            for(MultipartFile file : files){// check format and size file
                String formatFile = FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
                if(!formatFile.equals("jpg") && !formatFile.equals("png") && !formatFile.equals("jpeg"))
                    throw new BadRequestException("Định dạng file phải là JPG,JPEG hoặc PNG");
                if(file.getSize()>5242880)
                    throw new BadRequestException("Kích thước file không được vượt quá 5MB");
            }
            List<ImageResponse> imageResponses = new ArrayList<>();
            List<Future<ImageResponse>> futureList = new ArrayList<>();
            ExecutorService executorService  = Executors.newFixedThreadPool(10);
            for(MultipartFile file : files){
                futureList.add(executorService.submit(new ProcessMultipartFile(file,uploadFileService)));
            }
            for (Future future :futureList)
            {
                imageResponses.add((ImageResponse) future.get());
            }
            return imageResponses;
        }
        throw new BadRequestException("Upload tối đa 10 ảnh");
    }
//        public static void main(String[] args) {
//            byte[] fileContent = new byte[0];
//            try {
//                fileContent = FileUtils.readFileToByteArray(new File("D:/avt.jpg"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String encodedString = Base64.getEncoder().encodeToString(fileContent);
//            System.out.println(encodedString);
//        }
}
