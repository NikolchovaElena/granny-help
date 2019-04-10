package com.example.granny.service;

import com.example.granny.domain.entities.Cause;
import com.example.granny.domain.entities.User;
import com.example.granny.domain.models.service.CauseServiceModel;
import com.example.granny.repository.CauseRepository;
import com.example.granny.service.api.CauseService;
import com.example.granny.service.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CauseServiceImpl implements CauseService {
    static final IllegalArgumentException NO_CAUSE_WITH_THAT_EXCEPTION =
            new IllegalArgumentException("Cause could not be found");

    private final CauseRepository causeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CauseServiceImpl(CauseRepository causeRepository, ModelMapper modelMapper) {
        this.causeRepository = causeRepository;

        this.modelMapper = modelMapper;
    }

    @Override
    public CauseServiceModel submit(CauseServiceModel model) {
        Cause cause = causeRepository.saveAndFlush(modelMapper.map(model, Cause.class));
        return modelMapper.map(cause, CauseServiceModel.class);
    }

    @Override
    public CauseServiceModel edit(CauseServiceModel model) {
        return null;
    }

    @Override
    public void delete(CauseServiceModel model) {

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
}
