package com.example.granny.service.api;

import com.example.granny.domain.models.binding.CauseFormBindingModel;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.domain.models.service.UserServiceModel;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface CauseService {
    CauseServiceModel submit(CauseFormBindingModel model, String email) throws IOException;

    CauseServiceModel edit(CauseFormBindingModel model, Integer causeId) throws IOException;

    void delete(Integer causeId, String email, Authentication authentication);

    List<CauseServiceModel> findAll();

    List<CauseServiceModel> findAllApproved(Integer id);

    List<CauseServiceModel> findAllPending(Integer id);

    CauseServiceModel findById(Integer id);

    void followCause(String email, Integer causeId);

    void unFollowCause(String email, Integer causeId);

    boolean hasAuthority(Authentication authentication, String authority);
}
