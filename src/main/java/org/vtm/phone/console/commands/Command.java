package org.vtm.phone.console.commands;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public abstract class Command
{
    protected List<String> arguments = new LinkedList<>();
    protected String output = "";

    public abstract Command execute();

    public int getExpectedArgsQty()
    {
        return 0;
    }

    public boolean needsLogin()
    {
        return true;
    }

    public void appendArg(String arg)
    {
        if(StringUtils.isNotBlank(arg))
            arguments.add(arg);
    }

    protected boolean validateArgs()
    {
        if(arguments.size() < getExpectedArgsQty())
        {
            output = "Error: Not enough arguments.";
            return false;
        }

        return true;
    }

    public String getOutput()
    {
        return output;
    }
}
