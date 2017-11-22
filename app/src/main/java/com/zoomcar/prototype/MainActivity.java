package com.zoomcar.prototype;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zoomcar.prototype.fragments.DamageSummaryFragment;
import com.zoomcar.prototype.fragments.DamagesQnAFragment;
import com.zoomcar.prototype.fragments.InspectFragment;
import com.zoomcar.prototype.interfaces.IOnCompleteClickListener;
import com.zoomcar.prototype.interfaces.IOnDamageReportListener;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.interfaces.IOnReportMoreClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        IOnQuestionClickListener, IOnDamageReportListener, IOnCompleteClickListener, IOnReportMoreClickListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frame_fragment_host)
    FrameLayout mFrameFragmentHost;

    private FragmentManager mFragmentManager;

    private Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mDatabase = Database.getInstance();
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, InspectFragment.newInstance(3)).commitAllowingStateLoss();
    }

    @Override
    public void onClick(int sectionId, int questionId) {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, DamagesQnAFragment.newInstance(sectionId, questionId)).commitAllowingStateLoss();
    }

    @Override
    public void onReportDamage() {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, DamageSummaryFragment.newInstance()).commitAllowingStateLoss();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onReportMore() {
        mFragmentManager.beginTransaction().replace(R.id.frame_fragment_host, InspectFragment.newInstance(3)).commitAllowingStateLoss();
    }
}
