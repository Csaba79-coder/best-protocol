package com.csaba79coder.bestprotocol.controller.exception;

import com.csaba79coder.bestprotocol.controller.value.ErrorCode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This class contains test methods for the {@link ControllerExceptionHandler} class.
 * It tests the exception handling logic for the {@link NoSuchElementException} and
 * {@link InputMismatchException} exceptions thrown in the controller.
 */
class ControllerExceptionHandlerTest {

    private static final String ERROR_CODE_001 = "ERROR_CODE_001";
    private static final String ERROR_CODE_002 = "ERROR_CODE_002";

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    /**
     * Test case for verifying that a {@link NoSuchElementException} exception thrown in the controller
     * is handled correctly by the {@link ControllerExceptionHandler}.
     *
     * <p>This test method verifies that the controller handler returns an HTTP NOT_FOUND response
     * and the error message is correctly included in the response body.</p>
     */
    @Test
    @DisplayName("handle Invalid Input Exception returns Bad Request Response")
    void handleNoSuchElementException_returnsNotFoundResponse() {
        // Given
        String message = "No such element found";
        NoSuchElementException ex = new NoSuchElementException(message);

        // When
        ResponseEntity<Object> response = handler.handleNoSuchElementException(ex);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Map.of(ERROR_CODE_001, message).toString(), response.getBody());
    }

    /**
     * Test case for verifying that an {@link InputMismatchException} exception thrown in the controller
     * is handled correctly by the {@link ControllerExceptionHandler}.
     *
     * <p>This test method verifies that the controller handler returns an HTTP BAD_REQUEST response
     * and the error message is correctly included in the response body.</p>
     */
    @Test
    void handleInvalidInputException_returnsBadRequestResponse() {
        // Given
        String message = "Invalid input";
        InputMismatchException ex = new InputMismatchException(message);

        // When
        ResponseEntity<Object> response = handler.handleInvalidInputException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Map.of(ERROR_CODE_002, message).toString(), response.getBody());
    }

    /**
     * Test case for verifying that the {@link ControllerExceptionHandler#responseBodyWithMessage}
     * method correctly creates the response body with the given error code and message.
     *
     * <p>This test method verifies that the response body created by the method matches the expected
     * response body for a given error code and message.</p>
     *
     * private method was set public meanwhile testing it, so it can be accessed from the test class
     * after that set back to private and the test is annotated with Disabled!
     */
    @Test
    @DisplayName("response Body With Message returns correct response body")
    @Disabled("This test is not currently working and needs to be fixed")
    public void testResponseBodyWithMessage() {
        // Test setup
        ControllerExceptionHandler handler = new ControllerExceptionHandler();
        ErrorCode errorCode = ErrorCode.ERROR_CODE_001;
        String errorMessage = "Test error message";

        // Method invocation
        String responseBody = handler.responseBodyWithMessage(errorCode, errorMessage);

        // Assertion
        assertThat(responseBody)
                .isEqualTo(Map.of(errorCode, errorMessage).toString());
    }
}