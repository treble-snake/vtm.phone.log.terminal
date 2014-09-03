package org.vtm.phone.console.commands;

public class LoginCommand extends Command
{
    public static final String ALIAS = "login";

    private static final String LOGIN = "jack";
    private static final String PASSWORD = "ass";

    private boolean isLoginCorrect = false;

    @Override
    public void run()
    {
        String login = arguments.get(0);
        String password = arguments.get(1);

        isLoginCorrect = LOGIN.equals(login) && PASSWORD.equals(password);

        output = isLoginCorrect ?
                "Welcome, " + LOGIN + "!" :
                "Wrong login data.";
    }

    @Override
    public boolean needsAuthentication()
    {
        return false;
    }

    public boolean isCorrectLoginData()
    {
        return isLoginCorrect;
    }

    @Override
    public int getExpectedArgsQty()
    {
        return 2;
    }
}
