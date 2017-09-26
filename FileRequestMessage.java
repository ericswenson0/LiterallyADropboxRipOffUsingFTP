import java.util.*;
import java.net.*;
import java.io.*;


public class FileRequestMessage extends Message
{
	/*
			Eric Swenson
			November 2nd 2016
			Jarvis

			Request File message The client sends

			Methods:

			abstract public void processFile(File file);


			Modification History:
			December 8th 2016

			implemented everything. Didn't test.

			December 12th 2016

			went through assigment again, tried to make sure everything is nice and tidy

			Still unsure how to test this out

*/

	private static final long serialVersionUID = 1;
	private File file;

	public FileRequestMessage(File file)
	{
		if(file == null)
			throw new IllegalArgumentException("FileRequestMessage was passed a null file");

		this.file = file;
	}//what up dude I got a file and a directory object and find this in the server and then copy this to my computer

	public void get(File directoryOfSharedFiles, OutputStream outputStream) throws IOException
	{
		if( directoryOfSharedFiles == null)
			throw new IllegalArgumentException("get in FileRequestMessage was passed an invalid directoryOfSharedFiles");

		BinaryFileCopier bfc;

		bfc = new BinaryFileCopier();
		try
		{
			bfc.copyFile(new FileInputStream(this.file),outputStream,1000);
		}
		catch(IOException ioe)
		{
			ioe.getStackTrace();
			System.out.println("Eric, sorry buddy, but something failed in your FileRequestMessage");
		}
	}



}