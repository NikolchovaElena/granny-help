package com.example.granny.service.api;

import com.example.granny.domain.models.service.CauseServiceModel;

import java.util.List;

public interface CauseService {
    CauseServiceModel submit(CauseServiceModel model);

    CauseServiceModel edit(CauseServiceModel model);

    void delete(CauseServiceModel model);

    List<CauseServiceModel> findAll();

    List<CauseServiceModel> findAllApproved(Integer id);

    List<CauseServiceModel> findAllPending(Integer id);

    CauseServiceModel findById(Integer id);

}
