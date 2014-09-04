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
    public static final String LOG_ENTRY_FORMAT = "[%s] %s -> %s {%s}\n";
    public static final String LOG_FILENAME_PATTERN = "logpart%d.log";
    public static final String LOGS_DIR = "/data/logs/";
    public static final String STATIONS_DIR = "/data/list/";
    public static final String STATIONS_FILENAME = "stations.dat";

    private List<String> stationIds = new LinkedList<>();
    private List<String> stationEntries = new LinkedList<>();
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
        do
        {
            String stationId = getRandomStationId();
            if (!stationIds.contains(stationId))
            {
                stationIds.add(stationId);
                stationEntries.add(stationId + " \t->\t " + getRandomAddress() + "\n");
            }

        } while (stationIds.size() < STATIONS_QTY);

        // add target from
        targetStationFrom = getRandomStationId();
        stationIds.add(targetStationFrom);
        String tmp = targetStationFrom + " \t->\t " + getRandomAddress(true) + "\n";
        stationEntries.add(tmp);
        System.out.println("Target station from: " + tmp);

        // add target to
        targetStationTo = getRandomStationId();
        stationIds.add(targetStationTo);
        tmp = targetStationTo + " \t->\t " + getRandomAddress(false) + "\n";
        stationEntries.add(tmp);
        System.out.println("Target station to: " + tmp);

        Collections.shuffle(stationEntries);

        writeStationEntries();
        return this;
    }

    private void writeStationEntries()
    {
        try
        {
            FileOutputStream os = new FileOutputStream(resourcesDir + STATIONS_DIR + STATIONS_FILENAME);
            for (String entry : stationEntries)
            {
                os.write(entry.getBytes());
            }
            os.close();


        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("stations was written");
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
                FileOutputStream os = new FileOutputStream(resourcesDir + LOGS_DIR
                        + String.format(LOG_FILENAME_PATTERN, i + 1));

                for (int j = 0; j < ENTRIES_PER_LOG_FILE; j++)
                {
                    calendar.add(Calendar.SECOND, RandomUtils.nextInt(31));
                    Collections.shuffle(stationIds);

                    String stationFrom = stationIds.get(0);
                    String stationTo = RandomUtils.nextInt(100) > 70 ? stationIds.get(1) : getRandomStationId();
                    String duration = RandomUtils.nextInt(5) + "m:" + RandomUtils.nextInt(60) +"s";

                    // we use targetStationTo only once in targetFile
                    if (stationTo.equals(targetStationTo) && (
                            (i == TARGET_FILE_NUMBER && j != TARGET_ENTRY_NUMBER) ||
                                    (i >= TARGET_FILE_NUMBER - 1 && i <= TARGET_FILE_NUMBER + 2)
                    ))
                    {
                        stationTo = stationIds.get(2);
                    }

                    if (i == TARGET_FILE_NUMBER && j == TARGET_ENTRY_NUMBER)
                    {
                        stationFrom = targetStationFrom;
                        stationTo = targetStationTo;
                        duration = "0m:48s";
                    }

                    String entry = String.format(LOG_ENTRY_FORMAT,
                            DateFormatUtils.format(calendar.getTime(), DATE_FORMAT),
                            stationFrom,
                            stationTo,
                            duration
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
        return (RandomUtils.nextInt(20) + 1) +
                (RandomUtils.nextInt(2) == 0 ? " avn, " : " str, ") +
                RandomUtils.nextInt(100) +
                (RandomUtils.nextInt(2) == 0 ? (", off. " + RandomUtils.nextInt(200))
                        : "");
    }

    private String getRandomAddress(boolean withOffice)
    {
        return (RandomUtils.nextInt(20) + 1) +
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
