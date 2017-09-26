public class ClientGetOrQuitState extends MessageState
{
	/*
		Eric Swenson
		December 7th 2016
		Dr. Jarvis

		Client state that asks if you want to get a file or quit the program

		Class Variables:
		Instance of the Singleton

		Enums:

		GetAFile
		Quit

		methods:

		public static synchronized ClientGetOrQuitState getInstance()
		public NavigationState execute()


		Modification History:

		December 7th 2016
		Finished writing the class, works. Standby, I probably will have to implement some logic to get this to work in
		the system.


		*/

	public enum StateName
	{
		GetAFile,
		Quit;
	}
	private static ClientGetOrQuitState instance = new ClientGetOrQuitState();

	public ClientGetOrQuitState()
	{
		super(StateName.values());
	}

	public static synchronized ClientGetOrQuitState getInstance()
	{
		return ClientGetOrQuitState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		nextState = null;

		buttonText = super.showDialog();

		switch(StateName.valueOf(buttonText))
		{
			case GetAFile:
			{

				nextState = ClientSelectFileState.getInstance();
				break;
			}
			case Quit:{nextState = null;break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ClientGetOrQuitState");

		}

			return nextState;
	}
}