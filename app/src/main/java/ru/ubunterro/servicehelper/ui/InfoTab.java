package ru.ubunterro.servicehelper.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.engine.Storage;
import ru.ubunterro.servicehelper.models.Order;
import ru.ubunterro.servicehelper.models.Repair;

public class InfoTab extends Fragment implements View.OnClickListener {
    private static final String TAG = "Tab1Fragment";


    public InfoTab(){

    }

    private Repair r;

    void updateFields(View view){
        TextView textNameInfo = view.findViewById(R.id.textERNameInfo);
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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_tab,container,false);

        Log.d("tabs", "datatab created");


        Intent intent = getActivity().getIntent();
        int repairId = intent.getExtras().getInt("id");

        r = Storage.getRepair(repairId);


        updateFields(view);


        Button orderButton = view.findViewById(R.id.buttonOrder);
        orderButton.setOnClickListener(this);

        Button editButton = view.findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFields(getView());
    }

    @Override
    public void onClick(View view){
        Log.e("SHLP", String.valueOf(view.getId()));
        if (view.getId() == R.id.buttonOrder){
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

                    // айди заказа и имя пользователя сервер получает из авторизации, поэтому передаём пустышки
                    Order o = new Order(-1, "Ремонт " + Integer.toString(r.getId()) + ": " +
                            edt.getText().toString(), new Date(), "");
                    DBAgent.makeOrder(o);

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
            // кнопка редактирования
        } else if (view.getId() == R.id.buttonEdit){

            Intent intent = getActivity().getIntent();
            int repairId = intent.getExtras().getInt("id");

            Intent goToEditActivity = new Intent(getContext(), EditRepairActivity.class);
            goToEditActivity.putExtra("id", repairId);
            this.startActivity(goToEditActivity);

            //DBAgent.setRepairInfo();
            Log.e("SHLP", "edited 2");

        }


    }

}