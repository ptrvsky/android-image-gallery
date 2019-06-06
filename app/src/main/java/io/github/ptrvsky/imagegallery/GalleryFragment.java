package io.github.ptrvsky.imagegallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;

public class GalleryFragment extends Fragment {

    private SharedPreferences sp;
    private SharedPreferences.Editor spe;
    private int layoutId;
    private MyAdapter adapter;  // Adapter with image data
    private FragmentListener fragmentListener;  // OnClick listener created to deliver information about image click to main activity
    private int spanCount = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Gallery");

        sp = getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        spe = sp.edit();
        layoutId = sp.getInt("selectedStyleIndex", 0);

        View view;
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;

        if (layoutId == 0) {    // Setting up recycler view to show images from selected directory in grid view style

            view = inflater.inflate(R.layout.fragment_grid, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.grid_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MyAdapter(R.layout.cell_grid, sp.getString("directoryPath", ""), spanCount);

        } else {    // Setting up recycler view to show images from selected directory in list view style

            view = inflater.inflate(R.layout.fragment_list, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MyAdapter(R.layout.cell_list, sp.getString("directoryPath", ""), spanCount);

        }

        MyAdapter.AdapterListener listener = new MyAdapter.AdapterListener() {
            @Override
            public void onItemClick(File[] images, int imageNumber) {
                fragmentListener.onRecyclerViewItemClicked(images, imageNumber);
            }
        };

        adapter.setAdapterListener(listener);
        recyclerView.setAdapter(adapter);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (layoutId == 0) {
            inflater.inflate(R.menu.menu_grid, menu);
        } else {
            inflater.inflate(R.menu.menu_list, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.show_as_list) {
            spe.putInt("selectedStyleIndex", 1);
            spe.commit();
            ((MainActivity) getActivity()).startFragment();
            return true;
        }
        if (id == R.id.show_as_grid) {
            spe.putInt("selectedStyleIndex", 0);
            spe.commit();
            ((MainActivity) getActivity()).startFragment();
            return true;
        }
        if (id == R.id.settings) {
            ((MainActivity) getActivity()).startSettingsFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface FragmentListener {
        void onRecyclerViewItemClicked(File[] images, int imageNumber);
    }

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

}

