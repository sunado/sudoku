package com.sunado;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

public class SudokuSAT4J {
    private final int MAXVAR = 729;
    private final int SIZE = 9;
    private ModelIterator solver = new ModelIterator(SolverFactory.newDefault());
    private int[][] board = new int[9][9];
    private long numberOfModels;

    public SudokuSAT4J() throws ContradictionException, TimeoutException {
        this.numberOfModels = 0L;
    }

    public SudokuSAT4J(int[][] board) throws ContradictionException, TimeoutException {
        for(int row = 0; row < 9; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.board[row][column] = board[row][column];
            }
        }

        this.addAllClause();
        this.sudokuSolve();
    }

    public void setBoard(int[][] board) throws ContradictionException, TimeoutException {
        this.solver = new ModelIterator(SolverFactory.newDefault());
        this.board = new int[9][9];

        for(int row = 0; row < 9; ++row) {
            for(int column = 0; column < 9; ++column) {
                this.board[row][column] = board[row][column];
            }
        }

        this.addAllClause();
        this.sudokuSolve();
    }

    public int[][] getBoard() {
        int[][] temp = new int[9][9];

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                temp[i][j] = this.board[i][j];
            }
        }

        return temp;
    }

    public long getNumberOfModels() {
        return this.numberOfModels;
    }

    private void addAllClause() throws ContradictionException {
        int sqrtSIZE;
        int rowBlock;
        for(sqrtSIZE = 0; sqrtSIZE < 9; ++sqrtSIZE) {
            for(rowBlock = 0; rowBlock < 9; ++rowBlock) {
                this.addClauseCell(sqrtSIZE, rowBlock);
            }
        }

        for(sqrtSIZE = 0; sqrtSIZE < 9; ++sqrtSIZE) {
            this.addClauseRow(sqrtSIZE);
        }

        for(sqrtSIZE = 0; sqrtSIZE < 9; ++sqrtSIZE) {
            this.addClauseColumn(sqrtSIZE);
        }

        sqrtSIZE = (int)Math.sqrt(9.0D);

        for(rowBlock = 0; rowBlock < sqrtSIZE; ++rowBlock) {
            for(int columnBlock = 0; columnBlock < sqrtSIZE; ++columnBlock) {
                this.addClauseBlock(rowBlock, columnBlock);
            }
        }

        this.addClauseCellFill();
    }

    private void addClauseCellFill() throws ContradictionException {
        int[] clause = new int[1];
        boolean value = false;

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                int var5 = this.board[i][j];
                if(var5 > 0) {
                    clause[0] = this.getIndexCell(i, j, var5 - 1);
                    this.solver.addClause(new VecInt(clause));
                }
            }
        }

    }

    private void addClauseCell(int row, int column) throws ContradictionException {
        int[] clause = new int[9];

        int i;
        for(i = 0; i < 9; ++i) {
            clause[i] = this.getIndexCell(row, column, i);
        }

        this.solver.addClause(new VecInt(clause));
        clause = new int[2];

        for(i = 0; i < 9; ++i) {
            for(int j = i + 1; j < 9; ++j) {
                clause[0] = -this.getIndexCell(row, column, i);
                clause[1] = -this.getIndexCell(row, column, j);
                this.solver.addClause(new VecInt(clause));
            }
        }

    }

    private void addClauseRow(int row) throws ContradictionException {
        int[] clause = new int[9];

        int number;
        int i;
        for(number = 0; number < 9; ++number) {
            for(i = 0; i < 9; ++i) {
                clause[i] = this.getIndexCell(row, i, number);
            }

            this.solver.addClause(new VecInt(clause));
        }

        clause = new int[2];

        for(number = 0; number < 9; ++number) {
            for(i = 0; i < 9; ++i) {
                for(int j = i + 1; j < 9; ++j) {
                    clause[0] = -this.getIndexCell(row, i, number);
                    clause[1] = -this.getIndexCell(row, j, number);
                    this.solver.addClause(new VecInt(clause));
                }
            }
        }

    }

    private void addClauseColumn(int column) throws ContradictionException {
        int[] clause = new int[9];

        int number;
        int i;
        for(number = 0; number < 9; ++number) {
            for(i = 0; i < 9; ++i) {
                clause[i] = this.getIndexCell(i, column, number);
            }

            this.solver.addClause(new VecInt(clause));
        }

        clause = new int[2];

        for(number = 0; number < 9; ++number) {
            for(i = 0; i < 9; ++i) {
                for(int j = i + 1; j < 9; ++j) {
                    clause[0] = -this.getIndexCell(i, column, number);
                    clause[1] = -this.getIndexCell(j, column, number);
                    this.solver.addClause(new VecInt(clause));
                }
            }
        }

    }

    private void addClauseBlock(int rowBlock, int columnBlock) throws ContradictionException {
        int sqrtSIZE = (int)Math.sqrt(9.0D);
        int startRow = rowBlock * sqrtSIZE;
        int finishRow = startRow + sqrtSIZE;
        int startColumn = columnBlock * sqrtSIZE;
        int finishColumn = startColumn + sqrtSIZE;
        int[] clause = new int[9];

        int number;
        int i;
        int j;
        for(number = 0; number < 9; ++number) {
            i = 0;

            for(j = startRow; j < finishRow; ++j) {
                for(int column = startColumn; column < finishColumn; ++column) {
                    clause[i] = this.getIndexCell(j, column, number);
                    ++i;
                }
            }

            this.solver.addClause(new VecInt(clause));
        }

        clause = new int[2];

        for(number = 0; number < 9; ++number) {
            for(i = 0; i < 9; ++i) {
                for(j = i + 1; j < 9; ++j) {
                    clause[0] = -this.getIndexCell(startRow + i / sqrtSIZE, startColumn + i % sqrtSIZE, number);
                    clause[1] = -this.getIndexCell(startRow + j / sqrtSIZE, startColumn + j % sqrtSIZE, number);
                    this.solver.addClause(new VecInt(clause));
                }
            }
        }

    }

    private void sudokuSolve() throws TimeoutException {
        this.numberOfModels = 0L;
        if(this.solver.isSatisfiable()) {
            int[] model = this.solver.model();

            for(int i = 0; i < 729; ++i) {
                int value = model[i] - 1;
                if(value > 0) {
                    int row = value / 81;
                    int column = (value - row * 9 * 9) / 9;
                    int number = value - row * 9 * 9 - column * 9 + 1;
                    this.board[row][column] = number;
                }
            }

            ++this.numberOfModels;
        }

        if(this.solver.isSatisfiable()) {
            ++this.numberOfModels;
        }

    }

    private int getIndexCell(int row, int column, int number) {
        return row * 9 * 9 + column * 9 + number + 1;
    }
}
