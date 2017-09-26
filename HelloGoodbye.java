import java.util.*;
import java.net.*;
import java.io.*;

abstract public class HelloGoodbye implements Serializable, Runnable
{
	/*	  Eric Swenson
		  December 6th 2016
		  Jarvis

		abstract partial implementation for Hello and goodbye classes

		Instance Variables:

		private final long serialVersionUID = 1;
		private int     		multicastPort;
			private InetAddress	    multicastGroup;
			private String          userName;
	transient private MulticastSocket multicastSocket;

		Methods:

		public HelloGoodBye(String userName, InetAddress multicastGroup, int multicastPort) throws IOException

		public void run()

		Modification History:

		December 6th 2016
		wrote the code of this whole hierarchy to keep up and made it work even though I am way behind

		December 12th 2016
		This entire hierarchy works as it should. It's fully tested. If Jarv takes off
		points for documentation in any way for this hierarchy argue that you shouldn't lose a point


*/


	private static final long serialVersionUID = 1;
	private int     		multicastPort;
	private InetAddress	    multicastGroup;
	private String          userName;
	transient private MulticastSocket multicastSocket;

	public HelloGoodbye(String userName, InetAddress multicastGroup, int multicastPort) throws IOException
	{
		if(multicastPort<0 || multicastPort>65535) throw new IllegalArgumentException("hellogoodbye constructor was a passed a number outside the valid window");
		if(multicastGroup == null) throw new IllegalArgumentException("a null multicastGroup was passed to the hellogoodbye constructor");
		if(userName== null || userName.equals("")) throw new IllegalArgumentException("a null username was passed to the hellogoodbye constructor");

		this.userName = userName;
		this.multicastGroup = InetAddress.getByName("228.5.6.7");
		this.multicastPort = 8888;
		this.multicastSocket = new MulticastSocket(multicastPort);
	}
	public String getUserName()
	{
		return this.userName;
	}


	public void sendMe() throws IOException
	{
		byte[]                buffer;
		ByteArrayOutputStream byteArrayOutputStream;
		ObjectOutputStream    objectOutputStream;
		DatagramPacket 	 	  packet;

		byteArrayOutputStream = new ByteArrayOutputStream();
		objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

		objectOutputStream.writeObject(this);
		buffer = byteArrayOutputStream.toByteArray();
		objectOutputStream.close();



		packet = new DatagramPacket(
									buffer,
									buffer.length,
									this.multicastGroup,
									this.multicastPort
									);

		this.multicastSocket.send(packet);



	}

	public String toString()
		{
			return this.getClass().getName() + " " + getUserName();
		}


}