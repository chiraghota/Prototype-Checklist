package com.zoomcar.prototype.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zoomcar.prototype.Database;
import com.zoomcar.prototype.IntentUtil;
import com.zoomcar.prototype.R;
import com.zoomcar.prototype.interfaces.IOnDamageReportListener;
import com.zoomcar.prototype.interfaces.IOnTitleSetListener;
import com.zoomcar.prototype.model.Answer;
import com.zoomcar.prototype.model.AnswerGroup;
import com.zoomcar.prototype.model.Damage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment to display list of answers
 * corresponding to a particular question
 */
public class DamagesQnAFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.text_question)
    TextView mTextQuestion;
    @BindView(R.id.radio_group_questions_container)
    RadioGroup mRadioGroupQuestionsContainer;
    @BindView(R.id.button_done)
    AppCompatButton mButtonDone;
    Unbinder unbinder;

    private IOnDamageReportListener mReportListener;
    private IOnTitleSetListener mTitleSetListener;

    private int mSectionId;
    private int mQuestionId;
    private int mAnswerGroupId;
    private int mSelectedAnswerId;

    private Database mDatabase;

    public static Fragment newInstance(int sectionId, int questionId) {
        Bundle args = new Bundle();
        args.putInt(IntentUtil.SECTION_ID, sectionId);
        args.putInt(IntentUtil.QUESTION_ID, questionId);

        DamagesQnAFragment fragment = new DamagesQnAFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSectionId = getArguments().getInt(IntentUtil.SECTION_ID);
        mQuestionId = getArguments().getInt(IntentUtil.QUESTION_ID);
        mDatabase = Database.getInstance();
        mSelectedAnswerId = -1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IOnDamageReportListener) {
            mReportListener = (IOnDamageReportListener) context;
        }

        if (context instanceof IOnTitleSetListener) {
            mTitleSetListener = (IOnTitleSetListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_damages_qna, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleSetListener.setTitle("Damages");

        mButtonDone.setEnabled(false);

        mAnswerGroupId = mDatabase.getQuestionMap().get(mQuestionId).answerGroupId;
        final AnswerGroup answerGroup = mDatabase.getAnswerGroupMap().get(mAnswerGroupId);
        mTextQuestion.setText(getContext().getResources().getString(R.string.damage_qna_title, mDatabase.getQuestionMap().get(mQuestionId).text));

        mSelectedAnswerId = mDatabase.getQuestionToDamageMap().containsKey(mQuestionId) ? mDatabase.getQuestionToDamageMap().get(mQuestionId).answerId : 1;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int answerId : answerGroup.answerIds) {
            final Answer answer = mDatabase.getAnswerMap().get(answerId);
            AppCompatRadioButton answerRadio = (AppCompatRadioButton) inflater.inflate(R.layout.item_radio_text, mRadioGroupQuestionsContainer, false);
            answerRadio.setText(answer.text);
            mRadioGroupQuestionsContainer.addView(answerRadio);
            answerRadio.setTag(answerId);
            answerRadio.setOnClickListener(this);

            if (answerId == mSelectedAnswerId) {
                answerRadio.performClick();
            }
        }

        Log.i("answerGroupId", String.valueOf(mAnswerGroupId));
    }

    @OnClick(R.id.button_done)
    public void onButtonClick() {
        final int oldAnswer = mDatabase.getQuestionToDamageMap().containsKey(mQuestionId) ? mDatabase.getQuestionToDamageMap().get(mQuestionId).answerId : -1;
        Log.i("questionId", String.valueOf(mQuestionId));
        if (mSelectedAnswerId != oldAnswer) {
            mDatabase.removeDamage(mQuestionId);
            if (mSelectedAnswerId != 1) {
                mDatabase.addDamage(0, new Damage(mSectionId, mQuestionId, mAnswerGroupId, mSelectedAnswerId));
            }
        }

        mReportListener.onReportDamage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        mSelectedAnswerId = (int) view.getTag();
        mButtonDone.setEnabled(true);
    }
}
