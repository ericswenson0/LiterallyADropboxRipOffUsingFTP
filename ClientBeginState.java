import java.io.*;
import java.util.*;
public class ClientBeginState extends MessageState
{

	/*	  Eric Swenson
		  December 9th 2016
		  Jarvis

		Client Begin State Implementation

		Instance Variables:

		Methods:

		private ClientBeginState()

		public void run()

		public static synchronized ClientBeginState getInstance()

		public NavigationState execute()

		Modification History:

		December 7th 2016
		wrote the code. Tested.

		December 10th 2016
		Finished


*/
	public enum StateName
	{
		Start,
		Quit;
	}


	private static ClientBeginState instance = new ClientBeginState();

	private FileNameToFileServerMap fileNameServerMap;

	private ClientBeginState()
	{
			super(StateName.values());
	}

	public static synchronized ClientBeginState getInstance()
	{
		return ClientBeginState.instance;
	}
	public FileNameToFileServerMap getFileNameServerMap()
	{
		return this.fileNameServerMap;
	}


	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;
		File targetFile;
		File sourceFile;
		Listener listen;


		nextState = null;
		fileNameServerMap = null;

		buttonText = super.showDialog();

		switch(StateName.valueOf(buttonText))
		{
			case Start:
				{

					this.fileNameServerMap = new FileNameToFileServerMap();

					System.out.println(getFileNameServerMap());
					listen = new Listener(Resources.getInstance().getClientServerDirectory(),fileNameServerMap);
					new Thread(listen).start();



					if(Resources.getInstance().getClientServerDirectory().exists())
							Resources.getInstance().getClientServerDirectory().renameTo(new File("ServerDirectory" + " " + (new Date().getTime())));


					nextState = ClientGetOrQuitState.getInstance();
					break;
				}
			case Quit:{nextState = null;break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ClientBeginState");

		}

			return nextState;
	}


}
