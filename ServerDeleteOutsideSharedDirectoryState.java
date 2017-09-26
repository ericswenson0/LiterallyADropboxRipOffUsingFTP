public class ServerDeleteOutsideSharedDirectoryState extends MessageState
{
	/*	  Eric Swenson
				  December 9th 2016
				  Jarvis

				Warns and stops user from deleting outside of the shared directory

				Instance Variables:
				Enum:
				OK

				private static ClientFileNotInServerDirectoryState instance = new ClientFileNotInServerDirectoryState();

				Methods:

				public ClientFileNotInServerDirectoryState()

					public static synchronized ClientFileNotInServerDirectoryState getInstance()

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
		private static ServerDeleteOutsideSharedDirectoryState instance = new ServerDeleteOutsideSharedDirectoryState();

		public ServerDeleteOutsideSharedDirectoryState()
		{
			super(StateName.values());
		}

		public static synchronized ServerDeleteOutsideSharedDirectoryState getInstance()
		{
			return ServerDeleteOutsideSharedDirectoryState.instance;
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