package org.vtm.phone.console;

import org.vtm.phone.console.commands.Command;
import org.vtm.phone.console.commands.ExitCommand;
import org.vtm.phone.console.commands.LoginCommand;

import java.util.Scanner;

public class PhoneLogTerminal
{
    private static final String WELCOME_MSG = "Welcome! Please log in or use 'help' command for more information.";
    protected static final String INPUT_MARKER = "> ";

    private boolean isLogged = false;

    public static void main(String[] args)
    {
        new PhoneLogTerminal().run();
    }

    public void run()
    {
        try
        {
            Command command;
            Scanner lineInput;
            Scanner console = new Scanner(System.in);
            CommandFactory commandFactory = new CommandFactory();

            do
            {

                if(!isLogged)
                    System.out.println(WELCOME_MSG);

                System.out.print(INPUT_MARKER);

                lineInput = new Scanner(console.nextLine());
                command = commandFactory.get(lineInput.next());

                if(!isLogged && command.needsAuthentication())
                    continue;

                while (lineInput.hasNext()) {
                    command.appendArg(lineInput.next());
                }

                System.out.println(command.execute().getOutput());
                System.out.println();

                if(command instanceof LoginCommand)
                    isLogged = ((LoginCommand)command).isCorrectLoginData();

            } while (!(command instanceof ExitCommand));
        }
        catch (Exception e)
        {
            System.err.println("Sorry, error occurred! Please restart the terminal.");
            e.printStackTrace();
        }
    }
}
