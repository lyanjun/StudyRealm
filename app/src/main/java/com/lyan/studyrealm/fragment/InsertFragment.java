package com.lyan.studyrealm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lyan.studyrealm.R;
import com.lyan.studyrealm.data.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author LYJ
 * Created on 2017/2/21.
 * Time 13:50
 * 添加数据
 */

public class InsertFragment extends Fragment {

    @BindView(R.id.food_type)
    EditText foodType;
    @BindView(R.id.food_name)
    EditText foodName;
    @BindView(R.id.food_price)
    EditText foodPrice;
    @BindView(R.id.make)
    Button make;
    private Unbinder unbinder;



    public interface InsertListener {
        void insertDataToRealm(Food food);
    }

    private InsertListener listener;

    public void setInsertListener(InsertListener insertListener) {
        this.listener = insertListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.make)
    public void onClick() {
        String typeStr = foodType.getText().toString();
        String foodStr = foodName.getText().toString();
        String priceStr = foodPrice.getText().toString();
        if (listener != null && !TextUtils.isEmpty(typeStr) &&
                !TextUtils.isEmpty(foodStr) &&
                !TextUtils.isEmpty(priceStr)){
            Food food = new Food();
            food.setType(typeStr);//类别
            food.setName(foodStr);//名称
            food.setPrice(Double.parseDouble(priceStr));//价格
            listener.insertDataToRealm(food);
        }
    }
    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
