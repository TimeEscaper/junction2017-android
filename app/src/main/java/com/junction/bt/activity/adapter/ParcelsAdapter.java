package com.junction.bt.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junction.bt.R;
import com.junction.bt.activity.ParcelInfoActivity;
import com.junction.bt.api.model.Parcel;

import java.util.List;

/**
 * Created by sibirsky on 25.11.17.
 */

public class ParcelsAdapter extends RecyclerView.Adapter<ParcelsAdapter.ParcelViewHolder> {

    private final List<Parcel> parcels;
    private final LayoutInflater inflater;
    private final Context context;

    public ParcelsAdapter(final Context context, List<Parcel> places) {
        this.inflater = LayoutInflater.from(context);
        this.parcels = places;
        this.context = context;
    }

    @Override
    public ParcelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ParcelViewHolder(inflater.inflate(R.layout.parcel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ParcelViewHolder holder, int position) {
        holder.bind(parcels.get(position).getParcelId(), parcels.get(position).getAlias());
    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }

    public class ParcelViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private String parcelAlias;
        private Integer parcelId;

        public ParcelViewHolder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.parcel_alias);
        }

        public void bind(final Integer parcelId, String parcelAlias) {
            this.parcelId = parcelId;
            this.parcelAlias = parcelAlias;
            textView.setText(parcelAlias);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ParcelInfoActivity.class);
                    intent.putExtra(ParcelInfoActivity.PARCEL_ID_TAG, parcelId);
                    context.startActivity(intent);
                }
            });
        }
    }
}
