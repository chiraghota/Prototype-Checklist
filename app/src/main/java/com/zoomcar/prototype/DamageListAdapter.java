package com.zoomcar.prototype;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoomcar.prototype.model.Answer;
import com.zoomcar.prototype.model.Damage;
import com.zoomcar.prototype.model.Question;
import com.zoomcar.prototype.model.Section;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Presents all the damages reported by the user in a RecyclerView.
 */
public class DamageListAdapter extends RecyclerView.Adapter<DamageListAdapter.ViewHolder> {
    private static final int ITEM_NO_DAMAGE_TYPE = 0;
    private static final int ITEM_DAMAGE_TYPE = 1;

    private Context mContext;
    private Database mDatabase;
    private ArrayList<Damage> mDamages;
    private int mSectionId;

    public DamageListAdapter(Context context) {
        this.mContext = context;
        this.mDatabase = Database.getInstance();
        this.mDamages = mDatabase.getDamages();
        this.mSectionId = -1;
    }

    public DamageListAdapter(Context context, int sectionId) {
        this.mContext = context;
        this.mDatabase = Database.getInstance();
        this.mSectionId = sectionId;

        this.mDamages = new ArrayList<>();
        for (Damage damage : mDatabase.getDamages()) {
            if (damage.sectionId == mSectionId) {
                this.mDamages.add(damage);
            }
        }
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
                return new ViewHolder(v);
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

                if (mSectionId == -1) {
                    damageViewHolder.mTextTitle.setText(mContext.getResources().getString(R.string.damage_title, section.text, question.text));
                } else {
                    damageViewHolder.mTextTitle.setText(mContext.getResources().getString(R.string.damage_title_without_section, question.text));
                }

                damageViewHolder.mTextDamage.setText(answer.text);

                holder.itemView.setTag(damage.questionId);
                break;
            case ITEM_NO_DAMAGE_TYPE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mDamages.size() > 0) {
            return mDamages.size();
        } else {
            return mSectionId == -1 ? 0 : 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 0 && mDamages.size() > 0) {
            return ITEM_DAMAGE_TYPE;
        } else {
            return ITEM_NO_DAMAGE_TYPE;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class DamageViewHolder extends ViewHolder {
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_damage)
        TextView mTextDamage;

        DamageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.text_damage)
        void onRemoveDamage() {
            final int position = getAdapterPosition();
            mDamages.remove(position);
            notifyItemRemoved(position);

            int index = 0;
            boolean found = false;
            for (Damage damage : mDatabase.getDamages()) {
                if (damage.questionId == (int) itemView.getTag()) {
                    found = true;
                    break;
                }
            }

            if (found) mDatabase.getDamages().remove(index);
        }
    }
}
