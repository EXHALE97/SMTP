package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;

import java.security.InvalidParameterException;
import java.util.Map;

public class Help implements Command {
    private static final String HELP_WITH_PARAMETER = "HELP %s\r\n";
    private static final String HELP_WITHOUT_PARAMETER = "HELP\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        String help;
        if (argumentValue.isEmpty()) {
            help = HELP_WITHOUT_PARAMETER;
        } else {
            help = String.format(HELP_WITH_PARAMETER, argumentValue);
        }

        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(help);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}
