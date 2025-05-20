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
&nbsp;&nbsp;&nbsp;• Provides direct access to the developer’s professional profiles on LinkedIn and GitHub.

### 1. 📡 Stream from Internet
&nbsp;&nbsp;&nbsp;• Watch internet-hosted video streams directly from the Android app using ExoPlayer.   

#### 2. ▶️ Stream from Local (Phone Storage)
&nbsp;&nbsp;&nbsp;• Stream video files saved locally on Android phone thougha user-friendly interface.  

#### 3. 🎥 Capture Video using USB webcam
The Android app allows remote control of video recording on the Raspberry Pi via HTTP triggers.   
&nbsp;&nbsp;&nbsp;⎔ **Start Recording**   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tapping the "Start Recording Video" button sends a `GET /start-capture` HTTP request to the Pi.     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The Python HTTPServer on the Pi listens for this trigger.   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Upon receiving the request, it runs the video capture executable using: `subprocess.Popen(["./main"])`.   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• This starts capturing video using the connected USB webcam via the V4L2 API.   
&nbsp;&nbsp;&nbsp;⎔ **Stop Recording**   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Pressing "Stop Recording Video" sends a GET /stop-capture request.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• The server intercepts this signal and gracefully stops the ongoing video recording process.  




Pressing 'Start Recording Video' button on app sends `/start-capture` trigger on HTTP to Pi.    
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• HTTPServer listening to the port receives `/start-capture` trigger  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Runs subprocess.Popen([./main"]) to start capturing the video using the webcam
&nbsp;&nbsp;&nbsp;⎔ Clicking 'Stop Recording Video' button sends `/stop-capture` trigger on HTTP to Pi.
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• HTTPServer listening to the port receives `/stop-capture` trigger  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• Stops capturing the video

**Folder Structure**
```
pi-video-capture/
├── main.c                   # V4L2 video capture program
├── main                     # Compiled ready to be run by pyhton script
├── Makefile                 # Build System config
├── trigger_server.py        # 
└── captured_frames/         # Store saved .mp4 files
```

#### 4. Stream Live Feed from Pi



#### 5. Stream Recorded Video from Pi


