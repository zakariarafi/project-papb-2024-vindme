package com.example.vindme.activity.wishlist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Wishlist")
public class Wishlist implements Serializable {

  @SerializedName("idWishlist")
  @PrimaryKey
  @ColumnInfo(name = "idWishlist")
  @NonNull
  String idWishlist;

  @SerializedName("artis")
  @ColumnInfo(name = "nama")
  String namaArtist;

  @SerializedName("judul")
  @ColumnInfo(name = "judul")
  String judulAlbum;

  @SerializedName("harga")
  @ColumnInfo(name = "harga")
  String hargaAlbum;

  public Wishlist(String idWishlist, String namaArtist, String judulAlbum, String hargaAlbum) {
    super();
    this.idWishlist = idWishlist;
    this.namaArtist = namaArtist;
    this.judulAlbum = judulAlbum;
    this.hargaAlbum = hargaAlbum;
  }

  public Wishlist(){};

  public String getIdWishlist() {
    return idWishlist;
  }
  public void setIdWishlist(String idWishlist) {
    this.idWishlist = idWishlist;
  }

  public String getNamaArtist() {
    return namaArtist;
  }

  public String getJudulAlbum() {
    return judulAlbum;
  }

  public String getHargaAlbum() {
    return hargaAlbum;
  }
}
