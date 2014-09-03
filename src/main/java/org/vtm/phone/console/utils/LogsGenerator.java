package org.vtm.phone.console.utils;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LogsGenerator
{
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int STATIONS_QTY = 155;
    public static final int LOG_FILES_QTY = 41;
    public static final int ENTRIES_PER_LOG_FILE = 1000;
    public static final int TARGET_FILE_NUMBER = 26;
    public static final int TARGET_ENTRY_NUMBER = 732;
    public static final String ENTRY_FORMAT = "[%s] %s -> %s {%s}\n";
    public static final String LOG_FILENAME_PATTERN = "logpart%d.log\n";
    public static final String LOGS_DIR = "data/logs/";
    public static final String STATIONS_DIR = "data/list/";
    public static final String STATIONS_FILENAME = "stations.dat";

    private List<String> stationIds = new LinkedList<>();
    private String resourcesDir;
    private String targetStationFrom;
    private String targetStationTo;

    public static void main(String[] args)
    {
        if (args.length < 1)
            throw new RuntimeException("Not enough args");

        new LogsGenerator(args[0])
                .generateStationsList()
                .generateLogEntries();
    }

    public LogsGenerator(String resourcesDir)
    {
        this.resourcesDir = resourcesDir;
    }

    public LogsGenerator generateStationsList()
    {
        try
        {
            String stationId;
            FileOutputStream os = new FileOutputStream(resourcesDir + STATIONS_DIR + STATIONS_FILENAME);
            do
            {
                stationId = getRandomStationId();
                if (!stationIds.contains(stationId))
                {
                    stationIds.add(stationId);
                    os.write((stationId + " \t->\t " + getRandomAddress() + "\n").getBytes());
                }

            } while (stationIds.size() < STATIONS_QTY);

            // add target from
            stationId = getRandomStationId();
            targetStationFrom = stationId;
            os.write((stationId + " \t->\t " + getRandomAddress(true) + "\n").getBytes());

            // add target to
            stationId = getRandomStationId();
            targetStationTo = stationId;
            os.write((stationId + " \t->\t " + getRandomAddress(false) + "\n").getBytes());

            os.close();

            System.out.println("Target station from: " + targetStationFrom);
            System.out.println("Target station to: " + targetStationFrom);
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }

        return this;
    }

    public LogsGenerator generateLogEntries()
    {
        // starting date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, Calendar.AUGUST, 5);

        try
        {
            for (int i = 0; i < LOG_FILES_QTY; i++)
            {
                FileOutputStream os = new FileOutputStream(resourcesDir + LOGS_DIR + String.format(LOG_FILENAME_PATTERN, i+1));

                for (int j = 0; j < ENTRIES_PER_LOG_FILE; j++)
                {
                    calendar.add(Calendar.SECOND, RandomUtils.nextInt(30));
                    Collections.shuffle(stationIds);

                    String stationFrom = stationIds.get(0);
                    String stationTo = RandomUtils.nextInt(100) > 70 ? stationIds.get(1) : getRandomStationId();

                    // we use targetStationFrom only once in targetFile
                    if (stationTo.equals(targetStationTo) && (
                            (i == TARGET_FILE_NUMBER && j != TARGET_ENTRY_NUMBER) ||
                                    (i >= TARGET_FILE_NUMBER - 1 && i <= TARGET_FILE_NUMBER + 2)
                    ))
                    {
                        stationTo = stationIds.get(2);
                    }

                    if (i == TARGET_FILE_NUMBER && j == TARGET_ENTRY_NUMBER)
                    {
                        stationFrom = targetStationTo;
                        stationTo = targetStationTo;
                    }

                    String entry = String.format(ENTRY_FORMAT,
                            DateFormatUtils.format(calendar.getTime(), DATE_FORMAT),
                            stationFrom,
                            stationTo,
                            RandomUtils.nextInt(200) + ":" + RandomUtils.nextInt(60)
                    );
                    if (i == TARGET_FILE_NUMBER && j == TARGET_ENTRY_NUMBER)
                        System.out.println("Target entry: " + entry);

                    os.write(entry.getBytes());
                }

                os.close();
            }
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }

        return this;
    }

    private String getRandomAddress()
    {
        return (RandomUtils.nextInt(20)+1) +
                (RandomUtils.nextInt(2) == 0 ? " avn, " : " str, ") +
                RandomUtils.nextInt(100) +
                (RandomUtils.nextInt(2) == 0 ? (", off. " + RandomUtils.nextInt(200))
                        : "");
    }

    private String getRandomAddress(boolean withOffice)
    {
        return (RandomUtils.nextInt(20)+1) +
                (RandomUtils.nextInt(2) == 0 ? " avn, " : " str, ") +
                RandomUtils.nextInt(100) +
                (withOffice ? (", off. " + RandomUtils.nextInt(200))
                        : "");
    }

    private String getRandomStationId()
    {
        return RandomStringUtils.random(3, true, false)
                + "."
                + RandomStringUtils.random(4, false, true)
                + "."
                + RandomStringUtils.random(3, true, false);
    }
}
