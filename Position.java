public enum Position
{
/*
	Eric Swenson
	October 31st - Halloween

				~~~~Enums are Spooky~~~

				Enum position class

	Instance Variables:
	Enums
	FIRST
	LAST
	RANDOM


	Methods:

	abstract public int get(int size);


	Modification History:

	October 31st, 2016
	Began my journey creating the entire enum class, then tested

	Works

*/
	FIRST
	{
		@Override
		public int get(int size)
		{
			checkParameter("FIRST", size);

			return 0;

		}

	},
	LAST
	{
		@Override
		public int get(int size)
		{
			checkParameter("LAST", size);

			return size-1;
		}

	},
	RANDOM
	{
		@Override
		public int get(int size)
		{
			checkParameter("RANDOM", size);

			return (int)(Math.random()*(Math.ceil(size)-Math.floor(1)+1));

		}

	};

	abstract public int get(int size);


	private static void checkParameter(String enumConstant, int size)//this does the error checking for the enum functions
	{
		if(enumConstant == null) throw new IllegalArgumentException("your enumConstant is null in checkParameter in Position");
		if(size<1)
			throw new IllegalArgumentException("Position.get() calls for int >= 1 you passed " + size);
	}




}