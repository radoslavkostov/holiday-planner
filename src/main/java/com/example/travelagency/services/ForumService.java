package com.example.travelagency.services;

import com.example.travelagency.entities.ForumEntity;
import com.example.travelagency.models.view.ForumViewModel;
import com.example.travelagency.repositories.ForumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {
    private final ForumRepository forumRepository;
    private final ModelMapper modelMapper;

    public ForumService(ForumRepository forumRepository, ModelMapper modelMapper) {
        this.forumRepository = forumRepository;
        this.modelMapper = modelMapper;
    }

    public void init() {
        if (forumRepository.count() == 0) {
            ForumEntity forumEntity = new ForumEntity();
            forumEntity.setName("Travel Forum");
            forumRepository.save(forumEntity);
        }
    }

    public List<ForumViewModel> getForums() {
        return forumRepository.findAll().stream().map(forumEntity -> modelMapper.map(forumEntity, ForumViewModel.class)).collect(Collectors.toList());
    }

    public ForumViewModel getForumById(Long id) {
        return forumRepository.getForumEntityById(id).map(forumEntity -> modelMapper.map(forumEntity, ForumViewModel.class)).orElse(null);
    }
}
