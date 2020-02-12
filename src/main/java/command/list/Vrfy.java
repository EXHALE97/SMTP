package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.security.InvalidParameterException;
import java.util.Map;

public class Vrfy implements Command {
    private static final String VRFY = "VRFY %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        if (argumentValue.isEmpty()) {
            throw new InvalidParameterException("invalid address");
        }

        String vrfy = String.format(VRFY, argumentValue);

        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(vrfy);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

