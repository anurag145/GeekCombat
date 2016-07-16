package com.honeyneutrons.undoswipe;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honeyneutrons.undoswipe.helper.ItemTouchHelperAdapter;
import com.honeyneutrons.undoswipe.helper.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    private Dialog dialog;
    public interface OnStartDragListener {


    }

    private final Context context;
    static List<Item> itemList = new ArrayList<>();
    private TextView tvNumber;


   private final OnStartDragListener dragStartListener;

    public ItemAdapter(Context context, OnStartDragListener dragStartListener, TextView tvNumber) {
        this.context = context;
        this.dragStartListener=dragStartListener;
        this.tvNumber=tvNumber;

    }

    @Override
    public void onItemDismiss(final int position) {

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {


    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {

        final Item item = itemList.get(position);
        itemViewHolder.tvItemName.setText(item.getItemName());

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_adapter, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder,View.OnClickListener {

        protected RelativeLayout container;
        protected TextView tvItemName;



        public ItemViewHolder(final View v) {
            super(v);
            container = (RelativeLayout) v.findViewById(R.id.container);
            tvItemName = (TextView) v.findViewById(R.id.tvItemName);

        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onItemSelected(Context context) {

            dialog= new Dialog(context);
            dialog.setContentView(R.layout.photo_dialog);
            dialog.setCancelable(true);
            dialog.show();
            TextView textView= (TextView)dialog.findViewById(R.id.texty);
            textView.setText("PUSSY");

        }

        @Override
        public void onItemClear(Context context) {

        }

    }

}
