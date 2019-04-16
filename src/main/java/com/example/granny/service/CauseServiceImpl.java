package com.example.granny.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.CauseFormBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.error.CauseNotFoundException;
import com.example.granny.error.UserNotFoundException;
import com.example.granny.repository.CauseRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.CloudinaryService;
import com.example.granny.service.api.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.granny.service.UserServiceImpl.NO_USER_WITH_THAT_EXCEPTION;

@Service
public class CauseServiceImpl implements CauseService {
    static final CauseNotFoundException CAUSE_NOT_FOUND =
            new CauseNotFoundException("The cause you requested could not be found");
    static final String COULD_NOT_DELETE_CAUSE = "You are not authorized to delete this cause";
    static final String CAUSE_IMG_ID = "CAUSE";

    private final CauseRepository causeRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CauseServiceImpl(CauseRepository causeRepository, UserRepository userRepository, CommentService commentService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.causeRepository = causeRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CauseServiceModel submit(CauseFormBindingModel model, String email) throws IOException {
        User author = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));

        Cause cause = modelMapper.map(model, Cause.class);
        cause.setAuthor(author);
        String image = GlobalConstants.DEFAULT_IMG;

        MultipartFile file = model.getImageUrl();
        if (!file.isEmpty()) {
            image = cloudinaryService.uploadImage(file, CAUSE_IMG_ID + author.getEmail());
        }
        cause.setImageUrl(image);
        return modelMapper.map(causeRepository.saveAndFlush(cause), CauseServiceModel.class);
    }

    @Override
    public CauseServiceModel edit(CauseFormBindingModel model, Integer causeId) throws IOException {
        Cause cause = causeRepository.findById(causeId).orElseThrow(() -> CAUSE_NOT_FOUND);
        MultipartFile file = model.getImageUrl();

        if (!file.isEmpty()) {
            if (!cause.getImageUrl().equals(GlobalConstants.DEFAULT_IMG)) {
                cloudinaryService.deleteImage(CAUSE_IMG_ID + cause.getAuthor().getEmail());
            }
            String image = cloudinaryService.uploadImage(file, CAUSE_IMG_ID + cause.getAuthor().getEmail());
            cause.setImageUrl(image);
        }

        cause.setTitle(model.getTitle());
        cause.setDescription(model.getDescription());
        cause.setLocation(model.getLocation());

        return modelMapper.map(causeRepository.saveAndFlush(cause), CauseServiceModel.class);
    }

    @Override
    public void approve(Integer causeId) {
        Cause cause = causeRepository.findById(causeId).orElseThrow(() -> CAUSE_NOT_FOUND);
        cause.setApproved(true);
        causeRepository.saveAndFlush(cause);
    }

    @Override
    public void delete(Integer id, String email, Authentication authentication) {
        Cause cause = causeRepository.findById(id).orElseThrow(() -> CAUSE_NOT_FOUND);

        if ((cause.getAuthor().getEmail().equals(email)) &&
                (hasAuthority(authentication, GlobalConstants.ROLE_MODERATOR))) {
            throw new AccessDeniedException(COULD_NOT_DELETE_CAUSE);
        }
        cause.getFollowers().clear();
        commentService.deleteAll(id);
        causeRepository.delete(cause);
    }

    @Override
    public List<CauseServiceModel> findAll() {

        List<Cause> causes = causeRepository.findAll();
        return causes.stream()
                .map(c -> modelMapper.map(c, CauseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CauseServiceModel> findAllApproved(Integer id) {
        List<Cause> causes = causeRepository.findAllApprovedByAuthorId(id);
        return causes.stream()
                .map(c -> modelMapper.map(c, CauseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CauseServiceModel> findAllPending(Integer id, Authentication authentication) {
        List<Cause> causes;

        if (hasAuthority(authentication, GlobalConstants.ROLE_MODERATOR)) {
            causes = causeRepository.findAllPending();
        } else {
            causes = causeRepository.findAllPendingByAuthorId(id);
        }
        return causes.stream()
                .map(c -> modelMapper.map(c, CauseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseServiceModel findById(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow(() -> CAUSE_NOT_FOUND);
        return modelMapper.map(cause, CauseServiceModel.class);
    }

    @Override
    public void followCause(String email, Integer causeId) {
        Cause cause = causeRepository.findById(causeId).orElseThrow(() -> CAUSE_NOT_FOUND);
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User not found"));

        if (cause.getFollowers().isEmpty()) {
            cause.setFollowers(new HashSet<>());
        }
        cause.getFollowers().add(user);
        causeRepository.save(cause);
    }

    @Override
    public void unFollowCause(String email, Integer causeId) {
        Cause cause = causeRepository.findById(causeId).orElseThrow(() -> CAUSE_NOT_FOUND);
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User not found"));

        if (user.getPins().size() == 0) {
            return;
        }

        Set<User> followers = cause.getFollowers()
                .stream()
                .filter(u -> u.getId() != user.getId())
                .collect(Collectors.toSet());

        cause.getFollowers().clear();
        cause.getFollowers().addAll(followers);
        causeRepository.save(cause);
    }

    @Override
    public boolean hasAuthority(Authentication authentication, String authority) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(ga -> ga.getAuthority()
                        .equals(authority));
    }
}
