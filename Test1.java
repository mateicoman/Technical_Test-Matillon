public class Test1
{
	public static int Comp(String a, String b)
	{
		int counter = 0;
		char[] charsA = a.toCharArray();
		char[] charsB = b.toCharArray();
		
		for(int index = 0; index < charsA.length; index++)
		{
			if(charsA[index] != charsB[index])
				counter++;
		}
		return counter;
	}
	
	public static void main (String args[])
	{
		String firstString = new String("D23W8MCCIZQOP9");
		String secondString = new String("D236862CEZQOPS");
		System.out.println(Comp(firstString, secondString));
	}

}
