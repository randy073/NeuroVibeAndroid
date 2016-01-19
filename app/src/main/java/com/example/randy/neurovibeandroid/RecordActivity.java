package com.example.randy.neurovibeandroid;

import android.app.Activity;
import android.content.Intent;
import com.example.randy.neurovibeandroid.MySQLiteHelper;
import android.os.Bundle;
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



        if(b != null){
             mode = (String) b.get("NeuroVibeAndroid.MODE");
             rand_num = (int) b.get("NeuroVibeAndroid.RANDOM_NUMBER");
        }

        if(rand_num < 4) {
            displayMode = "private";
        }else{
            displayMode = "public";
        }

        if(mode.equals("testing")){
            showTest();
        }else{
            showTraining();
        }


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
