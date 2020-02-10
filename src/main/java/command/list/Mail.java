package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;
import model.Validator;

import java.util.Map;

public class Mail implements Command {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String fromValue = parameters.get(MailFormUnits.FROM);
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        String mail;
        if (fromValue != null) {
            if (!Validator.validateEmail(fromValue)) {
                throw new InvalidParameterException("invalid sender email");
            }
            mail = String.format(MAIL, fromValue);

        } else {
            if (!Validator.validateEmail(argumentValue)) {
                throw new InvalidParameterException("invalid from email");
            }
            mail = String.format(MAIL, argumentValue);
        }

        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        try {
            executeCommand(mail);
        } catch (Exception e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }
    }
}

