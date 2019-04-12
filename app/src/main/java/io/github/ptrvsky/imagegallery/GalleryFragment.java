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
    private FragmentListener fragmentListener;  // OnClick listener created to deliver information about image click to main activity

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sp = getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        spe = sp.edit();
        layoutId = sp.getInt("selected_style_index", 0);

        View view;
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        MyAdapter adapter;  // Adapter with image data

        if (layoutId == 0) {    // Setting up recycler view to show images from selected directory in grid view style

            view = inflater.inflate(R.layout.fragment_grid, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.grid_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MyAdapter(R.layout.cell_grid, sp.getString("directory_path", ""));

        } else {    // Setting up recycler view to show images from selected directory in list view style

            view = inflater.inflate(R.layout.fragment_list, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MyAdapter(R.layout.cell_list, sp.getString("directory_path", ""));

        }

        MyAdapter.AdapterListener listener = new MyAdapter.AdapterListener() {
            @Override
            public void onItemClick(File image) {
                fragmentListener.onRecyclerViewItemClicked(image);
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
            spe.putInt("selected_style_index", 1);
            spe.commit();
            ((MainActivity) getActivity()).startFragment();
            return true;
        }
        if (id == R.id.show_as_grid) {
            spe.putInt("selected_style_index", 0);
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
        void onRecyclerViewItemClicked(File image);
    }

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

}

