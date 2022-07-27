package com.punkmic.soccernews.ui.news;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.punkmic.soccernews.data.remote.SoccerNewsApi;
import com.punkmic.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {


    private final MutableLiveData<List<News>> news = new MutableLiveData<List<News>>();
    private final SoccerNewsApi api;

    public NewsViewModel() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://punkmic.github.io/soccer-news-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         api = retrofit.create(SoccerNewsApi.class);
        findNews();
    }

    private void findNews() {
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.isSuccessful()){
                    news.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e("NewsViewModel", "Retrofit error: ", t);
                Log.e("s", call.request().url().toString());
            }
        });
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}