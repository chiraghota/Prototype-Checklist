package com.zoomcar.prototype.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoomcar.prototype.R;
import com.zoomcar.prototype.interfaces.IOnSectionSelectListener;
import com.zoomcar.prototype.interfaces.IOnTitleSetListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SelectSectionFragment extends Fragment {
    @BindView(R.id.button_option_rear)
    AppCompatButton mButtonOptionRear;
    @BindView(R.id.button_option_passenger)
    AppCompatButton mButtonOptionPassenger;
    @BindView(R.id.button_option_front)
    AppCompatButton mButtonOptionFront;
    @BindView(R.id.button_option_driver)
    AppCompatButton mButtonOptionDriver;
    Unbinder unbinder;

    private IOnSectionSelectListener mSectionSelectListener;
    private IOnTitleSetListener mTitleSetListener;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        SelectSectionFragment fragment = new SelectSectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_section, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleSetListener.setTitle("Choose side");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IOnSectionSelectListener) {
            mSectionSelectListener = (IOnSectionSelectListener) context;
        }

        if (context instanceof IOnTitleSetListener) {
            mTitleSetListener = (IOnTitleSetListener) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_option_rear)
    public void onSelectRear() {
        mSectionSelectListener.onSelectSection(1);
    }

    @OnClick(R.id.button_option_passenger)
    public void onSelectPassenger() {
        mSectionSelectListener.onSelectSection(2);
    }

    @OnClick(R.id.button_option_front)
    public void onSelectFront() {
        mSectionSelectListener.onSelectSection(3);
    }

    @OnClick(R.id.button_option_driver)
    public void onSelectDriver() {
        mSectionSelectListener.onSelectSection(4);
    }
}
