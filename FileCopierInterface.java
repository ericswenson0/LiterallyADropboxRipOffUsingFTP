import javax.swing.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public interface FileCopierInterface
{
	/*
			Eric Swenson
			October 2016
			Dr. Jarvis

			FileCopierInterface

			Class Variables:


			methods:

			public void copyFile(InputStream input, OutputStream output, int bufferSize)throws IOException


			Modification History:

			Wrote this some time in October, didn't document because I didn't realize when I wrote it how important
				documentation is

			December 1st 2016
			Used this in my project, works how I need it to work.


		*/
	public void copyFile(InputStream input, OutputStream output,int bufferSize)throws IOException;
}