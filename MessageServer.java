import java.io.*;
import java.net.*;
import java.util.*;

public class MessageServer extends Server
{
	/*		  Eric Swenson
			  December 6th 2016
			  Jarvis

			Message server that handles only server objects

			Instance Variables:

			private SharedFiles sharedFiles;


			Methods:

			public MessageServer(SharedFiles sharedFiles, int port)

			Modification History:

			December 6 2016

			wrote it

			December 12

			got it working
*/
	private SharedFiles sharedFiles;

	public MessageServer(SharedFiles sharedFiles)
	{
		this(sharedFiles,0);

	}
	public MessageServer(SharedFiles sharedFiles, int port)
	{

		super(port);
		if(sharedFiles == null)
			throw new IllegalArgumentException("sharedFiles was null in MessageServer overloaded Constructor");

		this.sharedFiles = sharedFiles;
	}
	public SharedFiles getSharedFiles()
	{
		return this.sharedFiles;
	}

	@Override
	public void handleConnection(InputStream inputStream, OutputStream outputStream)
	{
		System.out.println("made it to handleConnection in MessageServer");
		Object message;
		message = null;
		System.out.println("made it to handle the connection");
				try
				{
					message = new ObjectInputStream(inputStream).readObject();
				}
				catch(ClassCastException e)
				{
					System.out.println(e.getMessage());
				}
				catch(IOException ioe)
				{
					System.out.println(ioe.getMessage());
				}
				catch(ClassNotFoundException cnfe)
				{
					System.out.println(cnfe.getMessage());
				}

				log(" I just handled: " + message.getClass().toString());
					try
					{
							((FileRequestMessage)message).get(this.getSharedFiles().getSharedDirectory(),outputStream);
							((SharedFilesRequestMessage)message).get(this.sharedFiles,outputStream);
					}
					catch(ClassCastException cce)
					{
						cce.getStackTrace();
						System.out.println(message.getClass().toString());
					}
				catch(Exception e)
				{
					e.getStackTrace();
				}
	}

}