// v4l2_capture.c

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>              // open()
#include <unistd.h>             // close()
#include <errno.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <linux/videodev2.h>

#define DEVICE "/dev/video0"        // Webcam
#define WIDTH 640
#define HEIGHT 480
#define NUM_BUFFERS 4

struct buffer {
    void   *start;
    size_t  length;
};

int main() {
    // Open the video device
    // fd -> file descriptor for /dev/video0
    int fd = open(DEVICE, O_RDWR);              // Open device for reading and writing
    if (fd < 0) {
        perror("Failed to open video device");
        return 1;
    }

    // Step 1: Set video capture format, resolution
    struct v4l2_format fmt = {0};                   // Clear all fields
    fmt.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;         // Buffer of a single-planar video capture stream (Link: https://www.kernel.org/doc/html/latest/userspace-api/media/v4l/buffer.html#c.V4L.v4l2_buf_type)
    fmt.fmt.pix.width = WIDTH;                      // Width of video frame in pixels (Link: https://www.kernel.org/doc/html/latest/userspace-api/media/v4l/pixfmt-v4l2.html#c.v4l2_pix_format)
    fmt.fmt.pix.height = HEIGHT;                    // Height of video frame in pixels
    fmt.fmt.pix.pixelformat = V4L2_PIX_FMT_YUYV;    // RGB or YUYV formats. Picked YUYV (Natively supported in USB webcams). RGB is bulky and so on.
    fmt.fmt.pix.field = V4L2_FIELD_NONE;            // Images are progressive (frame-based) format, not interlaced (field-based, ie top/bottom fields and so on)

    // Apply the format settings - ioctl system call
    if (ioctl(fd, VIDIOC_S_FMT, &fmt) < 0) {            // VIDIOC_S_FMT -> IOCTL command 'Set Format'
        perror("Failed to set format");
        return 1;
    }

    // Step 2: Request memory-mapped buffers
    struct v4l2_requestbuffers req = {0};           // Clear all fields
    req.count = NUM_BUFFERS;                        // Allows pipelining: while one buffer is being filled, others can be processed or queued.
    req.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;         // Buffers for video capture
    req.memory = V4L2_MEMORY_MMAP;                  // Ask for memory-mapped I/O — kernel allocates buffers, we map them into user space with mmap()

    // Apply the memory-mapped buffer request - ioctl system call
    if (ioctl(fd, VIDIOC_REQBUFS, &req) < 0) {      // VIDIOC_REQBUFS -> IOCTL command 'Request Buffers'
        perror("Failed to request buffers");
        return 1;
    }

    // Step 3: Map the buffers into user space
    struct buffer buffers[NUM_BUFFERS];
    for (int i = 0; i < NUM_BUFFERS; i++) {
        struct v4l2_buffer buf = {0};
        buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
        buf.memory = V4L2_MEMORY_MMAP;
        buf.index = i;

        // Query the buffer's location and size from the driver
        if (ioctl(fd, VIDIOC_QUERYBUF, &buf) < 0) {
            perror("Failed to query buffer");
            return 1;
        }

        buffers[i].length = buf.length;                     // Store buffer length returned by driver
        buffers[i].start = mmap(NULL, buf.length,           // Map device buffer into user space memory
                                PROT_READ | PROT_WRITE,     // Memory can be read from and written to
                                MAP_SHARED,                 // Updates in memory shared btwn user space and kernel space (the driver)
                                fd, buf.m.offset);          // Use the file descriptor and offset provided by driver

        // Check if memory mapping failed
        if (buffers[i].start == MAP_FAILED) {
            perror("mmap failed");
            return 1;
        }

        // Queue the buffer for capture
        if (ioctl(fd, VIDIOC_QBUF, &buf) < 0) {
            perror("Failed to queue buffer");
            return 1;
        }
    }

    // Step 4: Start video capturing
    enum v4l2_buf_type type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
    if (ioctl(fd, VIDIOC_STREAMON, &type) < 0) {
        perror("Failed to start streaming");
        return 1;
    }

    // Step 5: Capture frames until stop signal
    int i = 0;
    while (1) {

        FILE *flag = fopen("/tmp/stop_capture", "r");
        if (flag) {
            fclose(flag);
            printf("Stop signal received. Exiting...\n");
            break;
        }

        struct v4l2_buffer buf = {0};
        buf.type = V4L2_BUF_TYPE_VIDEO_CAPTURE;
        buf.memory = V4L2_MEMORY_MMAP;

        // Dequeue filled buffer
        if (ioctl(fd, VIDIOC_DQBUF, &buf) < 0) {
            perror("Failed to dequeue buffer");
            return 1;
        }

        printf("Captured frame %d (%u bytes)\n", i + 1, buf.bytesused);

        // Save to file
        char filename[256];
        snprintf(filename, sizeof(filename), "captured_frames/frame_%d.yuyv", i);
        FILE *file = fopen(filename, "wb");
        if (!file) {
            perror("Failed to open file for writing");
            return 1;
        }
        fwrite(buffers[buf.index].start, 1, buf.bytesused, file);
        fclose(file);

        // Requeue buffer for reuse
        if (ioctl(fd, VIDIOC_QBUF, &buf) < 0) {
            perror("Failed to requeue buffer");
            return 1;
        }

        i++;  // Increment frame count
    }

    // Step 6: Stop streaming
    if (ioctl(fd, VIDIOC_STREAMOFF, &type) < 0) {
        perror("Failed to stop streaming");
        return 1;
    }

    // Step 7: Unmap and clean up
    for (int i = 0; i < NUM_BUFFERS; i++) {
        munmap(buffers[i].start, buffers[i].length);
    }

    close(fd);
    return 0;
}
