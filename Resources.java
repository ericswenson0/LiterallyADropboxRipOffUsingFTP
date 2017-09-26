import java.io.*;
import java.util.*;
import java.net.*;

public class Resources
{

	/*		  Eric Swenson
				  December 6th 2016
				  Jarvis

				Resource instance that gets the server properties we need

				Instance Variables:


					private static Resources  instance;
					private static File propertiesFile = new File("RemoteFileServer.properties");
					private int               			 unicastPortNumber;
					private ResourceBundle    			 resourceBundle;
					private int 						 multicastPortNumber;
					private InetAddress					 multicastGroup;
					private String 						 userName;

	private File						 clientServerDirectory;


				Methods:

				single constructor no param

				public MessageServer(SharedFiles sharedFiles, int port)

				Modification History:

				I fell behind documenting correctly, but as a class we started this in late november

				December 2nd 2016

				Have this implemented how I need it to be to work in my system.
*/
	private static Resources  instance;
	private static File propertiesFile = new File("RemoteFileServer.properties");
	private int               			 unicastPortNumber;
	private ResourceBundle    			 resourceBundle;
	private int 						 multicastPortNumber;
	private InetAddress					 multicastGroup;
	private String 						 userName;

	private File						 clientServerDirectory;

	private Resources()
	{
		Locale locale;

		Properties properties;

		properties = new Properties();

		try
		{
			properties.load(new FileReader(Resources.propertiesFile));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		locale = new Locale(get("Locale.Language",properties).trim(),get("Locale.Country",properties).trim());

		this.unicastPortNumber = Integer.parseInt(get("Unicast.PortNumber", properties));

		this.resourceBundle = resourceBundle.getBundle("RemoteFileServer",locale);

		this.multicastPortNumber = Integer.parseInt(get("Multicast.PortNumber", properties));

		this.userName = get("UserName",properties);



		this.clientServerDirectory = new File(System.getProperty("User.dir"),"ServerDirectory");



		try
		{
		this.multicastGroup = InetAddress.getByName(get("Multicast.Group",properties));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	public File getClientServerDirectory()
	{
		return this.clientServerDirectory;
	}

	public int getUnicastPortNumber()
	{
		return this.unicastPortNumber;
	}



	public void setUnicastPortNumber(int port)
	{
		this.unicastPortNumber = port;
	}

	public static synchronized Resources getInstance()
	{
		if(instance ==null)

		instance = new Resources();

		return Resources.instance;
	}

	public InetAddress getMulticastGroup()
	{
		return this.multicastGroup;
	}

	public int getMulticastPortNumber()
	{
		return this.multicastPortNumber;
	}

	public String getUsername()
	{
		return this.userName;
	}

	//public File getSubDirectory()
	//{
	//	return this.newSubDirectory;
	//}

	private String get(String key, Properties properties)
	{
		String result;

		result = "";

		result = properties.getProperty(key);

		return result;
	}

	public ResourceBundle getResourceBundle()
	{
		return this.resourceBundle;
	}

	public static void main(String[]args)
	{

	}
}