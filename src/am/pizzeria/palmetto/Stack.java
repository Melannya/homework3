package am.pizzeria.palmetto;

public class Stack {

    private Object[] data;
    private int tos;

    public Stack(int capacity) {
        data = new Object[capacity];
        tos = 0;
    }

    public Stack() {
        this(10);
    }

    public void push(Object value) {
        if (isFull()) {
            throw new RuntimeException("Stack is full");
        }

        data[tos] = value;
        tos++;
    }

    public Object pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        tos--;
        Object value = data[tos];
        data[tos] = null;

        return value;
    }

    public Object peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        return data[tos - 1];
    }

    public boolean isEmpty() {
        return tos == 0;
    }

    public int size() {
        return tos;
    }

    public boolean isFull() {
        return tos == data.length;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < tos; i++) {
            if (i > 0) {
                result.append(", ");
            }

            result.append(data[i]);
        }

        result.append("]");
        return result.toString();
    }
}
