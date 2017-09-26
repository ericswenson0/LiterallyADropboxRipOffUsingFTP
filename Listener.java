import java.io.*;
import java.net.*;
import java.util.*;

public class Listener implements Runnable
{
/*	  Eric Swenson
		  December 6th 2016
		  Jarvis

		Listens for multicast sends and has an inner class that has a lot of gets


		Instance Variables:

		private final long serialVersionUID = 1;
		private int     		multicastPort;
			private InetAddress	    multicastGroup;
			private String          userName;
	transient private MulticastSocket multicastSocket;

		Methods:

		public Goodbye(String userName, InetAddress multicastGroup, int multicastPort) throws IOException

		public void run()

		Modification History:

		not sure when I switched this from the multicastExample to the listener class. Some time that
		had me catching up in class.

		December 12th 2016 at 10:09pm

		At this point I am ready to work on part 15 just about. some parts of 14 are
		tripping me up

		December 14th 2016 at 7:29PM

		Morale is failing me now, I am going to cry myself to sleep


*/
private class ServerInfo
	{

		private File[] sharedFiles;
		private long timeOfLastUpdate;
		private InetAddress unicastIPNumber;
		private int unicastPortNumber;
		private String userName;



		public ServerInfo(Hello helloMessage, InetAddress unicastIPNumber)
		{
			if(helloMessage == null)
				throw new IllegalArgumentException("helloMessage in ServerInfo.constructor() was null");

			if(unicastIPNumber == null)
				throw new IllegalArgumentException("unicastIPNumber in ServerInfo.constructor() was null");

				this.userName = helloMessage.getUserName();

				this.unicastPortNumber = helloMessage.getUnicastPortNumber();

				this.unicastIPNumber = unicastIPNumber;

				this.timeOfLastUpdate = 0;

				this.sharedFiles = new File[]{};
		}

		public String getUsername()
		{
			return this.userName;
		}

		public String getDirectoryName()
		{
			return	(new File(Listener.this.getServerDirectory().getPath() +  "/" + this.userName.trim() + this.unicastIPNumber)).getPath();
		}

		public long getTimeOfLastUpdate()
		{
			return this.timeOfLastUpdate;
		}

		public InetAddress getUnicastIPNumber()
		{
			return this.unicastIPNumber;
		}

		public  int getUnicastPortNumber()
		{
			return this.unicastPortNumber;
		}

		public void setSharedFiles(File[] fileArray)
		{
			this.sharedFiles = fileArray;
		}

		public void setTimeOfLastUpdate(Long time)
		{
			this.timeOfLastUpdate = time;
		}
		public void createFiles()
		{

			System.out.println("hey I am in createFiles()");
			InetSocketAddress socketAddress;
			File parentF;

			socketAddress = new InetSocketAddress(this.getUnicastIPNumber(), this.getUnicastPortNumber());
			try
			{
			for(int i = 0;i<this.sharedFiles.length;i++)
			{

				parentF = new File(sharedFiles[i].getParent());

				if(parentF != null)
				{
					System.out.println("hey i do this");
					parentF.mkdirs();
					sharedFiles[i].createNewFile();
					System.out.println(this.sharedFiles[i]);
					System.out.println("do i get here?");
				}
				Listener.this.getFileNameMap().putServerAddressFor(sharedFiles[i], socketAddress);
			}

			}
			catch(IOException ioe)
			{
				System.out.println(ioe.getMessage());
			}
		}

		public void deleteFiles()
		{
			for(int i = 0;i<this.sharedFiles.length;i++)
			{
				sharedFiles[i].delete();
			}

		}

		public File[] getSharedFilesFromServer() throws IOException, ClassNotFoundException
		{
			ObjectOutputStream output;
			ObjectInputStream input;
			Socket socket;
			File[] file;
			File[] sharedFiles;
			SharedFilesRequestMessage requestShared;
			System.out.println("hey i am here after the declarations of getSharedFilesFromServer");
			try
			{
				System.out.println(this.getUnicastIPNumber() + " " + this.getUnicastPortNumber());
			socket = new Socket(this.getUnicastIPNumber(), this.getUnicastPortNumber());
			}
			catch(Exception e)
			{
				e.printStackTrace();

				throw  new RuntimeException("Throw that shit fam");
			}
			System.out.println("connected to server");
			requestShared = new SharedFilesRequestMessage();
			System.out.println("created request shared");
			output =  new ObjectOutputStream(socket.getOutputStream());//writes a shared files request to socket outputstream
				System.out.println("OUTPUT GETS FUCKED UP?");
			input  =  new ObjectInputStream(socket.getInputStream());
			output.writeObject(requestShared);
			file = (File[])input.readObject();
			sharedFiles = new File[file.length];
			System.out.println("before loop");
			for(int i = 0; i<file.length;i++)
			{
				sharedFiles[i] = new File(Listener.this.serverDirectory.getAbsolutePath(),file[i].getPath());
			}
			setSharedFiles(sharedFiles);

			return sharedFiles;
		}
	}

	private HashMap<String,ServerInfo>       servers;
	private File                             serverDirectory;
	private FileNameToFileServerMap 		 fileNameToFileServerMap;


