package manager;

import tasks.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int LIMIT_HISTORY_TASKS = 10;
    private final List<Task> historyTasks = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (historyTasks.size() >= LIMIT_HISTORY_TASKS) {
            historyTasks.removeFirst();
        }
        historyTasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }
}