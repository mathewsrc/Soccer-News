package com.punkmic.soccernews.ui.news;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.punkmic.soccernews.data.SoccerNewsRepository;
import com.punkmic.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public enum State {
        DOING, DONE, ERROR;
    }

    private final MutableLiveData<State> state = new MutableLiveData<>();
    private final MutableLiveData<List<News>> news = new MutableLiveData<List<News>>();

    public NewsViewModel() {
        this.findNews();
    }

    private void findNews() {
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(@NonNull Call<List<News>> call, @NonNull Response<List<News>> response) {
                if (response.isSuccessful()) {
                    news.setValue(response.body());
                    state.setValue(State.DONE);
                } else {
                    state.setValue(State.ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<News>> call, Throwable error) {
                state.setValue(State.ERROR);
            }
        });
    }

    public void saveNews(News news) {
        SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }

    public LiveData<State> getState() {
        return this.state;
    }
}