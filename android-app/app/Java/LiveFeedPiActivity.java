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

public class LiveFeedPiActivity extends AppCompatActivity {

    public Button button;
    private static final String PI_URL = "http://192.168.50.17:8000/start-live-stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_live_feed_pi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button send trigger to Pi to start recording & Open ExoPlayer
        button = (Button) findViewById(R.id.startLiveStreamButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendTriggerTask().execute(PI_URL);
                //goToExoPlayer();
//                new Thread(() -> {
//                    try {
//                        // Simple 3-second delay to let ffmpeg start the stream
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    runOnUiThread(LiveFeedPiActivity.this::goToExoPlayer);
//                }).start();
            }
        });

        button = (Button) findViewById(R.id.viewLiveStreamButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExoPlayer();
            }
        });

        button = (Button) findViewById(R.id.stopLiveStreamButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendTriggerTask().execute("http://192.168.50.17:8000/stop-stream");
                Toast.makeText(LiveFeedPiActivity.this, "Stop command sent to Pi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class SendTriggerTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                conn.connect();

                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void goToExoPlayer() {
        Intent intent = new Intent(LiveFeedPiActivity.this, ExoPlayerActivity.class);
        intent.putExtra("VIDEO_URL", "http://192.168.50.17/stream.m3u8");
        startActivity(intent);
    }
}