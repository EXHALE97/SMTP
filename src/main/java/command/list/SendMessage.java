package command.list;

import command.Command;
import exception.InvalidParameterException;
import exception.SmtpException;
import model.*;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SendMessage implements Command {

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        String toValue = parameters.get(MailFormUnits.TO);
        String subjectValue = parameters.get(MailFormUnits.SUBJECT);
        String mailTextValue = parameters.get(MailFormUnits.MAIL_TEXT);

        if (!Validator.validateMailText(mailTextValue)) {
            throw new InvalidParameterException("'.' can't be a single character in sentence");
        }

        if (!Validator.validateGetter(toValue)) {
            throw new InvalidParameterException("invalid getter email\\emails");
        }

        try {
            (new Ehlo()).execute(parameters);
            (new Mail()).execute(parameters);

            for (String rcpt : toValue.split(",")) {
                Map<MailFormUnits, String> parameter = new HashMap<MailFormUnits, String>();
                parameter.put(MailFormUnits.TO, rcpt);
                (new Rcpt()).execute(parameter);
            }

            (new Data()).execute(null);

            MemoBuffer memoBuffer = MemoBuffer.getInstance();
            SmtpSocket smtpSocket = SmtpSocket.getInstance();
            PrintWriter output = smtpSocket.getOutput();
            Scanner input = smtpSocket.getInput();
            memoBuffer.appendClient("Subject: " + subjectValue + "\n");
            output.write("Subject: " + subjectValue + "\r\n");
            output.flush();

            long messageId = MessageID.getMessageID();
            memoBuffer.appendClient("Message ID: " + messageId + "\n");
            output.write("Message ID: " + messageId + "\r\n");
            output.flush();

            memoBuffer.appendClient("\n");
            output.write("\r\n");
            output.flush();

            for (String sentence : mailTextValue.split("\n")) {
                memoBuffer.appendClient(sentence + "\n");
                output.write(sentence + "\r\n");
                output.flush();
            }

            memoBuffer.appendClient(".\n");
            output.write("\r\n.\r\n");
            output.flush();
            memoBuffer.appendServer(input.nextLine());

            (new Quit()).execute(null);
        } catch (Exception e) {
            throw new SmtpException(e);
        } finally {
            SmtpSocket.getInstance().close();
        }
    }
}

