package com.zoomcar.prototype.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoomcar.prototype.Database;
import com.zoomcar.prototype.IntentUtil;
import com.zoomcar.prototype.R;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.model.Question;
import com.zoomcar.prototype.model.Section;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment to show car's image to allow the customer to inspect specific areas to
 * report damages
 */
public class InspectFragment extends Fragment {
    @BindView(R.id.text_inspect_title)
    TextView mTextInspectTitle;
    @BindView(R.id.horizontal_scroll_image)
    ImageView mHorizontalScrollImage;
    @BindView(R.id.horizontal_scroll_container)
    HorizontalScrollView mHorizontalScrollContainer;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.button_no_damages)
    AppCompatButton mButtonNoDamages;
    Unbinder unbinder;

    private IOnQuestionClickListener mQuestionClickListener;

    private int tolerancePixels;
    private int mSectionId;
    private Database mDatabase;

    public static Fragment newInstance(int sectionId) {
        Bundle args = new Bundle();
        args.putInt(IntentUtil.SECTION_ID, sectionId);

        InspectFragment fragment = new InspectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IOnQuestionClickListener) {
            mQuestionClickListener = (IOnQuestionClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tolerancePixels = getContext().getResources().getDimensionPixelSize(R.dimen.tolerance);
        mSectionId = getArguments().getInt(IntentUtil.SECTION_ID);
        mDatabase = Database.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspect, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Section section = mDatabase.getSectionMap().get(mSectionId);

        mTextInspectTitle.setText(getContext().getString(R.string.inspect_title, section.text));
        mHorizontalScrollImage.setImageResource(section.drawableId);

        final GestureDetectorCompat mDetectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                final float x = e.getX();
                final float y = e.getY();

                Log.i("x", String.valueOf(x));
                Log.i("y", String.valueOf(y));

                for (final int questionId : section.questionIds) {
                    final Question question = mDatabase.getQuestionMap().get(questionId);
                    final float qx = (question.coordinates[0] + 12) * 2;
                    final float qy = (question.coordinates[1] + 12) * 2;

                    Log.i("id", String.valueOf(question.id));
                    Log.i("question", String.valueOf(question.text));
                    Log.i("qx", String.valueOf(qx));
                    Log.i("qy", String.valueOf(qy));

                    if (Math.abs(x - qx) <= tolerancePixels && Math.abs(y - qy) <= tolerancePixels) {
                        // Found the question
                        mQuestionClickListener.onClick(mSectionId, questionId);
                        break;
                    }
                }

                return false;
            }
        });

        mHorizontalScrollImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mDetectorCompat.onTouchEvent(motionEvent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
