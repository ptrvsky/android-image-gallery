package io.github.ptrvsky.imagegallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FilenameFilter;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private int cellLayoutId;
    private AdapterListener adapterListener;    // OnClick listener created to deliver information about image click to main activity
    private File dir;  // Directory with images
    private final String[] extensions = new String[]{"png", "jpg"}; // Image formats acceptable in gallery
    private File images[];  // Collection of all images files taken from selected directory
    private final FilenameFilter image_filter = new FilenameFilter() {  // Filter created to skip non-image files in selected directory
        @Override
        public boolean accept(File dir, String name) {
            for (String ext : extensions) {
                if (name.endsWith("." + ext)) {
                    return true;
                }
            }
            return false;
        }
    };

    public MyAdapter(int cellLayoutId, String directoryPath) {
        this.cellLayoutId = cellLayoutId;
        this.dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + directoryPath);
        this.images = dir.listFiles(image_filter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {   // Single image view holder

        private ImageView img;
        private TextView imageName;
        private TextView resolution;
        private TextView size;
        private File imageFile;
        private int imageNumber;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            if (cellLayoutId == R.layout.cell_list) {
                imageName = (TextView) view.findViewById(R.id.imageName);
                resolution = (TextView) view.findViewById(R.id.resolution);
                size = (TextView) view.findViewById(R.id.size);
            }
            view.setOnClickListener(new View.OnClickListener() {    // Set onClick Listener to view
                @Override
                public void onClick(View view) {
                    if (adapterListener != null) {
                        adapterListener.onItemClick(images, imageNumber);
                    }
                }
            });
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(cellLayoutId, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, final int i) {
        // Setting image to ImageView
        viewHolder.imageFile = images[i];

        // Loading a bitmap:
        // To optimize memory usage, at first BitmapFactory option is set to true so we can get image size without loading image to memory.
        // Then calculateInSampleSize method, based on real image size and size we want, return inSampleSize option value.
        // At the end, inJustDecodeBounds is set back to false so we can load image with properly set inSampleSize option.

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmpWithoutLoadingToMemory = BitmapFactory.decodeFile(viewHolder.imageFile.getAbsolutePath(), options);
        int imageRealHeight = options.outHeight;
        int imageRealWidth = options.outWidth;
        options.inSampleSize = calculateInSampleSize(options, 300, 300);
        options.inJustDecodeBounds = false;
        final Bitmap bmp = BitmapFactory.decodeFile(viewHolder.imageFile.getAbsolutePath(), options);

        viewHolder.img.setImageBitmap(bmp);
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (cellLayoutId == R.layout.cell_list) {    // Setting additional information (only if we use list layout)
            viewHolder.imageName.setText(viewHolder.imageFile.getName());
            viewHolder.resolution.setText(imageRealWidth + "x" + imageRealHeight + " px");
            viewHolder.size.setText(String.valueOf(images[i].length() / 1024) + " kB");
        }
        viewHolder.imageNumber = i;
    }

    @Override
    public int getItemCount() {
        try {
            return images.length;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public interface AdapterListener {
        void onItemClick(File[] images, int imageNumber);
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
