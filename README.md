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
&nbsp;&nbsp;&nbsp;• Centralized navigation hub.      
&nbsp;&nbsp;&nbsp;• Quick access to all major functionalities: Stream from Internet, Stream from Local etc.    
&nbsp;&nbsp;&nbsp;• Provide direct access to the developer’s professional profiles on LinkedIn and GitHub.  
 
### 1. 📡 Stream from Internet    
&nbsp;&nbsp;&nbsp;• Watch internet-hosted video streams directly from the Android app using ExoPlayer.   

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;📁 **Cureent Implementation**    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Video URLs are hardcoded in the app and tied to each button. Not scalable for future changes.   

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;🏗️ **Future Enhancement**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Host a JSON file (e.g., on GitHub Pages) containing video metadata.    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• App fetches and parses this JSON to load videos dynamically - enabling easy updates.   



### 2. ▶️ Stream from Local (Phone Storage)
&nbsp;&nbsp;&nbsp;• Stream video files saved locally on Android phone thougha user-friendly interface.  

---

### 3. 🎥 Capture Video using USB webcam
The Android app allows remote control of video recording on the Raspberry Pi via HTTP triggers. It provides a simple interface to start and stop video capture from a USB webcam connected to the Pi.  

&nbsp;&nbsp;&nbsp;⎔ **Start Recording**   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tapping the `Start Recording Video` button sends a `GET /start-capture` HTTP request to the Pi.     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The Python HTTPServer on the Pi listens for this trigger.   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Upon receiving the request, it runs the video capture executable using: `subprocess.Popen(["./main"])`.   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• This starts capturing video using the connected USB webcam via the V4L2 API.   
&nbsp;&nbsp;&nbsp;⎔ **Stop Recording**   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Pressing `Stop Recording Video` sends a `GET /stop-capture` request.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The server intercepts this signal and gracefully stops the ongoing video recording process.     

📁 **Current Outputs**   
&nbsp;&nbsp;&nbsp;&nbsp;• Each captured frame is saved as a raw `.yuyv` file.   
&nbsp;&nbsp;&nbsp;&nbsp;• Files are named sequentially: `frame_0.yuyv`, `frame_1.yuyv`, and so on.  

🚧 **Future Enhancement**   
&nbsp;&nbsp;&nbsp;&nbsp;• The `.yuyv` frames will be automatically converted to a compressed `.mp4` video using FFmpeg.    
&nbsp;&nbsp;&nbsp;&nbsp;• The resulting `.mp4` file will be timestamped and named accordingly.   
&nbsp;&nbsp;&nbsp;&nbsp;• The server will return the final filename back to the Android app in the HTTP response.   

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

### 4. 📽️ Stream Live Feed from Pi (Notes on [Notion](https://hajjsalad.notion.site/4-Stream-Live-Feed-from-Pi-1f7a741b5aab80999b3ae4be82567a37))
The Android app can remotely control live video streaming from the Raspberry Pi using HTTP commands, enabling real-time video access directly from the Pi’s USB webcam.  
 
&nbsp;&nbsp;&nbsp;⎔ **Start Streaming**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Tapping `Start Live Stream` in the app sends a `GET /start-live-stream` request to the Pi.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The Python HTTP server running on the Pi receives this trigger and launches an `FFmpeg` process  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;using `subprocess.Popen()` to begin live streaming via HLS.    
&nbsp;&nbsp;&nbsp;⎔ **View Live Stream**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Tapping `View Live Stream` opens an `ExoPlayer` instance within the app to play the HLS stream.     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• ⚠️ *Note: There is a known startup delay in the stream. If playback is attempted immediately, it crashes*     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*the Exoplayer*.       
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• *Future versions will eliminate the separate `View Live Stream` button in favor of auto-play once the*     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*stream is confirmed active.*      
&nbsp;&nbsp;&nbsp;⎔ **Stop Streaming**  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Tapping `Stop Live Stream` sends a `GET /stop-live-stream` request to the Pi.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The server intercepts this request and terminates the FFmpeg process, ending the stream cleanly.    

**Folder Structure**  
```
pi-live-stream/
└── trigger_server.py        # Python HTTP server to handle streaming triggers
```

---

### 5. Stream Recorded Video from Pi
&nbsp;&nbsp;&nbsp;⎔ Coming soon....

---

### Android Folder Structure
```
android-app/
├── app/                      # Base application module
│   ├── AndroidManifest.xml   # App manifest
│   ├── java/
│   │   └── com/example/raspberryplayer/
│   │       ├── MainPageActivity.java           # Home screen with navigation buttons
│   │       ├── StreamInternetActivity.java     # Plays video from a URL
│   │       ├── StreamLocalActivity.java        # Plays video from device storage
│   │       ├── CaptureVideoActivity.java       # Sends HTTP commands to record video via Pi
│   │       ├── LiveFeedPiActivity.java         # Triggers live stream start/stop
│   │       ├── ExoPlayerActivity.java          # Displays live stream using ExoPlayer
│   │       └── StreamVideoPiActivity.java      # Streams recorded videos hosted by the Pi
│   └── res/
│       ├── layout/
│       │   ├── activity_main_page.xml
│       │   ├── activity_stream_internet.xml
│       │   ├── activity_stream_local.xml
│       │   ├── activity_capture_video.xml
│       │   ├── activity_live_feed_pi.xml
│       │   ├── activity_exo_player.xml
│       │   └── activity_stream_video_pi.xml
│       ├── drawable/                           # Icons, backgrounds, etc.
│       └── values/
│           ├── strings.xml                     # UI text content
│           ├── colors.xml
│           └── themes.xml
```

---

### Demo

| ![Demo 1](./1.gif) | ![Demo 2](./2.gif) | ![Demo 3](./3.gif) |
|:------------------:|:------------------:|:------------------:|

