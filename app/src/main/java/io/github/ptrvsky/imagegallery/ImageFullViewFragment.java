package io.github.ptrvsky.imagegallery;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;

public class ImageFullViewFragment extends Fragment {

    private String imagePath;   // String with selected image file path

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_full_view, container, false);
        imagePath = getArguments().getString("imagePath");  // Get image path from fragment argument
        // Set image for ImageView
        final Bitmap bmp = BitmapFactory.decodeFile(imagePath);
        ImageView img = (ImageView) view.findViewById(R.id.imageViewFull);
        img.setImageBitmap(bmp);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_image_full_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.set_as_wallpaper) {
            setWallpaper(BitmapFactory.decodeFile(imagePath));
            return true;
        }
        if (id == R.id.share) {
            shareImage(new File(imagePath));
            return true;
        }
        if (id == R.id.details) {
            ((MainActivity) getActivity()).startDetailsFragment(new File(imagePath));
        }
        return super.onOptionsItemSelected(item);
    }

    // Method that set image given in argument to system wallpaper
    public void setWallpaper(Bitmap bmp) {
        WallpaperManager m = WallpaperManager.getInstance(getContext());
        try {
            m.setBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method that open share intent with image given in argument
    public void shareImage(File image) {
        Intent intent3 = new Intent(Intent.ACTION_SEND);
        intent3.setType("image/jpg");
        intent3.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
        startActivity(Intent.createChooser(intent3, "Share image"));
    }


}

