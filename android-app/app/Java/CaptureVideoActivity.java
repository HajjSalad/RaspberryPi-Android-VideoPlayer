package com.example.piplayer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CaptureVideoActivity extends AppCompatActivity {

    private Button startButton, stopButton;
    private static final String start = "http://192.168.50.17:8000/start-capture";
    private static final String stop = "http://192.168.50.17:8000/stop-capture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capture_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startButton = findViewById(R.id.startRecordingVideoButton);
        stopButton = findViewById(R.id.stopRecordingVideoButton);

        startButton.setOnClickListener(view -> {
            sendTriggerRequest(start);
            Toast.makeText(getApplicationContext(), "Video Recording Started", Toast.LENGTH_SHORT).show();
        });

        stopButton.setOnClickListener(view -> {
            sendTriggerRequest(stop);
            Toast.makeText(getApplicationContext(), "Video Recording Stopped", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Captured frames stored in Pi", Toast.LENGTH_SHORT).show();
        });
    }
    private void sendTriggerRequest(String urlStr) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}