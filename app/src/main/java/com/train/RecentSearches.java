package com.train;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecentSearches extends Fragment implements AdapterView.OnItemClickListener {

    ListView recentSearches;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_searches, container, false);

        recentSearches = (ListView)view.findViewById(R.id.userTimeTableList);

        ArrayAdapter<String> startStationAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.recentSearch));
        recentSearches.setAdapter(startStationAdapter);
        recentSearches.setOnItemClickListener(this);

        return view;
    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Recent Timetables");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv = (TextView)view;
        Toast.makeText(getContext(), tv.getText(), Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable()).addToBackStack(null).commit();
    }
}