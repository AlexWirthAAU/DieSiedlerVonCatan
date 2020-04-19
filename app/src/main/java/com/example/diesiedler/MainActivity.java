package com.example.diesiedler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void discover(View view) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

/*
package com.example.diesiedler;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btn1 = (Button) findViewById(R.id.dummy_button);
        TextView txt2 = (TextView) findViewById(R.id.textView);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String[] workouts = {"Übung 1", "Übung 2", "Übung 3", "Übung 4", "Übung 5", "Übung 6", "Übung 7", "Übung 8", "Übung 9", "Übung 10"};

                int min = 0;
                int max = workouts.length - 1;
                int rnd = (int) (Math.random() * ((max - min) + 1)) + min;

                System.out.println("Anzahl Übungen = " + max);
                System.out.println("Zufallsübung Nr = " + rnd);
                System.out.println("Workout: " + workouts[rnd]);
            }

        });

        Button btn2 = (Button) findViewById(R.id.dummy_button);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);
            }
        });
    }
}*/