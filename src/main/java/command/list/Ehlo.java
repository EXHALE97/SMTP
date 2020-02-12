package command.list;

import command.Command;
import exception.SmtpException;
import model.*;

import java.net.InetAddress;
import java.security.InvalidParameterException;
import java.util.Map;


public class Ehlo implements Command {
    private static final String EHLO = "EHLO %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        try {
            String ehlo = String.format(EHLO, InetAddress.getLocalHost().getHostName());
            executeCommand(ehlo);
        } catch (Exception e) {
            throw new SmtpException(e);
        }
    }
}
