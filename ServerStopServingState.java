import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;


public class ServerStopServingState extends MessageState
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

						Gives the user a chance to have the file stop serving

							Instance Variables:
							Enum:
							StopServer,
							AddFile,
							RemoveFile;

							private static ServerSaveFileState instance = new ClientFileNotInServerDirectoryState();

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
		StopServer,
		AddFile,
		RemoveFile;
	}

	private static ServerStopServingState instance = new ServerStopServingState();

	private ServerStopServingState()
	{
			super(StateName.values());
	}

	public static synchronized ServerStopServingState getInstance()
	{
		return ServerStopServingState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;
		MessageServer messageServer;
		Goodbye gb;

		buttonText = super.showDialog();
		gb = null;
		switch(StateName.valueOf(buttonText))
		{
			case StopServer:
			{
				ServerSelectDirectoryState.getInstance().getUnicastServer().command(Server.ServerCommand.StopServer);

				ServerSelectDirectoryState.getInstance().getHelloMessage().command(Hello.Command.Stop);
				try
				{
					gb = new Goodbye(Resources.getInstance().getUsername(), Resources.getInstance().getMulticastGroup(),Resources.getInstance().getMulticastPortNumber());
				}
				catch(IOException ioe)
				{
					ioe.getStackTrace();
					throw new RuntimeException("gb never got initialized if this was thrown");
				}
					gb.run();
				nextState = ServerStartServingState.getInstance();
				break;
			}
			case AddFile:{nextState = ServerSelectFileState.getInstance();break;}
			case RemoveFile:{nextState = ServerRemoveFileState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ServerStopServingState");

		}

			return nextState;
	}



}