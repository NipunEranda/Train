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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecentSearches extends Fragment implements View.OnClickListener {

    ListView recentSearchList;
    ArrayList<String> names = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Button viewRecentSearches, deleteRecentSearches, clearRecentSearch;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_searches, container, false);

        deleteRecentSearches = (Button) view.findViewById(R.id.recentViewDeleteBtn);
        recentSearchList = (ListView) view.findViewById(R.id.listView1);
        viewRecentSearches = (Button) view.findViewById(R.id.recentViewBtn);
        clearRecentSearch = (Button) view.findViewById(R.id.clearRecentBtn);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, names);

        recentSearchList.setAdapter(adapter);
        deleteRecentSearches.setOnClickListener(this);
        viewRecentSearches.setOnClickListener(this);
        clearRecentSearch.setOnClickListener(this);

        if (adapter.getCount() == 0) {
            adapter.add("lol1");
            adapter.add("lol2");
            adapter.add("lol3");
            adapter.add("lol4");
            adapter.add("lol5");
            adapter.add("lol6");
        }else{

        }

        return view;
    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Recent Timetables");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.recentViewBtn:
                view();
                break;

            case R.id.recentViewDeleteBtn:
                delete();
                break;

            case R.id.clearRecentBtn:
                clear();
                break;
        }
    }

    private void delete() {
        int pos = recentSearchList.getCheckedItemPosition();

        if (pos < 0 || adapter.getCount() <= 0) {

        } else {
            adapter.remove(names.get(pos));
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        adapter.clear();
    }

    private void view() {

        if (recentSearchList.getCheckedItemPosition() < 0 || adapter.getCount() <= 0) {
        } else {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable()).addToBackStack(null).commit();
        }
    }
}