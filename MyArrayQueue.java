import java.util.Arrays;

/**
 * This class provides a concrete implementation of a Circular Queue
 * implemented using an array.
 */
public class MyArrayQueue {

    /** An array to hold the items in the queue. */
    protected Object[] queue;

    /** Index of the item at the front of the queue. */
    protected int front;

    /** Number of elements currently in the queue. */
    protected int numElements;

    /** The default initial capacity of the queue. */
    protected static final int DEFAULT_CAPACITY = 10;

    /**
     * Constructor: Sets up an empty queue of the specified initial capacity.
     *
     * @param capacity the initial capacity of the queue
     */
    public MyArrayQueue(int capacity) {
        // Check if the given capacity is at least 1, otherwise throw an exception.
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be >= 1");
        }
        // Initialize the backing array with the given capacity.
        queue = new Object[capacity];
        // Set the initial front index to 0.
        front = 0;
        // Initially, there are no elements in the queue.
        numElements = 0;
    }

    /**
     * Default constructor, creates a queue with default capacity (10).
     */
    public MyArrayQueue() {
        // Delegate to the constructor with a specified capacity.
        this(DEFAULT_CAPACITY);
    }

    /**
     * Check if the queue is empty.
     *
     * @return true if the number of elements is zero, false otherwise.
     */
    public boolean isEmpty() {
        return numElements == 0;
    }

    /**
     * Check if the queue is full.
     *
     * @return true if the number of elements equals the length of the backing array.
     */
    public boolean isFull() {
        return numElements == queue.length;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the element at the front, or null if the queue is empty.
     */
    public Object peek() {
        // If the queue is empty, return null.
        if (isEmpty()) {
            return null;
        } else {
            // Otherwise, return the element at index "front".
            return queue[front];
        }
    }

    /**
     * Adds an element to the tail of the queue. If the queue is full, the method
     * doubles the size of the backing array and copies the elements such that the
     * front element is moved to index 0.
     *
     * @param theElement the element to be added to the queue.
     */
    public void enqueue(Object theElement) {
        // Step 1: Check if the queue is full.
        if (isFull()) {
            // Create a new array of double the current size.
            Object[] newQueue = new Object[queue.length * 2];

            // Step 2: Copy elements starting from the 'front' to the new array, 
            // ensuring the logical order of the queue is maintained.
            // We use modulo arithmetic to wrap around when reaching the end.
            for (int i = 0; i < numElements; i++) {
                newQueue[i] = queue[(front + i) % queue.length];
            }
    
            // Step 3: Replace the old queue with the new, larger queue.
            queue = newQueue;
    
            // Step 4: Reset the front index to 0 since we've reindexed the elements.
            front = 0;
        }
    
        // Step 5: Find the index at which to insert the new element.
        // This is calculated using the current front and number of elements.
        int rear = (front + numElements) % queue.length;
    
        // Step 6: Place the new element at the calculated rear position.
        queue[rear] = theElement;
    
        // Increase the count of elements in the queue.
        numElements++;
    }
    
    /**
     * Removes an element from the front of the queue and returns it.
     * This version does not reindex (shift) elements unless a resize is needed.
     * Instead, it simply removes the element and advances the front pointer.
     *
     * @return the removed element from the front of the queue.
     * @throws IllegalStateException if the queue is empty.
     */
    public Object dequeue() throws IllegalStateException {
        // Check if the queue is empty and throw an exception if it is.
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        // Retrieve the element at the front of the queue.
        Object removedElement = queue[front];
        
        // Set the current front position to null to assist garbage collection.
        queue[front] = null;
        
        // Move the front pointer forward in a circular manner.
        // Use modulo arithmetic to wrap around when reaching the end.
        front = (front + 1) % queue.length;
        
        // Decrement the number of elements since we have removed one.
        numElements--;
        
        // Return the removed element.
        return removedElement;
    }
    
    /**
     * Returns the queue as a String for printing. This method prints the raw
     * backing array, which may include null entries for empty slots.
     *
     * @return a string representation of the backing array.
     */
    public String toString() {
        return Arrays.toString(queue);
    }

    /**
     * Main method simply tests the queue implementation. It performs several
     * enqueue and dequeue operations and prints the raw backing array after each
     * operation.
     * 
     * @param args unused.
     */
    public static void main(String[] args) {
        // Capture the current system time (not used in further logic).
        System.currentTimeMillis();
        
        // Create a new queue with an initial capacity of 3.
        MyArrayQueue q = new MyArrayQueue(3);
        System.out.println(q.toString());
        
        // Test enqueuing several elements and print the state of the queue.
        System.out.println("\n\nENQUEUE");
        q.enqueue("a");
        System.out.println("a");
        System.out.println(q.toString());
        
        System.out.println("\n\nENQUEUE");
        q.enqueue("b");
        System.out.println("b");
        System.out.println(q.toString());
        
        System.out.println("\n\nENQUEUE");
        q.enqueue("c");
        System.out.println("c");
        System.out.println(q.toString());
        
        // Test dequeue: this should remove the element at the front ("a").
        System.out.println("\n\nDEQUEUE");
        System.out.println(q.dequeue());
        System.out.println(q.toString());
        
        // Enqueue "d" which should take the next available slot (circular behavior).
        System.out.println("\n\nENQUEUE");
        q.enqueue("d");
        System.out.println("d");
        System.out.println(q.toString());
        
        // Enqueue "e" - since the queue might be full, it will double in size,
        // reindexing the existing elements so that the front is at index 0.
        System.out.println("\n\nENQUEUE");
        q.enqueue("e");
        System.out.println("e");
        System.out.println(q.toString());
        
        System.out.println("\n\nENQUEUE");
        q.enqueue("f");
        System.out.println("f");
        System.out.println(q.toString());
        
        System.out.println("\n\nENQUEUE");
        q.enqueue("g");
        System.out.println("g");
        System.out.println(q.toString());
        
        // Test dequeue: remove the element at the front (which is now "b").
        System.out.println("\n\nDEQUEUE");
        System.out.println(q.dequeue());
        System.out.println(q.toString());
        
        // Test another dequeue: this should remove the next element ("c").
        System.out.println("\n\nDEQUEUE");
        System.out.println(q.dequeue());
        System.out.println(q.toString());
        
        // Enqueue "h": this should use the circular available space without immediate reindexing.
        System.out.println("\n\nENQUEUE");
        q.enqueue("h");
        System.out.println("h");
        System.out.println(q.toString());
        
        // Now, empty the queue by dequeuing until it is empty.
        while (!q.isEmpty()) {
            System.out.println("\n\nDEQUEUE");
            System.out.println(q.dequeue());
            System.out.println(q.toString());
        }
        
        // Try to dequeue once more, which should trigger an exception since the queue is empty.
        try {
            System.out.println("\n\nDEQUEUE");
            System.out.println(q.dequeue());
            System.out.println(q.toString());
        } catch (IllegalStateException e) {
            // Print the exception message.
            System.out.println(e.getMessage());
            System.out.println(q.toString());
        }
    }
}
