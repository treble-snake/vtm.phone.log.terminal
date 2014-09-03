package org.vtm.phone.console.commands;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.vtm.phone.console.commands.exception.CommandException;

import java.io.FileInputStream;
import java.net.URL;
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

    protected abstract void run() throws CommandException;

    public int getExpectedArgsQty()
    {
        return 0;
    }

    // TODO temp false
    public boolean needsAuthentication()
    {
        return false;
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

    protected List<String> getEntries(String file) throws CommandException
    {
        URL resource = this.getClass().getClassLoader().getResource(file);
        if(resource == null)
            throw new CommandException("File not found.");

        try
        {
            String stationsListText = IOUtils.toString(new FileInputStream(resource.getPath()));
            return Arrays.asList(stationsListText.split("\\n"));
        }
        catch (java.io.IOException e)
        {
            throw new CommandException("Could not read file.");
        }
    }
}
