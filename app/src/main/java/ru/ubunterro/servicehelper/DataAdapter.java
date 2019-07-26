package ru.ubunterro.servicehelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;



public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Repair> repairs;
    private View.OnClickListener mOnItemClickListener;

    public DataAdapter(List<Repair> repairs){
        this.repairs = repairs;
    }

    DataAdapter(Context context, List<Repair> repairs) {
        this.repairs = repairs;
        this.inflater = LayoutInflater.from(context);


    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
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
