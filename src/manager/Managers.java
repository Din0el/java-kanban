package manager;

public class Managers {
    public static TaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager(); // InMemoryTaskManager реализует интерфейс TaskManager
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager(); // InMemoryHistoryManager реализует интерфейс HistoryManager
    }
}

/*public class Managers {
    public static TaskManager getDefaultTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefaultHistoryManager() {
        return new InMemoryHistoryManager();
    }
}*/
