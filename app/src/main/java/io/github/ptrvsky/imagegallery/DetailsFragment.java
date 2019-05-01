package io.github.ptrvsky.imagegallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;

public class DetailsFragment extends Fragment {

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private File imageFile;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();

        FragmentManager fragmentManager = getChildFragmentManager();
        mPager = (ViewPager) view.findViewById(R.id.pagerDetails);
        pagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);
        mPager.setAdapter(pagerAdapter);
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0) {
                fragment = new ImageDataFragment();
                Bundle args = new Bundle();
                args.putString("imagePath", imageFile.getAbsolutePath());
                fragment.setArguments(args);
            } else {
                fragment = new ImageExifFragment();
                Bundle args = new Bundle();
                args.putString("imagePath", imageFile.getAbsolutePath());
                fragment.setArguments(args);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Details";
            } else {
                return "EXIF";
            }
        }

    }
}
