package lms.mobile.vindme.activity.activity.home;

public class Album {
  int coverAlbum;
  String artis, album, harga;

  public Album(int coverAlbum, String artis, String album, String harga) {
    this.coverAlbum = coverAlbum;
    this.artis = artis;
    this.album = album;
    this.harga = harga;
  }

  public int getCoverAlbum() {
    return coverAlbum;
  }

  public String getArtis() {
    return artis;
  }

  public String getAlbum() {
    return album;
  }

  public String getHarga() {
    return harga;
  }
}
