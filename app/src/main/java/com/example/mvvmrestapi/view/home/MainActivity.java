package com.example.mvvmrestapi.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mvvmrestapi.R;
import com.example.mvvmrestapi.data.models.Article;
import com.example.mvvmrestapi.data.models.ArticleListResponse;
import com.example.mvvmrestapi.view.articledetail.ArticleDetailActivity;
import com.example.mvvmrestapi.view.home.adapter.ArticleAdapter;
import com.example.mvvmrestapi.view.home.homeviewmodel.HomeViewModel;
import com.example.mvvmrestapi.view.mutablearticle.MutableActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.init();
        initUI();

        fetchDataFromAPI();
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        articleAdapter = new ArticleAdapter(this, new ArrayList<>(), article -> {
            gotoActivityDetail(article);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(articleAdapter);
    }

    private void gotoActivityDetail(Article article) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("data", article);
        startActivity(intent);
    }

    private void fetchDataFromAPI() {
        homeViewModel.getArticleLiveData().observe(this, new Observer<ArticleListResponse>() {
            @Override
            public void onChanged(ArticleListResponse articleListResponse) {
                articleAdapter.setData(articleListResponse.getArticleList());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            startActivity(new Intent(this, MutableActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fetchDataFromAPI();
    }
}
