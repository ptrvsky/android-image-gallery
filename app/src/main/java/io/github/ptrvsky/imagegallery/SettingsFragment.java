package io.github.ptrvsky.imagegallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String[] styles = {"Grid", "List"};
    private SharedPreferences sp;
    private SharedPreferences.Editor spe;
    private TextView textView;  // TextView with directory path

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Settings");
        sp = getActivity().getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        spe = sp.edit();
        textView = (TextView) view.findViewById(R.id.directoryPathTextView);
        textView.setText("sdcard/" + sp.getString("directoryPath", ""));

        // Creating spinner which let user choose gallery style
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, styles);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
        spinner.setSelection(sp.getInt("selectedStyleIndex", 0));

        setHasOptionsMenu(true);

        return view;

    }

    // OnClick method for "Exit" button click
    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).startFragment();
    }

    // Spinner methods
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0 || pos == 1) {
            spe.putInt("selectedStyleIndex", pos);
            spe.commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


}
