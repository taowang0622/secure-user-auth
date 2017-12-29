package io.github.taowang0622.exception;

import io.github.taowang0622.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotExistException extends RuntimeException {
    private int id;

    public UserNotExistException(int id) {
        super("User not exist!!");
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
