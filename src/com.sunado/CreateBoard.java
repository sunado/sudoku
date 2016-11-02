package com.sunado;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;

public class CreateBoard {
    private final int SIZE = 9;
    private int[][] board;
    private int[][] boardFill;
    private SudokuSAT4J sat;

    public CreateBoard(String level) throws ContradictionException, TimeoutException {
        this.createBoardFill();
        this.createBoard(level);
    }

    private void createBoard(String level) throws ContradictionException, TimeoutException {
        byte lev = 0;
        this.board = this.getBoardFill();
        if(level == "Easy") {
            lev = 30;
        } else if(level == "Normal") {
            lev = 40;
        } else if(level == "Hard") {
            lev = 50;
        }

        for(int i = 0; i < lev; ++i) {
            this.deleteRandomCell();
        }

    }

    private void deleteRandomCell() throws ContradictionException, TimeoutException {
        int i = 0;

        do {
            int randomRow = (int)(Math.random() * 9.0D);
            int randomColumn = (int)(Math.random() * 9.0D);
            this.board[randomRow][randomColumn] = 0;
            this.sat.setBoard(this.board);
            if(this.sat.getNumberOfModels() == 1L) {
                break;
            }

            this.board[randomRow][randomColumn] = this.boardFill[randomRow][randomColumn];
            ++i;
        } while(i < 50);

    }

    private void createBoardFill() throws ContradictionException, TimeoutException {
        this.boardFill = new int[9][9];

        int randomRow;
        int randomColumn;
        for(int n = 1; n <= 9; this.boardFill[randomRow][randomColumn] = n++) {
            randomRow = (int)(Math.random() * 9.0D);
            randomColumn = (int)(Math.random() * 9.0D);
        }

        this.sat = new SudokuSAT4J(this.boardFill);
        this.boardFill = this.sat.getBoard();
    }

    public int[][] getBoardFill() {
        int[][] temp = new int[9][9];

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                temp[i][j] = this.boardFill[i][j];
            }
        }

        return temp;
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
}