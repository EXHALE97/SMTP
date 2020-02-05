package command;

public enum CommandType {

    //    HELLO(),
//    MAIL(),
//    RCPT(),
//    DATA(),
//    RSET(),
//    SEND(),
//    SOML(),
//    SAML(),
//    VRFY(),
//    EXPN(),
//    HELP(),
//    NOOP(),
//    QUIT();
    ;

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
