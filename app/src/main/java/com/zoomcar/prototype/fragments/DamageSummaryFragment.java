package com.zoomcar.prototype.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoomcar.prototype.decorations.DamageDecoration;
import com.zoomcar.prototype.adapters.DamageAdapter;
import com.zoomcar.prototype.Database;
import com.zoomcar.prototype.R;
import com.zoomcar.prototype.interfaces.IOnCompleteClickListener;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.interfaces.IOnReportMoreClickListener;
import com.zoomcar.prototype.interfaces.IOnTitleSetListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Allow user to confirm reporting of damages
 */
public class DamageSummaryFragment extends Fragment {
    @BindView(R.id.text_summary_title)
    TextView mTextSummaryTitle;
    @BindView(R.id.recycler_damage_summary_list)
    RecyclerView mRecyclerDamageSummaryList;
    Unbinder unbinder;

    private IOnCompleteClickListener mCompleteClickListener;
    private IOnReportMoreClickListener mReportMoreClickListener;
    private IOnTitleSetListener mTitleSetListener;
    private IOnQuestionClickListener mQuestionClickListener;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        DamageSummaryFragment fragment = new DamageSummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IOnCompleteClickListener) {
            mCompleteClickListener = (IOnCompleteClickListener) context;
        }

        if (context instanceof IOnReportMoreClickListener) {
            mReportMoreClickListener = (IOnReportMoreClickListener) context;
        }

        if (context instanceof IOnQuestionClickListener) {
            mQuestionClickListener = (IOnQuestionClickListener) context;
        }

        if (context instanceof IOnTitleSetListener) {
            mTitleSetListener = (IOnTitleSetListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_damage_summary, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleSetListener.setTitle("Damages Summary");

        mRecyclerDamageSummaryList.setNestedScrollingEnabled(false);
        mRecyclerDamageSummaryList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerDamageSummaryList.addItemDecoration(new DamageDecoration(getActivity(), R.dimen.medium_spacing));

        DamageAdapter sectionDamageAdapter = new DamageAdapter(getContext(), mQuestionClickListener, mCompleteClickListener, mReportMoreClickListener);
        mRecyclerDamageSummaryList.setAdapter(sectionDamageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}