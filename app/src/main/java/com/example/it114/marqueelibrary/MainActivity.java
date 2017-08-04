package com.example.it114.marqueelibrary;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MarqueeTextView marqueeTextView;
    Button pause,resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee);
        marqueeTextView.spinnerText(0, 5, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        }, Color.RED);
        marqueeTextView.move(30);
        pause = (Button) findViewById(R.id.pause);
        resume = (Button) findViewById(R.id.resume);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Pause", Toast.LENGTH_SHORT).show();
                marqueeTextView.pause();
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Resume", Toast.LENGTH_SHORT).show();
                marqueeTextView.resume();
            }
        });
    }
}
