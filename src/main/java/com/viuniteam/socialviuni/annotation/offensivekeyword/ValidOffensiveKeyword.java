package com.viuniteam.socialviuni.annotation.offensivekeyword;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OffensiveKeywordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOffensiveKeyword {
    String message() default "Không được chứa từ ngữ thô tục";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
