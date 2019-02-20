package application;

public class ArrayQueue<T> implements QueueInterface<T> {
    
    private T [] queue;  // circular array with 1 unused location
    private int frontIndex;
    private int backIndex;
    
    private static final int DEFAULT_INITIAL_CAPACITY = 50;
    
    public  ArrayQueue ()  {    
     this (DEFAULT_INITIAL_CAPACITY);  
    }
    
    @SuppressWarnings("unchecked")
	public  ArrayQueue (int initialCapacity)   {
        queue = (T[]) new Object[initialCapacity + 1];
        frontIndex = 0;
        backIndex = initialCapacity;
    } 
   
    @Override
	public void enqueue (T newEntry)    {
        if (isArrayFull())   // Double size of array if full
            doubleArray();        
        backIndex = (backIndex + 1) % queue.length;
        queue[backIndex] = newEntry;
    } 
    
    @Override
	public T getFront() {
        T front = null;
        if (!isEmpty ())
            front = queue [frontIndex];
        return front;
    } 
    
    @SuppressWarnings("unchecked")
	private void doubleArray(){
        T[] oldQueue = queue;
        int oldSize = oldQueue.length;
        queue= (T[])new Object[2*oldSize];

        for (int index=0; index<(oldSize-1); index++) {
            queue[index] = oldQueue[frontIndex];
            frontIndex = (frontIndex+1) % oldSize;
        } 
        frontIndex = 0;
        backIndex = oldSize - 2;
    } 
    
    @Override
	public T dequeue() {
        T front = null;
        if (!isEmpty()){
            front = queue[frontIndex];
            queue[frontIndex] = null;
            frontIndex = (frontIndex+1) % queue.length;
        } 
        return front;
    } 

    @Override
	public boolean isEmpty(){
        return frontIndex == ((backIndex+1)% queue.length);
    }
    
    private boolean isArrayFull()  {
        return frontIndex == ((backIndex+2)% queue.length);
    }

    @Override
	
    public void clear() {
		while (!isEmpty()){
			dequeue();
		}
		
	}


}
