package command;

import command.list.*;

public enum CommandType {
    AUTH(new Auth()),
    EHLO(new Ehlo()),
    HELO(new Helo()),
    MAIL(new Mail()),
    RCPT(new Rcpt()),
    DATA(new Data()),
    RSET(new Rset()),
    SEND(new Send()),
    SOML(new Soml()),
    SAML(new Saml()),
    VRFY(new Vrfy()),
    EXPN(new Expn()),
    HELP(new Help()),
    NOOP(new Noop()),
    QUIT(new Quit()),
    TURN(new Turn()),
    CONNECT(new Connect()),
    SUBMIT(new Submit()),
    SEND_MESSAGE(new SendMessage());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
