package ru.ubunterro.servicehelper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoTab extends Fragment {
    private static final String TAG = "Tab1Fragment";


    public InfoTab(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_tab,container,false);

        Log.d("tabs", "datatab created");


        Intent intent = getActivity().getIntent();
        int repairId = intent.getExtras().getInt("id");

        Repair r = RepairsStorage.getRepair(repairId);


        TextView textNameInfo = view.findViewById(R.id.textNameInfo);
        TextView textCompanyInfo =  view.findViewById(R.id.textCompanyInfo);
        TextView textDefInfo =  view.findViewById(R.id.textDefInfo);
        TextView textRecvInfo =  view.findViewById(R.id.textRecvInfo);
        TextView textDescInfo = view.findViewById(R.id.textDescInfo);

        Log.d("tabs", textNameInfo.getText().toString());

        textNameInfo.setText(r.getName());
        textCompanyInfo.setText(r.getClient());
        textDefInfo.setText(r.getDef());
        textRecvInfo.setText(r.getRecv());
        textDescInfo.setText(r.getDescription());

        return view;
    }
}