package printer;

import java.util.Date;

public class Task implements Comparable<Task>{
    private final int id;
    private Date startTime;
    private final int pageCount;
    private final int priority;
    private int duration;


    public Task(int[] parameters) {
        if (parameters.length != 3) {
            throw new IllegalArgumentException("Количество значений в массиве должно быть равно 3");
        }
        this.pageCount = parameters[0];
        this.priority = parameters[1];
        this.id = parameters[2];
        this.startTime = new Date();
    }

    public Task(int id, int pageCount, int priority) {
        this.id = id;
        this.pageCount = pageCount;
        this.priority = priority;
        this.startTime = new Date();
    }

    public int[] toIntArrayWithTime() {
        return new int[] {pageCount, priority, id, duration};
    }

    public int[] toIntArray() {
        return new int[] {pageCount, priority, id};
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Task o) {
        // Сначала сравниваем по приоритету
        int priorityDiff = o.getPriority() - this.priority;
        if (priorityDiff != 0) {
            return priorityDiff;
        }

        // Если приоритеты равны, то сравниваем по времени поступления
        int compare = o.getStartTime().compareTo(this.startTime);
        if (compare != 0) {
            return compare;
        }

        // Если приоритеты и время поступления равны, то сравниваем по id
        return this.id - o.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;
        return task.getId() == this.id;
    }
}
