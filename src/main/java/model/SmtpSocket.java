package model;

import exception.SmtpSocketException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SmtpSocket {

    private static final Logger LOGGER = LogManager.getLogger(SmtpSocket.class);
    private static boolean instanceCreated;
    private static final SmtpSocket INSTANCE;
    static {
        INSTANCE = new SmtpSocket();
        instanceCreated = true;
    }

    private static boolean socketCreated;
    private static final int PORT = 25;
    private Socket socket;
    private Scanner input;
    private PrintWriter output;

    private SmtpSocket() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone singleton object");
            throw new RuntimeException("try to clone singleton object");
        }
    }

    public static SmtpSocket getInstance() {
        return INSTANCE;
    }

    public void create(String address) throws SmtpSocketException {
        if (socketCreated) {
            return;
        }
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            if (!inetAddress.isReachable(5000)) {
                throw new SmtpSocketException(address + " is invalid host");
            }
            socket = new Socket(inetAddress, PORT);
            input = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            memoBuffer.appendClient("connected to " + address + "\n");
            memoBuffer.appendServer(input.nextLine());

            socketCreated = true;
        } catch (UnknownHostException e) {
            close();
            throw new SmtpSocketException("invalid host", e);
        } catch (IOException e) {
            close();
            throw new SmtpSocketException(e);
        }
    }

    public void close() {
        socketCreated = false;

        if (input != null) {
            input.close();
        }

        if (output != null) {
            output.close();
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARN, e);
            }
        }
    }

    public Scanner getInput() throws SmtpSocketException {
        if (!socketCreated) {
            throw new SmtpSocketException("socket closed");
        }
        return input;
    }

    public PrintWriter getOutput() throws SmtpSocketException {
        if (!socketCreated) {
            throw new SmtpSocketException("socket closed");
        }
        return output;
    }
}
