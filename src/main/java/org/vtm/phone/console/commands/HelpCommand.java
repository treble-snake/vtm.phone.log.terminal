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
                "find [stationID] [dateFrom](optional) [dateTo](optional) -- will find log entries for station with specific id, for specific period\n" +
                "station [all | searchText] -- will show list of all phones stations or will find station by ID or Address\n" +
                "help -- this command\n" +
                "exit -- will close the terminal\n";
    }

    @Override
    public boolean needsAuthentication()
    {
        return false;
    }
}
