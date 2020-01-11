package ru.ubunterro.servicehelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class InfoTab extends Fragment implements View.OnClickListener {
    private static final String TAG = "Tab1Fragment";


    public InfoTab(){

    }

    private Repair r;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_tab,container,false);

        Log.d("tabs", "datatab created");


        Intent intent = getActivity().getIntent();
        int repairId = intent.getExtras().getInt("id");

        r = RepairsStorage.getRepair(repairId);


        TextView textNameInfo = view.findViewById(R.id.textNameInfo);
        TextView textCompanyInfo =  view.findViewById(R.id.textCompanyInfo);
        TextView textDefInfo =  view.findViewById(R.id.textDefInfo);
        TextView textRecvInfo =  view.findViewById(R.id.textRecvInfo);
        TextView textDescInfo = view.findViewById(R.id.textDescInfo);
        TextView textSerialInfo = view.findViewById(R.id.textSerialInfo);
        TextView textResponsibleInfo = view.findViewById(R.id.textResponsibleInfo);

        Log.d("tabs", textNameInfo.getText().toString());

        textNameInfo.setText(r.getName());
        textCompanyInfo.setText(r.getClient());
        textDefInfo.setText(r.getDef());
        textRecvInfo.setText(r.getRecv());
        textDescInfo.setText(r.getDescription());
        textSerialInfo.setText(r.getSerialNumber());
        textResponsibleInfo.setText(r.getResponsible());

        Button orderButton = view.findViewById(R.id.buttonOrder);
        orderButton.setOnClickListener(this);

        return view;
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonOrder:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.order_dialog, null);
                final View parentView = view;
                dialogBuilder.setView(dialogView);

                final TextInputEditText edt = dialogView.findViewById(R.id.textEditPartDesc);

                dialogBuilder.setTitle("Заказ детали");
                //dialogBuilder.setMessage("Ну да");
                dialogBuilder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("password", "123789456");
                        params.put("num", Integer.toString(r.getId()));
                        params.put("dev", r.getName());
                        params.put("fio", SettingsManager.getFIO(getContext()));
                        params.put("order", edt.getText().toString());

                        DBAgent.makePostRequest("http://149.154.68.13/order.php", params);
                        Snackbar.make(parentView, "Заказ отправлен", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                    }
                });
                dialogBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();


        }
    }
}