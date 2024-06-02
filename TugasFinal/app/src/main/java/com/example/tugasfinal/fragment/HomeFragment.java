package com.example.tugasfinal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tugasfinal.Product;
import com.example.tugasfinal.ProductAdapter;
import com.example.tugasfinal.R;
import com.example.tugasfinal.RetrofitClient;
import com.example.tugasfinal.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;

    private ExecutorService executorService;
    private Handler mainHandler;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.item);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        fetchProducts();

        ImageView iv_search = view.findViewById(R.id.IV_Search);
        ImageView iv_cart = view.findViewById(R.id.IV_Cart);


        iv_search.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, searchFragment)
                    .commit();
        });

        iv_cart.setOnClickListener(v -> {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, favoriteFragment)
                    .commit();
        });


    }

    private void fetchProducts() {
        progressBar.setVisibility(View.VISIBLE);

        executorService.execute(() -> {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<List<Product>> call = apiService.getProducts();
            try {
                Response<List<Product>> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    mainHandler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        productAdapter = new ProductAdapter(getContext(), products);
                        recyclerView.setAdapter(productAdapter);
                    });
                } else {
                    mainHandler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                mainHandler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
