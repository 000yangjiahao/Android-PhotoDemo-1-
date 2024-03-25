package com.example.photodemo.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.example.photodemo.BR;
import com.example.photodemo.entity.UnsplashPhoto;
import com.example.photodemo.mvvm.ObservableViewModel;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainViewModel extends ObservableViewModel {
    private static final String url = "https://api.unsplash.com/photos/random?client_id=D1iV9IoCJ3l76N23H7DpV3hfmBCpu0LPUDw0U734_0Y";
    OkHttpClient client=new OkHttpClient();
    @Bindable
    private String currentImageUrl="";
    private final Gson gson;
    public MainViewModel(@NonNull Application application) {
        super(application);
        gson = new Gson();
    }

    public String getCurrentImageUrl() {
        return currentImageUrl;
    }

    public void setCurrentImageUrl(String currentImageUrl) {
        this.currentImageUrl = currentImageUrl;
        notifyPropertyChanged(BR.currentImageUrl);
    }

    public void loadImage(){

        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Logger.e("图片加载失败",e);
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try( ResponseBody responseBody = response.body() ) {
                    assert responseBody != null;
                    String jsonString = responseBody.string();
                    UnsplashPhoto unsplashPhoto = gson.fromJson(jsonString, UnsplashPhoto.class);
                    if (unsplashPhoto != null && unsplashPhoto.getUrls() != null) {
                        setCurrentImageUrl(unsplashPhoto.getUrls().getRaw());
                    }
                }
            }
        });
    }
}
