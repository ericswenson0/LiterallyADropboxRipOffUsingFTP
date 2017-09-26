import java.util.*;
public class SimpleList<T>
{
/*

	Eric Swenson
	11/1/16
	Dr. Jarvis

	this class, SimpleList, does various things to a generic arrayList object, taking, placing, and clearing elements and so on

	Instance Variables:


	Methods:
	public  SimpleList(Position putPosition, Position takePosition)
	public void clear()
	public Position getPutPosition()
	public boolean isEmpty()
	public void put(T value)
	public void put(T[] valueArray)
	public int size()
	public T take()
	public ArrayList<T> takeAll()
	public static void main(String[]args)
	private static void print(String[]array)


	Modification History:

	November 1st 2016:

	Added Methods:
		public  SimpleList(Position putPosition, Position takePosition)
		public void clear()
		public Position getPutPosition()
		public boolean isEmpty()
		public void put(T value)
		public void put(T[] valueArray)
		public int size()
		public T take()
		public ArrayList<T> takeAll()
		public static void main(String[]args)
		private static void print(String[]array)

	Have massive errors throughout. Compiles fine.


*/
	private ArrayList <T>  list;
	private Position putPosition;
	private Position takePosition;

	public  SimpleList(Position putPosition, Position takePosition)
	{
		if(putPosition == null)
			throw new IllegalArgumentException("putPosition in simpleList is nu");

		if(takePosition == null)
			throw new IllegalArgumentException("takePosition in Simplelist was null");

		this.putPosition  = putPosition;
		this.takePosition = takePosition;

		this.list = new ArrayList<T>();

	}
	public void clear(){this.list.clear();}

	public Position getPutPosition(){return this.putPosition;}

	public Position getTakePosition(){return this.takePosition;}

	public boolean isEmpty(){return this.list.size() == 0;}

	public void put(T value)
	{
		if(value == null)
		throw new IllegalArgumentException("you passed a null value to put in SimpleList");
			list.add(getPutPosition().get(list.size()+1),value);
	}

	public void put(T[] valueArray)
	{
		if(valueArray == null)
		throw new IllegalArgumentException("you passed a null value to put in SimpleList");

				for(int i = 0; i<valueArray.length;i++)
				{
					this.put(valueArray[i]);
				}
	}

	public int size(){return this.list.size();}

	public T take()
	{
		return this.list.remove(this.getTakePosition().get(this.size()));
	}

	public ArrayList<T> takeAll()
	{
		ArrayList<T> result;

		//ArrayList<T> hold;

		int hold;

		hold = this.list.size();

		result = new ArrayList<T>();

		while(hold!=result.size())
		{
			result.add(this.take());
		}


		list.clear();
		return result;
	}




}