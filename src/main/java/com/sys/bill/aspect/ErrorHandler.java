package com.sys.bill.aspect;

import com.sys.bill.common.exception.*;
import com.sys.bill.common.response.ApiErrorResponse;
import com.sys.bill.messages.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController的异常/错误处理器
 *
 * @author zooqi@senthink.com
 * @date 2018/07/26
 */
@RestControllerAdvice(basePackageClasses = {
        com.sys.bill.api.controller.PackageInfo.class
})
public class ErrorHandler {

    @Autowired
    private CodeMsg codeMsg;

    /**
     * 处理{@link BizException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleBizException(BizException e) {
        ApiErrorResponse<String> response = new ApiErrorResponse<>(e.getCode(), e.getMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * 处理{@link BizFailureException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizFailureException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleBizFailureException(BizFailureException e) {
        ApiErrorResponse<String> response =
                new ApiErrorResponse<>(codeMsg.failureCode(), codeMsg.failureMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * 处理{@link BizIllegalArgumentException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizIllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleBizIllegalArgumentException(BizIllegalArgumentException e) {
        ApiErrorResponse<String> response =
                new ApiErrorResponse<>(codeMsg.illegalArgumentCode(), codeMsg.illegalArgumentMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * 处理{@link IllegalArgumentException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiErrorResponse<String> response =
                new ApiErrorResponse<>(codeMsg.illegalArgumentCode(), codeMsg.illegalArgumentMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * 处理{@link DataAccessException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleDataAccessException(DataAccessException e) {
        ApiErrorResponse<String> response =
                new ApiErrorResponse<>(codeMsg.dataAccessExceptionCode(), codeMsg.dataAccessExceptionMsg(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * 处理{@link BindException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ApiErrorResponse<List<String>>> handleBindException(BindException e) {
        List<FieldError> errors = e.getFieldErrors();
        List<String> fields = errors
                .stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrorResponse<List<String>> response =
                new ApiErrorResponse<>(codeMsg.bindExceptionCode(), codeMsg.bindExceptionMsg(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * 处理{@link MethodArgumentNotValidException}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        String fields = errors
                .stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage() + ",")
                .reduce("", String::concat);
        ApiErrorResponse<String> response =
                new ApiErrorResponse<>(codeMsg.methodArgumentCode(), codeMsg.methodArgumentMsg(), fields);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }

    /**
     * AuthenticationException异常处理
     *
     * @param e ${@link AuthenticationException}
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        ApiErrorResponse<String> response = new ApiErrorResponse<>(codeMsg.authenticationExceptionCode(),
                codeMsg.authenticationExceptionMsg(),
                e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(response);
    }
}
