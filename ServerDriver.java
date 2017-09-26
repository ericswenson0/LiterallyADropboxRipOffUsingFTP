import javax.swing.*;

public class ServerDriver
{
	public static void main(String[]args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		NavigationState navState;

		navState = ServerBeginState.getInstance();

		while(navState!=null)
		{
			navState = navState.execute();
		}
	}
}