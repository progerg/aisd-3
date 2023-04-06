package javaqueue.printer;

public class Task {
    private final int id;
    private final int pageCount;
    private final int priority;


    public Task(int[] parameters) {
        if (parameters.length != 3) {
            throw new IllegalArgumentException("Количество значений в массиве должно быть равно 3");
        }
        this.pageCount = parameters[0];
        this.priority = parameters[1];
        this.id = parameters[2];
    }

    public Task(int id, int pageCount, int priority) {
        this.id = id;
        this.pageCount = pageCount;
        this.priority = priority;
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
