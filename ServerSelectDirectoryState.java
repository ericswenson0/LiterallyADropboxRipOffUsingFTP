import java.util.*;
import java.net.*;
import java.io.*;

public class ServerSelectDirectoryState extends FileChooserState implements Runnable
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

						Gives the user a chance to select a directory a file

							Instance Variables:
							Enum:
							Select
							Cancel

							private static ServerSelectDirectoryState instance = new ClientFileNotInServerDirectoryState();

							Methods:

							public ServerSelectDirectoryState()

								public static synchronized SServerSelectDirectoryState getInstance()

								public NavigationState execute()

							Modification History:

							December 7th 2016
							wrote the code. Tested.

							December 10th 2016
							Implemented Server Logic State Fully

*/

	public enum StateName
	{
		Select,
		Cancel;
	}

	private long timeOfLastUpdate;

	private Hello helloMessage;

	private static ServerSelectDirectoryState instance = new ServerSelectDirectoryState();

	private Server messageServer;

	private ServerSelectDirectoryState()
	{
		super(StateName.values());
		this.timeOfLastUpdate = 0;

		this.helloMessage = null;


	}

	public static synchronized ServerSelectDirectoryState getInstance()
	{
		return ServerSelectDirectoryState.instance;
	}


	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;
		SharedFiles sharedFiles;

		buttonText = super.showDialog(null, null);

		switch(StateName.valueOf(buttonText))
			{
				case Select:
				{
					sharedFiles = new SharedFiles(this.getSelectedFile());

					new Thread(sharedFiles).start();

					for(int i = 0;i<sharedFiles.toArray().length;i++)
						{
							System.out.println(sharedFiles.toArray()[i].toString());

						}

					try
						{
							this.helloMessage = new Hello(Resources.getInstance().getUsername(),Resources.getInstance().getMulticastGroup(),8888,8888,5000,sharedFiles);

							this.helloMessage.run();
						}
					catch(IOException ioe)
						{
							System.out.println(ioe.getStackTrace());
							new RuntimeException("Eric go to ServerSelectDirectoryState.execute()");
						}

					try
					{
						this.messageServer = new MessageServer(sharedFiles, Resources.getInstance().getUnicastPortNumber());
					}
					catch(Exception e)
					{
						throw new RuntimeException("ServerSelectDirectoryState did not start a message server");
					}


					updateTimeOfLastUpdate();


					nextState = ServerStartServingState.getInstance();

					break;
				}

				case Cancel:{nextState = ServerBeginState.getInstance();break;}

				default: throw new RuntimeException("Unrecognized enum value in execute of ServerSelectDirectoryState");
			}

		return nextState;

	}

	public long getTimeOfLastUpdate()
	{
		return this.timeOfLastUpdate;
	}

	public void updateTimeOfLastUpdate()
	{
		Thread thread;
		this.timeOfLastUpdate = System.currentTimeMillis();

			thread = new Thread(new SharedFiles(super.getSelectedFile()));
			thread.setName("SharedFilesThreadInSelectDirectoryState");
			thread.start();
	}
	public void run()
	{

	}

	public Hello getHelloMessage()
	{
		return this.helloMessage;
	}

	public Server getUnicastServer()
	{
		return this.messageServer;
	}


}