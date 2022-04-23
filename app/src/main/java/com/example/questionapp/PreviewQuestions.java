package com.example.questionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PreviewQuestions extends AppCompatActivity {

    private TextView textShowQuestionNum, textShowQuestion, textShowAnswer;
    private Button btnPrev, btnNext;
    private List<Question> questionList;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);

        textShowQuestionNum = findViewById(R.id.textShowQuestionNum);
        textShowQuestion = findViewById(R.id.textShowQuestion);
        textShowAnswer = findViewById(R.id.textShowAnswer);

        questionList = getAllQuestions();
        setFields(questionList.get(index));

        btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index == 0) {
                    Toast.makeText(getApplicationContext(), "You are already at the very first Question.", Toast.LENGTH_SHORT).show();
                } else {
                    index--;
                    setFields(questionList.get(index));
                }
            }
        });

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index >= questionList.size()) {
                    Toast.makeText(getApplicationContext(), "You are already at the very last Question.", Toast.LENGTH_SHORT).show();
                } else {
                    index++;
                    setFields(questionList.get(index));
                }
            }
        });
    }

    private List<Question> getAllQuestions() {
        DBHelper myDB = new DBHelper(PreviewQuestions.this);
        return myDB.getAllQuestions();
    }

    private void setFields(Question q) {
        textShowQuestionNum.setText(q.getId());
        textShowQuestion.setText(q.getQuestion());
        textShowAnswer.setText(q.getAnswer());
    }
}
