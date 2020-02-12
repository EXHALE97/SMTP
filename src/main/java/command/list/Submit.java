package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.security.InvalidParameterException;
import java.util.Map;

public class Submit implements Command {
    private static final String SUBMIT = "%s\r\n";
    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);
        String submit = String.format(SUBMIT, argumentValue);
        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(submit);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}
