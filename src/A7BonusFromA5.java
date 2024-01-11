import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

class Customer implements Comparable<Customer> { // create a customer
    private static int counter = 1;
    private int id;
    private int checkoutTime;

    public Customer() {
        this.id = counter++;
        Random random = new Random();
        this.checkoutTime = random.nextInt(10) + 1; // random transaction time between 1 to 10 seconds
    }

    public int getId() { // all our interfaces for the virtual store
        return id;
    }

    public int getCheckoutTime() {
        return checkoutTime;
    }

    @Override
    public int compareTo(Customer other) {
        return Integer.compare(this.checkoutTime, other.checkoutTime);
    }

    @Override
    public String toString() {
        return "Customer " + id;
    }
}

public class A7BonusFromA5{
    public static void main(String[] args) {
        int numCheckoutLines = 5;
        ArrayList<Queue<Customer>> checkoutLines = new ArrayList<>(); // array to hold our queues
        for (int i = 0; i < numCheckoutLines; i++) {
            checkoutLines.add(new PriorityQueue<>());
        }

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer();  // each choosing the shortest line
            int shortestLineIndex = findShortestLine(checkoutLines);
            checkoutLines.get(shortestLineIndex).offer(customer);
            System.out.println("Customer " + customer.getId() + " joined Queue " + (shortestLineIndex + 1));
        }

        while (!allQueuesEmpty(checkoutLines)) { //Iterate through each checkout line
            for (int i = 0; i < numCheckoutLines; i++) {
                Queue<Customer> queue = checkoutLines.get(i);
                if (!queue.isEmpty()) {
                    Customer currentCustomer = queue.poll();
                    System.out.println("Processing " + currentCustomer + " in Queue " + (i + 1));
                    currentCustomer.getCheckoutTime();
                }
            }
        }
    }

    private static int findShortestLine(ArrayList<Queue<Customer>> checkoutLines) { // find index of the shortest queue based on queue sizes
        int shortestLineIndex = 0;
        int shortestLineSize = checkoutLines.get(0).size();

        for (int i = 1; i < checkoutLines.size(); i++) {
            if (checkoutLines.get(i).size() < shortestLineSize) {
                shortestLineSize = checkoutLines.get(i).size();
                shortestLineIndex = i;
            }
        }
        return shortestLineIndex;
    }

    private static boolean allQueuesEmpty(ArrayList<Queue<Customer>> checkoutLines) { // Check if all queues are empty
        for (Queue<Customer> queue : checkoutLines) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
