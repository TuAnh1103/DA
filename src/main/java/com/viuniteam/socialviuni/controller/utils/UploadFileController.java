package com.viuniteam.socialviuni.controller.utils;
import com.viuniteam.socialviuni.service.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
public class UploadFileController {

    private final UploadFileService uploadFileService;


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(value = "file",required = false) MultipartFile[] file){
        return uploadFileService.getLinkUploadListFile(file);
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
