package com.train;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Train extends Fragment implements View.OnClickListener {

    Button addTBtn, viewBtn;

    @Nullable
    @Override //start
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trains, container, false);
        addTBtn = view.findViewById(R.id.addTbtn);
        addTBtn.setOnClickListener(this);

        viewBtn = view.findViewById(R.id.viewTbtn);
        viewBtn.setOnClickListener(this);
        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Trains");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addTbtn:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddTrain()).addToBackStack(null).commit();
                break;
            case R.id.viewTbtn:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewTrain()).addToBackStack(null).commit();
                break;
        }

    }//end
}