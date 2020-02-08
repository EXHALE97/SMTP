package model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MemoBuffer {

    private static final Logger LOGGER = LogManager.getLogger(MemoBuffer.class);
    private static final MemoBuffer INSTANCE;
    private static boolean instanceCreated;
    private StringBuilder memoBuffer = new StringBuilder();

    static {
        INSTANCE = new MemoBuffer();
        instanceCreated = true;
    }

    private MemoBuffer() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "");
            throw new RuntimeException("");
        }
    }

    public static MemoBuffer getInstance() {
        return INSTANCE;
    }

    public void appendClient(String s) {
        memoBuffer.append("Client: ");
        memoBuffer.append(s);
        memoBuffer.append("\n");
    }

    public void appendServer(String s) {
        memoBuffer.append("Server: ");
        memoBuffer.append(s);
        memoBuffer.append("\n");
    }

    @Override
    public String toString() {
        return memoBuffer.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }
}