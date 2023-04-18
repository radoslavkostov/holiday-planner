package com.example.holidayplanner.services;

import com.example.holidayplanner.entities.ArticleEntity;
import com.example.holidayplanner.models.view.ArticleViewModel;
import com.example.holidayplanner.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private ArticleService articleService;

    @BeforeEach
    void setUp(){
        articleService = new ArticleService(articleRepository, modelMapper);
    }

    @Test
    void testGetAllArticles(){
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(1L);
        articleEntity.setName("Article One");
        articleEntity.setContent("Content");
        articleEntity.setShortDescription("Short Description");
        articleEntity.setImageURL("Image URL");
        List<ArticleEntity> articleEntities = new ArrayList<>();
        articleEntities.add(articleEntity);
        ArticleViewModel articleViewModel = modelMapper.map(articleEntity, ArticleViewModel.class);
        List<ArticleViewModel> articleViewModels = new ArrayList<>();
        articleViewModels.add(articleViewModel);
        when(articleRepository.findAll()).thenReturn(articleEntities);

        assertEquals(articleService.getArticles(), articleViewModels);
    }

    @Test
    void testGetArticleById(){
        ArticleEntity articleEntity = new ArticleEntity();
        Long id = 1L;
        articleEntity.setId(id);
        articleEntity.setName("Article One");
        articleEntity.setContent("Content");
        articleEntity.setShortDescription("Short Description");
        articleEntity.setImageURL("Image URL");
        ArticleViewModel articleViewModel = modelMapper.map(articleEntity, ArticleViewModel.class);
        when(articleRepository.findById(id)).thenReturn(java.util.Optional.of(articleEntity));

        assertEquals(articleService.findById(id), articleViewModel);
    }

    @Test
    void testGetAllArticles_emptyRepository() {
        when(articleRepository.findAll()).thenReturn(Collections.emptyList());
        List<ArticleViewModel> result = articleService.getArticles();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetArticleById_emptyResult() {
        Long id = 1L;
        when(articleRepository.findById(id)).thenReturn(Optional.empty());
        ArticleViewModel result = articleService.findById(id);
        assertNull(result);
    }
}
