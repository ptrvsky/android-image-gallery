package io.github.ptrvsky.imagegallery;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

public class ImageSlideFragment extends Fragment {

    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private File[] images;

    public ImageSlideFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_slide, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        mPager = (ViewPager) view.findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(getArguments().getInt("viewPagerPosition"));
        return view;
    }

    public void setImages(File[] images) {
        this.images = images;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ImageFullViewFragment();
            Bundle args = new Bundle();
            args.putString("imagePath", images[position].getAbsolutePath());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    }


}