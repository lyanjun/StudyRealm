package com.lyan.studyrealm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lyan.studyrealm.R;
import com.lyan.studyrealm.data.Food;
import com.lyan.studyrealm.fragment.DeleteFragment;
import com.lyan.studyrealm.fragment.InsertFragment;
import com.lyan.studyrealm.fragment.SelectFragment;
import com.lyan.studyrealm.fragment.UpdataFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Banana Apple Durian Grape Lemon Date Bennet
 */
public class AsyncActivity extends AppCompatActivity implements InsertFragment.InsertListener ,
        UpdataFragment.UpdataListener ,DeleteFragment.DeleteListener{

    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.select)
    Button select;
    private Realm realm;//数据库操作对象
    private List<Fragment> fragments;//模块
    private int index = 0;


    private RealmAsyncTask insertTask,updataTask,deleteTask;//异步添加任务
    private RealmResults<Food> foods;//查询结果
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();//获取数据操作对象
        fragments = new ArrayList<>();//实例化模块集合
        //添加数据模块
        InsertFragment insertFragment = new InsertFragment();
        insertFragment.setInsertListener(this);
        fragments.add(insertFragment);
        //更新数据模块
        UpdataFragment updataFragment = new UpdataFragment();
        updataFragment.setUpdataListener(this);
        fragments.add(updataFragment);
        //删除数据模块
        DeleteFragment deleteFragment = new DeleteFragment();
        deleteFragment.setDeleteListener(this);
        fragments.add(deleteFragment);
        fragments.add(new SelectFragment());//查询数据模块
        //初始化界面
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag,insertFragment).add(R.id.frag,fragments.get(1))
                .add(R.id.frag,fragments.get(2)).add(R.id.frag,fragments.get(3))
                .hide(fragments.get(1)).hide(fragments.get(2)).hide(fragments.get(3))
                .show(insertFragment).commit();
    }

    /**
     * 切换模块
     * @param view
     */
    @OnClick({R.id.insert, R.id.update, R.id.delete, R.id.select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert://增
                showFragment(0);
                break;
            case R.id.update://改
                showFragment(1);
                break;
            case R.id.delete://删
                showFragment(2);
                break;
            case R.id.select://查
                showFragment(3);
                ((SelectFragment)fragments.get(3)).reset();
                break;
        }
    }

    /**
     * 显示选中的模块
     * @param what
     */
    private void showFragment(int what){
        if (what == index){
            return;
        }
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        for (int i = 0 ; i < fragments.size(); i++){
            if (what == i){
                transaction.show(fragments.get(i));
            }else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
        index = what;
    }
    @Override
    protected void onDestroy() {
        cancelTask(insertTask);
        cancelTask(updataTask);
        cancelTask(deleteTask);
        realm.close();
        super.onDestroy();
    }

    private void cancelTask(RealmAsyncTask task){
        if (task!=null&&!task.isCancelled()){
            task.cancel();
        }
    }

    /**
     * 添加数据
     */
    @Override
    public void insertDataToRealm(final Food food) {
        insertTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {//执行异步操作
                Log.w("insertDataToRealm", "execute: ");
                Log.i("线程", Thread.currentThread().getName());
                realm.copyToRealm(food);
            }
        }, new Realm.Transaction.OnSuccess() {//操作成功
            @Override
            public void onSuccess() {
                Toast.makeText(AsyncActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                Log.i("insertDataToRealm", "onSuccess: ");
                Log.i("线程", Thread.currentThread().getName());
                selectAllDataFromRealm();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("线程", Thread.currentThread().getName());
                Log.d("onError", error.getMessage());
            }
        });
    }

    /**
     * 异步全查询
     */
    private void selectAllDataFromRealm(){
        foods = realm.where(Food.class).findAllAsync();
        foods.addChangeListener(new RealmChangeListener<RealmResults<Food>>() {
            @Override
            public void onChange(RealmResults<Food> element) {
                Log.d("selectAllDataFromRealm", "查询到结果");
                Log.i("线程", Thread.currentThread().getName());
                List<Food> result = realm.copyFromRealm(foods);
                if (result.size() == 0){
                    Log.d("结果集", "没有数据");
                    return;
                }
                for (Food food : result){
                    Log.d("结果集", "------------------------------");
                    Log.w("类别", food.getType());
                    Log.w("名称", food.getName());
                    Log.w("价格", food.getPrice()+"");
                }
                if (foods.isLoaded()){
                    foods.removeChangeListeners();
                }
            }
        });
    }

    /**
     * 更新数据
     * @param foodName
     */
    @Override
    public void updataToRealm(final String foodName , final double price) {
        updataTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //条件查询
                Food food = realm.where(Food.class).equalTo("name",foodName).findFirst();
                food.setPrice(price);//更改价格
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                selectAllDataFromRealm();
                Toast.makeText(AsyncActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除数据
     * @param foodName
     */
    @Override
    public void deleteDataToRealm(final String foodName) {
        deleteTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //条件查询
                Food food = realm.where(Food.class).equalTo("name",foodName).findFirst();
                food.deleteFromRealm();//删除数据
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(AsyncActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                selectAllDataFromRealm();
            }
        });
    }
}
