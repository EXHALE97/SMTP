package command.list;

import command.Command;
import exception.SmtpException;
import model.MailFormUnits;
import model.SmtpSocket;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class Auth implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Auth.class);

    private static final String MAIL_PROPERTIES_PATH = "/property/mail.properties";
    private static final String LOGIN = "mail.login";
    private static final String PASSWORD = "mail.password";

    private static final String AUTH = "AUTH PLAIN %s\r\n";

    @Override
    public void execute(Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        URL url = this.getClass().getResource(MAIL_PROPERTIES_PATH);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(url.toURI())));
        } catch (URISyntaxException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }

        String login = properties.getProperty(LOGIN);
        String password = properties.getProperty(PASSWORD);

        String s = "\000" + login + "\000" + password;
        byte[] encodeBytes = Base64.getEncoder().encode(s.getBytes());

        String auth = String.format(AUTH, new String(encodeBytes));
        SmtpSocket mailSocket = SmtpSocket.getInstance();
        try {
            executeCommand(auth);
        } catch (Exception e) {
            mailSocket.close();
            throw new SmtpException(e);
        }
    }
}
