import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;

public class Server implements Runnable
{
	/*
			Eric Swenson
			November 21 2016
			Jarvis

			Server Class

			Class Variables:

			private ServerSocket serverSocket;
			private AtomicBoolean serverIsRunning;

			Methods:

			abstract public void processFile(File file);


			Modification History:

			November 21 2016

			Fully implemented this in class.

*/
		public enum ServerCommand
		{
			StartServer,
			StopServer;
		}

		private ServerSocket serverSocket;
		private AtomicBoolean serverIsRunning;


	public Server()
	{
		try
		{
		this.serverSocket = new ServerSocket(0);

		this.serverIsRunning = new AtomicBoolean();

		this.serverIsRunning.set(false);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		//initialize atomic boolean
	}

	public Server(int port)
	{
		try
		{
		this.serverSocket = new ServerSocket(port);

		this.serverIsRunning = new AtomicBoolean();

		this.serverIsRunning.set(false);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}


	public int getPort()
	{
		return this.serverSocket.getLocalPort();
	}

	private ServerSocket getServerSocket()
	{
		return this.serverSocket;
	}

	public void log(String message)
	{
		System.out.println(message);

	}

	public void log(Exception e)
	{
		System.out.println(e);

	}
	private void setServerIsRunning(boolean value)
	{
		serverIsRunning.set(value);
	}

	public boolean serverIsRunning()
	{
		boolean run;
		run = false;

		if(this.serverIsRunning.get() == true)
		{
			run = true;
		}

		return run;
	}



	public synchronized void command(ServerCommand command)
	{


		if(command == null)
		throw new IllegalArgumentException ("a null command was sent to command() in Server class");


		switch(command)
		{
			case StartServer:{
								if(serverIsRunning() == false)
								{
									new Thread(this).start();
								}

								break;
							 }
			case StopServer:
							 {
								 if(serverIsRunning() == true)
								 {
									try
									{
										log("Server is down");

									Thread.sleep(3000);


							  	 	}
							  	 	catch(Exception e)
										{
											System.out.println(e.getMessage());
										}
									break;
								 }

							 }
	}


	}

	public void run()
	{
		Socket socket;

		setServerIsRunning(true);

		log("Server is running.");
		try
		{


		while(serverIsRunning.get() == true)
		{
			socket = getServerSocket().accept();

			new Thread(new ConnectionManager(socket)).start();


		}
		}
		catch(IOException ioe)
		{
			log("E you got an IO exception in the run method of your server class " + ioe.getMessage());
		}



		setServerIsRunning(false);

	}

	public void handleConnection(InputStream inputStream, OutputStream outputStream)
	{
		/*try
		{
			inputStream.toString();
		}
		catch(Exception e)
		{
			e.getMessage();
		}*/

	}

	private class ConnectionManager implements Runnable
	{
		private Socket socket;

		public ConnectionManager(Socket socket)
		{
			this.socket = socket;
		}

		public void run()
		{
			InputStream in;
			OutputStream out;
			try
			{
				Server.this.handleConnection(this.socket.getInputStream(), this.socket.getOutputStream());


			}
			catch(Exception e)
			{

			}
			finally
			{
				try
				{
					Server.this.serverSocket.close();
				}
				catch(Exception e)
				{
					log("I couldn't close the socket in server" + e.getMessage());
				}
			}

		}
	}


}