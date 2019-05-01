package io.github.ptrvsky.imagegallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
        String directoryPath = sp.getString("directoryPath", null);
        File tmpFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + directoryPath);
        if (tmpFile.exists() && tmpFile.isDirectory() && directoryPath != null) {
            if (selectedStyle == 0 || selectedStyle == 1) {
                startGalleryFragment();
            } else {
                startSettingsFragment();
            }
        } else {
            startSettingsFragment();
            if (directoryPath != null) {
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
        fragmentTransaction.commitAllowingStateLoss();
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

    public void pickDirectory(View view) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(i, "Choose directory"), 9999);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 9999:
                String directoryPath;
                try {
                    directoryPath = data.getData().getPath().split(":")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    directoryPath = "";
                }
                spe.putString("directoryPath", directoryPath);
                spe.commit();
                startSettingsFragment();
                break;
        }
    }

}
