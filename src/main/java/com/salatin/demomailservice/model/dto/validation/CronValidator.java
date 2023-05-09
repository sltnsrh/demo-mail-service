package com.salatin.demomailservice.model.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.support.CronExpression;

public class CronValidator implements ConstraintValidator<ValidCron, String> {

    @Override
    public void initialize(ValidCron constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cron, ConstraintValidatorContext context) {

        return CronExpression.isValidExpression(cron);
    }
}
