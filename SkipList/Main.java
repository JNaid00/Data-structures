//Jesse Naidoo
//u21433102
public class Main
{

	public static void firstKey(SkipList<Integer> skiplist)
	{
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else
			System.out.println("First key : " + skiplist.first());
	}

	public static void lastKey(SkipList<Integer> skiplist)
	{
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else
			System.out.println("Last key : " + skiplist.last());
	}

	public static void searchKey(SkipList<Integer> skiplist, Integer key)
	{
		if (skiplist.isEmpty())
			System.out.println("List is empty");
		else
		{
			Integer result = skiplist.search(key);
			if (result != null)
				System.out.println("Found key " + result);
			else
				System.out.println("Key " + key + " not found");	
		}
	}

	public static void printList(SkipList<Integer> skiplist) 
	{ 
    		System.out.println();
		System.out.println("Skip List Content:"); 
    		for (int i = 0; i < skiplist.maxLevel; i++) 
    		{ 
        		SkipListNode<Integer> node = skiplist.root[i]; 
        		System.out.print("Level " + i + ": "); 
        		while (node != null) 
        		{ 
            			System.out.print(node.key + " "); 
            			node = node.next[i]; 
        		} 
        		System.out.println(); 
    		}
		System.out.println();
	} 

	public static void main(String[] args)
	{
		//Practical 1	

		SkipList<Integer> skiplist = new SkipList<Integer>(4);
		// firstKey(skiplist);
		// lastKey(skiplist);
		System.out.println(skiplist.search(5));
		System.out.println(skiplist.first());
		System.out.println(skiplist.last());
		System.out.println(skiplist.isEmpty());
		//skiplist.insert(100);
		System.out.println(skiplist.isEmpty());

		skiplist.insert(8);
		skiplist.insert(5);

		skiplist.insert(12);

		System.out.println(skiplist.first());
		System.out.println(skiplist.last());

		printList(skiplist);
		
		System.out.println(skiplist.search(10));

		skiplist.insert(10);
		skiplist.insert(10);

		printList(skiplist);

		//searchKey(skiplist, 10);
		System.out.println(skiplist.search(10));
        //searchKey(skiplist, 5);
		System.out.println(skiplist.search(5));
		skiplist.insert(1000);
		//skiplist.insert(150);
		
		lastKey(skiplist);
		skiplist.insert(-100);
		printList(skiplist);
		firstKey(skiplist);
		System.out.println(skiplist.search(-123));
		System.out.println(skiplist.last());

		/* Expected Output:
		First key : 5
		Last key : 12

		Skip List Content:
		Level 0: 5 8 12
		Level 1: 5 12
		Level 2: 5
		Level 3:

		Key 10 not found

		Skip List Content:
		Level 0: 5 8 10 12
		Level 1: 5 12
		Level 2: 5
		Level 3:

		Found key 10
		*/


	}
}
