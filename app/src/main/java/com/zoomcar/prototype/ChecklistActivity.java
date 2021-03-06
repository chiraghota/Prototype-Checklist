package com.zoomcar.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zoomcar.prototype.fragments.DamageSummaryFragment;
import com.zoomcar.prototype.fragments.DamagesQnAFragment;
import com.zoomcar.prototype.fragments.InspectFragment;
import com.zoomcar.prototype.interfaces.IOnCompleteClickListener;
import com.zoomcar.prototype.interfaces.IOnContinueClickListener;
import com.zoomcar.prototype.interfaces.IOnDamageReportListener;
import com.zoomcar.prototype.interfaces.IOnFinishSectionsClickListener;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.interfaces.IOnReportMoreClickListener;
import com.zoomcar.prototype.interfaces.IOnTitleSetListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChecklistActivity extends AppCompatActivity implements
        IOnQuestionClickListener,
        IOnDamageReportListener,
        IOnCompleteClickListener,
        IOnReportMoreClickListener,
        IOnContinueClickListener,
        IOnFinishSectionsClickListener,
        IOnTitleSetListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frame_fragment_host)
    FrameLayout mFrameFragmentHost;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();

        final int sectionId = getIntent().getIntExtra(IntentUtil.SECTION_ID, 1);

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFragmentManager.getBackStackEntryCount() == 0) {
                    finish();
                } else {
                    mFragmentManager.popBackStack();
                }
            }
        });

        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, InspectFragment.newInstance(sectionId)).commitAllowingStateLoss();
    }

    @Override
    public void onClickQuestion(int sectionId, int questionId) {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, DamagesQnAFragment.newInstance(sectionId, questionId)).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onReportDamage() {
        mFragmentManager.popBackStack();
    }

    @Override
    public void onCompleteSections() {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, DamageSummaryFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onFinalClick() {
        finish();
    }

    @Override
    public void onReportMore() {
        Intent intent = new Intent(this, SingleCheckListActivity.class);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 10) {
            if (data.getBooleanExtra(IntentUtil.SUBMIT, false)) finish();
        }
    }

    @Override
    public void onContinueClick(int nextSectionId) {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, InspectFragment.newInstance(nextSectionId)).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void setTitle(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }
}
