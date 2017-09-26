import java.io.*;
public class ServerSaveFileState extends FileChooserState
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
		Save,
		Cancel;
	}
	private static ServerSaveFileState instance = new ServerSaveFileState();

	public ServerSaveFileState()
	{
		super(StateName.values());
	}

	public static synchronized ServerSaveFileState getInstance()
	{
		return ServerSaveFileState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		buttonText = super.showDialog(ServerSelectDirectoryState.getInstance().getSelectedFile(), ServerSelectFileState.getInstance().getSelectedFile());

		nextState = null;

		switch(StateName.valueOf(buttonText))
		{
			case Save:
			{
				File targetFile;
				File sourceFile;

				sourceFile = ServerSelectFileState.getInstance().getSelectedFile();
				targetFile = this.getSelectedFile();



				if(sourceFile.equals(targetFile))
				{
					nextState = ServerSourceAndTargetAreSameState.getInstance();
				}
				else
				{
					if(this.getInstance().getSelectedFile().exists())
					{
						nextState = ServerOverWriteExistingFileState.getInstance();
					}
				}
				if(sourceFile.equals(targetFile) == false)

					try
					{
						InputStream in = new FileInputStream(new File(sourceFile.toString()));
						OutputStream out = new FileOutputStream(new File(targetFile.toString()));
						BinaryFileCopier copier;

						copier = new BinaryFileCopier();

						copier.copyFile(in,out,1000);

						in.close();

						out.close();

						nextState = ServerStopServingState.getInstance();

					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
					}

					ServerSelectDirectoryState.getInstance().updateTimeOfLastUpdate();
				break;
			}
			case Cancel:{nextState = ServerStopServingState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ServerSaveFileState");

		}

			return nextState;
	}


}