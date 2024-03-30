package com.example.application_ai_la_trieu_phu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class mh_player extends AppCompatActivity implements View.OnClickListener {

    private Button btn_showscore, btn_time;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    private TextView txtQuestion;
    private ImageButton btn_5050, btn_callfr, btn_audience, btn_change;
    private boolean isPlaying;
    private boolean pickAnswerReady;
    private boolean help1Ready , help2Ready, help3Ready, help4Ready;
    private int currentQuestionIndex = 0;
    private DatabaseReference mDatabase;
    private List<function_Questions> randomQuestions;
    private List<function_Questions> questionList = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private long defaultTime = 30000; // 30 giây
    private long remainingTime = defaultTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        first_activity();
        findbyID();
        setEvent();
    }

    private void first_activity() {
        isPlaying = false;
        pickAnswerReady = true;
        help1Ready = true;
        help2Ready = true;
        help3Ready =true;
        help4Ready = true;
    }

    public void listCauHoi() {
        function_DialogListCauHoi dialogFragment = new function_DialogListCauHoi();
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    private void setEvent() {


        btn_showscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCauHoi();
            }
        });

        btn_5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                buttonOpenDialog5050();
            }
        });
        btn_callfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                buttonOpenDialogCallfr();
            }
        });
        btn_audience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                buttonDialogAudience();
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                buttonDialogChange();
            }
        });
        call_firebase();
        // ivent 4 button
        btnAnswer1.setOnClickListener(buttonClickListener);
        btnAnswer2.setOnClickListener(buttonClickListener);
        btnAnswer3.setOnClickListener(buttonClickListener);
        btnAnswer4.setOnClickListener(buttonClickListener);
    }


    // ivent for each button
    private View.OnClickListener buttonClickListener  = new View.OnClickListener() {
        public void onClick(View v) {
            if (v instanceof Button) {
                Button selectedButton = (Button) v;
                checkAnswer(selectedButton);
                setEnableAnswerSelected(selectedButton);
            }
        }
    };
    @Override
    public void onClick(View v) {
        // Xử lý sự kiện click cho các Case (TextView)
    }
    private void call_firebase() {
        // create DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // read Firebase
        mDatabase.child("Questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //loop get all cau hoi
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    function_Questions question = new function_Questions();
                    question.quest = questionSnapshot.child("quest").getValue(String.class);

                    List<function_Answers> answerList = new ArrayList<>();

                    // loop get all cau tra loi
                    for (DataSnapshot answerSnapshot : questionSnapshot.child("listanswer").getChildren()) {
                        function_Answers answer = new function_Answers();
                        answer.answers = answerSnapshot.child("answer").getValue(String.class);
                        Boolean checkValue = answerSnapshot.child("check").getValue(Boolean.class);
                        boolean check = (checkValue != null) ? checkValue.booleanValue() : false; // check null ?

                        answer.check = check;
                        answerList.add(answer);
                    }
                    question.list = answerList;
                    questionList.add(question);
                }

                randomQuestions = getRandomQuestions(questionList, 16);

                // Hiển thị câu hỏi đầu tiên
                showQuestion(currentQuestionIndex);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // log error
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }



    private int getIndexByButton(Button button) {
        if (button == btnAnswer1) return 0;
        if (button == btnAnswer2) return 1;
        if (button == btnAnswer3) return 2;
        if (button == btnAnswer4) return 3;
        return -1;
    }
    // display quest
    private void showQuestion(int questionIndex) {
        Log.d("check", "Cau hoi hien tai: " + currentQuestionIndex);
        Log.d("check", "So cau hoi: " + randomQuestions.size());
        if (questionIndex >= 0 && questionIndex < randomQuestions.size()) {
            Log.e("check", "index income dung.");
        } else {
            Log.e("check", "index income saii.");
        }
        if (questionIndex >= 0 && questionIndex < 15) {
            function_Questions question = randomQuestions.get(questionIndex);
            txtQuestion.setText(question.quest);
            btnAnswer1.setText(question.list.get(0).answers);
            btnAnswer2.setText(question.list.get(1).answers);
            btnAnswer3.setText(question.list.get(2).answers);
            btnAnswer4.setText(question.list.get(3).answers);

            // Reset
            setAnswerButtonDrawable(btnAnswer1,R.drawable.pngplayer_answer_background_normal);
            setAnswerButtonDrawable(btnAnswer2,R.drawable.pngplayer_answer_background_normal);
            setAnswerButtonDrawable(btnAnswer3,R.drawable.pngplayer_answer_background_normal);
            setAnswerButtonDrawable(btnAnswer4,R.drawable.pngplayer_answer_background_normal);
            remainingTime = 30000; // 30 giây
            stopTimer();
            startTimer();
            setEnableHelp();
        }
    }

    // get random quest, follow count la so cau hoi
    private List<function_Questions> getRandomQuestions(List<function_Questions> questions, int count) {
        Random random = new Random();
        List<function_Questions> randomQuestions = new ArrayList<>(questions);

        // check count
        if (count <= 0 || count > questions.size()) {
            throw new IllegalArgumentException("Invalid count value");
        }

        // Xáo trộn danh sách và chỉ lấy count phần tử đầu tiên
        Collections.shuffle(randomQuestions, random);
        return randomQuestions.subList(0, count);
    }

    private void checkAnswer(Button selectedAnswerIndex) {
        int index = getIndexByButton(selectedAnswerIndex);
        setAnswerButtonDrawable(selectedAnswerIndex, R.drawable.pngplayer_answer_background_selected);
        function_Questions currentQuestion = randomQuestions.get(currentQuestionIndex);
        function_Answers selectedAnswer = currentQuestion.list.get(index);
        Log.d("check", "checkanswer active"+index);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Kiểm tra đáp án
                if (selectedAnswer.check) { //  case true
                    setAnswerButtonDrawable(selectedAnswerIndex, R.drawable.pngplayer_answer_background_true);
                    Toast.makeText(mh_player.this, "Đáp án đúng!", Toast.LENGTH_SHORT).show();
                } else {  // case false --> intent mh_start
                    setAnswerButtonDrawable(selectedAnswerIndex, R.drawable.pngplayer_answer_background_wrong);
                    Toast.makeText(mh_player.this, "Đáp án sai! Bạn đã bị loại", Toast.LENGTH_SHORT).show();
                    back_tomhstart();
                }


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        if (currentQuestionIndex < randomQuestions.size()) {
                            showQuestion(currentQuestionIndex);
                            setEnableAllAnswer();
                        } else {
                            Toast.makeText(mh_player.this, "Bạn đã hoàn thành tất cả câu hỏi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000); //  hold 3s after setdrawable selected
            }


        }, 3000); // hold 3s after setdrawable true or false
    }

    private void setEnableHelp(){
        if(help1Ready) btn_5050.setEnabled(true);
        else btn_5050.setEnabled(false);
        if(help2Ready) btn_callfr.setEnabled(true);
        else btn_callfr.setEnabled(false);
        if(help3Ready) btn_audience.setEnabled(true);
        else btn_audience.setEnabled(false);
        if(help4Ready) btn_change.setEnabled(true);
        else btn_change.setEnabled(false);
    }
    private void setUnnableAllHelp(){
        btn_5050.setEnabled(false);
        btn_callfr.setEnabled(false);
        btn_audience.setEnabled(false);
        btn_change.setEnabled(false);
    }
    private  void setEnableAnswerSelected(Button selectedAnswerIndex){
        int index = getIndexByButton(selectedAnswerIndex);
        Log.d("Check index button", "setEnableAnswer: "+index);
        if (index==0){
            btnAnswer2.setEnabled(false);
            btnAnswer3.setEnabled(false);
            btnAnswer4.setEnabled(false);
            setUnnableAllHelp();
        }
        if (index==1){
            btnAnswer1.setEnabled(false);
            btnAnswer3.setEnabled(false);
            btnAnswer4.setEnabled(false);
            setUnnableAllHelp();
        }
        if (index==2){
            btnAnswer1.setEnabled(false);
            btnAnswer2.setEnabled(false);
            btnAnswer4.setEnabled(false);
            setUnnableAllHelp();
        }
        if (index==3){
            btnAnswer1.setEnabled(false);
            btnAnswer2.setEnabled(false);
            btnAnswer3.setEnabled(false);
            setUnnableAllHelp();
        }
    }
    private void setEnableAllAnswer(){
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnAnswer4.setEnabled(true);
    }


    private void buttonOpenDialog5050() {

        final function_CustomDialog5050 dialog = new function_CustomDialog5050(this);

        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    setAnswerButtonDrawable(btn_5050, R.drawable.pngplayer_button_image_help_5050_x);
                    help1Ready = false;
                }
                startTimer();
            }
        });
    }

    private void buttonOpenDialogCallfr() {

        final function_CustomDialogCallfr dialog = new function_CustomDialogCallfr(this);

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    setAnswerButtonDrawable(btn_callfr, R.drawable.pngplayer_button_image_help_call_x);
                    help2Ready = false;
                }
                startTimer();
            }
        });
    }

    private void buttonDialogAudience() {
        final function_CustomDialogAudience dialog = new function_CustomDialogAudience(this);

        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    setAnswerButtonDrawable(btn_audience, R.drawable.pngplayer_button_image_help_audience_x);
                    help3Ready = false;
                }
                startTimer();
            }
        });
    }
    private void buttonDialogChange() {
        help4Ready = false;
    }






    // cac method phu...
    private void back_tomhstart() {
        Intent intent = new Intent(mh_player.this, mh_start.class);
        startActivity(intent);
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(remainingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                long secondsRemaining = millisUntilFinished / 1000;
                btn_time.setText("Time: " + secondsRemaining + " s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(mh_player.this, "TIME OUT", Toast.LENGTH_SHORT).show();
                back_tomhstart();
            }

        }.start();
    }
    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    protected void onPause() {
        super.onPause();
        stopTimer(); // Dừng bộ đếm thời gian khi activity bị pause
    }
    private void setAnswerButtonDrawable(Button button, int drawableResource) {
        button.setBackgroundResource(drawableResource);
    }
    private void setAnswerButtonDrawable(ImageButton button, int drawableResource) {
        button.setBackgroundResource(drawableResource);
    }
    private void findbyID() {
        btn_showscore = (Button) findViewById(R.id.btn_showscore);
        btn_time = (Button) findViewById(R.id.btn_time);
        btnAnswer1 = (Button) findViewById(R.id.btn_Answer1);
        btnAnswer2 = (Button) findViewById(R.id.btn_Answer2);
        btnAnswer3 = (Button) findViewById(R.id.btn_Answer3);
        btnAnswer4 = (Button) findViewById(R.id.btn_Answer4);
        btn_5050 = (ImageButton) findViewById(R.id.btn_5050);
        btn_callfr = (ImageButton) findViewById(R.id.btn_callfr);
        btn_audience = (ImageButton) findViewById(R.id.btn_audience);
        btn_change = (ImageButton) findViewById(R.id.btn_change);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
    }
}