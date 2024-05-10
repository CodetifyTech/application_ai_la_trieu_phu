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
    private function_Questions currentQuestion;
    private function_Answers selectedAnswer;
    private List<function_Questions> questionList = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private long defaultTime = 30000; // 30s
    private long remainingTime = defaultTime;
    private SoundManager soundManager;
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
        soundManager = SoundManager.getInstance(this);
        soundManager.playMusic(R.raw.gofind);
        first_activity();
        findbyID();
        setEvent();
        call_firebase();

    }
    private void first_activity() {
        isPlaying = false;
        pickAnswerReady = true;
        help1Ready = true;
        help2Ready = true;
        help3Ready = true;
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
        btnAnswer1.setOnClickListener(buttonClickListener);
        btnAnswer2.setOnClickListener(buttonClickListener);
        btnAnswer3.setOnClickListener(buttonClickListener);
        btnAnswer4.setOnClickListener(buttonClickListener);
    }
    private View.OnClickListener buttonClickListener  = new View.OnClickListener() {
        public void onClick(View v) {
            if (v instanceof Button) {
                Button selectedButton = (Button) v;
                checkAnswer(selectedButton);
                stopTimer();
                setEnableAnswerSelected(selectedButton);
            }
        }
    };
    //@Override
//    public void onClick(final View v) {
//        switch (v.getId()) {
//            case R.id.btn_Answer1:
//                checkAnswer(btnAnswer1);
//                setEnableAnswerSelected(btnAnswer1);
//                break;
//            case R.id.btn_Answer2:
//                checkAnswer(btnAnswer2);
//                setEnableAnswerSelected(btnAnswer2);
//                break;
//            case R.id.btn_Answer3:
//                checkAnswer(btnAnswer3);
//                setEnableAnswerSelected(btnAnswer3);
//                break;
//            case R.id.btn_Answer4:
//                checkAnswer(btnAnswer4);
//                setEnableAnswerSelected(btnAnswer4);
//                break;
//            case R.id.btn_change:
//                NoticeDialog noticeDialog = new NoticeDialog(this);
//                noticeDialog.setNotification("Bạn thực sự muốn đổi câu hỏi ?", "Đồng ý", "Hủy bỏ");
//                noticeDialog.show();
//                break;
//
//            default:
//                break;
//        }
//    }
    private void call_firebase() {
        // create DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // read Firebase
        mDatabase.child("Questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadQuestions(dataSnapshot);
                showQuestion();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }
    private void loadQuestions(DataSnapshot dataSnapshot) {
        questionList.clear();
        for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
            function_Questions question = new function_Questions();
            question.quest = questionSnapshot.child("quest").getValue(String.class);

            List<function_Answers> answerList = new ArrayList<>();
            // loop get all answers
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
        randomQuestions = shuffleQuestions(questionList, 16);
    }
    private int getIndexByButton(Button button) {
        if (button == btnAnswer1) return 0;
        if (button == btnAnswer2) return 1;
        if (button == btnAnswer3) return 2;
        if (button == btnAnswer4) return 3;
        return -1;
    }
    private Button getButtonByIndex(int index) {
        switch (index) {
            case 0:
                return btnAnswer1;
            case 1:
                return btnAnswer2;
            case 2:
                return btnAnswer3;
            case 3:
                return btnAnswer4;
            default:
                return null;
        }
    }
    private void showQuestion() {
        if(currentQuestionIndex==0)setEnableAllHelp(false);
        else setEnableHelp();
        if (currentQuestionIndex >= 0 && currentQuestionIndex < 15) {
            function_Questions question = randomQuestions.get(currentQuestionIndex);
            txtQuestion.setText(question.quest);
            btnAnswer1.setText(question.list.get(0).answers);
            btnAnswer2.setText(question.list.get(1).answers);
            btnAnswer3.setText(question.list.get(2).answers);
            btnAnswer4.setText(question.list.get(3).answers);

            setAnswerButtonDrawable(btnAnswer1,R.drawable.pngplayer_answer_background_normal);
            setAnswerButtonDrawable(btnAnswer2,R.drawable.pngplayer_answer_background_normal);
            setAnswerButtonDrawable(btnAnswer3,R.drawable.pngplayer_answer_background_normal);
            setAnswerButtonDrawable(btnAnswer4,R.drawable.pngplayer_answer_background_normal);
            remainingTime = 30000; // 30s
            stopTimer();
            startTimer();
            currentQuestion = randomQuestions.get(currentQuestionIndex);


        }
    }
    private void checkAnswer(Button selectedAnswerIndex) {
        int index = getIndexByButton(selectedAnswerIndex);
        setAnswerButtonDrawable(selectedAnswerIndex, R.drawable.pngplayer_answer_background_selected);
        soundManager.playSoundAnswer(index);
        currentQuestion = randomQuestions.get(currentQuestionIndex);
        selectedAnswer = currentQuestion.list.get(index);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (selectedAnswer.check) {
                    //Log.d("checkanswer", "checkanswer chosse:   "+selectedAnswer.getcheck());
                    soundManager.playSoundAnswerTrue(index);
                    setAnswerButtonDrawable(selectedAnswerIndex, R.drawable.pngplayer_answer_background_true);
                    Toast.makeText(mh_player.this, "Đáp án đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < currentQuestion.list.size(); i++) {
                        function_Answers answer = currentQuestion.list.get(i);
                        if (answer.check) soundManager.playSoundAnswerFault(i);
                    }
                    //Log.d("checkanswer", "checkanswer chosse:   "+selectedAnswer.getcheck());
                    setAnswerButtonDrawable(selectedAnswerIndex, R.drawable.pngplayer_answer_background_wrong);
                    Toast.makeText(mh_player.this, "Đáp án sai! Bạn đã bị loại", Toast.LENGTH_SHORT).show();
                    soundManager.playMusic(R.raw.lose);
                    back_tomhstart();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentQuestionIndex++;
                        if (currentQuestionIndex < randomQuestions.size()) {
                            showQuestion();
                            logQuestion();
                            setEnableHelp();
                            setEnableAllAnswer(true);
                        } else {
                            Toast.makeText(mh_player.this, "Bạn đã hoàn thành tất cả câu hỏi!", Toast.LENGTH_SHORT).show();
                            back_tomhstart();
                        }
                    }
                }, 5000);
            }
        }, 5000);


    }
    private void buttonOpenDialog5050() {
        final function_CustomDialog5050 dialog = new function_CustomDialog5050(this);
        soundManager.playMusic(R.raw.sound5050);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    setAnswerButtonDrawable(btn_5050, R.drawable.pngplayer_button_image_help_5050_x);
                    ivent5050();
                    help1Ready = false;
                    setEnableHelp();
                    setEnableAllHelp(false);
                }
                startTimer();
            }
        });
    }
    private void ivent5050(){
        if (currentQuestionIndex != 0) {
            List<Integer> wrongIndexes = new ArrayList<>();
            for (int i = 0; i < currentQuestion.list.size(); i++) {
                function_Answers answer = currentQuestion.list.get(i);
                if (!answer.check) {
                    wrongIndexes.add(i);
                }
            }
            Collections.shuffle(wrongIndexes);
            int index1 = wrongIndexes.get(0);
            int index2 = wrongIndexes.get(1);
            setAnswerButtonDrawable(getButtonByIndex(index1),R.drawable.pngplayer_answer_background_hide); getButtonByIndex(index1).setText(" "); getButtonByIndex(index1).setEnabled(false);
            setAnswerButtonDrawable(getButtonByIndex(index2),R.drawable.pngplayer_answer_background_hide); getButtonByIndex(index2).setText(" "); getButtonByIndex(index2).setEnabled(false);
        }
    }
    private void buttonOpenDialogCallfr() {
        final function_CustomDialogCallfr dialog = new function_CustomDialogCallfr(this);
        soundManager.playMusic(R.raw.help_call);
        dialog.show();
        soundManager.playMusic(R.raw.out_of_time);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    setAnswerButtonDrawable(btn_callfr, R.drawable.pngplayer_button_image_help_call_x);
                    iventCallfr();
                    help2Ready = false;
                    setEnableHelp();
                    setEnableAllHelp(false);
                }
                startTimer();
            }
        });
    }
    private void iventCallfr() {
        for (int i = 0; i < currentQuestion.list.size(); i++) {
            function_Answers answer = currentQuestion.list.get(i);
            if (answer.check) {
                Toast.makeText(this, "Bạn của bạn đã trợ giúp với đáp án là: "+answer.getanswers(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void buttonDialogAudience() {
        final function_CustomDialogAudience dialog = new function_CustomDialogAudience(this);
        soundManager.playMusic(R.raw.khan_gia);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    setAnswerButtonDrawable(btn_audience, R.drawable.pngplayer_button_image_help_audience_x);
                    iventAudience();
                    help3Ready = false;
                    setEnableHelp();
                    setEnableAllHelp(false);
                }
                startTimer();
            }
        });
    }
    private void iventAudience() {
        Toast.makeText(this, ""+Audience(1)+" "+getRandomAnswer(true),Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ""+Audience(2)+" "+getRandomAnswer(true),Toast.LENGTH_SHORT).show();
    }
    private String getRandomAnswer(boolean isCorrect) {
        List<function_Answers> answers = new ArrayList<>();
        for (function_Answers answer : currentQuestion.list) {
            if (answer.check == isCorrect) {
                answers.add(answer);
            }
        }
        if (!answers.isEmpty()) {
            return answers.get(0).answers;
        } else {
            return "";
        }
    }
    private void buttonDialogChange() {
        help4Ready = false;
        setEnableHelp();
        setEnableAllHelp(false);
    }







    // cac method suport...
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
                soundManager.playMusic(R.raw.out_of_time);
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
        stopTimer();
    }
    private void setAnswerButtonDrawable(Button button, int drawableResource) {
        button.setBackgroundResource(drawableResource);
    }
    private void setAnswerButtonDrawable(ImageButton button, int drawableResource) {
        button.setBackgroundResource(drawableResource);
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
    private void setEnableAllHelp(boolean temp){
        btn_5050.setEnabled(temp);
        btn_callfr.setEnabled(temp);
        btn_audience.setEnabled(temp);
        btn_change.setEnabled(temp);
    }
    private  void setEnableAnswerSelected(Button selectedAnswerIndex){
        int index = getIndexByButton(selectedAnswerIndex);
        if (index==0){
            btnAnswer2.setEnabled(false);
            btnAnswer3.setEnabled(false);
            btnAnswer4.setEnabled(false);
            setEnableAllHelp(false);
        }
        if (index==1){
            btnAnswer1.setEnabled(false);
            btnAnswer3.setEnabled(false);
            btnAnswer4.setEnabled(false);
            setEnableAllHelp(false);
        }
        if (index==2){
            btnAnswer1.setEnabled(false);
            btnAnswer2.setEnabled(false);
            btnAnswer4.setEnabled(false);
            setEnableAllHelp(false);
        }
        if (index==3){
            btnAnswer1.setEnabled(false);
            btnAnswer2.setEnabled(false);
            btnAnswer3.setEnabled(false);
            setEnableAllHelp(false);
        }
    }
    private void setEnableAllAnswer(boolean temp){
        btnAnswer1.setEnabled(temp);
        btnAnswer2.setEnabled(temp);
        btnAnswer3.setEnabled(temp);
        btnAnswer4.setEnabled(temp);
    }
    private String Audience(int index){
        if (index ==0) return "Đáp án đúng theo tôi là: ";
        if (index ==1) return "Tôi nghĩ đáp án đúng là: ";
        if (index ==2) return "Chắc chắc là đáp án: ";
        if (index ==3) return "Tin tôi đi đáp án đúng là: ";
        return null;
    }
    private List<function_Questions> shuffleQuestions(List<function_Questions> questions, int count) {
        Random random = new Random();
        List<function_Questions> randomQuestions = new ArrayList<>(questions);
        if (count <= 0 || count > questions.size()) {
            throw new IllegalArgumentException("Count error");
        }
        Collections.shuffle(randomQuestions, random);
        return randomQuestions.subList(0, count);
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
    private  void logQuestion(){
        String quest = currentQuestion.getquest();
        List<Integer> answers = new ArrayList<>();
        for (int i = 0; i < currentQuestion.list.size(); i++) {
            function_Answers answer = currentQuestion.list.get(i);
            answers.add(i);
        }
        Log.d("log quest  : ", quest);
        Log.d("log answer1: ", String.valueOf(answers.get(0)));
        Log.d("log answer2: ", String.valueOf(answers.get(1)));
        Log.d("log answer3: ", String.valueOf(answers.get(2)));
        Log.d("log answer4: ", String.valueOf(answers.get(3)));
    }
    @Override
    public void onClick(View view) {

    }
}