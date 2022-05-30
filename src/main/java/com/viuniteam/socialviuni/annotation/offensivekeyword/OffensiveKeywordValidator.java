package com.viuniteam.socialviuni.annotation.offensivekeyword;

import com.viuniteam.socialviuni.service.OffensiveKeywordService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OffensiveKeywordValidator implements ConstraintValidator<ValidOffensiveKeyword,String> {
    @Autowired
    private OffensiveKeywordService offensiveKeywordService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value==null)
            return true;
        return !offensiveKeywordService.isExist(value.toLowerCase());
    }
}
