package myuu.bneely.geoquiz;
//Brooks Neely
//CSC200
public class Question {

        private int mTextResId; //int is integer//
        private boolean mAnswerTrue; //boolean can mean true or false//

        public Question(int textResId, boolean answerTrue) {
            mTextResId = textResId;
            mAnswerTrue = answerTrue;
        }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
