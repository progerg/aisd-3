package printer;

public class Page implements Comparable<Page> {
    private final int time;
    private final int priority;
    private final Task task;

    public Page(int time, int priority, Task task) {
        this.time = time;
        this.priority = priority;
        this.task = task;
    }

    public int[] toIntArray() {
        return new int[] {time, priority};
    }

    public int getTime() {
        return time;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Page o) {
        // Сначала сравниваем по приоритету
        int priorityDiff = o.getPriority() - this.priority;
        if (priorityDiff != 0) {
            return priorityDiff;
        }

        // Если приоритеты равны, то сравниваем по времени поступления
        if (o.getTime() > this.time) {
            return -1;
        } else if (o.getTime() < this.time) {
            return 1;
        }

        return 0;
    }
}
