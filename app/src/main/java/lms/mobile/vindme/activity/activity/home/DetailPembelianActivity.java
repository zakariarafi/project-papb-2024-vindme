package lms.mobile.vindme.activity.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vindme.R;

public class DetailPembelianActivity extends AppCompatActivity {

  TextView tvArtist;
  TextView tvTitle;
  TextView tvPrice;
//  Button btBuy;
  ImageView ivCover;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_detail_pembelian);

    tvArtist = findViewById(R.id.tvArtist);
    tvTitle = findViewById(R.id.tvTitle);
//    btBuy = findViewById(R.id.btBuy);
    tvPrice = findViewById(R.id.tvPrice);
    ivCover = findViewById(R.id.ivCover);

    Intent intent = getIntent();
    int coverAlbum = intent.getIntExtra("coverAlbum", -1);
    String artist = intent.getStringExtra("artist");
    String album = intent.getStringExtra("album");
    String price = intent.getStringExtra("price");
    String pesan = intent.getStringExtra("pesan");

    if (coverAlbum != -1) {
      ivCover.setImageResource(coverAlbum);
    }
    tvArtist.setText(artist);
    tvTitle.setText(album);
    tvPrice.setText(price);

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