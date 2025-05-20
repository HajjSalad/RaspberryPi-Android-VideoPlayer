## 📹 RaspberryPi-Android-VideoPlayer
A versatile project that bridges a Raspberry Pi and an Android app to enable seamless video capture, playback, and streaming across devices.   

### 🚀 Project Overview
This system empowers users with:  
- 🎬 Android App for intuitive video playback  
- 🌐 Streaming from Internet and local device storage  
- 🎥 Video Capture via USB Webcam using V4L2 on the Raspberry Pi  
- 📡 Live Video Streaming from the Pi to the Android device over the network  

## 🔧 Features
### 🏠 Main Page
&nbsp;&nbsp;&nbsp;• Centralized navigation hub  
&nbsp;&nbsp;&nbsp;• Quick access to all major functionalities: Stream from Internet, Stream from Local etc.   
&nbsp;&nbsp;&nbsp;• Provide direct access to the developer’s professional profiles on LinkedIn and GitHub.

### 1. 📡 Stream from Internet
&nbsp;&nbsp;&nbsp;• Watch internet-hosted video streams directly from the Android app using ExoPlayer.   

#### 2. ▶️ Stream from Local (Phone Storage)
&nbsp;&nbsp;&nbsp;• Stream video files saved locally on Android phone thougha user-friendly interface.  

---

#### 3. 🎥 Capture Video using USB webcam
The Android app allows remote control of video recording on the Raspberry Pi via HTTP triggers.   
&nbsp;&nbsp;&nbsp;⎔ **Start Recording**   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tapping the `Start Recording Video` button sends a `GET /start-capture` HTTP request to the Pi.     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The Python HTTPServer on the Pi listens for this trigger.   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Upon receiving the request, it runs the video capture executable using: `subprocess.Popen(["./main"])`.   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• This starts capturing video using the connected USB webcam via the V4L2 API.   
&nbsp;&nbsp;&nbsp;⎔ **Stop Recording**   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Pressing `Stop Recording Video` sends a `GET /stop-capture` request.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The server intercepts this signal and gracefully stops the ongoing video recording process.   

**Folder Structure**  
```
pi-video-capture/
├── main.c                   # V4L2-based video capture source code
├── main                     # Compiled binary executed by the server
├── Makefile                 # Build configuration for compiling main.c
├── trigger_server.py        # Lightweight HTTP server for start/stop triggers
└── captured_frames/         # Output directory for captured frames
```

---

#### 4. 📽️ Stream Live Feed from Pi
The Android app can remotely control live video streaming from the Raspberry Pi using HTTP commands, enabling real-time video access directly from the Pi’s USB webcam.  
🔁 How It Works  
&nbsp;&nbsp;&nbsp;⎔ **Start Streaming**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Tapping `Start Live Stream` in the app sends a `GET /start-live-stream` request to the Pi.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The Python HTTP server running on the Pi receives this trigger and launches an `FFmpeg` process using `subprocess.Popen()` to begin live streaming via HLS.    
&nbsp;&nbsp;&nbsp;⎔ **View Live Stream**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Tapping `View Live Stream` opens an `ExoPlayer` instance within the app to play the HLS stream.     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• ⚠️ Note: There is a known startup delay in the stream. If playback is attempted immediately, it crashes the Exoplayer.      
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Future versions will eliminate the separate "View Live Stream" button in favor of auto-play once the stream is confirmed active.   
&nbsp;&nbsp;&nbsp;⎔ **Stop Streaming**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tapping `Stop Live Stream` sends a `GET /stop-live-stream` request to the Pi.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The server intercepts this request and terminates the FFmpeg process, ending the stream cleanly.    

**Folder Structure**  
```
pi-live-stream/
└── trigger_server.py        # Python HTTP server to handle streaming triggers
```

#### 5. Stream Recorded Video from Pi


