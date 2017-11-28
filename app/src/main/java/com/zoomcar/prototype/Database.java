package com.zoomcar.prototype;

import com.zoomcar.prototype.model.Answer;
import com.zoomcar.prototype.model.AnswerGroup;
import com.zoomcar.prototype.model.Damage;
import com.zoomcar.prototype.model.Question;
import com.zoomcar.prototype.model.Section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sets up data for sections, questions, answer categories and answers.
 * All data is hardcoded.
 */
public class Database {
    private static Database sBuilder;

    private Map<Integer, Section> mSectionMap;
    private Map<Integer, Question> mQuestionMap;
    private Map<Integer, AnswerGroup> mAnswerGroupMap;
    private Map<Integer, Answer> mAnswerMap;

    private Map<Integer, Damage> mQuestionToDamageMap;

    private ArrayList<Damage> mDamages;

    public static Database getInstance() {
        if (sBuilder != null) {
            return sBuilder;
        }

        sBuilder = new Database();
        return sBuilder;
    }

    private Database() {
        mSectionMap = new HashMap<>();
        mQuestionMap = new HashMap<>();
        mAnswerGroupMap = new HashMap<>();
        mAnswerMap = new HashMap<>();
        mQuestionToDamageMap = new HashMap<>();

        mDamages = new ArrayList<>();

        Answer answer1 = new Answer(1, "No Damage");
        Answer answer2 = new Answer(2, "Single Scratch");
        Answer answer3 = new Answer(3, "Multiple Scratches");
        Answer answer4 = new Answer(4, "Dents");
        Answer answer5 = new Answer(5, "Severe Damages");
        Answer answer6 = new Answer(6, "Partially worn out");
        Answer answer7 = new Answer(7, "Major cuts, Flat tyre");
        Answer answer8 = new Answer(8, "Damaged");

        mAnswerMap.put(1, answer1);
        mAnswerMap.put(1, answer1);
        mAnswerMap.put(2, answer2);
        mAnswerMap.put(3, answer3);
        mAnswerMap.put(4, answer4);
        mAnswerMap.put(5, answer5);
        mAnswerMap.put(6, answer6);
        mAnswerMap.put(7, answer7);
        mAnswerMap.put(8, answer8);

        AnswerGroup answerGroup1 = new AnswerGroup(1, "Type 1", new int[]{1, 2, 3, 4, 5});
        AnswerGroup answerGroup2 = new AnswerGroup(2, "Type 2", new int[]{1, 6, 7});
        AnswerGroup answerGroup3 = new AnswerGroup(3, "Type 3", new int[]{1, 8});

        mAnswerGroupMap.put(1, answerGroup1);
        mAnswerGroupMap.put(2, answerGroup2);
        mAnswerGroupMap.put(3, answerGroup3);

        Question question1 = new Question(1, "Rear Quarter Panel", new float[]{73, 68}, 1);
        Question question2 = new Question(2, "Rear Tyre", new float[]{91, 161}, 2);
        Question question3 = new Question(3, "Rear Door", new float[]{202, 107}, 1);
        Question question4 = new Question(4, "Rear Window", new float[]{186, 26}, 3);
        Question question5 = new Question(5, "Front Door", new float[]{346, 119}, 1);
        Question question6 = new Question(6, "Front Window", new float[]{346, 38}, 3);
        Question question7 = new Question(7, "Rear View Mirror", new float[]{423, 52}, 3);
        Question question8 = new Question(8, "Front Tyre", new float[]{512, 161}, 2);
        Question question9 = new Question(9, "Front Quarter Panel", new float[]{511, 87}, 1);
        Question question10 = new Question(10, "Rear Windshield", new float[]{136, 30}, 3);
        Question question11 = new Question(11, "Bootlid", new float[]{136, 97}, 1);
        Question question12 = new Question(12, "Left Tail lights", new float[]{24, 83}, 3);
        Question question13 = new Question(13, "Right Tail lights", new float[]{247, 83}, 3);
        Question question14 = new Question(14, "Rear Bumper", new float[]{136, 162}, 1);
        Question question15 = new Question(15, "Front Quarter Panel", new float[]{106, 87}, 1);
        Question question16 = new Question(16, "Front Tyre", new float[]{105, 161}, 2);
        Question question17 = new Question(17, "Front Door", new float[]{271, 119}, 1);
        Question question18 = new Question(18, "Front Window", new float[]{271, 38}, 3);
        Question question19 = new Question(19, "Rear Door", new float[]{415, 107}, 1);
        Question question20 = new Question(20, "Rear Window", new float[]{431, 26}, 3);
        Question question21 = new Question(21, "Rear Tyre", new float[]{526, 161}, 2);
        Question question22 = new Question(22, "Rear View Mirror", new float[]{194, 52}, 3);
        Question question23 = new Question(23, "Rear Quarter Panel", new float[]{529, 80}, 1);
        Question question24 = new Question(24, "Front Bumper", new float[]{137, 168}, 1);
        Question question25 = new Question(25, "Bonet", new float[]{137, 79}, 1);
        Question question26 = new Question(26, "Left HeadLight", new float[]{25, 113}, 3);
        Question question27 = new Question(27, "Right HeadLight", new float[]{253, 113}, 3);
        Question question28 = new Question(28, "Windshield", new float[]{137, 28}, 3);

        mQuestionMap.put(1, question1);
        mQuestionMap.put(2, question2);
        mQuestionMap.put(3, question3);
        mQuestionMap.put(4, question4);
        mQuestionMap.put(5, question5);
        mQuestionMap.put(6, question6);
        mQuestionMap.put(7, question7);
        mQuestionMap.put(8, question8);
        mQuestionMap.put(9, question9);
        mQuestionMap.put(10, question10);
        mQuestionMap.put(11, question11);
        mQuestionMap.put(12, question12);
        mQuestionMap.put(13, question13);
        mQuestionMap.put(14, question14);
        mQuestionMap.put(15, question15);
        mQuestionMap.put(16, question16);
        mQuestionMap.put(17, question17);
        mQuestionMap.put(18, question18);
        mQuestionMap.put(19, question19);
        mQuestionMap.put(20, question20);
        mQuestionMap.put(21, question21);
        mQuestionMap.put(22, question22);
        mQuestionMap.put(23, question23);
        mQuestionMap.put(24, question24);
        mQuestionMap.put(25, question25);
        mQuestionMap.put(26, question26);
        mQuestionMap.put(27, question27);
        mQuestionMap.put(28, question28);

        Section section1 = new Section(1, "Rear", new int[]{10, 11, 12, 13, 14}, R.drawable.merged_backside);
        Section section2 = new Section(2, "Passenger Side", new int[]{15, 16, 17, 18, 19, 20, 21, 22, 23}, R.drawable.merged_passengerside);
        Section section3 = new Section(3, "Front", new int[]{24, 25, 26, 27, 28}, R.drawable.merged_frontside);
        Section section4 = new Section(4, "Driver Side", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, R.drawable.merged_driverside);

        mSectionMap.put(1, section1);
        mSectionMap.put(2, section2);
        mSectionMap.put(3, section3);
        mSectionMap.put(4, section4);
    }

    public Map<Integer, Section> getSectionMap() {
        return mSectionMap;
    }

    public Map<Integer, Question> getQuestionMap() {
        return mQuestionMap;
    }

    public Map<Integer, AnswerGroup> getAnswerGroupMap() {
        return mAnswerGroupMap;
    }

    public Map<Integer, Answer> getAnswerMap() {
        return mAnswerMap;
    }

    public ArrayList<Damage> getDamages() {
        return mDamages;
    }

    public Map<Integer, Damage> getQuestionToDamageMap() {
        return mQuestionToDamageMap;
    }

    public void addDamage(int index, Damage damage) {
        mDamages.add(index, damage);
        mQuestionToDamageMap.put(damage.questionId, damage);
    }

    public void removeDamage(final int questionId) {
        int index = 0;
        boolean found = false;
        for (Damage d : mDamages) {
            if (d.questionId == questionId) {
                found = true;
                break;
            }

            index++;
        }

        if (found) mDamages.remove(index);
        mQuestionToDamageMap.remove(questionId);
    }
}
