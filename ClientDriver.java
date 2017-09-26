import javax.swing.*;

public class ClientDriver
{
	public static void main(String[]args)
	{
		try
		{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
		NavigationState navState;

		navState = ClientBeginState.getInstance();

		while(navState!=null)
		{
			navState = navState.execute();
		}
	}
}

/* at ClientSaveFileState.execute(ClientSaveFileState.java:91)*/