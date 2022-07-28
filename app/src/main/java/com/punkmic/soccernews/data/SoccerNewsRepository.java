package com.punkmic.soccernews.data;

import androidx.room.Room;

import com.punkmic.soccernews.App;
import com.punkmic.soccernews.data.local.AppDatabase;
import com.punkmic.soccernews.data.remote.SoccerNewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {

    private static final String REMOTE_API_URL = "https://punkmic.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";

    private final SoccerNewsApi remoteApi;
    private final AppDatabase localDb;

    public SoccerNewsApi getRemoteApi() {
        return remoteApi;
    }

    public AppDatabase getLocalDb() {
        return localDb;
    }

    private SoccerNewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoccerNewsApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), AppDatabase.class, LOCAL_DB_NAME).build();
    }

    public static SoccerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }
}