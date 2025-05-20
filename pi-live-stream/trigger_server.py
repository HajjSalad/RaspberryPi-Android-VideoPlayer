from http.server import BaseHTTPRequestHandler, HTTPServer
import subprocess
import threading

# FFmpeg command as a list
FFMPEG_CMD = [
    "ffmpeg",
    "-f", "v4l2",
    "-framerate", "15",
    "-video_size", "640x480",
    "-i", "/dev/video0",
    "-c:v", "libx264",
    "-profile:v", "baseline",   # Use 'baseline' or 'main' for compatibility
    "-level", "3.1",            # Level 3.1 is widely supported
    "-preset", "ultrafast",
    "-f", "hls",
    "-hls_time", "2",
    "-hls_list_size", "4",
    "-hls_flags", "delete_segments",
    "/var/www/html/stream.m3u8"
]

class RequestHandler(BaseHTTPRequestHandler):
    ffmpeg_process = None  # Store ffmpeg process here

    def do_GET(self):
        if self.path == '/start-stream':
            self.send_response(200)
            self.send_header('Content-type', 'text/plain')
            self.end_headers()

            # If ffmpeg already running, ignore or restart (your choice)
            if RequestHandler.ffmpeg_process is None or RequestHandler.ffmpeg_process.poll() is not None:
                # Start FFmpeg in a separate thread so server keeps running
                def run_ffmpeg():
                    print("Starting FFmpeg live stream...")
                    RequestHandler.ffmpeg_process = subprocess.Popen(FFMPEG_CMD)
                    RequestHandler.ffmpeg_process.wait()
                    print("FFmpeg process ended.")

                threading.Thread(target=run_ffmpeg, daemon=True).start()
                self.wfile.write(b"Live stream started.\n")
            else:
                self.wfile.write(b"Live stream already running.\n")

        elif self.path == '/stop-stream':
            self.send_response(200)
            self.send_header('Content-type', 'text/plain')
            self.end_headers()

            if RequestHandler.ffmpeg_process and RequestHandler.ffmpeg_process.poll() is None:
                RequestHandler.ffmpeg_process.terminate()
                RequestHandler.ffmpeg_process = None
                self.wfile.write(b"Live stream stopped.\n")
            else:
                self.wfile.write(b"No live stream running.\n")

        else:
            self.send_response(404)
            self.end_headers()
            self.wfile.write(b"Not Found.\n")

def run_server(server_class=HTTPServer, handler_class=RequestHandler, port=8000):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print(f"Listening on port {port}...")
    httpd.serve_forever()

if __name__ == "__main__":
    run_server()
