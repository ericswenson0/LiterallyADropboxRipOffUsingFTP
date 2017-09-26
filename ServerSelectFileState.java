import java.io.*;

public class ServerSelectFileState extends FileChooserState
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

						Gives the user a chance to save a file

							Instance Variables:
							Enum:
							Save
							Cancel

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
		Select,
		Cancel;
	}
	private static ServerSelectFileState instance = new ServerSelectFileState();

	public ServerSelectFileState()
	{
		super(StateName.values());
	}

	public static synchronized ServerSelectFileState getInstance()
	{
		return ServerSelectFileState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		File file = this.getSelectedFile();

		buttonText = super.showDialog(null, null);
		switch(StateName.valueOf(buttonText))
		{
			case Select:
			{
				file = this.getSelectedFile();

				if(file.exists() == false)
				{
					nextState = ServerSelectFileDoesNotExist.getInstance();
				}
				else
				{
					nextState = ServerSaveFileState.getInstance();
				}
				break;
			}
			case Cancel:{nextState = ServerStopServingState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ServerSelectFileState");

		}

			return nextState;
	}

}