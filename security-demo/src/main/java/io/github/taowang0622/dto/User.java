package io.github.taowang0622.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.taowang0622.validator.MyConstraint;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class User {
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    @JsonView(UserSimpleView.class)
    @MyConstraint(message = "This is a test for customized validator!!")
    @ApiModelProperty(value = "The name of the user")
    private String userName;
    @JsonView(UserDetailView.class)
//    @NotNull
    @NotBlank(message = "Password cannot be blank!!")
    @ApiModelProperty(value = "The password of the user")
    private String password;
    private int id;
    @JsonView(UserSimpleView.class)
    @Past(message = "Birthday must be a past date!!")
    private Date birthday;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
