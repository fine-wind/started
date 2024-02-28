package com.example.common.v0.validator;

import com.example.common.v0.exception.ServerException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Locale;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single/
 *
 * @since 1.0.0
 */
public class ValidatorUtils {

    private static ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setBasenames("i18n/validation");
        return bundleMessageSource;
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static void validateEntity(Object object, Class<?>... groups) throws ServerException {
        Locale.setDefault(LocaleContextHolder.getLocale());
        Validator validator = Validation.byDefaultProvider().configure().messageInterpolator(
                        null // todo new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(getMessageSource()))
                )
                .buildValidatorFactory().getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new ServerException(constraint.getMessage());
        }
    }
}
