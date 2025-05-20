## 📹 RaspberryPi-Android-VideoPlayer
A multi-functional project that connects a Raspberry Pi and an Android App for flexible video input/output capabilities. This system enables local and live video streaming between the Pi and Android, webcam capture via V4L2, and playback from various sources.

### 🔧 Features
### Main Page
&nbsp;&nbsp;&nbsp;• Provide links and navigation that lead to all the features.  

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


