package org.vtm.phone.console.commands;

import org.apache.commons.lang3.StringUtils;

public class NotFoundCommand extends Command
{
    public NotFoundCommand()
    {
    }

    public NotFoundCommand(String reason)
    {
        output = reason;
    }

    @Override
    public void run()
    {
        output = "Command not found. Reason: " +
                (StringUtils.isBlank(output) ? "Unknown" : output);
    }
}