	public Listener(File serverDirectory, FileNameToFileServerMap fileNameToFileServerMap)
	{
		if(serverDirectory==null)
			throw new IllegalArgumentException("serverDirectory in Listener constructor arrived null");

		if(fileNameToFileServerMap==null)
			throw new IllegalArgumentException("fileNameToFileServerMap in Listener constructor arrived null");
		this.servers = new HashMap<String,ServerInfo>();
		this.serverDirectory = serverDirectory;
		this.fileNameToFileServerMap = fileNameToFileServerMap;

	}

	public File getServerDirectory()
	{
		return this.serverDirectory;
	}

	private ServerInfo getServerInfo(String userName, InetAddress ipNumber)
	{
		return this.servers.get(toKey(userName,ipNumber));
	}

	private void put(ServerInfo info)
	{
		this.servers.put(toKey(info.getUsername(),info.getUnicastIPNumber()),info);
	}

	private String toKey(String userName, InetAddress ip)
	{
			return userName + ip.toString();

	}


	public void run()
	{


				int 			  port;
				InetAddress       group;
				HelloGoodbye      message;
				byte[]            buffer;
				DatagramPacket    packet;
				MulticastSocket   multicastSocket;
				ObjectInputStream objectInputStream;
				Object 			  object;
				int 			  messageCounter;
				InetAddress		  sendersIP;
				String			  userName;



		try
		{


			port  = 8888;
			group = InetAddress.getByName("228.5.6.7");
			buffer = new byte[1000];
			packet = new DatagramPacket(buffer,buffer.length);
			multicastSocket = new MulticastSocket(port);
			multicastSocket.joinGroup(group);
			messageCounter = 0;

				while(true)
					{
					System.out.println("Listening.");
					multicastSocket.receive(packet);
					objectInputStream = new ObjectInputStream(
										new ByteArrayInputStream(packet.getData()));

							message   = (HelloGoodbye) objectInputStream.readObject();
							sendersIP = packet.getAddress();
					   messageCounter = messageCounter + 1;



					System.out.println(message  + " " + sendersIP.toString());
					//try
					//	{
							//object = new ObjectInputStream(objectInputStream).readObject();
							System.out.println("Just received a " + message.getClass().getName() + " from " + packet.getAddress());
							//object = (HelloGoodbye)objectInputStream.readObject();
					//	}
					//catch(Exception e)
						//{

						//}

						handleMessage(message,sendersIP);

					}
		}
		catch(Exception e)
		{

			throw new RuntimeException("Eric, sorry, but the multicast Socket creation failed for some reason");
		}
	}

	private void handleMessage(HelloGoodbye message, InetAddress sendAddress)
	{
		Hello hello;
		Goodbye gb;
		ServerInfo info;
		String userName;
		ServerSocket socket;
		Socket clientSocket;
		boolean bool;
		boolean wasHello;

		bool     = false;
		wasHello = false;
		info     = null;
		hello    = null;
			try
			{
				message = (Hello)message;
				hello = (Hello)message;

				bool = true;
				wasHello=true;
			}
			catch(ClassCastException cce)
			{

			}
			try
			{
				message = (Goodbye)message;
				bool = false;
			}
			catch(ClassCastException cce)
			{

			}
			if(!bool)
				throw new RuntimeException("message was unable to cast");

			if(wasHello)//if message is a hello message
				if(!servers.containsValue(getServerInfo(message.getUserName(), sendAddress)))//if new hello
						{
							//put the hello into servers call update time?
							try
							{
								info = new ServerInfo(hello,sendAddress);
								System.out.println("This happens");
								this.servers.put(toKey(message.getUserName(), sendAddress),new ServerInfo(((Hello)message), sendAddress));
							}
							catch(ClassCastException cce)
							{
								cce.printStackTrace();
								System.out.println("E dawg Swag Money. You got an error in your handleMessage of Listener");
							}

							try{info.setSharedFiles(info.getSharedFilesFromServer());}
							catch(IOException ioe){System.out.println("listener.handle connection throws an IOException error");}
							catch(ClassNotFoundException ioe){System.out.println("listener.handle connection throws a classnotfound error");}
							System.out.println("Hey I am handling a new hello");
							info.deleteFiles();
							info.createFiles();
							System.out.println("Hey I am done handling a new hello");
						}

			if(wasHello)//if message is a hello message
				if(servers.containsValue(getServerInfo(message.getUserName(), sendAddress)))//if old hello
					{

					}

			if(!wasHello)//deletes files
					{
						this.servers.remove(message.getUserName());
						System.out.println("I just removed " +  message.getUserName() +  " from the shared files");
					}

						//if(servers.containsValue(getServerInfo("UserNameFixThis", packet.getAddress())))//if old hello
						//{
							//clear everything out call update time?

						//}

						//if(message.getClass().equals("Goodbye"))//if goodbye
						//{
							//call delete?

						//}
	}




	public FileNameToFileServerMap getFileNameMap()
	{
		return this.fileNameToFileServerMap;
	}

	public static void main(String[]args)
	{
		Listener listen = new Listener(new File(System.getProperty("user.dir")), new FileNameToFileServerMap());
		new Thread(listen).start();
	}
}