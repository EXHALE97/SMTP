package command;

import command.list.*;

public enum CommandType {
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
    SEND_MESSAGE(new SendMessage());

//    EHLO("EHLO %s\r"),
//    HELO("HELO %s\r"),
//    MAIL("MAIL FROM:<%s>\r"),
//    RCPT("RCPT TO:<%s>\r"),
//    DATA("DATA\r"),
//    RSET("RSET"),
//    SEND("SEND FROM:<%s>"),
//    SOML("SOML FROM:<%s>"),
//    SAML("SAML FROM:<%s>"),
//    VRFY("VRFY %s"),
//    EXPN("EXPN %s"),
//    HELP("HELP %s"),
//    NOOP("NOOP"),
//    QUIT("QUIT\r"),
//    TURN("TURN");

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
