package io.github.ptrvsky.imagegallery;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor spe;

    FragmentManager fragmentManager = getSupportFragmentManager();
    GalleryFragment.FragmentListener fragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        spe = sp.edit();
        //spe.clear().apply(); // Clear shared preferences (for testing)

        fragmentListener = new GalleryFragment.FragmentListener() {
            @Override
            public void onRecyclerViewItemClicked(File[] images, int imageNumber) {
                startSliderFragment(images, imageNumber);
            }
        };
        startFragment();
    }

    // Fragments
    public void startFragment() {
        int selectedStyle = sp.getInt("selectedStyleIndex", -1);
        String directoryPath = sp.getString("directoryPath", "");
        File tmpFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + directoryPath);
        if (tmpFile.exists() && tmpFile.isDirectory() && !directoryPath.equals("")) {
            if (selectedStyle == 0 || selectedStyle == 1) {
                startGalleryFragment();
            } else {
                startSettingsFragment();
            }
        } else {
            startSettingsFragment();
            if (!directoryPath.equals("")) {
                Toast.makeText(getApplicationContext(), "Directory doesn't exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startGalleryFragment() {
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setFragmentListener(fragmentListener);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, galleryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startSliderFragment(File[] images, int imageNumber) {
        ImageSlideFragment imageSlideFragment = new ImageSlideFragment();
        Bundle args = new Bundle();
        args.putInt("viewPagerPosition", imageNumber);
        imageSlideFragment.setArguments(args);
        imageSlideFragment.setImages(images);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, imageSlideFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startDetailsFragment(File image) {
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setImageFile(image);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Method that shows/hides action bar
    public void toggleActionBar(View view) {
        if (getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

}
