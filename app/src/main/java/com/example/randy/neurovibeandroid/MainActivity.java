package com.example.randy.neurovibeandroid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Vibrator;
import android.view.View;
import com.example.randy.neurovibeandroid.MySQLiteHelper;

import java.util.Random;

public class MainActivity extends Activity {

    private TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                Spinner spinner = (Spinner) findViewById(R.id.mode_spinner);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mode", parent.getItemAtPosition(position).toString());
                        editor.apply();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                generateVibration();
            }
        });



    }

    private void generateVibration(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Spinner spinner = (Spinner) findViewById(R.id.mode_spinner);

        if(!sharedPreferences.contains("mode")){
            editor.putString("mode","Training");
            editor.apply();
        }

        String mode = sharedPreferences.getString("mode",null);
        ArrayAdapter<String> myAdap = (ArrayAdapter<String>)spinner.getAdapter();
        int spinnerPosition = myAdap.getPosition(mode);
        spinner.setSelection(spinnerPosition);





        Random r = new Random();
        int rand = r.nextInt(8-0);

        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        long[] patterns = new long[8];
        int index = 0;
        for(int i = 4; i>=1; i--){
    		long on = (long)(1.0/i * 100);
    		long off = 100-on;
            Log.d("patterns ", " i = " + i  + " on = "+ on + " off = " + off );
    		patterns[index] = on;
            index++;
    		patterns[index] = off;
            index++;
    	}
        v.vibrate(patterns, -1);


        Log.d("DEBUG", Integer.toString(rand));
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("NeuroVibeAndroid.RANDOM_NUMBER", rand);
        startActivity(intent);

    }
}
