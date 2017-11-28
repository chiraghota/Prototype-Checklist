package com.zoomcar.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.button_option_first)
    AppCompatButton mButtonOptionFirst;
    @BindView(R.id.button_option_second)
    AppCompatButton mButtonOptionSecond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Choose");
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @OnClick(R.id.button_option_first)
    public void onClickFirst() {
        Intent intent = new Intent(this, ChecklistActivity.class);
        intent.putExtra(IntentUtil.SECTION_ID, 1);
        startActivity(intent);
    }

    @OnClick(R.id.button_option_second)
    public void onClickSecond() {
        Intent intent = new Intent(this, ChecklistActivity.class);
        intent.putExtra(IntentUtil.SECTION_ID, 1);
        startActivity(intent);
    }
}
