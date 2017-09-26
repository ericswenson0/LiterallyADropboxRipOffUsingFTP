import java.io.*;
import java.net.*;
import java.util.*;

public class FileNameToFileServerMap
{

	/*
			Eric Swenson
			December 12th
			Dr. Jarvis

			 creates a bridge from the file name to the server for the listner

			Class Variables:
			private HashMap<File, InetAddress> directory;

			methods:
			public void remove(File file)

			public void putServerAddressFor(File file, InetSocketAddress address)

			public int size()

			private String toKey(File file)


			Modification History:

			December 12th
			have the shell working, need to finish part 14 of the project

11
		*/
	private HashMap<File, InetSocketAddress> directory;

	public FileNameToFileServerMap()
	{
		this.directory = new HashMap<File,InetSocketAddress>();
	}

	public InetSocketAddress getServerAddressFor(File file)
	{
		return this.directory.get(file);
	}

	public void putServerAddressFor(File file, InetSocketAddress address)
	{
		System.out.println("" + file + " : " + address);
		this.directory.put(file,address);

	}
	public void remove(File file)
	{
		this.directory.remove(file);
	}
	public void remove(File[]array)
	{
		this.directory.remove(array);
	}//removes a file[] from the hashmap??
	public int size()
	{
		return this.directory.size();
	}

	public String toString()
	{
		return this.directory.toString();
	}




}