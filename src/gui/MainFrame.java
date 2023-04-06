package gui;

import printer.Printer;
import printer.Task;
import utils.ArrayUtils;
import utils.JTableUtils;
import utils.SwingUtils;

import javax.swing.*;
import java.text.ParseException;

public class MainFrame extends JFrame {
    private JTable inputTable;
    private JTable printerTable;
    private JTable doneTable;
    private JPanel mainPanel;
    private JScrollPane jScrollPane;
    private JButton startButton;
    private JTextArea logField;
    private JTable addTaskTable;
    private JButton addTask;

    private final Printer printer = new Printer(inputTable, printerTable, doneTable,
            logField);


    public MainFrame() {
        super("Printer");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        JTableUtils.initJTableForArray(addTaskTable, 50, true,
                true, false, false);
        JTableUtils.initJTableForArray(inputTable, 40, true,
                true, false, false);
        JTableUtils.initJTableForArray(printerTable, 40, true, true, false, false);
        JTableUtils.initJTableForArray(doneTable, 40, true, true, false, false);
        startButton.addActionListener(e -> {
            JButton source = (JButton) e.getSource();
            source.setEnabled(false);
            addTask.setEnabled(false);
            try {
                printer.checkTasks(3);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
            source.setEnabled(true);
            addTask.setEnabled(true);
//            try {
//                Thread thread = new Thread(() -> {
//                    try {
//                        printer.checkTasks(3);
//                    } catch (Exception ex) {
//                        SwingUtils.showErrorMessageBox(ex);
//                    }
//                });
//                thread.start();
//
//            } catch (Exception ex) {
//                SwingUtils.showErrorMessageBox(ex);
//            }
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
                    if (printer.addTask(new Task(arr))) {
                        matrix = ArrayUtils.insertRowInto2DArray(matrix, matrix.length, arr);
                    }
                }

                JTableUtils.writeArrayToJTable(inputTable, matrix);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        JTableUtils.writeArrayToJTable(addTaskTable, new int[][]{
                {1, 3, 4}
        });
    }

    public static void main(String[] args) {
        JFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
