package Sudoku;

import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.beans.*;
import javafx.concurrent.Task;

import static java.lang.Thread.sleep;


public class SudokuBean implements java.io.Serializable {

    private TextField sudokuField;
    private SudokuScene scene;
    private int x;
    private int y;
    boolean isFlashing = false;
    boolean isGreen = false;

    public SudokuBean (SudokuScene scene, TextField sudokuField, int x, int y) {
        this.scene = scene;
        this.sudokuField = sudokuField;
        this.x = x;
        this.y = y;

        addVetoableChangeListener(new NumberChangeListener(scene.sudoku, x, y, scene));
    }

    public void refresh()
    {
        isGreen = false;

        if(!isFlashing)
            sudokuField.setStyle(null);

        Integer var = scene.sudoku.get(x, y);
        sudokuField.setText(var == null ? "" : var.toString());
        if(!scene.sudoku.canModify(x, y))
            sudokuField.setDisable(true);
        else
            sudokuField.setDisable(false);
        if(scene.sudoku.hasConflict(x, y)) {
            isGreen = true;
            if(!isFlashing)
                changeColor("green");
        }
    }

    public void clear()
    {
        scene.sudoku.trySet(x, y, null);
        sudokuField.setText("");
    }

    private VetoableChangeSupport vcs = new VetoableChangeSupport(this);


    public synchronized void addVetoableChangeListener (VetoableChangeListener lst)
    { vcs.addVetoableChangeListener(lst); }

    public synchronized void setNumber(Integer newNumber) throws PropertyVetoException {
        Integer num = 0;
        vcs.fireVetoableChange("number", num, newNumber);
    }

    @Override
    public String toString() {
        return sudokuField.getText();
    }

    public void tryChangeNumber(Integer num) {
        try {
            setNumber(num);
        } catch (PropertyVetoException e) {

            flash("red");
            refresh();

            return;
        }

        if(scene.sudoku.checkVictory())
        {
            scene.popupWictory();
            scene.refresh();
        }
    }

    public void flash(String color)
    {
        isFlashing = true;
        changeColor(color);
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                sleep(300);
                Platform.runLater(() -> {
                    if(isGreen)
                        changeColor("green");
                    else
                        sudokuField.setStyle(null);
                    isFlashing = false;
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void changeColor(String color)
    {
        sudokuField.setStyle("-fx-background-color: " + color + ";");
    }

    static class NumberChangeListener implements VetoableChangeListener{
        private Sudoku sudoku;
        private SudokuScene scene;
        int x;
        int y;

        public NumberChangeListener(Sudoku sudoku, int x, int y, SudokuScene scene){
            this.sudoku = sudoku;
            this.x = x;
            this.y = y;
            this.scene = scene;
        }

        @Override
        public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {

            if (!evt.getPropertyName().equals("number"))
                return;

            Sudoku.Conflict con = sudoku.trySet(x, y, (Integer)evt.getNewValue());
            if(con.conflictExists)
            {
                scene.flash(con.x, con.y);
                throw new PropertyVetoException("Недопустимое значение в поле " + x + " " + y, evt);
            }

        }
    }
}
