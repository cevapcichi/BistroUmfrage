package de.munich.iteratec.bistroumfrage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Random;

public class DisplayQuestion extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout displayQuestion;
    private DatabaseReference mDatabase;
    private TextView question;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView answer4;

    private int countAnswer1 = 0;
    private int countAnswer2 = 0;
    private int countAnswer3 = 0;
    private int countAnswer4 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_question);



        //set background to random color
        displayQuestion = (LinearLayout) findViewById(R.id.displayQuestion);
        setRandomBackgroundColor(displayQuestion);


        //get int position that has been handed over to DisplayQuestion
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        int position = bundle.getInt("position");

        //connect to Database
        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.questionList.get(position).getQuestion());
        
        //set Text of TextViews
        question = (TextView) findViewById(R.id.question);
        question.setText(MainActivity.questionList.get(position).getQuestion());

        answer1 = (TextView) findViewById(R.id.answer1);
        answer1.setText(MainActivity.questionList.get(position).getAnswer1());
        answer1.setOnClickListener(this);

        answer2 = (TextView) findViewById(R.id.answer2);
        answer2.setText(MainActivity.questionList.get(position).getAnswer2());
        answer2.setOnClickListener(this);

        answer3 = (TextView) findViewById(R.id.answer3);
        answer3.setText(MainActivity.questionList.get(position).getAnswer3());
        answer3.setOnClickListener(this);

        answer4 = (TextView) findViewById(R.id.answer4);
        answer4.setText(MainActivity.questionList.get(position).getAnswer4());
        answer4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        dialogThanks();
        addAnswerCount(v);
        }


    public void dialogThanks() {

        //build AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(DisplayQuestion.this).setCancelable(false).setTitle("Danke für deine Teilnahme!").setMessage("Komm nächste Woche wieder für neue Fragen \uD83C\uDF89");


        //set AlertDialog and make it not focusable when created to prevent action bar and navigation bar showing
        final AlertDialog alert = dialog.create();
        alert.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        alert.show();
        //clear flags 'not focusable'
        alert.getWindow().getDecorView().setSystemUiVisibility(DisplayQuestion.this.getWindow().getDecorView().getSystemUiVisibility());
        alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        //close AlertDialog
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        //delay closing AlertDialog
        handler.postDelayed(runnable, 2500);
    }


    public void addAnswerCount(View v){
        switch (v.getId()){
            case R.id.answer1:
                countAnswer1++;
                mDatabase.child(answer1.getText().toString()).setValue(countAnswer1);
                break;
            case R.id.answer2:
                countAnswer2++;
                mDatabase.child(answer2.getText().toString()).setValue(countAnswer2);
                break;
            case R.id.answer3:
                countAnswer3++;
                mDatabase.child(answer3.getText().toString()).setValue(countAnswer3);
                break;
            case R.id.answer4:
                countAnswer4++;
                mDatabase.child(answer4.getText().toString()).setValue(countAnswer4);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }


    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    public void setRandomBackgroundColor(View view){
        int[] displayColors = getResources().getIntArray(R.array.displayColors);
        int randomColor = displayColors[new Random().nextInt(displayColors.length)];
        view.setBackgroundColor(randomColor);
    }
}
