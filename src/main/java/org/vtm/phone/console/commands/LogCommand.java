package org.vtm.phone.console.commands;

import org.apache.commons.lang3.StringUtils;
import org.vtm.phone.console.commands.exception.CommandException;
import org.vtm.phone.console.utils.LogsGenerator;

public class LogCommand extends Command
{
    public static final String ALIAS = "log";

    @Override
    protected void run() throws CommandException
    {
        String mode = arguments.get(0);
        switch (mode)
        {
            case "all":
                logAll();
                break;
            default:
                showLog(mode);
                break;
        }
    }

    private void logAll()
    {
        for(int i = 0; i < LogsGenerator.LOG_FILES_QTY; i++)
        {
            output += String.format(LogsGenerator.LOG_FILENAME_PATTERN, i+1) + "\n";
        }
    }

    private void showLog(String filename) throws CommandException
    {
        output = StringUtils.join(getEntries(LogsGenerator.LOGS_DIR + filename), "\n");
    }

    @Override
    public int getMinimumArgsQty()
    {
        return 1;
    }
}
