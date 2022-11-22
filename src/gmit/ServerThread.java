package gmit;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class ServerThread 
{
	
	private MessageHandler message;

    private final String limitedOptions = "1. Sign Up\n2. Login\n";
    private final String fullOptions = limitedOptions + "3. Add Bug\n4. Assign Bug\n5. View all Bugs\n6. Update bug\n";
    private final String welcomeMessage = "\n\nWelcome to the Bug Tracker";
    private final String welcomeMessageEnd = "\nplease enter the number between 1 & 2 for the\nmenu you wish to access\n";
    
	private Collection<User> users = new ArrayList<>();
    
	private Boolean logIn = false;
    private String name = "";
    
    public ServerThread(MessageHandler message)
    {
        this.message = message;
    }
    
    
 // All the server logic
    public void run()
    {
    	//1. This will display the welcome message
        message.sendString(welcomeMessage + name + welcomeMessageEnd);
        
        //2. This will give access to the full or limited menu depending whether your logged in or not
        if (logIn)
        {
        	message.sendString(fullOptions + "\nInput number 1-6");
        }
        else
        {
        	message.sendString(limitedOptions + "\nInput number 1-2");
        }
        
        //3. Choose a menu option
        int menuChoice = message.askNumber("> ");
        
        //4. Using a boolean to see if error is true or false
        if (menuChoice < 1 || menuChoice > (logIn ? 6 : 2))
        {
            System.out.println("Error");
            message.sendBoolean(true);// Error send client back to start
            return;
        }
        message.sendBoolean(false);// No Error

        menu(menuChoice);
    }
    
    private void menu(int menuChoice)
    {
    	switch (menuChoice)
    	{
    	case 1:
    		signUp();
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
    	Boolean auth = false;
    	
    	do {
    		message.sendString("Enter your email to login");
    		var email = message.requestString("Email: ");
    		
    		for (User user : users)
    		{
    			if (email.equalsIgnoreCase(user.getEmail()))
    			{
    				name = " " + user.getName();
    				auth = true;
                    logIn = true;
    			}
    		}
    		
    		message.sendBoolean(auth);
    	} while (!auth);
    }
    
    private void signUp()
    {
        Boolean added = false;
        // Request User info for registration
        do
        {
            String name = message.requestString("Name: ");
            int id = message.askNumber("ID: ");
            String email = message.requestString("Email: ");
            String department = message.requestString("Department: ");

            added = createUser(name, id, email, department);
        } while (!added);
    }

    private Boolean createUser(String name, int id, String email, String department)
    {
        User newUser = new User(name, id, email, department);

        for (User user : users)
        {
            if (newUser.getEmail().equalsIgnoreCase(user.getEmail()))
            {
                return false;
            }
            if (newUser.getId() == user.getId())
            {
                return false;
            }
        }

        System.out.println("Added new user\n    " + name + "," + id + "," + email + "," + department);
        users.add(newUser);
        return true;
    }
	
	private Socket incomingConnection;
	// input/output streams for sending/receiving data
	ObjectOutputStream out;
	ObjectInputStream in;
	
	
	// Method to send data to client
		private void sendMessage(String sms)
		{
			try {
				// writing out message
				out.writeObject(sms);
				// flushing the line, to write out and clear it
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		public void start() {
			// TODO Auto-generated method stub
			
		}

}
