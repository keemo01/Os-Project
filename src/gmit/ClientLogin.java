package gmit;

public class ClientLogin
{
    private MessageHandler message;

    public ClientLogin(MessageHandler message)
    {
        this.message = message;
    }

    // All the server logic
    public void Run()
    {
        //1. Print Welcome Message
        System.out.println(message.readString());

        //2. Print Menu List 1-2
        System.out.println(message.readString());

        //3. Handle Menu on client
        int menuOption = message.numberRequestHandler();
        handleMenu(menuOption);
        
        //4. User send
        Boolean error = message.readBoolean();
        System.out.println(error);
        if (error)
        {
            return;
        }

        handleMenu(menuOption);
    }

    private void handleMenu(int menuOption)
    {
        switch (menuOption)
        {
        case 1:
            registration();
            break;

        case 2:
            login();
            break;

        case 3:
            break;

        case 4:
            break;

        case 5:
            break;

        case 6:
            break;
        }
    }

    private void login()
    {
        Boolean validLogin = false;

        do
        {
            System.out.println(message.readString());
            message.requestStringHandler();// Respond to email request
            validLogin = message.readBoolean();
        } while (!validLogin);
    }

    private void registration()
    {
        message.requestStringHandler(); //This is the Name
        message.numberRequestHandler(); //This is the ID
        message.requestStringHandler(); //This is the Email
        message.requestStringHandler(); //This is the Department
    }
}