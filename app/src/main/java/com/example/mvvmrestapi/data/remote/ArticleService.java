package com.example.mvvmrestapi.data.remote;

import com.example.mvvmrestapi.data.models.Article;
import com.example.mvvmrestapi.data.models.ArticleListResponse;

import com.example.mvvmrestapi.data.models.ImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ArticleService {

    @GET("api/articles")
    Call<ArticleListResponse> findAllArticle();

    @Multipart
    @POST("v1/api/uploadfile/single")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part image);

    @POST("api/articles")
    Call<Article> postArticle(@Body Article article);

    @PATCH("api/articles/{id}")
    Call<Article> updateArticle(@Path("id") String id);

    @DELETE("api/articles/{id}")
    Call<Void> deleteArticle(@Path("id") String id);
}
