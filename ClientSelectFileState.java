import java.io.*;
public class ClientSelectFileState extends FileChooserState
{
	/*
		Eric Swenson
		December 7th 2016
		Dr. Jarvis

		Client state that lets a user choose a file

		Class Variables:
		Instance of the Singleton

		Enums:
		Select
		cancel

		methods:

		public static synchronized ClientSelectFileState getInstance()
		public NavigationState execute()


		Modification History:

		December 7th 2016
		Finished writing the class, works. Standby, I probably will have to implement some logic to get this to work in
		the system.


		*/

	public enum StateName
	{
		Select,
		Cancel;
	}
	private static ClientSelectFileState instance = new ClientSelectFileState();

	public ClientSelectFileState()
	{
		super(StateName.values());
	}

	public static synchronized ClientSelectFileState getInstance()
	{
		return ClientSelectFileState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		buttonText = super.showDialog();

		nextState = null;

		switch(StateName.valueOf(buttonText))
		{
			case Select:
			{

			if (ClientBeginState.getInstance().getFileNameServerMap().getServerAddressFor(this.getInstance().getSelectedFile()) == null)
							{

								nextState = ClientFileNotInServerDirectoryState.getInstance();

							}
							else
							{
								nextState = ClientFileNotInServerDirectoryState.getInstance();
							}

				break;
			}
			case Cancel:{nextState = ClientGetOrQuitState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ClientSelectFileState");

		}

			return nextState;
	}


}