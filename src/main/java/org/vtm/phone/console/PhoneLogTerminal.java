package org.vtm.phone.console;

import org.vtm.phone.console.commands.Command;
import org.vtm.phone.console.commands.ExitCommand;
import org.vtm.phone.console.commands.LoginCommand;

import java.util.Scanner;

public class PhoneLogTerminal
{
    private boolean isLogged = false;

    public static void main(String[] args)
    {
        new PhoneLogTerminal().run();
    }

    public void run()
    {
        CommandFactory commandFactory = new CommandFactory();
        try
        {
            Command command;
            Scanner console = new Scanner(System.in);
            Scanner lineInput;
            do
            {

                if(!isLogged)
                    System.out.println("Welcome! Please log in or use 'help' command for more information.");

                System.out.print("> ");
                lineInput = new Scanner(console.nextLine());

                String alias = lineInput.next();
                command = commandFactory.get(alias);

                if(!isLogged && command.needsLogin())
                    continue;

                while (lineInput.hasNext()) {
                    command.appendArg(lineInput.next());
                }

                if(command instanceof LoginCommand)
                    isLogged = ((LoginCommand)command).isCorrectLoginData();

                System.out.println(command.execute().getOutput());
                System.out.println();

            } while (!(command instanceof ExitCommand));
        }
        catch (Exception e)
        {
            System.err.println("Sorry, error occurred! Please restart the terminal.");
            e.printStackTrace();
        }
    }
}
