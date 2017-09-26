public class ServerSourceAndTargetAreSameState extends MessageState
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

						Gives the user a chance to save a file

							Instance Variables:
							Enum:
							Save
							Cancel

							private static ServerSaveFileState instance = new ClientFileNotInServerDirectoryState();

							Methods:

							public ServerSaveFileState()

								public static synchronized ServerSaveFileState getInstance()

								public NavigationState execute()

							Modification History:

							December 7th 2016
							wrote the code. Tested.

							December 10th 2016
							Implemented Server Logic State Fully

*/
	public enum StateName
		{
			OK
		}
		private static ServerSourceAndTargetAreSameState instance = new ServerSourceAndTargetAreSameState();

		public ServerSourceAndTargetAreSameState()
		{
			super(StateName.values());
		}

		public static synchronized ServerSourceAndTargetAreSameState getInstance()
		{
			return ServerSourceAndTargetAreSameState.instance;
		}

		public NavigationState execute()
		{
			String buttonText;
			NavigationState nextState;

			buttonText = super.showDialog();



			switch(StateName.valueOf(buttonText))
			{
				case OK:{nextState = ServerSaveFileState.getInstance();break;}
				default: throw new RuntimeException("Unrecognized enum value in execute of ServerSaveFileState");
			}

			return nextState;
		}


}