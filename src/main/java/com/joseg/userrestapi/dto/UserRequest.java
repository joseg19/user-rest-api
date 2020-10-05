package com.joseg.userrestapi.dto;

import com.joseg.userrestapi.entity.Phone;

import javax.validation.constraints.*;
import java.util.List;

public class UserRequest {

    @NotEmpty
    private String name;
    @Pattern(regexp="^[A-Za-z0-9]{1}[A-Za-z0-9+_.-]+@[A-Za-z0-9]{2,}.[A-Za-z0-9]{2,}$", message="{user.request.email}")
    private String email;
    @Pattern(regexp = "^(?=.*?\\d.*\\d)(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{4,20}$", message = "{user.request.password}")
    private String password;

    private List<Phone> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "UserRequest {" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "*****" + '\'' +
                ", phones=" + phones +
                '}';
    }
}
