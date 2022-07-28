package com.punkmic.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.punkmic.soccernews.MainActivity;
import com.punkmic.soccernews.databinding.FragmentFavoritesBinding;
import com.punkmic.soccernews.domain.News;
import com.punkmic.soccernews.ui.adapters.NewsAdapter;

import java.util.List;


public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        loadFavoriteNews();
        return binding.getRoot();
    }

    private void loadFavoriteNews() {
        MainActivity activity = (MainActivity) getActivity();
        List<News> favoriteNews = activity.getDb().newsDao().loadFavoriteNews();
        binding.rvNews.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.rvNews.setAdapter(new NewsAdapter(favoriteNews, updateNews -> {
            activity.getDb().newsDao().save(updateNews);
            loadFavoriteNews();
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}