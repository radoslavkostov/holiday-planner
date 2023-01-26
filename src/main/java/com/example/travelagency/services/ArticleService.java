package com.example.travelagency.services;

import com.example.travelagency.entities.ArticleEntity;
import com.example.travelagency.models.view.ArticleViewModel;
import com.example.travelagency.repositories.ArticleRepository;
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

    public void init(){
        if(articleRepository.count()==0){
            ArticleEntity articleOne = new ArticleEntity();
            ArticleEntity articleTwo = new ArticleEntity();
            ArticleEntity articleThree = new ArticleEntity();
            ArticleEntity articleFour = new ArticleEntity();
            articleOne.setName("Article One");
            articleOne.setShortDescription("lorem ispum lorem lorem");
            articleOne.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            articleTwo.setName("Article Two");
            articleTwo.setShortDescription("lorem ispum lorem lorem");
            articleTwo.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            articleThree.setName("Article Three");
            articleThree.setShortDescription("lorem ispum lorem lorem");
            articleThree.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            articleFour.setName("Article Four");
            articleFour.setShortDescription("lorem ispum lorem lorem");
            articleFour.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

            articleRepository.save(articleOne);
            articleRepository.save(articleTwo);
            articleRepository.save(articleThree);
            articleRepository.save(articleFour);
        }
    }

    public List<ArticleViewModel> getArticles(){
        return articleRepository.findAll().stream().map(articleEntity -> modelMapper.map(articleEntity, ArticleViewModel.class)).collect(Collectors.toList());
    }

    public ArticleViewModel findById(Long id){
        return articleRepository.findById(id).map(articleEntity -> modelMapper.map(articleEntity, ArticleViewModel.class)).orElse(null);
    }
}
