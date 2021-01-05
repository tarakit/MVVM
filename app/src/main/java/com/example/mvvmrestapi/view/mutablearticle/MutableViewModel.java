package com.example.mvvmrestapi.view.mutablearticle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmrestapi.data.models.Article;
import com.example.mvvmrestapi.data.models.ImageResponse;
import com.example.mvvmrestapi.repositories.ArticleRepository;

import java.io.File;

public class MutableViewModel extends ViewModel {

    ArticleRepository articleRepository;

    public void init(){
        articleRepository = new ArticleRepository();
    }

    public MutableLiveData<ImageResponse> uploadImageLiveData(File file){
        return articleRepository.uploadImage(file);
    }

    public MutableLiveData<Article> postArticle(Article article){
        return articleRepository.postArticle(article);
    }

}
