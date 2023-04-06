package myqueue.gui;

import myqueue.printer.Printer;
import myqueue.printer.TaskWithTime;
import myqueue.utils.ArrayUtils;
import myqueue.utils.JTableUtils;
import myqueue.utils.SwingUtils;

import javax.swing.*;
import java.util.Map;

public class MainFrame extends JFrame {
    private JTable inputTable;
    private JTable doneTable;

    private JScrollPane jScrollPane;
    private JPanel mainPanel;
    private JButton startButton;
    private JTable addTaskTable;
    private JButton addTask;
    private final Printer printer = new Printer();


    public MainFrame() {
        super("Printer");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        JTableUtils.initJTableForArray(addTaskTable, 50, true,
                true, false, false);
        JTableUtils.initJTableForArray(inputTable, 40, true,
                true, false, false);
        JTableUtils.initJTableForArray(doneTable, 40, true, true, false, false);
        startButton.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            source.setEnabled(false);
            addTask.setEnabled(false);
            Map<TaskWithTime, Integer> hashMap = printer.checkTasks();
            updateDoneTable(hashMap);
            JTableUtils.writeArrayToJTable(inputTable, new int[0][]);
            source.setEnabled(true);
            addTask.setEnabled(true);
        });

        addTask.addActionListener(e -> {
            try {
                int[][] matrix;
                try {
                    matrix = JTableUtils.readIntMatrixFromJTable(inputTable);
                    if (matrix == null || matrix[0].length == 1) {
                        matrix = new int[1][];
                    }
                } catch (Exception ex) {
                    matrix = new int[1][];
                }

                int[] arr = JTableUtils.readIntArrayFromJTable(addTaskTable);
                if (arr != null) {
                    if (printer.addTask(new TaskWithTime(arr))) {
                        matrix = ArrayUtils.insertRowInto2DArray(matrix, matrix.length, arr);
                    }
                }

                JTableUtils.writeArrayToJTable(inputTable, matrix);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        JTableUtils.writeArrayToJTable(addTaskTable, new int[][]{
                {1, 3, 4, 5}
        });
    }

    public void updateDoneTable(Map<TaskWithTime, Integer> hashMap) {
        int[][] matrix = new int[hashMap.size()][];
        int i = 0;
        for (Map.Entry<TaskWithTime, Integer> entry : hashMap.entrySet()) {
            matrix[i] = entry.getKey().toIntArrayWithTime();
            matrix[i][3] = entry.getValue();
            i++;
        }
        JTableUtils.writeArrayToJTable(doneTable, matrix);
    }

    public static void main(String[] args) {
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
