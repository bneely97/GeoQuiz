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

public class QuizActivity extends AppCompatActivity {

    private static final String MYTAG = "quizactivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton; //private not available outside of class; m (member) is global variable//
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionsBank = new Question[] {
            new Question(R.string.question_tennessee, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

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
                setButtons(false);
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
                setButtons(false);
            }

        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                updateQuestion();
                setButtons(true);
            }
        });
        mPreviousButton = (Button) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) { mCurrentIndex = mQuestionsBank.length - 1 ;}
                    else { mCurrentIndex = mCurrentIndex - 1; }
                updateQuestion();
                setButtons(true);
            }
        });
        updateQuestion();
    }
    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void setButtons(boolean newstate) {
        mTrueButton.setEnabled(newstate);
        mFalseButton.setEnabled(newstate);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }
}