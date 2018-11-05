package myuu.bneely.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView mAnswer;
    private static final String EXTRA_ANSWER_IS_TRUE = "myuu.bneely.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "myuu.bneely.geoquiz.answer_is_shown";
    private boolean mAnswerIsTrue;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Boolean message;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswer = (TextView) findViewById(R.id.answer_text_view);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        message = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false); //if key value if wrong the app would not crash but return default value if there is one//
        //    mAnswer.setText(""+message);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        Intent data = new Intent();
        data.putExtra("THEANSWER", "hello");
        setResult(RESULT_CANCELED, data);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswer.setText(R.string.true_button);
                } else {
                    mAnswer.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) { //Encapsulation means you hide something the others don't need to know. Using static in this code does that
        Intent intent = new Intent(packageContext, CheatActivity.class); //static means you call it on the class itself
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
}
