import java.io.File;
import javax.swing.*;
public class DirectoryLister implements FileProcessor
{

/*
		Eric Swenson
		November 2nd 2016
		Jarvis

		This class lists a directory of files in a string arraylist

		Instance Variables:

		private File rootDirectory;
		private SimpleList<File> filesNotYetProcessed;

		Methods:

		public DirectoryLister(File rootDirectory)
		private SimpleList<File> getListOfUnprocessedFiles()
		public File getRootDirectory()
		public void run()
		public void processFile(File file)
		Modification History:

		November 2nd, 2016

		Added:

		public DirectoryLister(File rootDirectory)

		private SimpleList<File> getListOfUnprocessedFiles()

		public File getRootDirectory()

		public void run()

		public void processFile(File file)

		November 3rd

		implemented FileProcessor

		November 21st

		tested with Counting, struggling

		November 22nd

		meeting with Jarvis, hopefully fixing issues

		Fixed fam!

		December 1st

		Apparently it doesn't work correctly. Solved it the way Jarvis needed it solved. Swag

		December 7th

		I still need to fix it so it goes into sub directories :(

*/

	private File rootDirectory;

	private SimpleQueue<File> filesNotYetProcessed;

	private FileProcessor fileProcessor;

	public DirectoryLister(File rootDirectory)
	{
		this(rootDirectory,null);

		if(rootDirectory.canExecute()==false)
			throw new IllegalArgumentException("You need to pass DirectoryLister.constructor a filePath");

		this.filesNotYetProcessed = new SimpleQueue<File>();

	}

	public DirectoryLister(File rootDirectory, FileProcessor fileProcessor)
	{
		if(rootDirectory.canExecute()==false)
			throw new IllegalArgumentException("You need to pass DirectoryLister.constructor a filePath");
		if(fileProcessor==null)
			throw new IllegalArgumentException("You passed the directory lister constructor a null fileProcessor");


		this.rootDirectory = rootDirectory;

		this.filesNotYetProcessed = new SimpleQueue<File>();

		if(fileProcessor == null)
		{
			this.fileProcessor = this;
		}
		else
		{
			this.fileProcessor = fileProcessor;
		}

	}

	private SimpleList<File> getListOfUnprocessedFiles()
	{
		return this.filesNotYetProcessed;
	}

	public File getRootDirectory()
	{
		return this.rootDirectory;
	}

	public void run()
	{
		File file;

		this.filesNotYetProcessed.put(getRootDirectory().listFiles());

		while(!getListOfUnprocessedFiles().isEmpty())
		{
			this.getFileProcessor().processFile(this.getListOfUnprocessedFiles().take());

			//if(currentFile.isDirectory())
			//{
			//	this.filesNotYetProcessed.put(currentFile.listFiles());
			//	currentFile = getListOfUnprocessedFiles().take();
			//}


		}
	}

	public void processFile(File file)
	{

		if(file == null)
			throw new IllegalArgumentException("You can't pass null to processFile in DirectoryLister");


		if(file.isDirectory())
		{
			System.out.println("Directory: " + file.getPath());
		}
		if(file.isFile())
		{
			System.out.println("File: " + file.getPath());
		}
	}

	public FileProcessor getFileProcessor()
	{
		return this.fileProcessor;
	}

	/*public static void main(String[] args)
		{
			//Testing the Listing of files & directories
			DirectoryLister file;

			//file = new DirectoryLister(new File(System.getProperty("user.dir")));

			//file.run();

			//now doing filechooser

			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFileChooser chooser;
			int input;

			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			input = chooser.showOpenDialog(null);
			File directFile;
			directFile = null;


			if(input == JFileChooser.APPROVE_OPTION) //OK was pressed
			{
				directFile = chooser.getSelectedFile();
			}

			if(input == JFileChooser.CANCEL_OPTION) //Cancel was pressed
			{

				System.out.println("You canceled this...");//cancel was selected
			}

			System.out.println(directFile);

			file = new DirectoryLister(directFile);
			file.run();

		}*/


}