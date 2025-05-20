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
&nbsp;&nbsp;&nbsp;• Quick access to all major functionalities: Stream from Internet etc.

### 1. 📡 Stream from Internet
&nbsp;&nbsp;&nbsp;• Watch internet-hosted video streams directly from the Android app using ExoPlayer.   

#### 2. ▶️ Stream from Local (Phone Storage)
&nbsp;&nbsp;&nbsp;• Stream video files saved locally on Android phone thougha user-friendly interface.  

#### 3. 🎥 Capture Video using USB webcam
Capture video from USB webcam using V4L2 in C, encode it to H.264 and save it to .`mp4`    
Step 1: Build a lightweight user-space video capture service for Pi using V4L2 API.   
  - Uses existing UVC (USB Video Class) driver in Linux for standard webcams.
  - Kernel provides driver (UVC module) that exposes `/dev/video0`
Step 2:

Folder Structure
```
pi-video-capture/
├── main.c               # V4L2 video capture program
├── Makefile             # Build System config
├── captured_frames/     # Store raw video frames
└── recordings/          # Store saved .mp4 files
```

#### 4. Stream Live Feed from Pi



#### 5. Stream Recorded Video from Pi


