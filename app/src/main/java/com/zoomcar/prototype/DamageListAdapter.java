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
    private Context mContext;
    private Database mDatabase;
    private ArrayList<Damage> mDamages;

    public DamageListAdapter(Context context) {
        this.mContext = context;
        this.mDatabase = Database.getInstance();
        this.mDamages = mDatabase.getDamages();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_damage_summary, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Damage damage = mDamages.get(position);

        final Section section = mDatabase.getSectionMap().get(damage.sectionId);
        final Question question = mDatabase.getQuestionMap().get(damage.questionId);
        final Answer answer = mDatabase.getAnswerMap().get(damage.answerId);

        holder.mTextTitle.setText(mContext.getResources().getString(R.string.damage_title, section.text, question.text));
        holder.mTextDamage.setText(answer.text);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDatabase.getDamages().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_damage)
        TextView mTextDamage;

        ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.text_damage)
        public void onRemoveDamage() {
            final int position = getAdapterPosition();
            mDamages.remove(position);
            notifyItemRemoved(position);
        }
    }
}
