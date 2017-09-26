import java.util.*;
import java.io.*;
import java.net.*;

public class ClientSaveFileState extends FileChooserState
{
/*
		Eric Swenson
		December 7th 2016
		Dr. Jarvis

		client state that lets a client save the file

		Class Variables:
		Instance of the Singleton

		Enums:

		Save
		Cancel
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
			Save,
			Cancel;
		}
		private static ClientSaveFileState instance = new ClientSaveFileState();

		public ClientSaveFileState()
		{
			super(StateName.values());
		}

		public static synchronized ClientSaveFileState getInstance()
		{
			return ClientSaveFileState.instance;
		}

		public NavigationState execute()
		{
			String 			   buttonText;
			NavigationState    nextState;
			Socket 			   socket;
			ObjectInputStream  input;
			ObjectOutputStream output;
			FileRequestMessage fileRequest;
			String relativePath;
			String parentFile;
			BinaryFileCopier bfc;
			FileNameToFileServerMap fileNameServerMap;
			InetSocketAddress socketAddress;
			File			source;

			source = this.getSelectedFile();
			fileNameServerMap = ClientBeginState.getInstance().getFileNameServerMap();


			nextState = null;
			fileRequest = null;
			parentFile = null;
			relativePath = null;
			bfc = null;


			buttonText = super.showDialog();

			switch(StateName.valueOf(buttonText))
			{
				case Save:
				{
					source = this.getInstance().getSelectedFile();
					socketAddress = fileNameServerMap.getServerAddressFor(source);

					System.out.println(socketAddress);
					try
					{
					socket = new Socket(socketAddress.getAddress(), socketAddress.getPort());

					bfc = new BinaryFileCopier();

					parentFile = ClientSelectFileState.getInstance().getSelectedFile().getParent();

					relativePath = new String(ClientSelectFileState.getInstance().getSelectedFile().getPath().substring(parentFile.length()));

					fileRequest = new FileRequestMessage(new File(relativePath));

					output = new ObjectOutputStream(socket.getOutputStream());

					output.writeObject(fileRequest);

					bfc.copyFile(socket.getInputStream(),new FileOutputStream(ClientSaveFileState.getInstance().getSelectedFile()),1000);

					nextState = ClientGetOrQuitState.getInstance();

					}
					catch(Exception e)
					{
						e.printStackTrace();
						System.out.println("Eric you have an error in client save file state");
					}

					break;
				}
				case Cancel:{nextState = ClientGetOrQuitState.getInstance();break;}
				default: throw new RuntimeException("Unrecognized enum value in execute of ClientSaveFileState");

			}

				return nextState;
	}
}