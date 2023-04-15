package com.example.holidayplanner.services;

import com.example.holidayplanner.models.view.ArticleViewModel;
import com.example.holidayplanner.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    public List<ArticleViewModel> getArticles(){
        return articleRepository.findAll().stream().map(articleEntity -> modelMapper.map(articleEntity, ArticleViewModel.class)).collect(Collectors.toList());
    }

    public ArticleViewModel findById(Long id){
        return articleRepository.findById(id).map(articleEntity -> modelMapper.map(articleEntity, ArticleViewModel.class)).orElse(null);
    }
}
