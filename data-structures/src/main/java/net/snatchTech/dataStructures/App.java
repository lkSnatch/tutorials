package net.snatchTech.dataStructures;

import java.util.ArrayDeque;

public class App {

    public static void main( String[] args ) {

        showArrayDeque();

    }

    private static void showArrayDeque() {

        ArrayDeque<String> structure = new ArrayDeque<>(6); // [null, null, null, null, null, null, null]
        // we have 7 elements in the array instead of 6.

        structure.addFirst("1First");   // [null, null, null, null, null, null, 1First]
        structure.addFirst("2First");   // [null, null, null, null, null, 2First, 1First]
        structure.addLast("3Last");     // [3Last, null, null, null, null, 2First, 1First]
        structure.addLast("4Last");     // [3Last, 4Last, null, null, null, 2First, 1First]

        structure.addLast("5Last");     // [3Last, 4Last, 5Last, null, null, 2First, 1First]
        structure.addLast("6Last");     // [3Last, 4Last, 5Last, 6Last, null, 2First, 1First] O(1)
        structure.addLast("7Last");     // [3Last, 4Last, 5Last, 6Last, 7Last, null, null, null, null, null, null, null, null, null, 2First, 1First]

        //structure.pollFirst();  // [3Last, 4Last, 5Last, 6Last, 7Last, null, null, null, null, null, null, null, null, null, null, 1First] double size / or double +2 size / System.arraycopy() O(n)

        structure.contains("4Last");
        structure.remove("4Last");  // [1First, 3Last, 5Last, 6Last, 7Last, null, null, null, null, null, null, null, null, null, null, null]
        // [3Last, 5Last, 6Last, 7Last, null, null, null, null, null, null, null, null, null, null, 2First, 1First]
        structure.remove("3Last");
        structure.addFirst("8First");
        structure.addFirst("9First");
        structure.pollFirst();
        structure.pollFirst();
        structure.pollFirst();
        structure.pollFirst();

        System.out.println(structure);

        //methodDescriptions(structure);

    }

    private static void methodDescriptions(ArrayDeque<String> structure) {

        // just add
        structure.addFirst("addFirst");
        structure.addLast("addLast");

        // repeating methods
        structure.add("add");   // addFirst
        structure.offer("offer");   // addLast
        structure.offerLast("offerLast");   // addLast
        structure.offerFirst("offerFirst"); // addFirst
        structure.push("push"); // addFirst

        // just return an element
        structure.peekFirst();
        structure.peekLast();
        structure.peek();   // peekFirst

        // like peekFirst/Last, but NoSuchElementException will be occurred if no element is found
        structure.getFirst();
        structure.getLast();

        // retrieves and removes an element
        structure.pollFirst();
        structure.pollLast();
        structure.poll();   // pollFirst

        // like pollFirst/Last, but NoSuchElementException will be occurred if no element is found
        structure.removeFirst();
        structure.removeLast();
        structure.remove(); // removeFirst
        structure.pop();    // removeFirst

        // find/remove by element
        structure.contains("contains");
        structure.remove("remove"); // removeFirstOccurrence

    }
}
