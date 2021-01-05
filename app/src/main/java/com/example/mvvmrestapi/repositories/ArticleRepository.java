package com.example.mvvmrestapi.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mvvmrestapi.data.models.Article;
import com.example.mvvmrestapi.data.models.ArticleListResponse;
import com.example.mvvmrestapi.data.models.ImageResponse;
import com.example.mvvmrestapi.data.remote.ArticleService;
import com.example.mvvmrestapi.data.remote.RetroInstance;
import com.example.mvvmrestapi.data.remote.RetrofitInstance;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepository {
    private static final String TAG = ArticleRepository.class.getSimpleName();
    ArticleService articleService;
    ArticleService articleService2;

    public ArticleRepository(){
        articleService = RetrofitInstance.createService(ArticleService.class);
        articleService2 = RetroInstance.createService(ArticleService.class);
    }

    public MutableLiveData<ImageResponse> uploadImage(File file){
        MutableLiveData<ImageResponse> imageLiveData = new MutableLiveData<>();
        Log.d(TAG, "File: "+ file.getName());

        RequestBody request = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getAbsolutePath(), request);
        Log.d(TAG, "uploadImage: "+ image);
        Call<ImageResponse> call = articleService2.uploadImage(image);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                imageLiveData.postValue(response.body());
                Log.d(TAG, "onResponse: "+ response.body().toString());
                Log.d(TAG, "success hz: ");
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
        return imageLiveData;
    }

    public MutableLiveData<ArticleListResponse> findAllArticles(){
        MutableLiveData<ArticleListResponse> articleLiveData = new MutableLiveData<>();

        Call<ArticleListResponse> listResponse = articleService.findAllArticle();
        listResponse.enqueue(new Callback<ArticleListResponse>() {
            @Override
            public void onResponse(Call<ArticleListResponse> call, Response<ArticleListResponse> response) {
                if(response.isSuccessful() & response.body() != null){
                    articleLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArticleListResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });

        return articleLiveData;
    }

    public MutableLiveData<Article> postArticle(Article article){
        MutableLiveData<Article> articleLiveData = new MutableLiveData<>();

        Call<Article> articleCall = articleService.postArticle(article);
        articleCall.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                articleLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
        return articleLiveData;
    }
}
