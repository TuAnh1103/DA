package com.viuniteam.socialviuni.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class WebAppUploadFileConfig {
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "viuni",
                "api_key","859339928167792",
                "api_secret","kymzmnSE8RlImfbEkaL3P5OMsVs",
                "secure",true
        ));
        return cloudinary;
    }
}
