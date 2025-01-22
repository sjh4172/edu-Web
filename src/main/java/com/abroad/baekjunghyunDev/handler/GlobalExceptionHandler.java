package com.abroad.baekjunghyunDev.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.abroad.baekjunghyunDev.dto.ResponseDto;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 400 Bad Request 상태 코드와 메시지 반환
        ResponseDto<String> response = new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleGeneralException(Exception ex) {
        // 500 Internal Server Error 상태 코드와 메시지 반환
        ResponseDto<String> response = new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    

//  @ExceptionHandler(value = Exception.class)
//  public ResponseDto<String> handleGeneralException(Exception e) {
//      return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//  }
    
}
