package com.example.vindme.activity.wishlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vindme.R;
import com.example.vindme.activity.cart.CartActivity;
import com.example.vindme.activity.home.HomeActivity;
import com.example.vindme.activity.profile.ProfileActivity;
import com.example.vindme.activity.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishlistFragment extends Fragment {

  RecyclerView rvWishlist;
  WishlistAdapter wishlistAdapter;
  List<Wishlist> wishlistList;
  AppDatabase appDatabase;
  DatabaseReference reference = FirebaseDatabase.getInstance().getReference("wishlist");

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public WishlistFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment WishlistFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static WishlistFragment newInstance(String param1, String param2) {
    WishlistFragment fragment = new WishlistFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

    rvWishlist = view.findViewById(R.id.rvWishlist);
    wishlistList = new ArrayList<>();

    wishlistAdapter = new WishlistAdapter(requireContext(), wishlistList);
    rvWishlist.setAdapter(wishlistAdapter);
    rvWishlist.setLayoutManager(new GridLayoutManager(requireContext(), 1));

    appDatabase = AppDatabase.getInstance(requireContext());

    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        wishlistList.clear();
        if (snapshot.exists()) {
          for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Wishlist wishlist = dataSnapshot.getValue(Wishlist.class);
            wishlistList.add(wishlist);
          }
          wishlistAdapter.notifyDataSetChanged();
        } else {
          fetchAPI();
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(getContext(), "Gagal memuat data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });


//    List<Wishlist> localWishlist = appDatabase.wishlistDao().getAllWishlist();
//    if (localWishlist.isEmpty()) {
//      Thread th = new Thread(() -> {
//        String urlString = "https://api-pam.portoku.my.id/vindme.php";
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
//            List<Wishlist> apiWishlist = new Gson().fromJson(jsonData.toString(), new TypeToken<List<Wishlist>>(){}.getType());
//
//            appDatabase.wishlistDao().insertWishlists(apiWishlist);
//
//            getActivity().runOnUiThread(() -> {
//              wishlistList.clear();
//              wishlistList.addAll(apiWishlist);
//              wishlistAdapter.notifyDataSetChanged();
//            });
//          } else {
//            // Error handling
//          }
//          connection.disconnect();
//        } catch (Exception e) {
//          getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show());
//        }
//      });
//
//      th.start();
//    } else {
//      wishlistList.addAll(localWishlist);
//      wishlistAdapter.notifyDataSetChanged();
//    }

    return view;
  }

  void fetchAPI() {
    Thread th = new Thread(() -> {
      String urlString = "https://api-pam.portoku.my.id/vindme.php";
      try {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          StringBuilder jsonData = new StringBuilder();
          String line;

          while ((line = reader.readLine()) != null) {
            jsonData.append(line);
          }
          reader.close();
          List<Wishlist> apiWishlist = new Gson().fromJson(jsonData.toString(),
                  new TypeToken<List<Wishlist>>(){}.getType());

          for (Wishlist wishlist : apiWishlist) {
            String firebaseKey = reference.push().getKey();
            wishlist.setIdWishlist(firebaseKey);
            reference.child(firebaseKey).setValue(wishlist);
          }

          getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Fetch data successfully", Toast.LENGTH_SHORT).show());
        } else {
          getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Failed to fetch data: " + responseCode, Toast.LENGTH_SHORT).show());
        }
        connection.disconnect();
      } catch (Exception e) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show());
      }
    });

    th.start();
  }

}