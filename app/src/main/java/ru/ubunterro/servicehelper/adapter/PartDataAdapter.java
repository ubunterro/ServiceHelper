package ru.ubunterro.servicehelper.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.models.Part;

public class PartDataAdapter extends RecyclerView.Adapter<PartDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Part> parts;
    private View.OnClickListener mOnItemClickListener;

    public PartDataAdapter(List<Part> parts){
        this.parts = parts;
    }

    public PartDataAdapter(Context context, List<Part> parts) {
        this.parts = parts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PartDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.part_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartDataAdapter.ViewHolder holder, int position) {
        Part part = parts.get(position);

        //Glide.with(holder.imageView.getContext()).load("http://goo.gl/gEgYUd").into(holder.imageView);
        Log.w("SHLP", part.getPhoto());
        Glide.with(holder.imageView.getContext()).load(part.getPhoto()).into(holder.imageView);



        holder.nameView.setText(part.getName() + " ×" + Double.toString(part.getAmount()));
        holder.SNView.setText(part.getSerialNumber());
        holder.descView.setText(part.getDescription());

    }

    @Override
    public int getItemCount() {
        return parts.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        final TextView  nameView, SNView, descView;
        final ImageView imageView;

        public ViewHolder(View view){
            super(view);

            imageView = (ImageView) view.findViewById(R.id.imageViewPartListItem);
            nameView = (TextView) view.findViewById(R.id.textViewPartListItemName);
            SNView = (TextView) view.findViewById(R.id.textViewPartListItemSN);
            descView = (TextView) view.findViewById(R.id.textViewPartListItemDesc);

            view.setTag(this);
            view.setOnClickListener(mOnItemClickListener);

        }
    }
}