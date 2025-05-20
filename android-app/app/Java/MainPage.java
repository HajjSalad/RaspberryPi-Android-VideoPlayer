package com.example.piplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainPage extends AppCompatActivity {
    public Button Button1, Button2, Button3, Button4, Button5, Button6, Button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button1 -> Stream from Internet
        Button1 = (Button) findViewById(R.id.button1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, StreamInternetActivity.class);
                startActivity(intent);
            }
        });

        // Button2 -> Stream from Local
        Button2 = (Button) findViewById(R.id.button2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, StreamLocalActivity.class);
                startActivity(intent);
            }
        });

        // Button3 -> Capture Video (Webcam)
        Button3 = (Button) findViewById(R.id.button3);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, CaptureVideoActivity.class);
                startActivity(intent);
            }
        });

        // Button4 -> Stream Live feed from Pi
        Button4 = (Button) findViewById(R.id.button4);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, LiveFeedPiActivity.class);
                startActivity(intent);
            }
        });

        // Button5 -> Stream From Pi (Recordings)
        Button5 = (Button) findViewById(R.id.button5);
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, StreamVideoPiActivity.class);
                startActivity(intent);
            }
        });

        // Button6 -> LinkedIn
        Button6 = (Button) findViewById(R.id.button6);
        Button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://hajj-salad/"));
                if (intent.resolveActivity(getPackageManager()) == null) {
                    // Fallback to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/hajj-salad/"));
                }
                startActivity(intent);
            }
        });

        // Button7 -> Github
        Button7 = (Button) findViewById(R.id.button7);
        Button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/HajjSalad"));
                startActivity(intent);
            }
        });
    }
}