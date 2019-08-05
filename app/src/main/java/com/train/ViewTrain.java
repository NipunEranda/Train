package com.train;

import android.content.Context;
import android.net.Uri;
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


public class ViewTrain extends Fragment {

    View view;
    ListView tView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_train, container, false);
        // Inflate the layout for this fragment
        tView = (ListView)view.findViewById(R.id.trainView);
        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("ViewTrain");


        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.viewTrain));

        tView.setAdapter(listAdapter);

        tView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //add a new fragment
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
            }
        });

    }


}
