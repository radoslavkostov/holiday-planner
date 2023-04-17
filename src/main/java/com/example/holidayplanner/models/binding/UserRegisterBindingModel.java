package com.example.holidayplanner.models.binding;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserRegisterBindingModel {

    @Size(min = 1, max = 20, message = "First name should be between 1 and 20 characters.")
    private String firstName;

    @Size(min = 1, max = 20, message = "Last name should be between 1 and 20 characters.")
    private String lastName;

    @Size(min = 1, max = 20, message = "Password should be at least 8 characters.")
    private String password;

    private String confirmPassword;

    @Email(message = "Not a valid email format.")
    @Size(min = 3, max = 20, message = "Not a valid email format.")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

}
