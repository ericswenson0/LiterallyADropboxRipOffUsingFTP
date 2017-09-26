import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;

public class ServerStartServingState extends MessageState
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

						Gives the user a chance to save a file

							Instance Variables:
							Enum:
							Save
							Cancel

							private static ServerStartServingState instance = new ClientFileNotInServerDirectoryState();

							Methods:

							public ServerSaveFileState()

								public static synchronized ServerSaveFileState getInstance()

								public NavigationState execute()

							Modification History:

							December 7th 2016
							wrote the code. Tested.

							December 10th 2016
							Implemented Server Logic State Fully

*/
	public enum StateName
	{
		StartServer,
		Quit;
	}

	private static ServerStartServingState instance = new ServerStartServingState();

	private ServerStartServingState()
	{
			super(StateName.values());
	}

	public static synchronized ServerStartServingState getInstance()
	{
		return ServerStartServingState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;


		buttonText = super.showDialog();


		switch(StateName.valueOf(buttonText))
		{
			case StartServer:
			{
				ServerSelectDirectoryState.getInstance().getUnicastServer().command(Server.ServerCommand.StartServer);

				//ServerSelectDirectoryState.getInstance().getHelloMessage().command(Hello.Command.Start);

				nextState = ServerStopServingState.getInstance();
				try
				{
				ServerSelectDirectoryState.getInstance().getHelloMessage().sendMe();
				}
				catch(IOException ioe)
				{
					ioe.getStackTrace();
				}
				break;
			}
			case Quit:
			{
				ServerSelectDirectoryState.getInstance().getHelloMessage().command(Hello.Command.Quit);


				nextState = null;
				break;
				}
			default: throw new RuntimeException("Unrecognized enum value in execute of erverStartServingState");

		}

			return nextState;
	}


}