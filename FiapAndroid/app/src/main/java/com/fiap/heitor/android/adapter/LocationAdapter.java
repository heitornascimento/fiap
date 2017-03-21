package com.fiap.heitor.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fiap.heitor.android.R;
import com.fiap.heitor.android.model.Place;
import com.fiap.heitor.android.persistence.DAO;

import java.util.List;

/**
 * Created by heitornascimento on 19/03/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Place> mData;
    private ViewHolder mViewHolder;
    private Context mContext;
    private OnItemRecyclerClickLister mListener;

    public LocationAdapter(List<Place> data, Context context, OnItemRecyclerClickLister itemClick) {
        mContext = context;
        mData = data;
        mListener = itemClick;
    }

    public void setData(List<Place> data) {
        mData = data;
    }

    public void updateData(List<Place> newData) {
        mData.clear();
        mData.addAll(newData);
        mData = newData;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item_layout, parent, false);

        mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        final Place place = mData.get(position);
        if (place != null) {
            mViewHolder.txtLocationName.setText(place.getFeatureName());
            mViewHolder.txtLatitude.setText("Lat: "+String.valueOf(place.getLatitude()));
            mViewHolder.txtLongitude.setText("Long: "+String.valueOf(place.getLongitude()));
            mViewHolder.bindEdit(place, mListener);
            mViewHolder.bindDelete(place, mListener);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtLocationName;
        private TextView txtLatitude;
        private TextView txtLongitude;
        private ImageView imageViewEdit;
        private ImageView imageViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtLocationName = (TextView) itemView.findViewById(R.id.txt_location_name);
            txtLatitude = (TextView) itemView.findViewById(R.id.txt_latitude);
            txtLongitude = (TextView) itemView.findViewById(R.id.txt_longitude);
            imageViewEdit = (ImageView) itemView.findViewById(R.id.edit);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.delete);
        }

        public void bindEdit(final Place place,
                             final OnItemRecyclerClickLister lister) {
            imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lister.edit(place);
                }
            });

        }

        public void bindDelete(final Place place,
                               final OnItemRecyclerClickLister listener) {
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.delete(place);
                }
            });

        }
    }

    public interface OnItemRecyclerClickLister {
        void edit(Place place);

        void delete(Place place);
    }
}
