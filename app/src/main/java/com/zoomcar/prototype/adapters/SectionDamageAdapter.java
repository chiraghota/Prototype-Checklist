package com.zoomcar.prototype.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoomcar.prototype.Database;
import com.zoomcar.prototype.R;
import com.zoomcar.prototype.interfaces.IOnContinueClickListener;
import com.zoomcar.prototype.interfaces.IOnDamageRemovedListener;
import com.zoomcar.prototype.interfaces.IOnQuestionClickListener;
import com.zoomcar.prototype.model.Answer;
import com.zoomcar.prototype.model.Damage;
import com.zoomcar.prototype.model.Question;
import com.zoomcar.prototype.model.Section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Presents all the damages reported by the user in a RecyclerView
 * in a particular section.
 */
public class SectionDamageAdapter extends RecyclerView.Adapter<SectionDamageAdapter.ViewHolder> {
    private static final int ITEM_NO_DAMAGE_TYPE = 0;
    private static final int ITEM_DAMAGE_TYPE = 1;
    private static final int ITEM_CONTINUE = 2;

    private final Context mContext;

    private final IOnContinueClickListener mContinueClickListener;
    private final IOnQuestionClickListener mQuestionClickListener;
    private final IOnDamageRemovedListener mDamageRemovedListener;

    private final Database mDatabase;
    private final ArrayList<Damage> mDamages;

    private final int mSectionId;

    private int mNewDamagesCount;
    private int mOldDamagesCount;
    private boolean mShowNewHeader;
    private boolean mShowOldHeader;

    public SectionDamageAdapter(Context context,
                                int sectionId,
                                IOnContinueClickListener continueClickListener,
                                IOnQuestionClickListener questionClickListener,
                                IOnDamageRemovedListener damageRemovedListener) {
        this.mContext = context;
        this.mDatabase = Database.getInstance();
        this.mSectionId = sectionId;
        this.mContinueClickListener = continueClickListener;
        this.mQuestionClickListener = questionClickListener;
        this.mDamageRemovedListener = damageRemovedListener;

        this.mDamages = new ArrayList<>();
        for (Damage damage : mDatabase.getDamages()) {
            if (damage.sectionId == mSectionId) {
                this.mDamages.add(damage);

                if (damage.isNew) {
                    mShowNewHeader = true;
                    mNewDamagesCount++;
                } else {
                    mShowOldHeader = true;
                    mOldDamagesCount++;
                }
            }
        }

        sort();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case ITEM_DAMAGE_TYPE:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_damage_summary, parent, false);
                return new DamageViewHolder(v);
            case ITEM_NO_DAMAGE_TYPE:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_no_damage, parent, false);
                return new DamageButtonViewHolder(v);
            case ITEM_CONTINUE:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_continue, parent, false);
                return new ContinueButtonViewHolder(v);
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

                damageViewHolder.mTextTitle.setText(mContext.getResources().getString(R.string.damage_title_without_section, question.text));
                damageViewHolder.mTextDamage.setText(answer.text);

                holder.itemView.setTag(damage.questionId);
                break;
            case ITEM_NO_DAMAGE_TYPE:
                break;
            case ITEM_CONTINUE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDamages != null && mDamages.size() > 0 ? mDamages.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDamages.size() > 0) {
            if (position == (getItemCount() - 1)) {
                return ITEM_CONTINUE;
            } else {
                return ITEM_DAMAGE_TYPE;
            }
        } else {
            return ITEM_NO_DAMAGE_TYPE;
        }
    }

    public String getSectionHeader(int position) {
        if (mDamages.size() > 0) {
            if (position == 0 && mShowNewHeader) {
                return mContext.getResources().getString(R.string.new_reported_damages);
            } else if (position == mNewDamagesCount && mShowOldHeader) {
                return mContext.getResources().getString(R.string.existing_reported_damages);
            }
        }

        return null;
    }

    private void sort() {
        Collections.sort(mDamages, new Comparator<Damage>() {
            @Override
            public int compare(Damage o1, Damage o2) {
                return o1.isNew == o2.isNew ? 0 : (o2.isNew ? 1 : -1);
            }
        });
    }

    /**
     * All ViewHolder declaration goes below this line
     */

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class DamageButtonViewHolder extends ViewHolder {
        DamageButtonViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.button_no_damages)
        void onNoDamage() {
            if (mContinueClickListener != null) {
                mContinueClickListener.onContinueClick(mSectionId + 1);
            }
        }
    }

    class ContinueButtonViewHolder extends ViewHolder {
        ContinueButtonViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.button_continue)
        void onContinue() {
            if (mContinueClickListener != null) {
                mContinueClickListener.onContinueClick(mSectionId + 1);
            }
        }
    }

    class DamageViewHolder extends ViewHolder {
        @BindView(R.id.damage_summary_container)
        ConstraintLayout mContainer;
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_damage)
        TextView mTextDamage;

        DamageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.damage_summary_container)
        void onDamageClick() {
            if (mQuestionClickListener != null) {
                mQuestionClickListener.onClickQuestion(mSectionId, (int) itemView.getTag());
            }
        }

        @OnClick(R.id.text_damage)
        void onRemoveDamage() {
            final int position = getAdapterPosition();
            final boolean isNew = mDamages.get(position).isNew;
            if (isNew) {
                mNewDamagesCount--;
                mShowNewHeader = mNewDamagesCount != 0 && mShowNewHeader;
            } else {
                mOldDamagesCount--;
                mShowOldHeader = mOldDamagesCount != 0 && mShowOldHeader;
            }

            mDamages.remove(position);
            mDatabase.removeDamage((int) itemView.getTag());
            notifyItemRemoved(position);
            mDamageRemovedListener.onDamageRemoved();
        }
    }
}
