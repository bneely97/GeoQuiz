package myuu.bneely.geoquiz;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private static final String MYTAG = "quizactivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERED = "Answered";
    private static final String ANSWER_COUNT = "Answer";
    private static final String BUTTON_STATE = "mButtons";
    private Button mTrueButton; //private not available outside of class; m (member) is global variable//
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    private ArrayList<Integer> mAnsweredQuestions = new ArrayList<>();
    private Question[] mQuestionsBank = new Question[] {
            new Question(R.string.question_tennessee, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_Louisiana, false),
            new Question(R.string.question_Utah, true),
            new Question(R.string.question_Canada, true),
            new Question(R.string.question_India, true),
    };

    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;
    private int mIncorrectAnswers = 0;
    private int[] mAnswer = new int[mQuestionsBank.length];
    private boolean mButtons = true;

    @Override
    public void onStart() {
        super.onStart();
        Log.d(MYTAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MYTAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MYTAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        Log.i(MYTAG, "onSaveInstanceState");
        savedInstaceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstaceState.putIntegerArrayList(KEY_ANSWERED, mAnsweredQuestions);
        savedInstaceState.putIntArray(ANSWER_COUNT, mAnswer);
        savedInstaceState.putBoolean(BUTTON_STATE, mButtons);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(MYTAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MYTAG, "onDestroy() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnsweredQuestions = savedInstanceState.getIntegerArrayList(KEY_ANSWERED);
            mAnswer = savedInstanceState.getIntArray(ANSWER_COUNT);
            mButtons = savedInstanceState.getBoolean(BUTTON_STATE);
        }
        Log.d(MYTAG, "onCreate called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById (R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //           Toast toast = Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT);//
                //           toast.setGravity(Gravity.TOP, 0, 0);//
                //           toast.show();//
                checkAnswer(true);
                disableAnswer();
                mAnsweredQuestions.add(mCurrentIndex);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        Toast toast = Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT);
                //        toast.setGravity(Gravity.TOP, 0, 0);
                //        toast.show();
                checkAnswer(false);
                disableAnswer();
                mAnsweredQuestions.add(mCurrentIndex);
            }

        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
            }
        });
        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) { mCurrentIndex = mQuestionsBank.length - 1 ;}
                    else { mCurrentIndex = mCurrentIndex - 1; }
                updateQuestion();
            }
        });
        updateQuestion();
    }
    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (mAnswer[mCurrentIndex] != 0)
            disableAnswer();
        else
            enableAnswer();
    }

    private void disableAnswer() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void enableAnswer() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mCorrectAnswers += 1;
            mAnswer[mCurrentIndex]=1;
        } else {
            messageResId = R.string.incorrect_toast;
            mIncorrectAnswers += 1;
            mAnswer[mCurrentIndex]=-1;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        if ((mCorrectAnswers + mIncorrectAnswers) == mQuestionsBank.length) {
            double percentscore = ((double)mCorrectAnswers/(double)mQuestionsBank.length) * 100;
            //mQuestionTextView.setText("Score: " + String.valueOf(percentscore) + "%");
            Toast.makeText(this, getString(R.string.score, percentscore), Toast.LENGTH_LONG).show();
        }
    }
}
