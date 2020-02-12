package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;
import model.Validator;

import java.security.InvalidParameterException;
import java.util.Map;

public class Mail implements Command {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        if (!Validator.validateEmail(argumentValue)) {
            throw new InvalidParameterException("invalid sender email");
        }

        String mail = String.format(MAIL, argumentValue);
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(mail);
        } catch (Exception e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }
    }
}

