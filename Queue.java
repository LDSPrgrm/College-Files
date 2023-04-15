public class Queue {
    static int length = 3;
    static char[] queue = new char[length];
    static int front = -1;
    static int rear = -1;

    public static void main(String[] args) {

        enqueue('B');
        display();
        enqueue('A');
        display();
        enqueue('C');
        display();

        System.out.println();
        peek();
        dequeue();
        display();
        peek();
        dequeue();
        display();
        peek();
        dequeue();
        display();
    }

    private static void enqueue(char data) {
        if (isFull())
            System.out.print("\nQueue is full!");
        else if (front == -1 && rear == -1) {
            front = rear = 0;
            queue[rear] = data;
        }
        else {
            rear++;
            queue[rear] = data;
        }
    }

    private static void dequeue() { 
        if (isEmpty())
            System.out.print("\nQueue is empty!");
        else if (front == rear)
            front = rear = -1;
        else
            front++;
    }

    private static boolean isFull() {
        if(rear == length - 1)
           return true;
        else
           return false;
     }

    private static boolean isEmpty() {
        if(front == -1 && rear == -1) 
           return true;
        else
           return false;
    }

     private static void display() {
        if (isEmpty())
            System.out.print("\nQueue is empty!");
        else {
            System.out.print("\nQueue: ");
            for (int i = front; i <= rear; i++) {
                System.out.print(queue[i] + " ");
            }
        }
    }

    private static void peek() {
        System.out.print("\nThe element in front is: " + queue[front]);
    }
}