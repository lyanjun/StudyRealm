package com.lyan.studyrealm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyan.studyrealm.R;

/**
 * Author LYJ
 * Created on 2017/2/21.
 * Time 13:50
 */

public class SelectFragment extends Fragment{

    public interface SelectListener{
        void selectDataToRealm();
    }
    private SelectListener listener;
    public void setSelectListener(SelectListener selectListener){
        this.listener = selectListener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select,container,false);
        return view;
    }
}
