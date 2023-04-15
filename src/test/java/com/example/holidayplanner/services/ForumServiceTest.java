package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.ForumEntity;
import com.example.holidayplanner.models.view.ForumViewModel;
import com.example.holidayplanner.repositories.ForumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ForumServiceTest {

    @Mock
    private ForumRepository forumRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private ForumService forumService;

    @BeforeEach
    void setUp(){
        forumService = new ForumService(forumRepository, modelMapper);
    }

    @Test
    public void getForums_ShouldReturnListOfForumViewModels() {
        List<ForumEntity> forumEntities = new ArrayList<>();
        forumEntities.add(new ForumEntity());
        when(forumRepository.findAll()).thenReturn(forumEntities);

        List<ForumViewModel> result = forumService.getForums();

        assertEquals(1, result.size());
        assertEquals(ForumViewModel.class, result.get(0).getClass());
    }

    @Test
    public void getForums_ShouldReturnEmptyList_WhenNoForumsFound() {
        when(forumRepository.findAll()).thenReturn(new ArrayList<>());

        List<ForumViewModel> result = forumService.getForums();

        assertEquals(0, result.size());
    }
}
