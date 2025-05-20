package com.example.piplayer;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.Log;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class ExoPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;          // Create object of PlayerView
    private ExoPlayer player;               // Create object of ExoPlayer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exo_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Assign the playerView
        playerView = findViewById(R.id.player_view);
        String videoURL = getIntent().getStringExtra("VIDEO_URL");

        // Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Add url to the playerView
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoURL));   // Get media item from url
        player.setMediaItem(mediaItem);     // Assign media item to the ExoPlayer
        player.prepare();                   // Prepare it
        player.play();                      // Play it

        // If error occurs, show Toast message
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Log.e("ExoPlayer", "Error: " + error.getMessage(), error);
                Toast.makeText(getApplicationContext(), "Error Playing Media", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
        }
    }
}