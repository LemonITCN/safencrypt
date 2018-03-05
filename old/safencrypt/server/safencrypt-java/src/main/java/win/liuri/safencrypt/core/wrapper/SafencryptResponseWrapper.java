package win.liuri.safencrypt.core.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class SafencryptResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream capture;
    private ServletOutputStream output;
    private PrintWriter writer;

    public SafencryptResponseWrapper(HttpServletResponse response) {
        super(response);
        capture = new ByteArrayOutputStream(response.getBufferSize());
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null)
            throw new IllegalStateException("getWriter() has already been called on this response.");

        if (output == null)
            output = new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                }

                @Override
                public void write(int b) throws IOException {
                    capture.write(b);
                }

                @Override
                public void flush() throws IOException {
                    capture.flush();
                }

                @Override
                public void close() throws IOException {
                    capture.close();
                }
            };
        return output;
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        if (writer != null)
            writer.flush();
        else if (output != null)
            output.flush();
    }

    public byte[] getCaptureAsBytes() throws IOException {
        if (writer != null)
            writer.close();
        else if (output != null)
            output.close();
        return capture.toByteArray();
    }

    public String getCaptureAsString() throws IOException {
        return new String(getCaptureAsBytes(), getCharacterEncoding());
    }

}
