//Jesse Naidoo
//u21433102
import java.util.Random;

@SuppressWarnings("unchecked")
public class SkipList<T extends Comparable<? super T>>
{

	public int maxLevel;
	public SkipListNode<T>[] root;
	private int[] powers;
	private Random rd = new Random();

	SkipList(int i)
	{
		maxLevel = i;
		root = new SkipListNode[maxLevel];
		powers = new int[maxLevel];
		for (int j = 0; j < i; j++)
			root[j] = null;
		choosePowers();
		rd.setSeed(1230456789);
	}

	SkipList()
	{
		this(4);
	}

	public void choosePowers()
	{
		powers[maxLevel-1] = (2 << (maxLevel-1)) - 1;
		for (int i = maxLevel - 2, j = 0; i >= 0; i--, j++)
			powers[i] = powers[i+1] - (2 << j);
	}

	public int chooseLevel()
	{
		int i, r = Math.abs(rd.nextInt()) % powers[maxLevel-1] + 1;
		for (i = 1; i < maxLevel; i++)
		{
			if(r < powers[i])
				return i-1;
		}
		return i-1;
	}


	public boolean isEmpty()
	{
		return this.root[0] == null;
	}


	public void insert(T key)
	{
		SkipListNode<T>[] curr = new SkipListNode[maxLevel];
		SkipListNode<T>[] prev = new SkipListNode[maxLevel];
		SkipListNode<T> newNode;
		int level,i;

		curr[maxLevel - 1] = root[maxLevel - 1];
		prev[maxLevel - 1] = null;
		for (level = maxLevel - 1; level >=0; level--) {
			while (curr[level] != null && curr[level].key.compareTo(key) < 0) {
				prev[level] = curr[level];
				curr[level] = curr[level].next[level];
			}
			if (curr[level] != null && curr[level].key.equals(key))
				return;
			if (level > 0){
				if (prev[level] == null){
					curr[level - 1] = root[level - 1];
					prev[level - 1] = null;
				}
				else{
					curr[level - 1] = prev[level].next[level - 1];
					prev[level - 1] = prev[level];
				}
			}
		}

		level = chooseLevel();
		newNode = new SkipListNode<T>(key, level + 1);
		for (i = 0; i <= level; i++) {
			newNode.next[i] = curr[i];
			if (prev[i] == null){
				root[i] = newNode;
			}
			else prev[i].next[i] = newNode;
		}
	}	
	

	public T first()
	{
		if (isEmpty() == true)
		return null;
		return root[0].key;
	}

	public T last()
	{
		if (isEmpty() == true)
		return null;

		int level = maxLevel - 1;
		SkipListNode<T> node;
		node = root[level];
		while (level >= 0) {
			if (node != null){
				if (node.next[0] == null)
				return node.key;

				while (node.next[level] == null && level > 0)
					level--;

				if (node.next[level] != null)
				node = node.next[level];
			}
			else
				node = root[--level];
		}

		return node.key;
	}	

	public T search(T key)
	{
		int level;
		SkipListNode<T> prev,curr;
		if (isEmpty() == true)
		return null;
		for (level = maxLevel -1; level >= 0 && root[level] == null; level--);


		prev = curr = root[level];

		while (true) {
			if (key.equals(curr.key)){
				return curr.key;
			}
			else if (key.compareTo(curr.key) < 0) {
				if (level == 0)
					return null;
				else if (curr == root[level])
					curr = root[--level];
				else curr = prev.next[--level];
			}
			else{
				prev = curr;
				if (curr.next[level] != null)
					curr = curr.next[level];
				else{
					for (level--; level>=0 && curr.next[level] == null;level--);
					if (level >= 0)
						curr = curr.next[level];
					else return null;
				}
			}

		}
	}
}