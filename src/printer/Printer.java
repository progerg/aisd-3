package printer;

import utils.JTableUtils;
import utils.SwingUtils;

import javax.swing.*;
import java.util.*;

public class Printer {
    private final PriorityQueue<Task> tasks;
    private final PriorityQueue<Page> pages;
    private final List<Task> doneTasks;
    private final JTextArea logField;
    private final JTable printTable;
    private final JTable inputTable;
    private final JTable doneTable;

    public Printer(JTable inputTable, JTable printTable, JTable doneTable,
                   JTextArea logField) {
        this.inputTable = inputTable;
        this.printTable = printTable;
        this.doneTable = doneTable;
        this.logField = logField;
        this.tasks = new PriorityQueue<>();
        this.pages = new PriorityQueue<>();
        this.doneTasks = Collections.synchronizedList(new ArrayList<Task>());
    }

    public PriorityQueue<Task> getTasks() {
        return tasks;
    }

    public boolean addTask(Task task) {
        if (tasks.contains(task))
            return false;
        tasks.add(task);
        return true;
    }

    public void addTaskPages(Task task) {
        for (int i = 0; i < task.getPageCount(); i++) {
            pages.add(new Page(1, task.getPriority(), task));
        }
    }

    public void updateTaskTable() {
        int[][] matrix;
        if (!tasks.isEmpty()) {
            matrix = new int[tasks.size()][];
            Iterator<Task> itr = tasks.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Task task = itr.next();
                if (task != null) {
                    matrix[i] = task.toIntArray();
                }
                i++;
            }
        } else {
            matrix = new int[1][];
        }
        JTableUtils.writeArrayToJTable(inputTable, matrix);
    }

    public void updatePrintTable() {
        int[][] matrix;
        if (!pages.isEmpty()) {
            matrix = new int[pages.size()][];
            Iterator<Page> itr = pages.iterator();
            int i = 0;
            while (itr.hasNext()) {
                Page page = itr.next();
                if (page != null) {
                    matrix[i] = page.toIntArray();
                }
                i++;
            }
        } else {
            matrix = new int[1][];
        }
        JTableUtils.writeArrayToJTable(printTable, matrix);
    }

    public void updateDoneTable() {
        int[][] matrix = new int[doneTasks.size()][];
        for (int i = 0; i < doneTasks.size(); i++) {
            matrix[i] = doneTasks.get(i).toIntArrayWithTime();
        }
        JTableUtils.writeArrayToJTable(doneTable, matrix);
    }

    public void print(Task task) throws InterruptedException {
        logField.append("Началась печать страниц задания " + task.getId() + "\n");
        task.setStartTime(new Date());
        int k = 0;
        Page page;
        if (pages.isEmpty()) {
            return;
        }
        do {
            page = pages.poll();
            // Thread.sleep(page.getTime() * 1000L);
            k++;
            logField.append("Печать " + k + " страницы завершена" + "\n");
            updatePrintTable();
        } while (!pages.isEmpty());
        task.setDuration(k * page.getTime());
        doneTasks.add(task);
        logField.append(task.getId() + " задание выполнено" + "\n");
        updateDoneTable();
    }

    public void checkTasks(int checkInterval) throws InterruptedException {
        try {
            // печатаем задания и обновляем таблицу выходных заданий
            while (!this.getTasks().isEmpty()) {
                updateTaskTable();
                Task task = this.getTasks().poll();
                this.addTaskPages(task);
                this.print(task);
            }
        } catch (Exception ex) {

            SwingUtils.showErrorMessageBox(ex);
        }
//        Thread.sleep(checkInterval * 1000L);
    }
}