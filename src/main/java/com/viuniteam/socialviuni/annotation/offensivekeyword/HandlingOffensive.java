package com.viuniteam.socialviuni.annotation.offensivekeyword;

import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.service.OffensiveKeywordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@AllArgsConstructor
public class HandlingOffensive {
    private final OffensiveKeywordService offensiveKeywordService;
    public void handling(Object object){
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            ValidOffensive validOffensive = field.getAnnotation(ValidOffensive.class);
            if(validOffensive!=null){
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if(offensiveKeywordService.isExist(value.toLowerCase()))
                        throw new BadRequestException(validOffensive.name()+" không được chứa từ ngữ thô tục");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
