import java.io.*;

public class ServerRemoveFileState extends FileChooserState
{
	/*	  Eric Swenson
						  December 9th 2016
						  Jarvis

						Asks user if they want to overwrite an existing file

						Instance Variables:
						Enum:
						Remove
						Cancel

						private static ServerRemoveFileState instance = new ClientFileNotInServerDirectoryState();

						Methods:

						public ServerRemoveFileStatex()

							public static synchronized ServerRemoveFileState getInstance()

							public NavigationState execute()

						Modification History:

						December 7th 2016
						wrote the code. Tested.

						December 10th 2016
						Implemented Server Logic State Fully

*/
	public enum StateName
	{
		Remove,
		Cancel;
	}
	private static ServerRemoveFileState instance = new ServerRemoveFileState();

	public ServerRemoveFileState()
	{
		super(StateName.values());
	}

	public static synchronized ServerRemoveFileState getInstance()
	{
		return ServerRemoveFileState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		buttonText = super.showDialog(ServerSelectDirectoryState.getInstance().getSelectedFile(), null);

		switch(StateName.valueOf(buttonText))
		{
			case Remove:
			{
				if(this.getSelectedFile().getParent().equals(ServerSelectDirectoryState.getInstance().getSelectedFile().getPath())==true)
				{
				getSelectedFile().delete();

				nextState = ServerStopServingState.getInstance();
				}
				else
				{
					nextState = ServerDeleteOutsideSharedDirectoryState.getInstance();
				}

				break;
			}
			case Cancel:{nextState = ServerStopServingState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ServerRemoveFileState");

		}

			ServerSelectDirectoryState.getInstance().updateTimeOfLastUpdate();

			return nextState;
	}
}