package com.example.tugasfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteUtils {
    private static final String PREFS_NAME = "smartshop_prefs";
    private static final String KEY_FAVORITES = "favorites";

    public static void saveFavorite(Context context, Favorite favorite) {
        List<Favorite> favorites = getFavorites(context);
        for (Favorite b : favorites) {
            if (b.getProductTitle().equals(favorite.getProductTitle())) {
                Toast.makeText(context, "This item is already favorited", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        favorites.add(favorite);
        saveFavorites(context, favorites);
        Toast.makeText(context, "Product bookmarked!", Toast.LENGTH_SHORT).show();
    }

    public static void removeFavorite(Context context, Favorite favorite) {
        List<Favorite> favorites = getFavorites(context);
        boolean removed = favorites.removeIf(b -> b.getProductTitle().equals(favorite.getProductTitle()));
        if (removed) {
            saveFavorites(context, favorites);
            Toast.makeText(context, "Product removed from favorites!", Toast.LENGTH_SHORT).show();
        }
    }

    public static List<Favorite> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_FAVORITES, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Favorite>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    private static void saveFavorites(Context context, List<Favorite> favorites) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favorites);
        editor.putString(KEY_FAVORITES, json);
        editor.apply();
    }
}
