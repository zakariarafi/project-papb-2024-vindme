package com.example.vindme.activity.cart;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vindme.R;
import com.example.vindme.activity.home.HomeActivity;
import com.example.vindme.activity.profile.ProfileActivity;
import com.example.vindme.activity.search.SearchActivity;
import com.example.vindme.activity.wishlist.WishlistActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartActivity extends AppCompatActivity {

  TextView tvActivity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_cart);

    tvActivity = findViewById(R.id.tvActivity);
    tvActivity.setText("Cart");

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, new CartFragment());
    transaction.commit();

    BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
    bottomNav.setSelectedItemId(R.id.cart);
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

class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

  private final Context context;
  private final List<Cart> cartList;

  public CartAdapter(Context context, List<Cart> cartList) {
    this.context = context;
    this.cartList = cartList;
  }

  @NonNull
  @Override
  public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.row_cart, parent, false);
    return new CartViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
    Cart cart = cartList.get(position);
    Glide.with(context).load(cart.getCover()).into(holder.ivCover);
    holder.tvTitle.setText(cart.getTitle());
    holder.tvArtist.setText(cart.getArtist());
    holder.tvPrice.setText(cart.getPrice());

//    holder.btDelete.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
////        AppDatabase db = AppDatabase.getInstance(context);
////
////        db.cartDao().deleteCart(cart.getCartId());
////        cartList.remove(holder.getAdapterPosition());
////        notifyItemRemoved(holder.getAdapterPosition());
//
//        // Mendapatkan referensi ke Firebase Realtime Database
//        DatabaseReference cartRef = FirebaseDatabase.getInstance("https://papbd-7cfaf-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("carts");
//
//        // Mendapatkan userId dari FirebaseAuth (untuk memastikan penghapusan berdasarkan user yang login)
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        // Menghapus data dari Firebase Realtime Database
//        cartRef.child(userId).child(String.valueOf(cart.getCartId())).removeValue()
//            .addOnCompleteListener(task -> {
//              if (task.isSuccessful()) {
//                // Jika penghapusan berhasil, hapus dari daftar cart lokal dan update RecyclerView
//                cartList.remove(holder.getAdapterPosition());
//                notifyItemRemoved(holder.getAdapterPosition());
//
//                // Menampilkan Toast untuk mengonfirmasi penghapusan
//                Toast.makeText(context, "Album Berhasil dihapus", Toast.LENGTH_SHORT).show();
//              } else {
//                // Jika gagal, tampilkan pesan error
//                Toast.makeText(context, "Gagal menghapus album", Toast.LENGTH_SHORT).show();
//              }
//            });
//
//        Toast.makeText(context, "Album Berhasil dihapus", Toast.LENGTH_SHORT).show();
//
//      }
//    });

    holder.btDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://papbd-7cfaf-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference cartRef = database.getReference("carts");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String cartId = String.valueOf(cart.getCartId());
        int position = holder.getAdapterPosition();

        cartRef.child(userId).child(cartId).removeValue()
            .addOnCompleteListener(task -> {
              if (task.isSuccessful()) {
                cartList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Album berhasil dihapus dari keranjang", Toast.LENGTH_SHORT).show();
              } else {
                Log.e("FirebaseDelete", "Gagal menghapus cartId: " + cartId, task.getException());
                Toast.makeText(context, "Gagal menghapus album", Toast.LENGTH_SHORT).show();
              }
            });
      }
    });
  }

  @Override
  public int getItemCount() {
    return cartList.size();
  }

  public static class CartViewHolder extends RecyclerView.ViewHolder {
    ImageView ivCover;
    TextView tvTitle, tvArtist, tvPrice;
    Button btDelete;

    public CartViewHolder(@NonNull View itemView) {
      super(itemView);
      ivCover = itemView.findViewById(R.id.ivCover);
      tvTitle = itemView.findViewById(R.id.tvTitle);
      tvArtist = itemView.findViewById(R.id.tvArtist);
      tvPrice = itemView.findViewById(R.id.tvPrice);
      btDelete = itemView.findViewById(R.id.btDelete);
    }
  }
}