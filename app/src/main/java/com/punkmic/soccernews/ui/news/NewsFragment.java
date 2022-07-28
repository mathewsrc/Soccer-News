package com.punkmic.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.punkmic.soccernews.MainActivity;
import com.punkmic.soccernews.R;
import com.punkmic.soccernews.data.local.AppDatabase;
import com.punkmic.soccernews.databinding.FragmentNewsBinding;
import com.punkmic.soccernews.ui.adapters.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);

        loadNews();
        return binding.getRoot();
    }

    private void loadNews() {
        binding.rvNews.setLayoutManager(new LinearLayoutManager(this.getContext()));

        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, updateNews -> {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.getDb().newsDao().save(updateNews);
                }
            }));
        });
    }

    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    //binding.srlNews.setRefreshing(true);
                    break;
                case DONE:
                    //binding.srlNews.setRefreshing(false);
                    break;
                case ERROR:
                    //binding.srlNews.setRefreshing(false);
                    //Snackbar.make(binding.srlNews, R.string.error_network, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}