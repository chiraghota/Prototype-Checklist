package com.zoomcar.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.button_front)
    AppCompatButton mButtonFront;
    @BindView(R.id.button_driver)
    AppCompatButton mButtonDriver;
    @BindView(R.id.button_passenger)
    AppCompatButton mButtonPassenger;
    @BindView(R.id.button_rear)
    AppCompatButton mButtonRear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.select_a_section));
        }
    }

    @OnClick(R.id.button_front)
    public void onFrontSectionClick() {
        Intent intent = new Intent(this, CheckListActivity.class);
        intent.putExtra(IntentUtil.SECTION_ID, 4);
        startActivity(intent);
    }

    @OnClick(R.id.button_rear)
    public void onRearSectionClick() {
        Intent intent = new Intent(this, CheckListActivity.class);
        intent.putExtra(IntentUtil.SECTION_ID, 2);
        startActivity(intent);
    }

    @OnClick(R.id.button_passenger)
    public void onPassengerSectionClick() {
        Intent intent = new Intent(this, CheckListActivity.class);
        intent.putExtra(IntentUtil.SECTION_ID, 3);
        startActivity(intent);
    }

    @OnClick(R.id.button_driver)
    public void onDriverSectionClick() {
        Intent intent = new Intent(this, CheckListActivity.class);
        intent.putExtra(IntentUtil.SECTION_ID, 1);
        startActivity(intent);
    }
}
