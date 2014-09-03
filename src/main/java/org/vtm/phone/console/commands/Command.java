package org.vtm.phone.console.commands;

import org.apache.commons.lang3.StringUtils;
import org.vtm.phone.console.commands.exception.CommandException;

import java.util.LinkedList;
import java.util.List;

public abstract class Command
{
    protected List<String> arguments = new LinkedList<>();
    protected String output = "";

    public Command execute()
    {
        try
        {
            validate();
            run();
        }
        catch (CommandException e)
        {
            output = "Error: " + e.getMessage();
        }

        return this;
    }

    protected abstract void run();

    public int getExpectedArgsQty()
    {
        return 0;
    }

    public boolean needsAuthentication()
    {
        return true;
    }

    public void appendArg(String arg)
    {
        if(StringUtils.isNotBlank(arg))
            arguments.add(arg);
    }

    protected void validate() throws CommandException
    {
        if(arguments.size() < getExpectedArgsQty())
            throw new CommandException("Not enough arguments.");
    }

    public String getOutput()
    {
        return output;
    }
}
