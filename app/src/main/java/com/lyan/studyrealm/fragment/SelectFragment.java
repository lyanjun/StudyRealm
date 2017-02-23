package com.lyan.studyrealm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lyan.studyrealm.R;
import com.lyan.studyrealm.adapter.TestAdapter;
import com.lyan.studyrealm.data.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Author LYJ
 * Created on 2017/2/21.
 * Time 13:50
 */

public class SelectFragment extends Fragment {

    @BindView(R.id.listview)
    ListView listview;
    private Realm realm;

    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select, container, false);
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 加载数据
     */
    private void add() {
       realm.where(Food.class)
                .findAllAsync()
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foods -> {
                    listview.setAdapter(new TestAdapter(context,foods));
                });
    }

    /**
     * 刷新
     */
    public void reset(){
        add();
    }
    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
