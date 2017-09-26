import java.io.*;

public class ServerOverWriteExistingFileState extends MessageState
{

	/*	  Eric Swenson
					  December 9th 2016
					  Jarvis

					Asks user if they want to overwrite an existing file

					Instance Variables:
					Enum:
					Yes
					No

					private static ClientFileNotInServerDirectoryState instance = new ClientFileNotInServerDirectoryState();

					Methods:

					public ServerOverWriteExistingFileState()

						public static synchronized ServerOverWriteExistingFileState getInstance()

						public NavigationState execute()

					Modification History:

					December 7th 2016
					wrote the code. Tested.

					December 10th 2016
					Implemented Server Logic State Fully

*/
	public enum StateName
	{
		Yes,
		No;
	}

	private static ServerOverWriteExistingFileState instance = new ServerOverWriteExistingFileState();

	private ServerOverWriteExistingFileState()
	{
			super(StateName.values());
	}

	public static synchronized ServerOverWriteExistingFileState getInstance()
	{
		return ServerOverWriteExistingFileState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;
		String message;

		message = getMessage();

		message = message.substring(0,message.length()-1).trim();

		message = message + "; " + ServerSelectDirectoryState.getInstance().getSelectedFile().getName();

		buttonText = super.showDialog();

		switch(StateName.valueOf(buttonText))
		{
			case Yes:
				{

					File targetFile;
					File sourceFile;

									sourceFile = ServerSelectFileState.getInstance().getSelectedFile();
									targetFile = ServerSaveFileState.getInstance().getSelectedFile();

					try
										{
											InputStream in = new FileInputStream(new File(sourceFile.toString()));
											OutputStream out = new FileOutputStream(new File(targetFile.toString()));
											BinaryFileCopier copier;

											copier = new BinaryFileCopier();

											copier.copyFile(in,out,1000);

											in.close();

											out.close();

										}
										catch(Exception e)
										{
											System.out.println(e.getMessage());
					}
						ServerSelectDirectoryState.getInstance().updateTimeOfLastUpdate();

					nextState = ServerStopServingState.getInstance();
					break;
				}
			case No:{nextState = ServerSaveFileState.getInstance();break;}
			default: throw new RuntimeException("Unrecognized enum value in execute of ServerOverWriteExistingFileState");

		}

			return nextState;
	}
}
