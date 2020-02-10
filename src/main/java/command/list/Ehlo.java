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

public class Ehlo implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpSocketException {
        try {
            String smptServerValue = parameters.get(MailFormUnits.SMTP_SERVER);
            String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

            SmtpSocket smtpSocket = SmtpSocket.getInstance();
            if (smptServerValue != null) {
                smtpSocket.create(smptServerValue);
            } else {
                smtpSocket.create(argumentValue);
            }

            String ehlo;
            try {
                ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                throw new SmtpSocketException(e);
            }

            Scanner input = smtpSocket.getInput();
            PrintWriter output = smtpSocket.getOutput();
            MemoBuffer memoBuffer = MemoBuffer.getInstance();

            memoBuffer.appendClient(ehlo);
            output.write(ehlo);
            output.flush();
            memoBuffer.appendServer(input.nextLine());
        } catch (Exception e) {
            SmtpSocket.getInstance().close();
            throw new SmtpSocketException(e);
        }
    }
}
