import java.util.*;

public class A7HeapsP1 {
    public static class MinHeap<E extends Comparable<E>> { // inner class to create heap
        private ArrayList<E> list = new ArrayList<>();

        public MinHeap() { // start constructor
        }
        		
        public MinHeap(E[] objects) { // builds the heap
            for (E object : objects) {
                add(object);
            }
        }

        public void add(E newObject) { // adds object to heap
            list.add(newObject);
            int currentIndex = list.size() - 1;

            while (currentIndex > 0) { // maintains min heap after adding new elements 
                int parentIndex = (currentIndex - 1) / 2;
                if (list.get(currentIndex).compareTo(list.get(parentIndex)) < 0) {
                    E temp = list.get(currentIndex);
                    list.set(currentIndex, list.get(parentIndex));
                    list.set(parentIndex, temp);
                } else {
                    break;  // break to convert into heap
                }

                currentIndex = parentIndex; // same
            }
        }

        public E remove() {  // remove smallest element if not empty
            if (list.isEmpty()) {
                return null;
            }

            E removedObject = list.get(0);
            list.set(0, list.get(list.size() - 1)); // remove first element from heap
            list.remove(list.size() - 1);

            int currentIndex = 0;
            while (currentIndex < list.size()) {
                int leftChildIndex = 2 * currentIndex + 1;
                int rightChildIndex = 2 * currentIndex + 2;

                if (leftChildIndex >= list.size()) {
                    break;
                }
                int minIndex = leftChildIndex;
                if (rightChildIndex < list.size()) {
                    if (list.get(minIndex).compareTo(list.get(rightChildIndex)) > 0) { //find the min n in the nodes
                        minIndex = rightChildIndex;
                    }
                }

                if (list.get(currentIndex).compareTo(list.get(minIndex)) > 0) { // swap the values to order them
                    E temp = list.get(currentIndex);
                    list.set(currentIndex, list.get(minIndex));
                    list.set(minIndex, temp);
                    currentIndex = minIndex;
                } else {
                    break;
                }
            }

            return removedObject;
        }

        public int getSize() { // get size of heap 
            return list.size();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // main execution 
        MinHeap<Integer> minHeap = new MinHeap<>();

        System.out.println("Enter 5 numbers:"); // main input
        for (int i = 0; i < 5; i++) {
            int number = scanner.nextInt();
            minHeap.add(number);
        }

        System.out.println("Sorted numbers (in ascending order) removed from the min-heap:"); // main output
        while (minHeap.getSize() > 0) {
            System.out.println(minHeap.remove());
        }
    }
}
