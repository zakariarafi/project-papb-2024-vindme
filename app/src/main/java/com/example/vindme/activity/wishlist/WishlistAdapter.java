package com.example.vindme.activity.wishlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vindme.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder> {

  private final Context context;
  private final List<Wishlist> wishlistList;

  public WishlistAdapter(Context context, List<Wishlist> wishlistList) {
    this.context = context;
    this.wishlistList = wishlistList;
  }

  public static class WishlistViewHolder extends RecyclerView.ViewHolder {
    ImageView ivCover;
    TextView tvArtist, tvDescription, tvPrice;
    Button btDelete;

    public WishlistViewHolder(@NonNull View itemView) {
      super(itemView);
      ivCover = itemView.findViewById(R.id.ivCover);
      tvArtist = itemView.findViewById(R.id.tvArtist);
      tvDescription = itemView.findViewById(R.id.tvDescription);
      tvPrice = itemView.findViewById(R.id.tvPrice);
      btDelete = itemView.findViewById(R.id.btDelete);
    }
  }

  @NonNull
  @Override
  public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.row_view_wishlist, parent, false);
    return new WishlistViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
    Wishlist wishlist = wishlistList.get(position);
    holder.tvArtist.setText(wishlist.getNamaArtist());
    holder.tvDescription.setText(wishlist.getJudulAlbum());
    holder.tvPrice.setText("Rp. " + wishlist.getHargaAlbum());

//    holder.ivCover.setImageResource(wishlist.getCoverAlbum());

//    holder.ivCover.setOnClickListener(v -> {
//      Toast.makeText(context, wishlist.getJudulAlbum(), Toast.LENGTH_SHORT).show();
//    });
//    holder.btDelete.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        AppDatabase db = AppDatabase.getInstance(context);
//        db.wishlistDao().deleteWishlist(wishlist.getIdWishlist());
//        wishlistList.remove(holder.getAdapterPosition());
//        notifyItemRemoved(holder.getAdapterPosition());
//        Toast.makeText(context, "Wishlist Berhasil dihapus", Toast.LENGTH_SHORT).show();
//      }
//    });

    holder.btDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("wishlist")
                .child(wishlist.getIdWishlist());

        reference.removeValue().addOnCompleteListener(task -> {
          if (task.isSuccessful()) {
            int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
              wishlistList.remove(position);
              notifyItemRemoved(position);
            }
            Toast.makeText(context, "Wishlist berhasil dihapus", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(context, "Gagal menghapus wishlist", Toast.LENGTH_SHORT).show();
            Log.e("FirebaseError", task.getException() != null ? task.getException().getMessage() : "Unknown error");
          }
        });
      }
    });
  }

  @Override
  public int getItemCount() {
    return wishlistList.size();
  }
}
