package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Sudoku {

    public static class Conflict {
        boolean conflictExists;
        int x;
        int y;
        Conflict(boolean conflictExists)
        {
            this.conflictExists = conflictExists;
        }

        Conflict(boolean conflictExists, int x, int y)
        {
            this.conflictExists = conflictExists;
            this.x = x;
            this.y = y;
        }
    }

    private Integer table[][];
    private int group[][];
    private boolean modifable[][];
    private int range = 0;
    private int removePercentage;

    int size_x;
    int size_y;


    Sudoku(int size_x, int size_y, int range)
    {
        this.size_x = size_x;
        this.size_y = size_y;
        this.range = range;

        table = new Integer[size_x][size_y];
        group = new int[size_x][size_y];
        modifable = new boolean[size_x][size_y];

    }

    public void setGroup(int x, int y, int col)
    {
        group[x][y] = col;
    }

    public int getGroup(int x, int y)
    {
        return group[x][y];
    }


    public boolean canModify(int x, int y)
    {
        return modifable[x][y];
    }

    public Conflict trySet(int x, int y, Integer val)
    {

        if(!modifable[x][y]) {
            return new Conflict(true, x, y);
        }

        if(val == null) {
            table[x][y] = null;
            return new Conflict(false);
        }

        if(val <=0 || val > range) {
            return new Conflict(true, x, y);
        }

        Conflict con = isCorrect(x, y, val);
        if(!con.conflictExists)
            table[x][y] = val;

        return con;
    }

    public boolean hasConflict(int ix_x, int ix_y)
    {
        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++)
            {
                if(i == ix_x && j == ix_y)
                    continue;

                if(group[i][j] == group[ix_x][ix_y] || ix_x == i || ix_y == j)
                {
                    if(table[i][j] != null && table[i][j].equals(table[ix_x][ix_y]))
                    {
                        return true;
                    }
                }
            }
        return false;
    }

    public boolean checkVictory()
    {
        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++) {
                if (modifable[i][j] && table[i][j] == null)
                    return false;
                if (modifable[i][j] && hasConflict(i, j))
                    return false;
            }
        return true;
    }

    public void generateRandom(int level)
    {
        removePercentage = (level+1)*20;
        for(int i=0; i<size_x; i++)
            for (int j = 0; j < size_y; j++)
            {
                table[i][j] = null;
                modifable[i][j] = false;
            }

        generateNext(0, 0);
        removeSome();

        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++)
            {
                if (table[i][j] == null)
                    modifable[i][j] = true;
                else
                    modifable[i][j] = false;
            }
    }

    public void print()
    {
        for(int i=0; i<size_x; i++)
        {
            for(int j=0; j<size_y; j++)
                System.out.print(table[i][j] + " ");
            System.out.println();
        }
        System.out.println();
        for(int i=0; i<size_x; i++)
        {
            for(int j=0; j<size_y; j++)
                System.out.print(group[i][j] + " ");
            System.out.println();
        }
        System.out.println();
        for(int i=0; i<size_x; i++)
        {
            for(int j=0; j<size_y; j++)
                System.out.print(modifable[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public Integer get(int x, int y)
    {
        return table[x][y];
    }

    private void removeSome()
    {
        Random generator = new Random();
        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++)
            {
                if (generator.nextInt(100) < removePercentage) {
                    modifable[i][j] = true;
                    table[i][j] = null;
                }
            }
    }
    private Conflict isCorrect(int ix_x, int ix_y, int val)
    {
        for(int i=0; i<size_x; i++)
            for(int j=0; j<size_y; j++)
            {
                if(modifable[i][j])
                    continue;

                if(i == ix_x && j == ix_y)
                    continue;

                if(group[i][j] == group[ix_x][ix_y] || ix_x == i || ix_y == j)
                {
                    if(table[i][j] != null && table[i][j] == val)
                    {
                        return new Conflict(true, i, j);
                    }
                }
            }
        return new Conflict(false);
    }

    private boolean generateNext(int x, int y)
    {
        if(y == size_y)
            return true;

        int next_x, next_y;
        if(x == size_x-1)
        {
            next_y = y + 1;
            next_x = 0;
        }
        else
        {
            next_x = x + 1;
            next_y = y;
        }

        List<Integer> numbers = new ArrayList<>();
        for(int i=1; i<=range; i++)
            numbers.add(i);
        Collections.shuffle(numbers);

        for(Integer i : numbers)
        {
            if(!isCorrect(x, y, i).conflictExists)
            {
                table[x][y] = i;

                if(generateNext(next_x, next_y))
                    return true;

                table[x][y] = null;
            }
        }

        return false;
    }
}
