package io.github.taowang0622.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import io.github.taowang0622.dto.User;
import io.github.taowang0622.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sun.awt.windows.ThemeReader;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @ApiOperation("Return the status of the authentication")
    @GetMapping("/me")
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @ApiOperation("Create a new user")
    @PostMapping
    public User create(@ApiParam("A new user") @Valid @RequestBody User user, BindingResult result) {
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
    @ApiOperation("Retrieving all user info!!")
    public List<User> query(User user, @PageableDefault(page = 2, size = 17, sort = "username, asc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());

        //hardcoded users
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }


    @ApiOperation("Get the profile of the user specified by id")
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@ApiParam(value = "The user ID") @PathVariable int id) {
//        throw new UserNotExistException(id);
//        throw new RuntimeException("runtime exception");

        System.out.println("Entering getInfo API");
        User user = new User();
        user.setUserName("kevin");
        return user;
    }

    @ApiOperation("Delete the user specified by id(under construction)")
    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable int id) {
        System.out.println(id);
    }

    @ApiOperation("Update the profile of the user specified by id")
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


    //Ignore this psvm, just for fun
    public static void main(String[] args) throws InterruptedException {
        class MyRunnable implements Runnable {
//            private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
            Integer val = new Integer(0);

            @Override
            public void run() {
//                threadLocal.set((int) (Math.random() * 100));

                synchronized (val){
                    val = (int) (Math.random() * 100);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                System.out.println(threadLocal.get());
                    System.out.println(val);
                }


            }
        }

        MyRunnable myRunnable = new MyRunnable();

        Thread thread1 = new Thread(myRunnable);
        Thread thread2 = new Thread(myRunnable);

        thread1.start();
        thread2.start();

//        thread1.join();
//        thread2.join();
    }
}
