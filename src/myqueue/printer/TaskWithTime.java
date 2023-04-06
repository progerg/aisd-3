package myqueue.printer;

import java.util.Arrays;

public class TaskWithTime extends Task {
    private final int time;

    public TaskWithTime(int[] parameters) {
        super(Arrays.copyOfRange(parameters, 0, 3));
        if (parameters.length != 4) {
            throw new IllegalArgumentException("Количество значений в массиве должно быть равно 3");
        }
        this.time = parameters[3];
    }

    public TaskWithTime(int id, int pageCount, int priority, int time) {
        super(id, pageCount, priority);
        this.time = time;
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
        return task.getId() == this.getId();
    }

    public int getTime() {
        return time;
    }

    public int[] toIntArrayWithTime() {
        return new int[] {getPageCount(), getPriority(), getId(), getTime()};
    }
}
