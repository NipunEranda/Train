package com.train;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.train.utils.DatabaseHelper;

public class Train extends Fragment implements View.OnClickListener {

    Button addTBtn, viewBtn, editBtnT;
    DatabaseHelper trainDB;

    @Nullable
    @Override //start
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trains, container, false);

        trainDB = new DatabaseHelper(getContext());
        addTBtn = view.findViewById(R.id.addTbtn);
        addTBtn.setOnClickListener(this);
        viewBtn = view.findViewById(R.id.viewTbtn);
        viewBtn.setOnClickListener(this);
        editBtnT = view.findViewById(R.id.editTbtn);
        editBtnT.setOnClickListener(this);
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
                /*getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewTrain()).addToBackStack(null).commit();*/
                Cursor res = trainDB.getAllTrains();
                if(res.getCount() == 0){
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Train Id: "+res.getString(0)+"\n");
                    buffer.append("Train Name: "+res.getString(1)+"\n");
                    buffer.append("Starting Station: "+res.getString(2)+"\n");
                    buffer.append("Destination: "+res.getString(3)+"\n\n");
                }

                showMessage("Data", buffer.toString());
                break;
            case  R.id.editTbtn:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTrain()).addToBackStack(null).commit();
                break;
        }

    }//end

    public  void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}