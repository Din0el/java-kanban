package manager;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistoryManager();

    private int nextID = 0;

    public int getNextID() {
        return nextID++;
    }

    @Override
    public Task addTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic);
        return subtask;
    }

    @Override
    public Task updateTask(Task task) { //как ты просил просто обновляет
        Integer taskID = task.getId();
        tasks.put(taskID, task);
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) { //как ты просил просто обновляет
        Integer epicID = epic.getId();
        epics.put(epicID, epic);
        updateEpicStatus(epic);
        return epic;
    }

    /*@Override                     //   || ВАРИАНТ
    public Task updateTask(Task task) { //В случае если тебя не устроит , я передал прошлый вариант под put
        Integer taskID = task.getId();
        if (taskID == null) {
            return null;
        }
        tasks.put(taskID, task);
        return task;
    }

    @Override                         //   || ВАРИАНТ
    public Epic updateEpic(Epic epic) { //В случае если тебя не устроит , я передал прошлый вариант под put
        Integer epicID = epic.getId();
        if (epicID == null) {
            return null;
        }
        Epic oldEpic = epics.get(epicID);
        ArrayList<Subtask> oldEpicSubtaskList = oldEpic.getSubtaskList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : oldEpicSubtaskList) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.put(epicID, epic);
        ArrayList<Subtask> newEpicSubtaskList = epic.getSubtaskList();
        if (!newEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : newEpicSubtaskList) {
                subtasks.put(subtask.getId(), subtask);
            }
        }
        updateEpicStatus(epic);
        return epic;
    }

    @Override                                   //   || ВАРИАНТ
    public Subtask updateSubtask(Subtask subtask) { //В случае если тебя не устроит , я передал прошлый вариант под put
        Integer subtaskID = subtask.getId();
        if (subtaskID == null) {
            return null;
        }
        subtasks.put(subtaskID, subtask);
        int epicID = subtask.getEpicID();
        Epic epic = epics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtaskList();
        subtaskList.remove(subtask);
        subtaskList.add(subtask);
        epic.setSubtaskList(subtaskList);
        updateEpicStatus(epic);
        return subtask;
    }*/


    @Override
    public Subtask updateSubtask(Subtask subtask) { //как ты просил просто обновляет
        Integer subtaskID = subtask.getId();
        subtasks.put(subtaskID, subtask);
        int epicID = subtask.getEpicID();
        Epic epic = epics.get(epicID);
        updateEpicStatus(epic);
        return subtask;
    }

    @Override
    public Task getTaskByID(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtaskList();
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public Task deleteTaskByID(int id) {
        return tasks.remove(id);
    }

    @Override
    public Epic deleteEpicByID(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            ArrayList<Subtask> epicSubtasks = epic.getSubtaskList();
            for (Subtask subtask : epicSubtasks) {
                subtasks.remove(subtask.getId());
            }
            return epics.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public Subtask deleteSubtaskByID(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            int epicID = subtask.getEpicID();
            Subtask deletedSubtask = subtasks.remove(id);
            Epic epic = epics.get(epicID);
            ArrayList<Subtask> subtaskList = epic.getSubtaskList();
            subtaskList.remove(subtask);
            epic.setSubtaskList(subtaskList);
            updateEpicStatus(epic);
            return deletedSubtask;
        } else {
            return null;
        }
    }

    public void updateEpicStatus(Epic epic) {
        int allIsDoneCount = 0;
        int allIsInNewCount = 0;
        ArrayList<Subtask> list = epic.getSubtaskList();

        for (Subtask subtask : list) {
            if (subtask.getStatus() == Status.DONE) {
                allIsDoneCount++;
            }
            if (subtask.getStatus() == Status.NEW) {
                allIsInNewCount++;
            }
        }
        if (allIsDoneCount == list.size()) {
            epic.setStatus(Status.DONE);
        } else if (allIsInNewCount == list.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
