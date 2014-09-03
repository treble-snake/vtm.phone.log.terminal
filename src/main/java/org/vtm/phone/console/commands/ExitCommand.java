package org.vtm.phone.console.commands;

public class ExitCommand extends Command
{
    public static final String ALIAS = "exit";

    @Override
    public Command execute()
    {
        output = "Good bye!";
        return this;
    }

    @Override
    public boolean needsLogin()
    {
        return false;
    }
}
