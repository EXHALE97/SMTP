package controller;

import command.Command;
import command.CommandDefiner;
import exception.*;
import model.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Optional;

public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private static boolean instanceCreated;
    private static final Controller INSTANCE;

    static {
        INSTANCE = new Controller();
        instanceCreated = true;
    }

    private Controller() {
        if (instanceCreated) {
            LOGGER.log(Level.FATAL, "try to clone object");
            throw new RuntimeException("try to clone object");
        }
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void processRequest(String commandName, Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        Optional<Command> optionalCommand = CommandDefiner.defineCommand(commandName);
        if (!optionalCommand.isPresent()) {
            throw new InvalidParameterException(commandName + " is invalid command");
        }
        Command command = optionalCommand.get();
        command.execute(parameters);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException("try to clone object");
        }
        return super.clone();
    }
}
