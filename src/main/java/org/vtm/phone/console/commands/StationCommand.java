package org.vtm.phone.console.commands;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.vtm.phone.console.commands.exception.CommandException;
import org.vtm.phone.console.utils.LogsGenerator;

import java.util.LinkedList;
import java.util.List;

public class StationCommand extends Command
{
    public static final String ALIAS = "station";

    @Override
    protected void run() throws CommandException
    {
        String mode = arguments.get(0);
        switch (mode)
        {
            case "all":
                showAll();
                break;
            default:
                find();
                break;
        }
    }

    private void showAll() throws CommandException
    {
        output = StringUtils.join(getEntries(getFile()), "\n");
    }

    private void find() throws CommandException
    {
        final String needle = StringUtils.join(arguments, " ");
        List<String> result = new LinkedList<>(getEntries(getFile()));
        CollectionUtils.filter(result, new Predicate()
        {
            @Override
            public boolean evaluate(Object object)
            {
                return object.toString().contains(needle);
            }
        });
        output = StringUtils.join(result, "\n");
    }

    private String getFile()
    {
        return LogsGenerator.STATIONS_DIR + LogsGenerator.STATIONS_FILENAME;
    }

    @Override
    public int getMinimumArgsQty()
    {
        return 1;
    }
}
