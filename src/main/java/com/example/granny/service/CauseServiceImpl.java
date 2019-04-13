package com.example.granny.service;

import com.example.granny.constants.GlobalConstants;
import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.binding.CauseFormBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.repository.CauseRepository;
import com.example.granny.repository.UserRepository;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.CloudinaryService;
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
    static final IllegalArgumentException NO_CAUSE_WITH_THAT_EXCEPTION =
            new IllegalArgumentException("Cause could not be found");

    private final CauseRepository causeRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CauseServiceImpl(CauseRepository causeRepository, UserRepository userRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.causeRepository = causeRepository;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CauseServiceModel submit(CauseFormBindingModel model, String email) throws IOException {
        User author = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(NO_USER_WITH_THAT_EXCEPTION));
        Cause cause = modelMapper.map(model, Cause.class);
        cause.setAuthor(author);
        String image = GlobalConstants.CAUSE_DEFAULT_IMG;

        MultipartFile file = model.getImageUrl();
        if (!file.isEmpty()) {
            image = cloudinaryService.uploadImage(file, "CAUSE" + author.getEmail());
        }
        cause.setImageUrl(image);
        return modelMapper.map(causeRepository.saveAndFlush(cause), CauseServiceModel.class);
    }

    @Override
    public CauseServiceModel edit(CauseFormBindingModel model, Integer causeId) throws IOException {
        Cause cause = causeRepository.findById(causeId).orElseThrow(() ->
                new IllegalArgumentException(NO_CAUSE_WITH_THAT_EXCEPTION));
        MultipartFile file = model.getImageUrl();

        if (!file.isEmpty()) {
            if (!cause.getImageUrl().equals(GlobalConstants.CAUSE_DEFAULT_IMG)) {
                cloudinaryService.deleteImage("CAUSE" + cause.getAuthor().getEmail());
            }
            String image = cloudinaryService.uploadImage(file, "CAUSE" + cause.getAuthor().getEmail());
            cause.setImageUrl(image);
        }

        cause.setTitle(model.getTitle());
        cause.setDescription(model.getDescription());
        cause.setLocation(model.getLocation());

        return modelMapper.map(causeRepository.saveAndFlush(cause), CauseServiceModel.class);
    }

    @Override
    public void delete(Integer causeId, String email, Authentication authentication) {

        Cause cause = causeRepository.findById(causeId).orElseThrow(() ->
                new IllegalArgumentException(NO_CAUSE_WITH_THAT_EXCEPTION));

        if (!cause.getAuthor().getEmail().equals(email) ||
                !hasAuthority(authentication, GlobalConstants.ROLE_MODERATOR)) {
            throw new AccessDeniedException("You are not authorized to delete this cause");
        }

//        cause.getFollowers().clear();
//        cause = causeRepository.saveAndFlush(cause);
        causeRepository.deleteRelation(causeId);
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
    public List<CauseServiceModel> findAllPending(Integer id) {
        List<Cause> causes = causeRepository.findAllPendingByAuthorId(id);
        return causes.stream()
                .map(c -> modelMapper.map(c, CauseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CauseServiceModel findById(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(NO_CAUSE_WITH_THAT_EXCEPTION));

        return modelMapper.map(cause, CauseServiceModel.class);
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
