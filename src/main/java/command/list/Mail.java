package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpSocketException;
import model.MailFormUnits;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class Mail implements Command {
    private static final String MAIL = "MAIL FROM:<%s>\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpSocketException {

    }
}

