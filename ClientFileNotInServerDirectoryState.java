public class ClientFileNotInServerDirectoryState extends MessageState
{
	/*	  Eric Swenson
			  December 9th 2016
			  Jarvis

			Client file not in server error message implementation

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
			Implemented the file not in the directory logic


*/

	public enum StateName
		{
			OK
		}
		private static ClientFileNotInServerDirectoryState instance = new ClientFileNotInServerDirectoryState();

		public ClientFileNotInServerDirectoryState()
		{
			super(StateName.values());
		}

		public static synchronized ClientFileNotInServerDirectoryState getInstance()
		{
			return ClientFileNotInServerDirectoryState.instance;
		}

		public NavigationState execute()
		{
			String buttonText;
			NavigationState nextState;

			buttonText = super.showDialog();



			switch(StateName.valueOf(buttonText))
			{
				case OK:
				{
					nextState = ClientSelectFileState.getInstance();
					break;
				}
				default: throw new RuntimeException("Unrecognized enum value in execute of ServerSaveFileState");
			}

			return nextState;
		}


}