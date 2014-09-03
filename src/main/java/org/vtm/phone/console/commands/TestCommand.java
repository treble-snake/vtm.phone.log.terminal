package org.vtm.phone.console.commands;

public class TestCommand extends Command
{
    public static final String ALIAS = "test";

    @Override
    public Command execute()
    {
        validateArgs();
        output = "some test";
        return this;
    }
}
