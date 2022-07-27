package com.punkmic.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.punkmic.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();
        List<News> news = new ArrayList<>();
        news.add(new News("Ferroviária tem desfalque importante",""));
        news.add(new News("Ferrinha joga no sábado",""));
        news.add(new News("Copa do mundo feminina está terminando",""));

        this.news.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}