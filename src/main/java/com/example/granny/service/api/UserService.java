package com.example.granny.service.api;

import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.binding.UserEditBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    boolean register(UserServiceModel model);

    UserServiceModel edit(String email, String newPassword, String oldPassword);

    UserServiceModel edit(String email, UserEditBindingModel model) throws IOException;

    void delete(Integer id);

    void delete(String email);

    void enableUser(User user);

    List<UserServiceModel> findAllUsers();

    List<CauseServiceModel> findAllPinned(Integer id);

    void setUserRole(Integer id, String role);

    boolean emailExists(String email);

    UserServiceModel findUserByEmail(String email);

    UserServiceModel findUserById(Integer id);

    List<UserServiceModel> findFourRandomUsers();

    boolean isFollowing(String email, Integer id);

    void editAddress(String email, AddressBindingModel model);

    void confirmAccount(String token);
}
