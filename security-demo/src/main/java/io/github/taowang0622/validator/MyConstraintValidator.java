package io.github.taowang0622.validator;

import io.github.taowang0622.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
Spring detects a class that implements ConstraintValidator<A, T>, then it will make it a Spring bean.
As a result, we can *autowire* any other beans inside!!
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {
    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("My Constraint Validator Init!!");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        helloService.greet();
        System.out.println(o);
        return false;
    }
}
