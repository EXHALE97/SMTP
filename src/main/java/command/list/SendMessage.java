package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpSocketException;
import model.MailFormUnits;

import java.util.Map;

public class SendMessage implements Command {
    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpSocketException {
    }
}

