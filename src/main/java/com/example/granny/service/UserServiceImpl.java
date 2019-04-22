package com.example.granny.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.constants.RootAdminData;
import com.example.granny.domain.entities.*;
import com.example.granny.domain.models.binding.AddressBindingModel;
import com.example.granny.domain.models.binding.UserEditBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.RoleServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import com.example.granny.error.LinkHasExpired;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenService tokenService;
    private final RoleService roleService;
    private final AddressDetailsService addressDetailsService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerificationTokenService tokenService, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder,
                           AddressDetailsService addressDetailsService, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.addressDetailsService = addressDetailsService;
        this.cloudinaryService = cloudinaryService;
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
                .orElseThrow(() -> GlobalConstants.EMAIL_NOT_FOUND_EXCEPTION);
        return user;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();

        userServiceModel.setAuthorities(new LinkedHashSet<>());
        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setBillingDetails(this.addressDetailsService.addNew());

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
    public void setUserRole(Integer id, String role) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> GlobalConstants.NO_USER_WITH_THAT_EXCEPTION);

        user.getAuthorities().clear();

        switch (role) {
            case "user":
                user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("ROLE_USER"), Role.class));
                break;
            case "moderator":
                user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("ROLE_USER"), Role.class));
                user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("ROLE_MODERATOR"), Role.class));
                break;
            case "admin":
                user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("ROLE_USER"), Role.class));
                user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("ROLE_MODERATOR"), Role.class));
                user.getAuthorities().add(modelMapper.map(roleService.findByAuthority("ROLE_ADMIN"), Role.class));
                break;
        }
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.countByEmail(email) > 0;
    }

    @Override
    public boolean isFollowing(String email, Integer causeId) {
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(GlobalConstants.NO_USER_WITH_THAT_EXCEPTION));
        boolean isMatch = false;

        for (Cause c : user.getPins()) {
            if (c.getId() == causeId) {
                isMatch = true;
                break;
            }
        }
        return isMatch;
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
                () -> new IllegalArgumentException(GlobalConstants.NO_USER_WITH_THAT_EXCEPTION));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserById(Integer id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(GlobalConstants.NO_USER_WITH_THAT_EXCEPTION));
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
                () -> new IllegalArgumentException(GlobalConstants.NO_USER_WITH_THAT_EXCEPTION));

        if (ifNotRoot(user)) {
            tokenService.delete(user);
            userRepository.delete(user);
        }
    }

    @Override
    public void delete(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(GlobalConstants.NO_USER_WITH_THAT_EXCEPTION));

        if (ifNotRoot(user)) {
            tokenService.delete(user);
            userRepository.delete(user);
        }
    }

    @Override
    public UserServiceModel edit(String email, String newPassword, String oldPassword) {
        User user = this.modelMapper.map(findUserByEmail(email), User.class);

        if (!this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException(GlobalConstants.INCORRECT_PASSWORD);
        }
        user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
        enableUser(user);
        this.userRepository.save(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel edit(String email, UserEditBindingModel model) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(GlobalConstants.NO_USER_WITH_THAT_EXCEPTION));
        MultipartFile file = model.getImageUrl();

        if (!user.getImageUrl().equals(GlobalConstants.PROFILE_DEFAULT_IMG)) {
            cloudinaryService.deleteImage(email);
        }

        String image = file.isEmpty() ? user.getImageUrl() :
                cloudinaryService.uploadImage(file, email);

        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setImageUrl(image);
        user.setAbout(model.getAbout());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public void editAddress(String email, AddressBindingModel model) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> GlobalConstants.NO_USER_WITH_THAT_EXCEPTION);
        AddressDetails address = user.getBillingDetails();

        this.addressDetailsService.edit(address, model);
    }

    private boolean ifNotRoot(User user) {
        return !user.getEmail().equals(RootAdminData.ROOT_EMAIL);
    }

    @Override
    public void confirmAccount(String token) {
        VerificationToken verificationToken = tokenService.findBy(token);

        if (verificationToken == null) {
            throw new LinkHasExpired("The link is invalid or broken!");
        }

        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
            throw new LinkHasExpired("The link has expired!");
        }

        enableUser(verificationToken.getUser());
    }
}
