package com.punkmic.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.punkmic.soccernews.R;
import com.punkmic.soccernews.databinding.FragmentNewsBinding;
import com.punkmic.soccernews.ui.adapters.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        observeNews();
        observeStates();
        binding.swipeToRefresh.setOnRefreshListener(newsViewModel::findNews);
        return binding.getRoot();
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            binding.rvNews.setAdapter(new NewsAdapter(news, newsViewModel::saveNews));
        });
    }

    private void observeStates() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    binding.swipeToRefresh.setRefreshing(true);
                    break;
                case DONE:
                    binding.swipeToRefresh.setRefreshing(false);
                    break;
                case ERROR:
                    binding.swipeToRefresh.setRefreshing(false);
                    Snackbar.make(binding.swipeToRefresh, R.string.error_network, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}