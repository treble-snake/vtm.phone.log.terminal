package org.vtm.phone.console.commands;

public class TestCommand extends Command
{
    public static final String ALIAS = "test";

    @Override
    public void run()
    {
        output = "some test";
    }
}
