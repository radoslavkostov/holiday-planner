package com.example.travelagency.services;

import com.example.travelagency.entities.ForumEntity;
import com.example.travelagency.repositories.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {
    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    public void init(){
        if(forumRepository.count() == 0){
            ForumEntity forumEntity = new ForumEntity();
            forumEntity.setName("Travel Forum");
            forumRepository.save(forumEntity);
        }
    }

    public List<ForumEntity> getForums() {
        return forumRepository.findAll();
    }

    public ForumEntity getForumById(Long id) {
        return forumRepository.getForumEntityById(id).orElse(null);
    }
}
