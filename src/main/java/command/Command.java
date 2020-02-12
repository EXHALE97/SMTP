package command;

import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.MemoBuffer;
import model.SmtpSocket;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public interface Command {
    void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException;

    default void executeCommand(String command) throws Exception {
        SmtpSocket mailSocket = SmtpSocket.getInstance();
        Scanner input = mailSocket.getInput();
        PrintWriter output = mailSocket.getOutput();
        MemoBuffer memoBuffer = MemoBuffer.getInstance();
        memoBuffer.appendClient(command);
        output.write(command);
        output.flush();
        memoBuffer.appendServer(input);
    }
}
