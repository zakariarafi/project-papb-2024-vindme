package com.example.vindme.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vindme.R;

public class DetailPembelianActivity extends AppCompatActivity {

  TextView tvArtist;
  TextView tvTitle;
  TextView tvPrice;
  TextView tvDetail;
  ImageView ivCover;
//  Button btBuy;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_detail_pembelian);

    tvArtist = findViewById(R.id.tvArtist);
    tvTitle = findViewById(R.id.tvTitle);
    tvPrice = findViewById(R.id.tvPrice);
    ivCover = findViewById(R.id.ivCover);
    tvDetail = findViewById(R.id.tvDetail);
//    btBuy = findViewById(R.id.btBuy);

    Intent intent = getIntent();
    String cover = intent.getStringExtra("cover");
    String title = intent.getStringExtra("title");
    String artist = intent.getStringExtra("artist");
    String detail = intent.getStringExtra("detail");
    String price = intent.getStringExtra("price");
    String pesan = intent.getStringExtra("pesan");

    Glide.with(getApplicationContext()).load(cover).into(ivCover);
    tvArtist.setText(artist);
    tvTitle.setText(title);
    tvPrice.setText(price);
    tvDetail.setText(detail);

    Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_SHORT).show();

//    btBuy.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//
//        intent.putExtra("message", "Kamu telah membeli album " + title );
//
//        startActivity(intent);
//      }
//    });

  }
}