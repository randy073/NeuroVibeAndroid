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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Vibrator;
import android.view.View;
import com.example.randy.neurovibeandroid.MySQLiteHelper;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends Activity {

    private TextView mTextView;
    private int counter = 30;
    private float intensity = 0.1f;



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
                Button vibrateButton = (Button) findViewById(R.id.vibrate_button);
                vibrateButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (counter == 90) {
                            counter = 30;
                        } else {
                            counter += 10;
                        }

                        if(intensity >= 0.9f){
                            intensity = 0.1f;
                        }else{
                            intensity += 0.1f;
                        }
//                        long[] pattern = genVibratorPattern(intensity, 100);
                        long[] pattern = getVibratePattern(counter);
                        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(pattern,-1);

                        Log.d("Vibration", Arrays.toString(pattern));
//                        Log.d("Intensity", Float.toString(intensity));

                    }

                });

                Button patternButton = (Button) findViewById(R.id.pattern_button);
                patternButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        generatePattern(false);


                    }

                });
                generateVibration();
            }
        });



    }

    public long[] genVibratorPattern( float intensity, long duration )
    {
        float dutyCycle = Math.abs( ( intensity * 2.0f ) - 1.0f );
        long hWidth = (long) ( dutyCycle * ( duration - 1 ) ) + 1;
        long lWidth = dutyCycle == 1.0f ? 0 : 1;

        int pulseCount = (int) ( 2.0f * ( (float) duration / (float) ( hWidth + lWidth ) ) );
        long[] pattern = new long[ pulseCount ];

        for( int i = 0; i < pulseCount; i++ )
        {
            pattern[i] = intensity < 0.5f ? ( i % 2 == 0 ? hWidth : lWidth*10 ) : ( i % 2 == 0 ? lWidth*10 : hWidth );
        }

        return pattern;

    }

     private void generatePattern(boolean increasing){
         Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        if(increasing){
            long[] pattern1 = {7, 15, 7, 15,3, 15, 3, 15, 7, 25, 7, 25, 5, 25, 5, 25, 5, 30, 5, 30, 3, 30, 3, 30,2, 30, 2, 30};
            v.vibrate(pattern1,-1);
        }else{
           long[] pattern2 = {2, 30, 2, 30, 3, 30, 3, 30, 5, 30, 5, 30, 5, 25, 5, 25, 7, 25, 7, 25, 3, 15, 3, 15, 7, 15, 7, 15};
            v.vibrate(pattern2,-1);
        }



    }
    private long[] getVibratePattern(int intensity) {
        long[] pattern = null;
        final long[] smooth90 = {2, 30, 2, 30}; // Smooth - 90%
        final long[] smooth80 = {3, 30, 3, 30}; // Smooth - 80%
        final long[] smooth70 = {5, 30, 5, 30}; // Smooth - 75%
        final long[] smooth60 = {5, 25, 5, 25}; // Smooth - 60%
        final long[] smooth50 = {7, 25, 7, 25}; // Smooth - 50%
        final long[] smooth40 = {3, 15, 3, 15}; // Smooth - 40%
        final long[] smooth30 = {7, 15, 7, 15}; // Smooth - 30%
        switch (intensity){
            case 90: return smooth90;
            case 80: return smooth80;
            case 70: return smooth70;
            case 60: return smooth60;
            case 50: return smooth50;
            case 40: return smooth40;
            case 30: return smooth30;
        }
        return pattern;
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
        //long [] patterns = genVibratorPattern(0.7f,20);
        long [] patterns = getVibratePattern(counter);
        v.vibrate(patterns,-1);
//        long[] patterns = new long[8];
//        int index = 0;
//        for(int i = 4; i>=1; i--){
//    		long on = (long)(1.0/i * 100);
//    		long off = 100-on;
//            Log.d("patterns ", " i = " + i  + " on = "+ on + " off = " + off );
//    		patterns[index] = on;
//            index++;
//    		patterns[index] = off;
//            index++;
//    	}
//        v.vibrate(patterns, -1);


        Log.d("DEBUG", Integer.toString(rand));
        Log.d("Counter",Integer.toString(counter));

        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("NeuroVibeAndroid.RANDOM_NUMBER", rand);
        startActivity(intent);

    }
}
