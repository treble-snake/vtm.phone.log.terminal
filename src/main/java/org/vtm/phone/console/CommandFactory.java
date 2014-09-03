package org.vtm.phone.console;

import org.vtm.phone.console.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory
{
    private static final Map<String, Class<? extends Command>> commands = new HashMap<>();
    static
    {
        commands.put(LoginCommand.ALIAS, LoginCommand.class);
        commands.put(ExitCommand.ALIAS, ExitCommand.class);
        commands.put(HelpCommand.ALIAS, HelpCommand.class);
        commands.put(TestCommand.ALIAS, TestCommand.class);
    }

    public Command get(String alias)
    {
        alias = alias.toLowerCase();

        if (!commands.containsKey(alias))
            return new NotFoundCommand("No alias found.");

        try
        {
            return commands.get(alias).newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            return new NotFoundCommand(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}