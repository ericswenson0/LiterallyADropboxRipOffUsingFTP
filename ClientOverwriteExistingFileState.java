public class ClientOverwriteExistingFileState extends MessageState
{

/*
		Eric Swenson
		December 7th 2016
		Dr. Jarvis

			Client state that asks the user to save over an existing file or not

		Class Variables:
		Instance of the Singleton

		Enums:

		Yes
		No
		methods:

		public static synchronized ClientOverwriteExistingFileState getInstance()
		public NavigationState execute()


		Modification History:

		December 7th 2016
		Finished writing the class, works. Standby, I probably will have to implement some logic to get this to work in
		the system.


		*/
	public enum StateName
	{
		Yes,
		No;
	}
	private static ClientOverwriteExistingFileState instance = new ClientOverwriteExistingFileState();

	public ClientOverwriteExistingFileState()
	{
		super(StateName.values());
	}

	public static synchronized ClientOverwriteExistingFileState getInstance()
	{
		return ClientOverwriteExistingFileState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;
		FileRequestMessage fileRequest;

		buttonText = super.showDialog();

		switch(StateName.valueOf(buttonText))
		{
			case Yes:
			{



				nextState = ClientGetOrQuitState.getInstance();
				break;
			}
			case No:{nextState = ClientGetOrQuitState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ClientOverwriteExistingFileState");

		}

			return nextState;
	}

}