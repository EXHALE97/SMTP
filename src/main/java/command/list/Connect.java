package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.security.InvalidParameterException;
import java.util.Map;

public class Connect implements Command {
    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        try {
            SmtpSocket.getInstance().create();
        } catch (SmtpException e) {
            throw new SmtpException(e);
        }
    }
}