package com.example.randy.neurovibeandroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.randy.neurovibeandroid.MySQLiteHelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


/**
 * Created by randy on 1/16/2016.
 */
public class RecordActivity extends Activity {
    private String displayMode;
    private String mode;
    private int rand_num = -1;
    private int guessed_num = -1;
    private String[] people = {"Romit","Randy","Nirupam","Mahanth","Obama","Michael Jordan","Bill Gates","Taylor Swift"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent mainIntent = getIntent();
        Bundle b = mainIntent.getExtras();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mode = sharedPreferences.getString("mode",null);

        if(b != null){
             rand_num = (int) b.get("NeuroVibeAndroid.RANDOM_NUMBER");
        }

        if(rand_num < 4) {
            displayMode = "private";
        }else{
            displayMode = "public";
        }

        if(mode.equals("Testing")){
            showTest();
        }else{
            showTraining();
        }

//        Intent mStartActivity = new Intent(this, MainActivity.class);
//        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        int mPendingIntentId = 123456;
//        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId,mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager mgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10000, mPendingIntent);



    }

    public void showTest(){
        if(displayMode.equals("public")){
            for(int i = 0; i < 4; i++){
                String btnID = "imgBtn" + i;
                int idResource = getResources().getIdentifier(btnID, "id", getPackageName());
                ImageButton btn = (ImageButton) findViewById(idResource);
                int imageID = getResources().getIdentifier("public" + i, "drawable", getPackageName());
                btn.setImageResource(imageID);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
                        helper.addRecord(mode,"auto",people[rand_num],"",Integer.toString(rand_num),Integer.toString(guessed_num));
                        finish();
                    }
                });
            }
        }else{
            for(int i = 0; i < 4; i++){
                String btnID = "imgBtn" + i;
                int idResource = getResources().getIdentifier(btnID, "id", getPackageName());
                ImageButton btn = (ImageButton) findViewById(idResource);
                int imageID = getResources().getIdentifier("private"+i, "drawable", getPackageName());
                btn.setImageResource(imageID);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
                        helper.addRecord(mode, "auto", people[rand_num], "", Integer.toString(rand_num), Integer.toString(guessed_num));
                        finish();
                    }
                });
            }
        }




    }




    public void showTraining(){
        int imgNumber;
        if(displayMode.equals("public")){
            imgNumber = rand_num - 4;
            String btnID = "imgBtn" + imgNumber;
            int idResource = getResources().getIdentifier(btnID, "id", getPackageName());
            ImageButton btn = (ImageButton) findViewById(idResource);
            int imageID = getResources().getIdentifier("public" + imgNumber, "drawable", getPackageName());
            btn.setImageResource(imageID);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
                    helper.addRecord(mode,"auto",people[rand_num],"",Integer.toString(rand_num),Integer.toString(guessed_num));
                    finish();
                }
            });

        }else{
            imgNumber = rand_num;
            String btnID = "imgBtn" + imgNumber;
            int idResource = getResources().getIdentifier(btnID, "id", getPackageName());
            ImageButton btn = (ImageButton) findViewById(idResource);
            int imageID = getResources().getIdentifier("private"+imgNumber, "drawable", getPackageName());
            btn.setImageResource(imageID);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
                    helper.addRecord(mode, "auto", people[rand_num], "", Integer.toString(rand_num), Integer.toString(guessed_num));
                    finish();
                }
            });

        }
    }
}
