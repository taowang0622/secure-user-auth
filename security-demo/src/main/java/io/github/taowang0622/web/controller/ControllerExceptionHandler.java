package io.github.taowang0622.web.controller;

import io.github.taowang0622.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
//@ControllerAdvice---->@Component
//@RestController----->@Controller----->@Component
//@Service----->@Component
//@Repository----->@Component
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    //if @ResponseStatus not set, the status code would be 200!!
    public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", ex.getId());
        map.put("message", ex.getMessage());
        return map;
    }

}
