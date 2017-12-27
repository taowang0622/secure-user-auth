package io.github.taowang0622.web;


import com.fasterxml.jackson.annotation.JsonView;
import io.github.taowang0622.dto.User;
import io.github.taowang0622.exception.UserNotExistException;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable int id) {
        System.out.println(id);
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> {
//                FieldError fieldError = (FieldError) error;
//                String message = fieldError.getField() + " " + error.getDefaultMessage();
//                System.out.println(message);
                System.out.println(error.getDefaultMessage());
            });
        }

        System.out.println(user.getUserName());
        System.out.println(user.getPassword());

        user.setId(1);
        return user;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            result.getAllErrors().forEach(System.out::println);
        }

        System.out.println(user.getUserName());
        System.out.println(user.getPassword());

        user.setId(1);
        return user;
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(User user, @PageableDefault(page = 2, size = 17, sort = "username, asc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }


    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable int id) {
        throw new UserNotExistException(id);
//        User user = new User();
//        user.setUserName("kevin");
//        return user;
    }
}
