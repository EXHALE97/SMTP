package model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

    private class AppendServerThread extends Thread {
        private Scanner input;

        AppendServerThread(Scanner input) {
            this.input = input;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String s = input.nextLine();
                    memoBuffer.append("Server: ");
                    memoBuffer.append(s);
                    memoBuffer.append("\n");
                }
            } catch (Exception e) {
                //
            }
        }
    }

    public void appendClient(String s) {
        memoBuffer.append("Client: ");
        memoBuffer.append(s);
        memoBuffer.append("\n");
    }

    public void appendServer(Scanner input) {
        AppendServerThread thread = new AppendServerThread(input);
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, e);
        } finally {
            thread.interrupt();
        }
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