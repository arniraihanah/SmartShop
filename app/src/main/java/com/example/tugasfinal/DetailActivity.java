package com.example.tugasfinal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView titleTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView ratingTextView;
    private TextView categoryTextView;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        productImageView = findViewById(R.id.productImageView);
        titleTextView = findViewById(R.id.titleTextView);
        priceTextView = findViewById(R.id.priceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        ratingTextView = findViewById(R.id.ratingTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        favoriteButton = findViewById(R.id.btFavorite);

        Product product = getIntent().getParcelableExtra("product");

        if (product != null) {
            titleTextView.setText(product.getTitle());
            priceTextView.setText("$" + product.getPrice());
            descriptionTextView.setText(product.getDescription());
            ratingTextView.setText("Rating: " + product.getRate() + " (" + product.getCount() + " reviews)");
            categoryTextView.setText("Category: " + product.getCategory());

            // Load image using Picasso or any other image loading library

            Picasso.get().load(product.getImage()).into(productImageView);
            favoriteButton.setOnClickListener(v -> {
                FavoriteUtils.saveFavorite(this, new Favorite(product));
                finish();
            });
        }


    }
}