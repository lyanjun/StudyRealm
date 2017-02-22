package com.lyan.studyrealm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lyan.studyrealm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author LYJ
 * Created on 2017/2/21.
 * Time 13:50
 */

public class DeleteFragment extends Fragment{
    private Unbinder unbinder;
    @BindView(R.id.food_name)
    EditText foodName;
    public interface DeleteListener{
        void deleteDataToRealm(String foodName);
    }
    private DeleteListener listener;
    public void setDeleteListener(DeleteListener deleteListener){
        this.listener = deleteListener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.make)
    public void onClick() {
        String foodStr = foodName.getText().toString();
        if (listener != null && !TextUtils.isEmpty(foodStr)){
            listener.deleteDataToRealm(foodStr);
        }
    }
    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
