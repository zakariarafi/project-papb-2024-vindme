package lms.mobile.vindme.activity.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import lms.mobile.vindme.activity.activity.cart.CartActivity;
import lms.mobile.vindme.activity.activity.profile.ProfileActivity;
import com.example.vindme.R;
import lms.mobile.vindme.activity.activity.search.SearchActivity;
import lms.mobile.vindme.activity.activity.wishlist.WishlistActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

//  TextView tvArtist;
//  TextView tvTitle;
//  TextView tvMessage;
//  Button btBeli;

//  String artist = "Ariana Grande";
//  String title = "The Tortured Poets";

  RecyclerView recyclerView;
  HomeAdapter homeAdapter;
  List<Album> albumList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_home);

    // Progress dua - Recycle View
    recyclerView = findViewById(R.id.rvHome);
    albumList = new ArrayList<>();

    albumList.add(new Album( R.drawable.sting, "Sting", "57th & 9th Vinyl", "675,000"));
    albumList.add(new Album(R.drawable.queen_theworks, "Queen", "The Works Vinyl", "450,000"));
    albumList.add(new Album(R.drawable.eltonjohn_tlfz,  "Elton John", "Too Low For Zero Vinyl", "625.000"));
    albumList.add(new Album(R.drawable.taylor_swift,  "Taylor Swift", "The Tortured Poets Department Vinyl", "699,000"));
    albumList.add(new Album(R.drawable.rihanna_goodgirlgonebad,  "Rihanna", "Good Girl Gone Bad Vinyl", "485,000"));
    albumList.add(new Album(R.drawable.ariana_grande,  "Ariana Grande", "Eternal Sunshine Vinyl", "599,000"));
    albumList.add(new Album(R.drawable.billie_eillish, "Billie Eilish", "HIT ME HARD AND SOFT Vinyl", "585,000"));

    homeAdapter = new HomeAdapter(this, albumList);
    recyclerView.setAdapter(homeAdapter);
    recyclerView.setLayoutManager(new GridLayoutManager(this,2));


//    tvArtist = findViewById(R.id.tvArtist);
//    tvTitle = findViewById(R.id.tvTitle);
//    btBeli = findViewById(R.id.btBuy);
//    tvMessage = findViewById(R.id.tvMessage);
//
//    tvArtist.setText(artist);
//    tvTitle.setText(title);
//
//    btBeli.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent intent = new Intent(getApplicationContext(), DetailPembelianActivity.class);
//
//        intent.putExtra("artist_name", artist);
//        intent.putExtra("album_title", title);
//
//        startActivity(intent);
//      }
//    });
//
//    Intent intent = getIntent();
//
//    String pesan = intent.getStringExtra("message");
//    tvMessage.setText(pesan);

    BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
    bottomNav.setSelectedItemId(R.id.home);
    bottomNav.setOnItemSelectedListener(item -> {
      if (item.getItemId() == R.id.home){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
      }
      else if(item.getItemId() == R.id.search){
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        finish();
      }
      else if (item.getItemId() == R.id.cart) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
        finish();
      }
      else if (item.getItemId() == R.id.wishlist) {
        startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
        finish();
      }
      else if (item.getItemId() == R.id.profile) {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
      }
      return false;
    });

  }
}

class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

  Context context;
  List<Album> albumList;

  public HomeAdapter(Context context, List<Album> albumList) {
    this.context = context;
    this.albumList = albumList;
  }

  public class HomeViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCover;
    TextView tvArtist, tvAlbum, tvPrice;


    public HomeViewHolder(@NonNull View itemView) {
      super(itemView);
      ivCover = itemView.findViewById(R.id.ivCover);
      tvArtist = itemView.findViewById(R.id.tvArtist);
      tvAlbum = itemView.findViewById(R.id.tvAlbum);
      tvPrice = itemView.findViewById(R.id.tvPrice);
    }
  }

  @NonNull
  @Override
  public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.row_home, parent, false);
    return new HomeViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
    Album album = albumList.get(position);
    holder.ivCover.setImageResource(album.getCoverAlbum());
    holder.tvArtist.setText(album.getArtis());
    holder.tvAlbum.setText(album.getAlbum());
    holder.tvPrice.setText(album.getHarga());

    holder.ivCover.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Intent intent = new Intent(context, DetailPembelianActivity.class);
        intent.putExtra("coverAlbum", album.getCoverAlbum());
        intent.putExtra("artist", album.getArtis());
        intent.putExtra("album", album.getAlbum());
        intent.putExtra("price", album.getHarga());
        intent.putExtra("pesan", album.getAlbum() + " Detail Product");
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return albumList.size();
  }
}