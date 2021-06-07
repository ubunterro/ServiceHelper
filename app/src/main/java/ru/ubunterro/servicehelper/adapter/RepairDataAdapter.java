package ru.ubunterro.servicehelper.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.models.Repair;


public class RepairDataAdapter extends RecyclerView.Adapter<RepairDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Repair> repairs;
    private View.OnClickListener mOnItemClickListener;

    public RepairDataAdapter(List<Repair> repairs){
        this.repairs = repairs;
    }

    public RepairDataAdapter(Context context, List<Repair> repairs) {
        this.repairs = repairs;
        this.inflater = LayoutInflater.from(context);


    }
    @Override
    public RepairDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepairDataAdapter.ViewHolder holder, int position) {
        Repair repair = repairs.get(position);
        //holder.imageView.setImageResource(repair.getImage());
        holder.idView.setText(Integer.toString(repair.getId()));
        holder.nameView.setText(repair.getName());
        holder.clientView.setText(repair.getClient());

        Repair.Status status = repair.getStatus();

        if(status == Repair.Status.DONE)
            holder.imageView.setImageResource(R.drawable.drawable_flag_green);
        else if(status == Repair.Status.IN_WORK)
            holder.imageView.setImageResource(R.drawable.drawable_flag_red);
        else
            holder.imageView.setImageResource(R.drawable.drawable_zip);

    }

    @Override
    public int getItemCount() {
        return repairs.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView idView, nameView, clientView;
        public ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
            idView = (TextView) view.findViewById(R.id.id);
            nameView = (TextView) view.findViewById(R.id.name);
            clientView = (TextView) view.findViewById(R.id.client);

            view.setTag(this);
            view.setOnClickListener(mOnItemClickListener);

        }
    }
}
