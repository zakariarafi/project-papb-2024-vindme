package com.example.vindme.activity.wishlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vindme.activity.cart.CartActivity;
import com.example.vindme.activity.profile.ProfileActivity;
import com.example.vindme.R;
import com.example.vindme.activity.home.HomeActivity;
import com.example.vindme.activity.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

//  RecyclerView rvWishlist;
//  WishlistAdapter wishlistAdapter;
//  List<Wishlist> wishlistList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_wishlist);

//    //Progres Recycle view
//    rvWishlist = findViewById(R.id.rvWishlist);
//    wishlistList = new ArrayList<>();
//
//    wishlistAdapter = new WishlistAdapter(this, wishlistList);
//    rvWishlist.setAdapter(wishlistAdapter);
//    rvWishlist.setLayoutManager(new GridLayoutManager(this, 1));
//
//    //Prgrest Rest API dan Thread
//    Thread th = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        String urlString = "http://10.0.2.2/ApiVindMe/apiWishlist.php";
//        try {
//          URL url = new URL(urlString);
//          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//          connection.setRequestMethod("GET");
//          connection.connect();
//
//          int responseCode = connection.getResponseCode();
//          if (responseCode == HttpURLConnection.HTTP_OK) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder jsonData = new StringBuilder();
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//              jsonData.append(line);
//            }
//            reader.close();
//
//            Gson gson = new Gson();
//            List<Wishlist> apiWishlist = gson.fromJson(jsonData.toString(), new TypeToken<List<Wishlist>>(){}.getType());
//
//            runOnUiThread(() -> {
//              wishlistList.clear();
//              wishlistList.addAll(apiWishlist);
//              wishlistAdapter.notifyDataSetChanged();
//            });
//
//          } else {
//            Log.e("API_ERROR", "Error: " + responseCode);
//          }
//          connection.disconnect();
//        } catch (Exception e) {
//          Log.e("API_ERROR", "Error: " + e.getMessage());
//          runOnUiThread(() -> Toast.makeText(WishlistActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show());
//        }
//      }
//    });
//
//    th.start();

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragment_container, new WishlistFragment())
          .commit();
    }

    BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
    bottomNav.setSelectedItemId(R.id.wishlist);
    bottomNav.setOnItemSelectedListener(item -> {
      if (item.getItemId() == R.id.home){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
      } else if(item.getItemId() == R.id.search){
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        finish();
      } else if (item.getItemId() == R.id.cart) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
        finish();
      } else if (item.getItemId() == R.id.wishlist) {
        startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
        finish();
      } else if (item.getItemId() == R.id.profile) {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
      }
      return false;
    });
  }
}