package command;

import exception.InvalidParameterException;
import exception.SmtpSocketException;
import model.MailFormUnits;
import java.util.Map;

public interface Command {
    void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpSocketException;
}
