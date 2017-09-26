import java.net.*;
import java.io.*;


public class ClientFileUnavailableErrorState extends MessageState
{
		/*	  Eric Swenson
				  December 9th 2016
				  Jarvis

				Client File unvailable error state

				Instance Variables:

				Methods:

				private ClientBeginState()

				public void run()

				public static synchronized ClientBeginState getInstance()

				public NavigationState execute()

				Modification History:

				December 7th 2016
				wrote the code. Tested.

				December 10th 2016
				Finished


*/
	public enum StateName
	{
		OK;
	}

	private static ClientFileUnavailableErrorState instance = new ClientFileUnavailableErrorState();

	private ClientFileUnavailableErrorState()
	{
			super(StateName.values());
	}

	public static synchronized ClientFileUnavailableErrorState getInstance()
	{
		return ClientFileUnavailableErrorState.instance;
	}

	public NavigationState execute()
	{
		String buttonText;
		NavigationState nextState;

		nextState = null;

		buttonText = super.showDialog();

		switch(StateName.valueOf(buttonText))
		{
			case OK:
			{
				nextState = ClientGetOrQuitState.getInstance();
				break;
			}

			default: throw new RuntimeException("Unrecognized enum value in execute of ClientFileUnavailableErrorState");

		}

			return nextState;
	}



}