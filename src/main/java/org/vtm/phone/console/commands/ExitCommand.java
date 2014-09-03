package org.vtm.phone.console.commands;

public class ExitCommand extends Command
{
    public static final String ALIAS = "exit";

    @Override
    public void run()
    {
        output = "Good bye!";
    }

    @Override
    public boolean needsAuthentication()
    {
        return false;
    }
}
