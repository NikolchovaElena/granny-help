package com.example.granny.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.constants.RootAdminData;
import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.Role;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.UserEditBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.RoleServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.*;
import com.example.granny.validation.api.UserValidationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    static final UsernameNotFoundException EMAIL_NOT_FOUND_EXCEPTION =
            new UsernameNotFoundException("Email not found");
    static final IllegalArgumentException NO_USER_WITH_THAT_EXCEPTION =
            new IllegalArgumentException("User could not be found");
    static final IllegalArgumentException INCORRECT_PASSWORD =
            new IllegalArgumentException("Incorrect password");

    private final UserRepository userRepository;
    private final VerificationTokenService tokenService;
    private final CauseService causeService;
    private final RoleService roleService;
    private final UserValidationService userValidation;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerificationTokenService tokenService,
                           CauseService causeService, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserValidationService userValidationService, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.causeService = causeService;
        this.roleService = roleService;
        this.cloudinaryService = cloudinaryService;
        this.userValidation = userValidationService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.createRootAdmin();
    }

    private void createRootAdmin() {
        if (this.userRepository.count() == 0) {
            User root = new User();
            root.setFirstName(RootAdminData.ROOT_FIRST_NAME);
            root.setLastName(RootAdminData.ROOT_LAST_NAME);
            root.setEmail(RootAdminData.ROOT_EMAIL);
            root.setPassword(this.bCryptPasswordEncoder.encode(RootAdminData.ROOT_ACCOUNT_PASSWORD));
            root.setEnabled(true);
            assignRolesToUser(root);

            try {
                this.userRepository.saveAndFlush(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void assignRolesToUser(User user) {
        if (this.userRepository.count() == 0) {
            this.roleService.findAllRoles()
                    .forEach(r -> user.getAuthorities()
                            .add(this.modelMapper.map(r, Role.class)));

        } else {
            RoleServiceModel role = this.roleService.findByAuthority(GlobalConstants.ROLE_USER);
            user.getAuthorities().add(this.modelMapper.map(role, Role.class));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> EMAIL_NOT_FOUND_EXCEPTION);
        return user;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();

//        if (!userValidation.isValid(userServiceModel)) {
//            throw new IllegalArgumentException();
//        }
        userServiceModel.setAuthorities(new LinkedHashSet<>());
        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return this.userRepository.save(user) != null;
    }

    @Override
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .filter(u -> !u.getEmail().equals(RootAdminData.ROOT_EMAIL))
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect id!"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        switch (role) {
            case "user":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                break;
            case "admin":
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
                userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
                break;
        }

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.countByEmail(email) > 0;
    }

    @Override
    public void followCause(UserServiceModel model, Integer id) {
        User user = modelMapper.map(model, User.class);
        if (user.getPins().size() == 0) {
            user.setPins(new ArrayList<>());
        }

        Cause cause = modelMapper.map(causeService.findById(id), Cause.class);
        user.getPins().add(cause);
        userRepository.save(user);
    }

    @Override
    public List<CauseServiceModel> findAllPinned(Integer id) {
        User user = modelMapper.map(findUserById(id), User.class);

        return user.getPins().stream()
                .map(c -> modelMapper.map(c, CauseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserById(Integer id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findFourRandomUsers() {
        return userRepository.findFiveRandomUsers()
                .stream()
                .filter(u -> !u.getEmail().equals(RootAdminData.ROOT_EMAIL))
                .limit(4)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));

        if (ifNotRoot(user)) {
            tokenService.delete(user);
            userRepository.delete(user);
        }
    }

    @Override
    public void delete(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));

        if (ifNotRoot(user)) {
            tokenService.delete(user);
            userRepository.delete(user);
        }
    }

    @Override
    public UserServiceModel edit(String email, String newPassword, String oldPassword) {
        User user = this.modelMapper.map(findUserByEmail(email), User.class);

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(INCORRECT_PASSWORD);
        }
        user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
        enableUser(user);
        this.userRepository.save(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel edit(String email, UserEditBindingModel model) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));

        if (!user.getImageUrl().equals(GlobalConstants.PROFILE_DEFAULT_IMG)) {
            cloudinaryService.deleteImage(email);
        }
        MultipartFile file = model.getImageUrl();
        String profilePicture = file.isEmpty() ? user.getImageUrl() :
                cloudinaryService.uploadImage(model.getImageUrl(), email);

        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setImageUrl(profilePicture);
        user.setAbout(model.getAbout());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    private boolean ifNotRoot(User user) {
        return !user.getEmail().equals(RootAdminData.ROOT_EMAIL);
    }
}
