package io.github.ptrvsky.imagegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ImageDataFragment extends Fragment {

    private ImageView imageView;
    private File imageFile;
    private TextView fileName;
    private TextView resolution;
    private TextView size;

    public ImageDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_data, container, false);

        imageFile = new File(getArguments().getString("imagePath"));
        Bitmap bmp = BitmapFactory.decodeFile(getArguments().getString("imagePath"));

        imageView = (ImageView) view.findViewById(R.id.imageViewInDetails);
        imageView.setImageBitmap(bmp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        fileName = (TextView) view.findViewById(R.id.fileNameDetail);
        resolution = (TextView) view.findViewById(R.id.resolutionDetail);
        size = (TextView) view.findViewById(R.id.sizeDetail);

        fileName.setText(imageFile.getName());
        resolution.setText(bmp.getHeight() + "x" + bmp.getWidth() + " px");
        size.setText(String.valueOf(imageFile.length() / 1024) + " kB");

        return view;
    }

}
