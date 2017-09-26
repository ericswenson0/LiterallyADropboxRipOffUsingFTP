import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;
public class ServerBeginState extends MessageState
{
	/*	  Eric Swenson
				  December 9th 2016
				  Jarvis

				Client file not in server error message implementation

				Instance Variables:
				Enum:
				Start
				Quit

				private static ClientFileNotInServerDirectoryState instance = new ClientFileNotInServerDirectoryState();

				Methods:

				public ClientFileNotInServerDirectoryState()

					public static synchronized ClientFileNotInServerDirectoryState getInstance()

					public NavigationState execute()

				Modification History:

				December 7th 2016
				wrote the code. Tested.

				December 10th 2016
				Implemented Server Logic State Fully


*/

	public enum StateName
	{
		Start,
		Quit;
	}

	private static ServerBeginState  instance = new ServerBeginState();

	private ServerBeginState()
	{
		super(StateName.values());
	}

	public static ServerBeginState getInstance()
	{
		return ServerBeginState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		buttonText = super.showDialog();

		switch(StateName.valueOf(buttonText))
		{
			case Start:
			{
				nextState = ServerSelectDirectoryState.getInstance();

				break;
			}
			case Quit:{nextState = null;break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ServerBeginState");

		}
		return nextState;
	}
}