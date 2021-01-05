package com.example.mvvmrestapi.view.home.homeviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmrestapi.data.models.ArticleListResponse;
import com.example.mvvmrestapi.repositories.ArticleRepository;

public class HomeViewModel extends ViewModel {

    ArticleRepository articleRepository;

    public void init(){
        articleRepository = new ArticleRepository();
    }

    public MutableLiveData<ArticleListResponse> getArticleLiveData(){
        return articleRepository.findAllArticles();
    }
}
