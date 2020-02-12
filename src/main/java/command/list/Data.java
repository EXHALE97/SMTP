package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.security.InvalidParameterException;
import java.util.Map;

public class Data implements Command {
    private static final String DATA = "DATA\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(DATA);
        } catch (Exception e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }
    }
}
