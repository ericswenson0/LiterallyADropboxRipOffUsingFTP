import javax.swing.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;


public class BinaryFileCopier implements FileCopierInterface
{
	/*
		Eric Swenson
		October 2016
		Dr. Jarvis

		Binary File Copier, reads an inputstream and outputs it to a file

		Class Variables:


		methods:

		public void copyFile(InputStream input, OutputStream output, int bufferSize)throws IOException


		Modification History:

		Wrote this some time in October, didn't document because I didn't realize when I wrote it how important
			documentation is

		December 1st 2016
		Used this in my project, works how I need it to work.


		*/

	public void copyFile(InputStream input, OutputStream output, int bufferSize)throws IOException
	{

		byte[] buffer;
		int    bytesRead;


		buffer = new byte[bufferSize];

		bytesRead = input.read(buffer);

		while(bytesRead>0)
		{
			output.write(buffer,0,bytesRead);
			bytesRead = input.read(buffer);
		}
	}

}