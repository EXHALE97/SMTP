package command.list;

import command.Command;
import exception.*;
import model.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class SendMessage implements Command {
    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String toValue = parameters.get(MailFormUnits.SENDER);
        String subjectValue = parameters.get(MailFormUnits.SUBJECT);
        String mailTextValue = parameters.get(MailFormUnits.MAIL_TEXT);


        if (!Validator.validateGetter(toValue)) {
            throw new InvalidParameterException("invalid getter email\\emails");
        }

        try {
            Map<MailFormUnits, String> parameter = new HashMap<MailFormUnits, String>();
            parameter.put(MailFormUnits.ARGUMENT, "");
            (new Connect()).execute(null);
            (new Ehlo()).execute(null);
            (new Auth()).execute(null);

            parameter.replace(MailFormUnits.ARGUMENT, "yulyaevrafova92@gmail.com");
            (new Mail()).execute(parameter);

            for (String rcpt : toValue.split(",")) {
                parameter.replace(MailFormUnits.ARGUMENT, rcpt);
                (new Rcpt()).execute(parameter);
            }

            (new Data()).execute(null);
            parameter.replace(MailFormUnits.ARGUMENT, "Subject: " + subjectValue);
            (new Submit()).execute(parameter);

            long messageId = MessageID.getMessageID();
            parameter.replace(MailFormUnits.ARGUMENT, "Message-ID: " + messageId);
            (new Submit()).execute(parameter);

            parameter.replace(MailFormUnits.ARGUMENT, "");
            (new Submit()).execute(parameter);

            for (String sentence : mailTextValue.split("\n")) {
                parameter.replace(MailFormUnits.ARGUMENT, sentence);
                (new Submit()).execute(parameter);
            }

            parameter.replace(MailFormUnits.ARGUMENT, "\r\n.");
            (new Submit()).execute(parameter);

            (new Quit()).execute(null);
        } catch (Exception e) {
            throw new SmtpException(e);
        } finally {
            SmtpSocket.getInstance().close();
        }
    }
}

