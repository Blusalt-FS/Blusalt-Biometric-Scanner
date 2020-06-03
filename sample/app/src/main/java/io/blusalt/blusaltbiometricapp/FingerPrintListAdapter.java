package io.blusalt.blusaltbiometricapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import io.blusalt.blusalt_biometric_sdk.CaptureComponentData;

class FingerPrintListAdapter extends RecyclerView.Adapter<FingerPrintListAdapter.ListViewHolder> {

    private LinkedList<CaptureComponentData> fingerPrints;
    private Context context;

    public FingerPrintListAdapter(LinkedList<CaptureComponentData> fingerPrints, Context context) {
        this.fingerPrints = fingerPrints;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_finger_print_image,parent, false);
        ListViewHolder holder =  new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.setDataObjects(fingerPrints);
    }

    @Override
    public int getItemCount() {
        return fingerPrints.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView text;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            image =itemView.findViewById(R.id.imageFingerPrint);
            text = itemView.findViewById(R.id.textFingerPrintName);

        }

        public void setDataObjects(LinkedList<CaptureComponentData> fingerPrints){
            image.setImageBitmap(fingerPrints.get(getAdapterPosition()).getImage());
            text.setText(fingerPrints.get(getAdapterPosition()).getCaputreName());
        }
    }
}
