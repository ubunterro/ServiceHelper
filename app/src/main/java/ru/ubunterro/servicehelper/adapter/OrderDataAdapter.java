package ru.ubunterro.servicehelper.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.ubunterro.servicehelper.R;
import ru.ubunterro.servicehelper.models.Order;

public class OrderDataAdapter extends RecyclerView.Adapter<OrderDataAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Order> orders;
    private View.OnClickListener mOnItemClickListener;

    public OrderDataAdapter(List<Order> orders){
        this.orders = orders;
    }

    public OrderDataAdapter(Context context, List<Order> orders) {
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public OrderDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.order_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderDataAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.idView.setText(Integer.toString(order.getId()));
        holder.timeView.setText(order.getLocalizedDatetime());
        holder.userView.setText(order.getUserOrderedName());
        holder.textView.setText(order.getText());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        final TextView idView, textView, timeView, userView;
        public ViewHolder(View view){
            super(view);

            idView = (TextView) view.findViewById(R.id.textViewOrderListId);
            textView = (TextView) view.findViewById(R.id.textViewPartListItemName);
            timeView = (TextView) view.findViewById(R.id.textViewPartListItemDesc);
            userView = (TextView) view.findViewById(R.id.textViewPartListItemSN);

            view.setTag(this);
            view.setOnClickListener(mOnItemClickListener);

        }
    }
}