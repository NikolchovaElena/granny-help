package com.example.granny.service.api;

import com.example.granny.domain.models.service.CommentServiceModel;
import com.example.granny.domain.models.view.CommentViewModel;

import java.util.List;

public interface CommentService {

    CommentViewModel create(String comment, String authorEmail, Integer causeId);

    List<CommentViewModel> findAll(Integer causeId);

    void deleteAll(Integer causeId);

}
