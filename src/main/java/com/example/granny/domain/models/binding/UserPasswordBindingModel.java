package com.example.granny.domain.models.binding;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.validation.annotations.ValidPassword;

import javax.validation.constraints.NotNull;

public class UserPasswordBindingModel {

    private String oldPassword;
    private String password;
    private String confirmPassword;

    public UserPasswordBindingModel() {
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @ValidPassword
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = GlobalConstants.FIELD_IS_REQUIRED)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
