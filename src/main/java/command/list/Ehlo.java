package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.*;

import java.net.InetAddress;
import java.util.Map;


public class Ehlo implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        SmtpSocket smtpSocket = SmtpSocket.getInstance();
        String smptServerValue = parameters.get(MailFormUnits.SMTP_SERVER);
        String argumentValue = parameters.get(MailFormUnits.ARGUMENT);

        try {
            if (smptServerValue != null) {
                smtpSocket.create(smptServerValue);
            } else {
                smtpSocket.create(argumentValue);
            }
        } catch (SmtpException e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }

        try {
            String ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            executeCommand(ehlo);
        } catch (Exception e) {
            smtpSocket.close();
            throw new SmtpException(e);
        }
    }
}
