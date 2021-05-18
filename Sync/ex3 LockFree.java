
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class LockFreeStack<T> {
	private AtomicReference<Node<T>> stack;

public LockFreeStack() {
		stack = new AtomicReference<Node<T>>();
	}

	public void push(T item) {
		Node<T> oldHead = null, newHead = new Node<T>(item);
		do {
			oldHead = stack.get();
			newHead.next = oldHead;

		} while (!stack.compareAndSet(oldHead, newHead));
	}

	public T pop() {
		Node<T> oldHead = null, newHead = null;
		do {
			oldHead = stack.get();

			if (oldHead == null) {
				return null;
			}

			newHead = oldHead.next;
		} while (!stack.compareAndSet(oldHead, newHead));

		return oldHead.value;
	}
}
