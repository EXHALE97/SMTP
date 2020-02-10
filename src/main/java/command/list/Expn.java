package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.util.Map;

public class Expn implements Command {
    private static final String EXPN = "EXPN %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        if (argumentValue.isEmpty()) {
            throw new InvalidParameterException("invalid address");
        }

        String expn = String.format(EXPN, argumentValue);

        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(expn);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

