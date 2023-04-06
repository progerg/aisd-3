package javaqueue.printer;

import java.util.*;


public class Printer {
    private final PriorityQueue<TaskWithTime> printerTasks;
    private final PriorityQueue<TaskWithTime> tasksWithTime;

    public Printer() {
        this.printerTasks = new PriorityQueue<>((o1, o2) -> {
            // Сначала сравниваем по приоритету
            int priorityDiff = o2.getPriority() - o1.getPriority();
            if (priorityDiff != 0) {
                return priorityDiff;
            }

            // Сравниваем по времени поступления
            int timeDiff = o1.getTime() - o2.getTime();
            if (timeDiff != 0) {
                return timeDiff;
            }

            // Если приоритеты и время поступления равны, то сравниваем по id
            return o2.getId() - o1.getId();
        });
        this.tasksWithTime = new PriorityQueue<>((o1, o2) -> {
            // Сначала сравниваем по времени поступления
            int timeDiff = o1.getTime() - o2.getTime();
            if (timeDiff != 0) {
                return timeDiff;
            }

            // Сравниваем по приоритету
            int priorityDiff = o2.getPriority() - o1.getPriority();
            if (priorityDiff != 0) {
                return priorityDiff;
            }

            // Если приоритеты и время поступления равны, то сравниваем по id
            return o2.getId() - o1.getId();
        });
    }

    public boolean addTask(TaskWithTime task) {
        if (tasksWithTime.contains(task))
            return false;
        tasksWithTime.add(task);
        return true;
    }

    public void addTasksToPrinter(int time) {
        while (tasksWithTime.peek() != null && tasksWithTime.peek().getTime() <= time) {
            printerTasks.add(tasksWithTime.poll());
        }
    }

    public Map<TaskWithTime, Integer> checkTasks() {
        HashMap<TaskWithTime, Integer> hashMap = new HashMap<>();
        int time = 0;
        while (!tasksWithTime.isEmpty()) {
            addTasksToPrinter(time);
            while (!printerTasks.isEmpty()) {
                TaskWithTime taskWithTime = printerTasks.poll();
                time += taskWithTime.getPageCount();
                hashMap.put(taskWithTime, time);
                addTasksToPrinter(time);
            }
            if (tasksWithTime.peek() != null)
                time = tasksWithTime.peek().getTime();
        }
        return hashMap;
    }
}