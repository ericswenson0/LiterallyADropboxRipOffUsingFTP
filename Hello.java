import java.util.concurrent.atomic.AtomicBoolean;
import java.util.*;
import java.net.*;
import java.io.*;

public class Hello extends HelloGoodbye
{
	/*Eric Swenson
		  December 6th 2016
		  Jarvis

			Sends a hello message connecting to a server

		Instance Variables:

		private final long serialVersionUID = 1;

		Enums:
		Start
		Stop
		Quit

		private final long serialVersionUID = 1;
			transient private int     		sleepBetweenSends;
			transient private boolean 		stopSending;
			private long 			  		timeOfLastUpdate;
			private int 					unicastPortNumber;
			transient private AtomicBoolean keepRunning;
			transient private AtomicBoolean keepSending;
			transient private Thread 		helloThread;
	transient private SharedFiles   sharedFiles;


		Methods:

		public Hello(String userName, InetAddress multicastGroup, int multicastPort) throws IOException

		public void run()

		public int getUnicastPortNumber()\
			public long getTimeOfLastUpdate()
		private void setKeepRunning(boolean value)
		private void setKeepSending(boolean value)

		Modification History:

		December 6th 2016
		wrote the code to keep up and made it work even though I am way behind


*/

	public enum Command
	{
		Start{
				@Override
				public void execute(Hello hello)
				{
					if(hello.helloThread == null)
						new Thread(hello).start();

					hello.setKeepSending(true);

				}

			 },
		Stop{
				@Override
				public void execute(Hello hello)
				{
					hello.setKeepSending(false);
				}

			 },
		Quit{
				@Override
				public void execute(Hello hello)
				{
					hello.setKeepRunning(false);
					hello.setKeepSending(false);

					if(hello.helloThread != null)
						if(hello.helloThread.isAlive())
							hello.helloThread.interrupt();


					hello.helloThread = null;
				}

			 };

		abstract public void execute(Hello hello);
	}

	private static final long serialVersionUID = 1;
	transient private AtomicBoolean keepRunning;
	transient private AtomicBoolean keepSending;
	transient private Thread 		helloThread;
	transient private SharedFiles   sharedFiles;
	transient private int     		sleepBetweenSends;
	private long 			  		timeOfLastUpdate;
	private int 					unicastPortNumber;

	public Hello(String userName, InetAddress multicastGroup, int multicastPort, int unicastPortNumber, int sleepBetweenSends, SharedFiles sharedFiles) throws IOException
	{
		super(userName,multicastGroup,multicastPort);

		if(unicastPortNumber<0 || unicastPortNumber > 65535)
			throw new IllegalArgumentException("unicastPortNumber in Hello Constructor was not in proper number bounds");
		if(sleepBetweenSends<1)
			throw new IllegalArgumentException("Sleep between sends in Hello Constructor cannot be zero or one");

		this.unicastPortNumber = unicastPortNumber;
		this.sleepBetweenSends = sleepBetweenSends;

		this.keepRunning = new AtomicBoolean();
		this.keepSending = new AtomicBoolean();
		this.helloThread = null;
	}

	public int getUnicastPortNumber()
	{
		return this.unicastPortNumber;
	}

	public long getTimeOfLastUpdate()
	{
		return this.timeOfLastUpdate;
	}

	public synchronized void command(Hello.Command command)
	{
		if(command == null || this == null)
			throw new IllegalArgumentException("You didn't pass command in Hello class a proper command value");

		command.execute(this);
	}

	public void run()
	{
		try
		{
			while(keepRunning())
			{
				if(this.keepSending())
				{
					this.timeOfLastUpdate = this.sharedFiles.getTimeOfLastUpdate();
					super.sendMe();
				}
				Thread.sleep(sleepBetweenSends);
			}
		}
		catch(IOException ioe)
		{
			throw new RuntimeException("in Hello.run() I encountered an error");
		}
		catch(InterruptedException ie)
		{

		}
	}

	private boolean keepRunning()
	{
		return this.keepRunning.get();
	}

	private boolean keepSending()
	{
		return this.keepSending.get();
	}

	private void setKeepRunning(boolean value)
	{
		this.keepRunning.set(value);
	}
	private void setKeepSending(boolean value)
	{
		this.keepSending.set(value);
	}



}