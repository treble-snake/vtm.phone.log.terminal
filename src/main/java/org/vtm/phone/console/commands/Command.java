package org.vtm.phone.console.commands;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.vtm.phone.console.commands.exception.CommandException;

import java.io.InputStream;
import java.util.Arrays;
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

    public int getMinimumArgsQty()
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
        if(arguments.size() < getMinimumArgsQty())
            throw new CommandException("Not enough arguments.");
    }

    public String getOutput()
    {
        return output;
    }

    protected List<String> getEntries(String file) throws CommandException
    {
        InputStream stream = this.getClass().getResourceAsStream(file);
        if(stream == null)
            throw new CommandException("File not found.");

        try
        {
            return Arrays.asList(IOUtils.toString(stream).split("\\n"));
        }
        catch (java.io.IOException e)
        {
            throw new CommandException("Could not read file.");
        }
    }

    protected abstract void run() throws CommandException;
}
