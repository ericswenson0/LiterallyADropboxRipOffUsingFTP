import java.util.*;
import java.net.*;
import java.io.*;

public class Goodbye extends HelloGoodbye
{
	/*Eric Swenson
	  December 6th 2016
	  Jarvis

	sends a goodbye message disconnecting from the server

	Instance Variables:

	private final long serialVersionUID = 1;

	Methods:

	public Goodbye(String userName, InetAddress multicastGroup, int multicastPort) throws IOException

	public void run()

	Modification History:

	December 6th 2016
	wrote the code to keep up and made it work even though I am way behind


*/

	private static final long serialVersionUID = 1;

	public Goodbye(String userName, InetAddress multicastGroup, int multicastPort) throws IOException
	{
		super(userName,multicastGroup,multicastPort);
	}

	public void run()
	{
		try
		{
				for(int i = 0; i<3;i++)
				{
				super.sendMe();
				Thread.sleep(3000);
				}
		}

		catch(Exception e)
		{
			throw new RuntimeException("Eric, we had an error again in Goodbye :0 " + e.getStackTrace());
		}
	}
}