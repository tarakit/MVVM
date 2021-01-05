package com.example.mvvmrestapi.view.articledetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mvvmrestapi.R;
import com.example.mvvmrestapi.data.models.Article;

public class ArticleDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView title, description;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        
        initUI();
        Intent intent = getIntent();
        Article article = (Article) intent.getSerializableExtra("data");
        
        if(article != null){
            title.setText(article.getTitle());
            description.setText(article.getDescription());
            Glide.with(this).load(article.getImage()).into(imageView);
        }else
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.txt_title);
        description = findViewById(R.id.txt_description);
    }
}