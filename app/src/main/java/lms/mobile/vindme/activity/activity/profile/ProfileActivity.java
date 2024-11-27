package lms.mobile.vindme.activity.activity.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vindme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import lms.mobile.vindme.activity.activity.cart.CartActivity;
import lms.mobile.vindme.activity.activity.home.HomeActivity;
import lms.mobile.vindme.activity.activity.search.SearchActivity;
import lms.mobile.vindme.activity.activity.wishlist.WishlistActivity;

public class ProfileActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);

    BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
    bottomNav.setSelectedItemId(R.id.profile);

    bottomNav.setOnItemSelectedListener(item -> {
      if (item.getItemId() == R.id.home) {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
      } else if (item.getItemId() == R.id.search) {
        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        finish();
      } else if (item.getItemId() == R.id.cart) {
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
        finish();
      } else if (item.getItemId() == R.id.wishlist) {
        startActivity(new Intent(getApplicationContext(), WishlistActivity.class));
        finish();
      } else if (item.getItemId() == R.id.profile) {
      }
      return false;
    });

    if (savedInstanceState == null) {
      loadFragment(new ProfileFragment());
    }
  }

  private void loadFragment(Fragment fragment) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragmentContainerView, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
  }
}