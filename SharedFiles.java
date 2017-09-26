import java.io.*;
import java.util.*;

public class SharedFiles implements Runnable
{
/*
		Eric Swenson
		December 2nd 2016
		Jarvis

		Class where the shared files are stored

		Instance Variables:

		private File rootDirectory;
		private SimpleList<File> filesNotYetProcessed;

		Methods:

		public SharedFiles(File sharedDirectory)
		private SimpleList<File> getListOfUnprocessedFiles()


		Modification History:

		December 2nd 2016

		Added everything

		December 11th

		Finished


*/

	private ArrayList<File> fileList;
	private long timeOfLastUpdate;
	private File sharedDirectory;

	public SharedFiles(File sharedDirectory)
	{
		this.sharedDirectory = sharedDirectory;

		this.timeOfLastUpdate = ServerSelectDirectoryState.getInstance().getTimeOfLastUpdate();

		this.fileList = new ArrayList<File>();

		timeOfLastUpdate = 0;
	}

	private ArrayList<File> getFileList(){return this.fileList;}

	public synchronized long getTimeOfLastUpdate()
	{
		return this.timeOfLastUpdate;
	}

	public synchronized boolean getSetTimeOfLastUpdate(long updateTime)
	{
		boolean update;

		update = false;

		if(getTimeOfLastUpdate()<updateTime)
		{
			update = true;
		}
		return update;
	}

	public File getSharedDirectory()
	{
		return this.sharedDirectory;
	}

	public void run()
	{
			Thread thread;
				if(getSetTimeOfLastUpdate(System.currentTimeMillis()))
					{

					thread = new Thread(new GetSharedFiles(getSharedDirectory()));
					thread.start();

					}
				try
				{
					Thread.sleep(5000);
				}
				catch(InterruptedException ie)
				{
					ie.getStackTrace();
				}
				System.out.println("checkpoint b");



	}
private class GetSharedFiles implements Runnable, FileProcessor
{
	private ArrayList<File> fileList;
	private int sharedDirectoryPathLength;
	DirectoryLister directoryList;

	public GetSharedFiles(File sharedDirectory)
		{
			if(sharedDirectory==null)
				throw new IllegalArgumentException("Shared Directory passed to GetSharedFiles.constructor was null");

			System.out.println(sharedDirectory.isFile() + sharedDirectory.toString());

			this.sharedDirectoryPathLength = sharedDirectory.toString().length();
			this.directoryList = new DirectoryLister(sharedDirectory,this);
			System.out.println(this.toString());

			this.fileList = new ArrayList<File>();
		}

		public void processFile(File file)
		{

			if(file.isFile())
			this.fileList.add(new File(file.getAbsolutePath().substring(this.sharedDirectoryPathLength)));
		}

		public void run()
		{


			System.out.println(this.directoryList.getFileProcessor());
			this.directoryList.run();

			setList(this.fileList);
		}
}

	private synchronized void setList(ArrayList<File> fileList)
	{
		File[] test;


		this.fileList = fileList;
		test = new File[fileList.size()];
		for(int i = 0;i<test.length;i++)
		{
			System.out.println(fileList.get(i));
		}
	}

	public synchronized File[] toArray()
	{
		return this.fileList.toArray(new File[0]);
	}


}