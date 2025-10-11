package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        int taskId = task.getId();

        if (nodeMap.containsKey(taskId)) {
            removeNode(nodeMap.get(taskId));
            nodeMap.remove(taskId);
        }

        Node newNode = new Node(task, tail, null);
        linkLast(newNode);
        nodeMap.put(taskId, newNode);
    }

    @Override
    public ArrayList<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return new ArrayList<>(history);
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = nodeMap.remove(id);
        removeNode(nodeToRemove);
        nodeMap.remove(id);
    }

    private void linkLast(Node newNode) {
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    private void removeNode(Node node) {
        if (node != null) {
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                head = node.next;
            }

            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
        }
    }

    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task, Node prev, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }
}
