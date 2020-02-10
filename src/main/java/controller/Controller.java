package controller;

import command.Command;
import command.CommandFactory;
import exception.*;
import model.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
            LOGGER.log(Level.FATAL, "");
            throw new RuntimeException("");
        }
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public void processRequest(String commandName, Map<MailFormUnits, String> parameters) throws InvalidParameterException, SmtpException {
        Optional<Command> optionalCommand = CommandFactory.defineCommand(commandName);
        if (!optionalCommand.isPresent()) {
            throw new InvalidParameterException(commandName + " is invalid command");
        }
        Command command = optionalCommand.get();
        command.execute(parameters);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instanceCreated) {
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }
}
