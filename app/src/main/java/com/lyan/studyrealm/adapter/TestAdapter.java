package com.lyan.studyrealm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lyan.studyrealm.R;
import com.lyan.studyrealm.data.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Author LYJ
 * Created on 2017/2/23.
 * Time 16:00
 */

public class TestAdapter extends RealmBaseAdapter<Food> implements ListAdapter {


    public TestAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Food> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Food food = adapterData.get(position);
        viewHolder.name.setText(food.getName());
        viewHolder.price.setText(String.valueOf(food.getPrice()));
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
