package com.springboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Aspect
@Component
public class CategoryAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryAspect.class);
    private static final String ERROR_MESSAGE = "File too Large.";

    private static final long FILE_SIZE = 104857L;

    @Around("execution(* com.springboot.controller.CategoryController.saveCategory(..))")
    public Object checkValidation(ProceedingJoinPoint joinPoint) {
        MultipartFile file = (MultipartFile) joinPoint.getArgs()[1];
        Object result = null;

        String extension = file.getOriginalFilename().split("\\.")[1];
        if (file.getSize() < FILE_SIZE) {
            if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
                LOGGER.info("Valid File extension");
                try {
                    result = joinPoint.proceed();
                    LOGGER.info(result.toString());
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                }
            } else {
                LOGGER.warn("File extension are not Valid");
                result = "File extension are not Valid";
            }
        } else {
            LOGGER.warn(ERROR_MESSAGE);
            result = ERROR_MESSAGE;
        }


        return result;
    }
}
