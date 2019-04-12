package io.github.ptrvsky.imagegallery;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.Fragment;
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
            public void onRecyclerViewItemClicked(File image) {
                startImageFullViewFragment(image);
            }
        };
        startFragment();
    }

    // Fragments
    public void startFragment() {
        int selectedStyle = sp.getInt("selected_style_index", -1);
        String directoryPath = sp.getString("directory_path", "");
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
        Fragment galleryFragment = new GalleryFragment();
        ((GalleryFragment) galleryFragment).setFragmentListener(fragmentListener);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, galleryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startSettingsFragment() {
        Fragment settingsFragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startImageFullViewFragment(File file) {
        Fragment imageFullViewFragment = new ImageFullViewFragment();
        Bundle args = new Bundle();
        args.putString("imagePath", file.getAbsolutePath());
        imageFullViewFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, imageFullViewFragment);
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
