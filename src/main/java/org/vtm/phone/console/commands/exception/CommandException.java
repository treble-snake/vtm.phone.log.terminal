package org.vtm.phone.console.commands.exception;

public class CommandException extends Exception
{
    public CommandException()
    {
    }

    public CommandException(String message)
    {
        super(message);
    }

    public CommandException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
