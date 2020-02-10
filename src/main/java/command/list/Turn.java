package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.util.Map;

public class Turn implements Command {
    private static final String TURN = "TURN\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(TURN);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

