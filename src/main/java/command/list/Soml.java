package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;
import model.Validator;

import java.util.Map;

public class Soml implements Command {
    private static final String SOML = "SOML FROM:<%s>\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
            throw new InvalidParameterException("invalid sender email");
        }

        String soml = String.format(SOML, argumentValue);

        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(soml);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

