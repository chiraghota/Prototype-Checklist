package com.zoomcar.prototype;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zoomcar.prototype.fragments.DamageSummaryFragment;
import com.zoomcar.prototype.fragments.DamagesQnAFragment;
import com.zoomcar.prototype.fragments.InspectFragment;
import com.zoomcar.prototype.interfaces.IOnCompleteClickListener;
import com.zoomcar.prototype.interfaces.IOnDamageReportListener;
import com.zoomcar.prototype.interfaces.IOnNoDamageClickListener;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.interfaces.IOnReportMoreClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckListActivity extends AppCompatActivity implements
        IOnQuestionClickListener,
        IOnDamageReportListener,
        IOnCompleteClickListener,
        IOnReportMoreClickListener,
        IOnNoDamageClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frame_fragment_host)
    FrameLayout mFrameFragmentHost;

    private FragmentManager mFragmentManager;

    private Database mDatabase;
    private int mSectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        ButterKnife.bind(this);

        mSectionId = getIntent().getIntExtra(IntentUtil.SECTION_ID, -1);

        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getString(R.string.checklist));
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDatabase = Database.getInstance();
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, InspectFragment.newInstance(mSectionId)).commitAllowingStateLoss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(int sectionId, int questionId) {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, DamagesQnAFragment.newInstance(sectionId, questionId)).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onReportDamage() {
        mFragmentManager.popBackStack();
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, DamageSummaryFragment.newInstance()).addToBackStack(null).commitAllowingStateLoss();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReportMore() {
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, InspectFragment.newInstance(mSectionId)).commitAllowingStateLoss();
    }

    @Override
    public void onNoDamageClick() {
        finish();
    }
}
