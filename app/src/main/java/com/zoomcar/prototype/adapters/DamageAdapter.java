package com.zoomcar.prototype.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zoomcar.prototype.Database;
import com.zoomcar.prototype.R;
import com.zoomcar.prototype.interfaces.IOnCompleteClickListener;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.interfaces.IOnReportMoreClickListener;
import com.zoomcar.prototype.model.Answer;
import com.zoomcar.prototype.model.Damage;
import com.zoomcar.prototype.model.Question;
import com.zoomcar.prototype.model.Section;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DamageAdapter extends RecyclerView.Adapter<DamageAdapter.ViewHolder> {
    private static final int ITEM_REPORT_MORE = 0;
    private static final int ITEM_DAMAGE_TYPE = 1;
    private static final int ITEM_SUBMIT = 2;

    private Context mContext;
    private Database mDatabase;
    private ArrayList<Damage> mDamages;

    private final IOnQuestionClickListener mQuestionClickListener;
    private final IOnCompleteClickListener mCompleteClickListener;
    private final IOnReportMoreClickListener mReportMoreClickListener;

    public DamageAdapter(Context context, IOnQuestionClickListener questionClickListener, IOnCompleteClickListener completeClickListener, IOnReportMoreClickListener reportMoreClickListener) {
        this.mContext = context;
        this.mDatabase = Database.getInstance();
        this.mDamages = mDatabase.getDamages();
        this.mQuestionClickListener = questionClickListener;
        this.mCompleteClickListener = completeClickListener;
        this.mReportMoreClickListener = reportMoreClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case ITEM_DAMAGE_TYPE:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_damage_summary, parent, false);
                return new DamageViewHolder(v);
            case ITEM_REPORT_MORE:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_report_damage, parent, false);
                return new ReportMoreButtonViewHolder(v);
            case ITEM_SUBMIT:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_submit_go, parent, false);
                return new SubmitButtonViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case ITEM_DAMAGE_TYPE:
                DamageViewHolder damageViewHolder = (DamageViewHolder) holder;
                final Damage damage = mDamages.get(position);
                final Section section = mDatabase.getSectionMap().get(damage.sectionId);
                final Question question = mDatabase.getQuestionMap().get(damage.questionId);
                final Answer answer = mDatabase.getAnswerMap().get(damage.answerId);

                damageViewHolder.setDamage(damage);
                damageViewHolder.mTextTitle.setText(mContext.getResources().getString(R.string.damage_title, section.text, question.text));
                damageViewHolder.mTextDamage.setText(answer.text);

                holder.itemView.setTag(damage.questionId);
                break;
            case ITEM_REPORT_MORE:
                ReportMoreButtonViewHolder reportMoreButtonViewHolder = (ReportMoreButtonViewHolder) holder;
                if (mDamages.size() > 0)
                    reportMoreButtonViewHolder.mReportButton.setText(mContext.getResources().getString(R.string.report_more_damages));
                else
                    reportMoreButtonViewHolder.mReportButton.setText(mContext.getResources().getString(R.string.report_damages));
                break;
            case ITEM_SUBMIT:
                SubmitButtonViewHolder submitButtonViewHolder = (SubmitButtonViewHolder) holder;

                if (mDamages.size() == 0) {
                    submitButtonViewHolder.mTextAgreement.setVisibility(View.VISIBLE);
                    submitButtonViewHolder.mTextAgreement.setChecked(false);
                    submitButtonViewHolder.mButtonSubmit.setEnabled(false);
                } else {
                    submitButtonViewHolder.mTextAgreement.setVisibility(View.GONE);
                    submitButtonViewHolder.mButtonSubmit.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDamages != null ? mDamages.size() + 2 : 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) return ITEM_SUBMIT;
        if (position == getItemCount() - 2) return ITEM_REPORT_MORE;

        return ITEM_DAMAGE_TYPE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ReportMoreButtonViewHolder extends ViewHolder {
        @BindView(R.id.button_report_more)
        AppCompatButton mReportButton;

        ReportMoreButtonViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.button_report_more)
        void onReportMore() {
            if (mReportMoreClickListener != null) {
                mReportMoreClickListener.onReportMore();
            }
        }
    }

    class SubmitButtonViewHolder extends ViewHolder {
        @BindView(R.id.agreement_text)
        CheckBox mTextAgreement;
        @BindView(R.id.button_submit)
        AppCompatButton mButtonSubmit;

        SubmitButtonViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            if (mDamages.size() == 0) {
                mTextAgreement.setVisibility(View.VISIBLE);
                mTextAgreement.setChecked(false);
                mButtonSubmit.setEnabled(false);
            } else {
                mTextAgreement.setVisibility(View.GONE);
                mButtonSubmit.setEnabled(true);
            }

            mTextAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mButtonSubmit.setEnabled(isChecked);
                }
            });
        }

        @OnClick(R.id.button_submit)
        void onContinue() {
            if (mCompleteClickListener != null) {
                mCompleteClickListener.onFinalClick();
            }
        }
    }

    class DamageViewHolder extends ViewHolder {
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_damage)
        TextView mTextDamage;

        private Damage mDamage;

        DamageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void setDamage(Damage damage) {
            mDamage = damage;
        }

        @OnClick(R.id.damage_summary_container)
        void onDamageClick() {
            if (mQuestionClickListener != null) {
                mQuestionClickListener.onClickQuestion(mDamage.sectionId, mDamage.questionId);
            }
        }

        @OnClick(R.id.text_damage)
        void onRemoveDamage() {
            final int position = getAdapterPosition();
            mDamages.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(getItemCount() - 1);
            mDatabase.removeDamage((int) itemView.getTag());
        }
    }
}

