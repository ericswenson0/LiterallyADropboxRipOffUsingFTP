import java.util.*;
import java.io.*;

public class SharedFilesRequestMessage extends Message
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

						Shared File Request Message Server User sends to directory to save a file

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

							December 13th 2016
							Looked over this again, added serializability

*/
	private static final long serialVersionUID = 1;

	public SharedFilesRequestMessage()
	{

	}

	public void get(SharedFiles sharedFiles, OutputStream outputStream) throws IOException, ClassNotFoundException
	{
		if(sharedFiles == null)
			throw new IllegalArgumentException("sharedFiles in get of SharedFilesRequestMessage is null");

		if(outputStream == null)
			throw new IllegalArgumentException("outputStream in get of SharedFilesRequestMessage is null");

		ObjectOutputStream oos;

				try
				{
					oos = new ObjectOutputStream(outputStream);

					oos.writeObject(sharedFiles.toArray());
				}
				catch(IOException ioe)
				{
					ioe.getStackTrace();

					System.out.println("eric, look in your Shared FilesRequestMessage.get()");
				}
	}


}