package com.lyan.studyrealm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lyan.studyrealm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.one)
    Button one;
    @BindView(R.id.two)
    Button two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.one, R.id.two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.two:
                startActivity(new Intent(this,AsyncActivity.class));
                break;
        }
    }
}
