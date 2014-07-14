import java.util.ArrayList;
import java.util.Iterator;


public class PrimeIterator implements Iterator<Integer> {

	private Iterator<Integer> iterate;

	public PrimeIterator(int max){
		ArrayList<Integer> thePrimes = new ArrayList<Integer>();
		Boolean [] primesArray = new Boolean[max+1];

		for(int i = 2; i < primesArray.length; i++){
			primesArray[i] = true;
		}

		for(int i = 2; i*i <= max; i++)
		{
			for(int j = i; j*i <= max; j++)
			{
				primesArray[i*j] = false;
			}
		}       

		for(int i = 2; i <= max; i++){
			if(primesArray[i])
				thePrimes.add(i);
		}

		iterate = thePrimes.iterator();

	}

	@Override
	public boolean hasNext() {
		return iterate.hasNext();
	}

	@Override
	public Integer next() {
		return iterate.next();
	}

	@Override
	public void remove() {
		iterate.remove();
	}
}
