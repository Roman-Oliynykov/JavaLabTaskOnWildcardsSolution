import java.util.Arrays;
import java.util.EmptyStackException;


public class StackDemo {

	public static void main(String[] args) throws EmptyStackException {
		
		MyStack<Number> stN = new MyStack<>();
		MyStack<Integer> stI = new MyStack<>();
		MyStack<Double> stD = new MyStack<>();		
		
		for(int i = 0; i < 3; i++) {
			stI.push( new Integer( i ) );
			stD.push( new Double( i + 3 ) );
		}
		
		System.out.println( "Integer stack: " + stI.toString() );
		System.out.println( "Double stack: " + stD.toString() );		
		
		stN.addAll( stI );
		stD.moveElementsTo( stN );
		
		System.out.println( "Number stack: " + stN.toString() );
	}

}

class MyStack <E> {
	protected final int INIT_CAPACITY = 128;
	protected int capacity;
	protected E[] storage;
	protected int index = 0;
	
	@SuppressWarnings("unchecked")
	public MyStack() {
		storage = (E[]) new Object [ capacity = INIT_CAPACITY ]; 
	} 
	public MyStack(int capacity) { storage = (E[]) new Object [ this.capacity = capacity ]; }
	public MyStack(MyStack<? extends E> stack) {
		while( !stack.empty() )
			push( stack.pop() );
	}
	
	public boolean push(E elem) {
		checkCapacity();
		storage[ index++ ] = elem;

		return true; 
	}
	
	public E pop() throws EmptyStackException {
		if ( empty() ) throw new EmptyStackException();
		
		E elem = storage[ --index ];
		storage[ index ] = null;
		
		return elem;
	}
	
	public boolean empty() { return index == 0; }
	
	public void addAll(MyStack<? extends E> stack) {

		while( !stack.empty() )
			push( stack.pop() );
		
	}
	
	public void moveElementsTo(MyStack<? super E> stack) {
		while( !empty() )
			stack.push( pop() );
	}	
	
	protected void checkCapacity() {
		if ( index < capacity ) return; 
		if ( capacity > (capacity << 1) ) throw new ArithmeticException("Integer overflow on attempt to extend MyStack<>");

		storage = Arrays.copyOf( storage, capacity <<= 1 ); // capacity = capacity * 2;
	}
	
	@Override
	protected MyStack<E> clone() {
		try {
			@SuppressWarnings("unchecked")
			MyStack<E> result = (MyStack<E>) super.clone();
			result.capacity = this.capacity;
			result.index = this.index;
			result.storage = this.storage.clone();

			return result;
			
		} catch ( CloneNotSupportedException e ) {
			throw new AssertionError("Assertion failed: super.clone() is not supported", e);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if ( other == null ) return false;
		if ( other.getClass() != this.getClass() ) return false;
		
		@SuppressWarnings("unchecked")
		MyStack<E> otherStack = (MyStack<E>)other;
		
		if ( otherStack.index != this.index )  return false;
		for(int i = 0; i < index; i++)
			if ( otherStack.storage[ i ] != this.storage[ i ] )  return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashVal = 42;
		
		for(int i = 0; i < index; i++)
			hashVal = 3 * hashVal + 5 * storage[ i ].hashCode() + 17; 
		
		return hashVal;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[ ");
		if ( index > 0 ) {
			for(int i = 0; i < index - 1; i++)
				result.append( storage[ i ].toString() + ", ");
			result.append( storage[ index - 1 ].toString() + " ");
		}
		result.append("]");
		
		return result.toString();
	}

	
}
