package com.example.questionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView headerText;
    private EditText textQuestion, textAnswer, textQuestionNumber;
    private Button btnAdd, btnUpdate, btnDelete, btnSubmit, btnPreview;
    private int addCounter = 1;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headerText = findViewById(R.id.headerText);
        headerText.setText("Question # " + addCounter);

        textQuestionNumber = findViewById(R.id.textQuestionNumber);
        textQuestion = findViewById(R.id.textQuestion);
        textAnswer = findViewById(R.id.textAnswer);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addCounter > 3) {
                    Toast.makeText(getApplicationContext(), "You can only add a maximum of THREE Questions.", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper myDB = new DBHelper(MainActivity.this);
                    boolean result = myDB.insertQuestion(textQuestion.getText().toString().trim(),
                            textAnswer.getText().toString().trim());
                    if (result) {
                        addCounter++;
                        headerText.setText("Question # " + addCounter);
                        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

                        clearFields();

                        textQuestion.requestFocus();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error Encountered while Saving.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(MainActivity.this);

                if (textQuestionNumber.getText() != null
                        && !textQuestionNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    try {
                        Question q = myDB.getData(Integer.valueOf(textQuestionNumber.getText().toString()));
                        if (q != null) {
                            textQuestion.setText(q.getQuestion());
                            textAnswer.setText(q.getAnswer());
                            isUpdate = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Question does not exist.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "QuestionNumber must be an Integer.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "QuestionNumber must not be empty when updating.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(MainActivity.this);

                if (textQuestionNumber.getText() != null
                        && !textQuestionNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    try {
                        Question q = myDB.getData(Integer.valueOf(textQuestionNumber.getText().toString()));
                        if (q != null) {
                            textQuestion.setText(q.getQuestion());
                            textAnswer.setText(q.getAnswer());
                            isUpdate = true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Question does not exist.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "QuestionNumber must be an Integer.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "QuestionNumber must not be empty when Deleting.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(MainActivity.this);

                if (textQuestionNumber.getText() != null
                        && !textQuestionNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    try {

                        int id = Integer.valueOf(textQuestionNumber.getText().toString());
                        String question = textQuestion.getText().toString().trim();
                        String answer = textAnswer.getText().toString().trim();

                        if (isUpdate) {
                            //update
                            boolean result = myDB.updateQuestion(id, question, answer);
                            if (result) {
                                clearFields();
                                Toast.makeText(getApplicationContext(), "Question successfully updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Unable to update question.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //delete
                            boolean result = myDB.deleteQuestion(id);
                            if (result) {
                                clearFields();
                                Toast.makeText(getApplicationContext(), "Question successfully deleted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Unable to delete question.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        clearFields();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "QuestionNumber must be an Integer.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "QuestionNumber must not be empty when Updating or Deleting.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnPreview = findViewById(R.id.btnPreview);
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PreviewQuestions.class);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                finish();
            }
        });
    }

    private void clearFields() {
        //clear fields
        textQuestionNumber.setText(null);
        textQuestion.setText(null);
        textAnswer.setText(null);
    }

}