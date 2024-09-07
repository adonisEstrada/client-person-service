package com.adonis.nttdata.exeption;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationException extends ChallengeException {
    public ValidationException() {
        super(HttpStatus.PRECONDITION_FAILED, null);
    }

    public ValidationException(String exString) {
        super(HttpStatus.PRECONDITION_FAILED, exString);
    }

    public ValidationException(String exString, Set<ConstraintViolation<Object>> constraintViolations) {
        super(HttpStatus.PRECONDITION_FAILED, createMessage(exString, constraintViolations));
    }

    private static String createMessage(String exString, Set<ConstraintViolation<Object>> constraintViolations) {
        if (constraintViolations == null)
            return "";

        return String.format("Datos incorrectos: %s",
                constraintViolations.stream()
                        .map(ValidationException::buildFieldFailed)
                        .collect(Collectors.joining(", "))
        );
    }

    private static String buildFieldFailed(ConstraintViolation constraintViolation) {
        return String.format("Campo= %s, Mensaje= %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
    }

}