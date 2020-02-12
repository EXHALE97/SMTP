package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;
import model.Validator;

import java.util.Map;

public class Rcpt implements Command {
    private static final String RCPT = "RCPT TO:<%s>\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String toValue = parameters.get(MailFormUnits.SENDER);
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        String rcpt;
        if (toValue != null) {
            if (!Validator.validateGetter(toValue)) {
                throw new InvalidParameterException("invalid getter email");
            }
            rcpt = String.format(RCPT, toValue);

        } else {
            if (!Validator.validateGetter(argumentValue)) {
                throw new InvalidParameterException("invalid getter email");
            }
            rcpt = String.format(RCPT, argumentValue);
        }

        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(rcpt);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}

