from http.server import BaseHTTPRequestHandler, HTTPServer
import subprocess

PORT = 8000

class TriggerHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/start-capture':
            self.send_response(200)
            self.end_headers()
            self.wfile.write(b"Starting capture...\n")

            print("Trigger received - starting capture...")

            # Remove stop flag before starting
            try:
                os.remove("/tmp/stop_capture")
            except FileNotFoundError:
                pass

            subprocess.Popen(["./main"])

        elif self.path == '/stop-capture':
            self.send_response(200)
            self.end_headers()
            self.wfile.write(b"Stopping capture...\n")

            print("Trigger received - stopping capture...")
            with open("/tmp/stop_capture", "w") as f:
                f.write("stop")

        else:
            self.send_response(404)
            self.end_headers()
            self.wfile.write(b"Invalid endpoint.\n")
        
def run_server():
    server_address = ('', PORT)
    httpd = HTTPServer(server_address, TriggerHandler)
    print(f"Listening on port {PORT}...")
    httpd.serve_forever()

if __name__ == "__main__":
    run_server()