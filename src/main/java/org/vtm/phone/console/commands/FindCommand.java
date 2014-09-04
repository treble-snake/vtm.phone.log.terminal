package org.vtm.phone.console.commands;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.vtm.phone.console.commands.exception.CommandException;
import org.vtm.phone.console.utils.LogsGenerator;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FindCommand extends Command
{
    public static final String ALIAS = "find";
    protected static final char DATE_TIME_SEPARATOR = '/';

    @Override
    protected void run() throws CommandException
    {
        try
        {
            final List<String> result = new LinkedList<>();
            final String id = arguments.get(0);
            final Date from = arguments.size() > 1 ?
                    DateUtils.parseDate(arguments.get(1).replace(DATE_TIME_SEPARATOR, ' '), LogsGenerator.DATE_FORMAT) :
                    null;
            final Date to = arguments.size() > 2 ?
                    DateUtils.parseDate(arguments.get(2).replace(DATE_TIME_SEPARATOR, ' '), LogsGenerator.DATE_FORMAT) :
                    null;

            for (int i = 0; i < 41; i++)
            {
                List<String> logEntries = new LinkedList<>(getLog(i));
                CollectionUtils.filter(logEntries, new Predicate()
                {
                    @Override
                    public boolean evaluate(Object object)
                    {
                        String entry = object.toString();
                        if(!entry.contains(id))
                            return false;

                        if(from != null || to != null)
                        {
                            try
                            {
                                String entryDateStr = entry.substring(1, LogsGenerator.DATE_FORMAT.length() + 1);
                                Date entryDate = DateUtils.parseDate(entryDateStr, LogsGenerator.DATE_FORMAT);
                                if( from != null && entryDate.before(from))
                                    return false;

                                if(to != null && entryDate.after(to))
                                    return false;
                            }
                            catch (ParseException e)
                            {
                                throw new RuntimeException("Log date corrupted.");
                            }
                        }


                        return true;
                    }
                });

                result.addAll(logEntries);
            }

            output = StringUtils.join(result, "\n");
        }
        catch (ParseException e)
        {
            throw new CommandException("Wrong date format.");
        }
        catch (RuntimeException e)
        {
            e.printStackTrace();
            throw new CommandException(e.getMessage(), e);
        }
    }

    private List<String> getLog(int i) throws CommandException
    {
        return getEntries(LogsGenerator.LOGS_DIR + String.format(LogsGenerator.LOG_FILENAME_PATTERN, i+1));
    }
}
