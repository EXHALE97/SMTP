package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.*;

import java.net.InetAddress;
import java.util.Map;

public class Helo implements Command {
    private static final String HELO = "HELO %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        try {
            smtpSocket.create(argumentValue);
        } catch (SmtpException e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }

        try {
            String helo = String.format(HELO, InetAddress.getLocalHost().getHostName());
            executeCommand(helo);
        } catch (Exception e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }
    }
}

