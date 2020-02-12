package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.security.InvalidParameterException;
import java.util.Map;

public class Rset implements Command {
    private static final String RSET = "RSET\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(RSET);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

