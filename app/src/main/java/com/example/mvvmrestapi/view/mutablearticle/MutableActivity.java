package com.example.mvvmrestapi.view.mutablearticle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mvvmrestapi.R;
import com.example.mvvmrestapi.data.models.Article;
import com.example.mvvmrestapi.data.models.ImageResponse;
import com.example.mvvmrestapi.helpers.FilePath;
import com.example.mvvmrestapi.view.home.homeviewmodel.HomeViewModel;

import java.io.File;

public class MutableActivity extends AppCompatActivity {

    ImageView imageView;
    EditText title, description;
    Button save;
    ProgressBar progressBar;
    Uri imageUri;
    MutableViewModel mutableViewModel;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutable);

        initUI();
        requestPermission();
        mutableViewModel = ViewModelProviders.of(this).get(MutableViewModel.class);
        mutableViewModel.init();

        imageView.setOnClickListener(view -> {
            showFileChooser();
        });
        handleSaveClicked();

//        mutableViewModel.getAllImages().observe(this, imageResponses -> {
//            Log.d("TAG", "onCreate: "+ imageResponses);
//        });
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return;
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "you just denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 1000 && resultCode == RESULT_OK && data != null){
            progressBar.setVisibility(View.VISIBLE);
            imageUri = data.getData();

            Log.d("TAG", "URI: "+ imageUri);
            try {
                String selectedFilePath = FilePath.getPath(MutableActivity.this, imageUri);
                final File file = new File(selectedFilePath);
                formDataConverter(file);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void formDataConverter(File file) {
            mutableViewModel.uploadImageLiveData(file)
                    .observe(MutableActivity.this, new Observer<ImageResponse>() {
                        @Override
                        public void onChanged(ImageResponse imageResponse) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Glide.with(MutableActivity.this).load(imageUri).centerCrop().into(imageView);
                            imageUrl = imageResponse.getImage();
                            Log.d("TAG", "onChanged: "+ imageUrl);
                        }
                    });
    }

    private void initUI() {
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);
        progressBar = findViewById(R.id.progressBar);
    }

    public void handleSaveClicked(){
        save.setOnClickListener(view -> {
            Article article = new Article(title.getText().toString(), description.getText().toString(), imageUrl);
            mutableViewModel.postArticle(article).observe(MutableActivity.this, new Observer<Article>() {
                @Override
                public void onChanged(Article article) {
                    Toast.makeText(MutableActivity.this, "Article Posted", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }
}