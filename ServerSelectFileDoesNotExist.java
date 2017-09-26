public class ServerSelectFileDoesNotExist extends MessageState
{
	/*	  Eric Swenson
							  December 9th 2016
							  Jarvis

							Error message that the selected file does not exist

							Instance Variables:
							Enum:
							OK

							private static ServerSelectFileDoesNotExist instance = new ClientFileNotInServerDirectoryState();

							Methods:

							public ServerSaveFileState()

								public static synchronized ServerSelectFileDoesNotExist getInstance()

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
		private static ServerSelectFileDoesNotExist instance = new ServerSelectFileDoesNotExist();

		public ServerSelectFileDoesNotExist()
		{
			super(StateName.values());
		}

		public static synchronized ServerSelectFileDoesNotExist getInstance()
		{
			return ServerSelectFileDoesNotExist.instance;
		}

		public NavigationState execute()
		{
			String buttonText;
			NavigationState nextState;

			buttonText = super.showDialog();


			switch(StateName.valueOf(buttonText))
			{
				case OK:{nextState = ServerSelectFileState.getInstance();break;}
				default: throw new RuntimeException("Unrecognized enum value in execute of ServerSaveFileState");
			}

			return nextState;
		}


}