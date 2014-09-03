package org.vtm.phone.console.commands;

public class HelpCommand extends Command
{
    public static final String ALIAS = "help";

    @Override
    public void run()
    {
        output = "Available commands:\n" +
                "login [username] [password] -- for logging in\n" +
                "log [all | filename] -- will show all log files or specific log file contents\n" +
                "find [date] -- will find log entries for current date\n" +
                "list -- will show list of all phones\n" +
                "help -- this command\n" +
                "exit -- will close the terminal\n";
    }

    @Override
    public boolean needsAuthentication()
    {
        return false;
    }
}
