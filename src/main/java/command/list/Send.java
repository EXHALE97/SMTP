package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;
import model.Validator;

import java.util.Map;

public class Send implements Command {
    private static final String SEND = "SEND FROM:<%s>\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
            throw new InvalidParameterException("invalid sender email");
        }

        String send = String.format(SEND, argumentValue);

        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(send);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

