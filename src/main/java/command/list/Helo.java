package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpSocketException;
import model.*;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

public class Helo implements Command {
    private static final String HELO = "HELO %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpSocketException {
        try {
            String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

            SmtpSocket smtpSocket = SmtpSocket.getInstance();
            smtpSocket.create(argumentValue);

            String helo;
            try {
                helo = String.format(HELO, InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                throw new SmtpSocketException(e);
            }

            Scanner input = smtpSocket.getInput();
            PrintWriter output = smtpSocket.getOutput();
            MemoBuffer memoBuffer = MemoBuffer.getInstance();

            memoBuffer.appendClient(helo);
            output.write(helo);
            output.flush();
            memoBuffer.appendServer(input.nextLine());
        } catch (Exception e) {
            SmtpSocket.getInstance().close();
            throw new SmtpSocketException(e);
        }
    }
}

