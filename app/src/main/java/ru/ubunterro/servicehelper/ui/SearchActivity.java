package ru.ubunterro.servicehelper.ui;

import android.content.Intent;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.adapter.RepairDataAdapter;
import ru.ubunterro.servicehelper.engine.DBAgent;
import ru.ubunterro.servicehelper.engine.Storage;
import ru.ubunterro.servicehelper.models.Repair;

public class SearchActivity extends AppCompatActivity {

    List<Repair> foundRepairs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = foundRepairs.get(position).getId();

            Intent goToRepairDetailsActivity = new Intent(getBaseContext(), RepairDetailsActivity.class);
            goToRepairDetailsActivity.putExtra("id", id);
            startActivity(goToRepairDetailsActivity);
        }
    };

    void redrawList(List<Repair> foundRepairs){
        //Log.w("Volley", "redrawn!");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listSearch);
        // создаем адаптер
        RepairDataAdapter adapter = new RepairDataAdapter(this, foundRepairs);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    public void search(View v) throws JSONException {
        foundRepairs.clear();

        TextView textSearch = findViewById(R.id.textSearchResult);

        TextInputEditText query = findViewById(R.id.query);
        String searchQuery = query.getText().toString();

        JSONObject searchObject = new JSONObject();

        JSONArray array = DBAgent.jsonArrayRepairs;



        for (int i = 0; i < array.length(); i++) {
            JSONObject currObject = array.getJSONObject(i);
            //name = currObject.getString("name");
            int id = currObject.getInt("id");
            String sId = Integer.toString(id);
            Repair r = Storage.getRepair(id);
            String client = r.getClient();
            String desc = r.getDescription();
            String name = r.getName();


            if (sId.equals(searchQuery)){
                Intent goToRepairDetailsActivity = new Intent(getBaseContext(), RepairDetailsActivity.class);
                goToRepairDetailsActivity.putExtra("id", id);
                startActivity(goToRepairDetailsActivity);
                break;
            }


            if     (Pattern.compile(Pattern.quote(searchQuery), Pattern.CASE_INSENSITIVE).matcher(name).find() ||
                    Pattern.compile(Pattern.quote(searchQuery), Pattern.CASE_INSENSITIVE).matcher(desc).find() ||
                    Pattern.compile(Pattern.quote(searchQuery), Pattern.CASE_INSENSITIVE).matcher(client).find())
            {
                searchObject = currObject;
                //textSearch.setText(textSearch.getText() + Integer.toString(searchObject.getInt("id")) + " ");
                foundRepairs.add(Storage.getRepair(searchObject.getInt("id")));

            }
        }

        redrawList(foundRepairs);



        //textSearch.setText(searchObject.getString("name"));
        //return searchObject;

    }
}
