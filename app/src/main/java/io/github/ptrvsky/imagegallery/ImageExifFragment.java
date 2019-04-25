package io.github.ptrvsky.imagegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class ImageExifFragment extends Fragment {

    private ImageView imageView;
    private File imageFile;
    private ExifInterface exifInterface;

    private TextView manufacturer;
    private TextView apertureValue;
    private TextView artist;
    private TextView brightness;
    private TextView date;
    private TextView exifVersion;
    private TextView exposureTime;
    private TextView gpsLatitude;
    private TextView gpsLongitude;
    private TextView imageUniqueId;
    private TextView iso;

    public ImageExifFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_exif, container, false);

        imageFile = new File(getArguments().getString("imagePath"));

        try {
             exifInterface = new ExifInterface(imageFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        manufacturer = (TextView) view.findViewById(R.id.manufacturer) ;
        apertureValue = (TextView) view.findViewById(R.id.apertureValue) ;
        artist = (TextView) view.findViewById(R.id.artist);
        brightness = (TextView) view.findViewById(R.id.brightness);
        date = (TextView) view.findViewById(R.id.date);
        exifVersion = (TextView) view.findViewById(R.id.exifVersion);
        exposureTime = (TextView) view.findViewById(R.id.exposureTime);
        gpsLatitude = (TextView) view.findViewById(R.id.gpsLatitude);
        gpsLongitude = (TextView) view.findViewById(R.id.gpsLongitude);
        imageUniqueId = (TextView) view.findViewById(R.id.imageUniqueId);
        iso = (TextView) view.findViewById(R.id.iso);

        manufacturer.setText(exifInterface.getAttribute(ExifInterface.TAG_MAKE));
        apertureValue.setText(exifInterface.getAttribute(ExifInterface.TAG_APERTURE_VALUE));
        artist.setText(exifInterface.getAttribute(ExifInterface.TAG_ARTIST));
        brightness.setText(exifInterface.getAttribute(ExifInterface.TAG_BRIGHTNESS_VALUE));
        date.setText(exifInterface.getAttribute(ExifInterface.TAG_DATETIME));
        exifVersion.setText(exifInterface.getAttribute(ExifInterface.TAG_EXIF_VERSION));
        exposureTime.setText(exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME));
        gpsLatitude.setText(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF) + " " +
                exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
        gpsLongitude.setText(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF) + " " +
                exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
        imageUniqueId.setText(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_UNIQUE_ID));
        iso.setText(exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS));

        Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        setHasOptionsMenu(false);

        return view;
    }

}
