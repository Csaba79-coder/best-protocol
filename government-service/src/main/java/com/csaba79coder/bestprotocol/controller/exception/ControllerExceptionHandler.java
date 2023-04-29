package com.csaba79coder.bestprotocol.controller.exception;

import com.csaba79coder.bestprotocol.controller.value.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.csaba79coder.bestprotocol.controller.value.ErrorCode.ERROR_CODE_001;
import static com.csaba79coder.bestprotocol.controller.value.ErrorCode.ERROR_CODE_002;

/**

 Global exception handler for controller layer.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**

     Handles NoSuchElementException and returns an HTTP NOT_FOUND response.
     @param ex The NoSuchElementException instance.
     @return The ResponseEntity with the error message and status code.
     */
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_001, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**

     Handles InputMismatchException and returns an HTTP BAD_REQUEST response.
     @param ex The InputMismatchException instance.
     @return The ResponseEntity with the error message and status code.
     */
    @ExceptionHandler(value = {InputMismatchException.class})
    public ResponseEntity<Object> handleInvalidInputException(InputMismatchException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE_002, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**

     Creates a response body with the specified error code and message.
     @param code The error code.
     @param message The error message.
     @return The response body as a string.
     */
    private String responseBodyWithMessage(ErrorCode code, String message) {
        return Map.of(code, message).toString();
    }
}
