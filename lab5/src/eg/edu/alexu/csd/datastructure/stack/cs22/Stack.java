package eg.edu.alexu.csd.datastructure.stack.cs22;

import java.util.Scanner;

public class Stack implements IStack {
     private class Node{
         Object data;
         Node next;
     }
     private int size;
     private Node top;
     public Stack() {
         size = 0;
         top = null;
    }

    @Override
    /**
     * Removes the element at the top of stack and returns that element.
     *
     * @return top of stack element, or through exception if empty
     */
    public Object pop() {
        if (top == null) {
            System.out.print("\nStack Underflow");
            return null;
        }
        else {
            Node temp = top;
            top = top.next;
            size--;
            return temp.data;
        }

    }

    @Override
    /**
     * Get the element at the top of stack without removing it from stack.
     *
     * @return top of stack element, or through exception if empty
     */
    public Object peek() {
        if (!isEmpty()) {
            return top.data;
        }
        else {
            System.out.println("Stack is empty");
            return null;
        }
    }

    @Override
    /**
     * Pushes an item onto the top of this stack.
     *
     * @param element
     * to insert
     */
    public void push(Object element) {
         Node temp = new Node();
         temp.data = element;
         temp.next = top;
         top = temp;
         size++;
    }

    @Override
    /**
     * Tests if this stack is empty
     *
     * @return true if stack empty
     */
    public boolean isEmpty() {
        if (top == null) return true;
        return false;
    }

    @Override
    /**
     * Returns the number of elements in the stack.
     *
     * @return number of elements in the stack
     */
    public int size() {
        return size;
    }

    public void UI() {
        Stack s = new Stack();
        boolean flag2 = true;
        while (flag2){
            System.out.println("Choose an action\n" +
                    "1: Push\n" +
                    "2: Pop\n" +
                    "3: Peek\n" +
                    "4: Get size\n" +
                    "5: Check if empty\n" +
                    "6: Exit");
            Scanner userChoice = new Scanner(System.in);
            boolean flag1 = true;
            String choice = "";
            while (flag1){
                choice = userChoice.nextLine();
                if (isNumeric(choice) && Integer.parseInt(choice) > 0 && Integer.parseInt(choice) < 7){
                    flag1 = false;
                }
                else System.out.println("Enter a valid input!");
            }
            switch (choice){
                case "1":{
                    System.out.println("Enter an object to insert in your stack");
                    Scanner StackInput = new Scanner(System.in);
                    s.push(StackInput.nextLine());
                    break;
                }
                case "2":{
                    System.out.println(s.pop());
                    break;
                }
                case "3":{
                    System.out.println(s.peek());
                    break;
                }
                case "4":{
                    System.out.println(s.size());
                    break;
                }
                case "5":{
                    if (s.isEmpty()){
                        System.out.println("The stack is empty");
                    }
                    else {
                        System.out.println("The stack is not empty");
                    }
                    break;
                }
                case "6":{
                    flag2 = false;
                    break;
                }
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
