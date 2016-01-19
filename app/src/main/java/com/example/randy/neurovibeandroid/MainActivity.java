package com.example.randy.neurovibeandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;
import android.os.Vibrator;
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

                generateVibration();
            }
        });
        MySQLiteHelper db = new MySQLiteHelper(this);

    }

    private void generateVibration(){
        Random r = new Random();
        int rand = r.nextInt(8-0);

        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        long[] patterns = {0, 300, 300, 100, 100, 1000, 1000};
        v.vibrate(patterns, -1);

        String mode = "testing";


        Log.d("DEBUG",Integer.toString(rand));
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("NeuroVibeAndroid.MODE",mode);
        intent.putExtra("NeuroVibeAndroid.RANDOM_NUMBER", rand);
        startActivity(intent);

    }
}
