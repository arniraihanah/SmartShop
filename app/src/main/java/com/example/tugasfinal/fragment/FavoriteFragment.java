package com.example.tugasfinal.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tugasfinal.Favorite;
import com.example.tugasfinal.FavoriteAdapter;
import com.example.tugasfinal.FavoriteUtils;
import com.example.tugasfinal.R;

import java.util.List;


public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private List<Favorite> favoriteList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageView iv_search = view.findViewById(R.id.IV_Search);
        ImageView iv_home = view.findViewById(R.id.IV_Home);

        iv_search.setOnClickListener(v -> {
            SearchFragment SearchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, SearchFragment)
                    .commit();
        });

        iv_home.setOnClickListener(v -> {
            HomeFragment HomeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, HomeFragment)
                    .commit();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteList = FavoriteUtils.getFavorites(getContext());
        adapter = new FavoriteAdapter(favoriteList, getContext(), this::onBookmarkDeleted);
        recyclerView.setAdapter(adapter);
        return  view;
    }

    private void onBookmarkDeleted(int position) {
    }
}