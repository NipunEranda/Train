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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;


public class EditTrain extends Fragment implements View.OnClickListener{

    Button updBtn, canlEBtn, deleBtn;
    EditText edTid, edTrainName, eEditStart, eEditEnd;
    View view;
    DatabaseHelper trainDB;

    @Nullable
    @Override //start
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_train, container, false);

        trainDB = new DatabaseHelper(getContext());
        edTid = view.findViewById(R.id.editTrainId);
        edTrainName = view.findViewById(R.id.editTrainName);
        eEditStart = view.findViewById(R.id.editTrainStart);
        eEditEnd = view.findViewById(R.id.editTrainEnd);
        updBtn = view.findViewById(R.id.udateTbtn);
        updBtn.setOnClickListener(this);
        canlEBtn = view.findViewById(R.id.cancelTbtn);
        canlEBtn.setOnClickListener(this);
        deleBtn = view.findViewById(R.id.editDeletebtn);
        deleBtn.setOnClickListener(this);



        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("EditTrains");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.udateTbtn:
                boolean isUpdate = trainDB.udateATrain(edTid.getText().toString(),
                        edTrainName.getText().toString(),
                        eEditStart.getText().toString(),
                        eEditEnd.getText().toString());
                if(isUpdate == true){
                    Toast.makeText(this.getActivity(),"Data Updated", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
                }else
                    Toast.makeText(this.getActivity(),"Error", Toast.LENGTH_SHORT).show();

            case R.id.cancelTbtn:
                /*AlertDialog.Builder canBuilder = new AlertDialog.Builder(this.getActivity());
                canBuilder.setMessage("Are you Sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert1 = canBuilder.create();
                alert1.show();*/
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
                break;
            case R.id.editDeletebtn:
                Integer deleteRows = trainDB.deleteATrain(edTid.getText().toString());
                if(deleteRows != 0)
                    Toast.makeText(this.getActivity(),"Data Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this.getActivity(),"Data not Deleted", Toast.LENGTH_SHORT).show();

        }

    }
}

