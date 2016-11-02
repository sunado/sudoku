
package com.sunado;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sunado.CreateBoard;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;

public class SudokuBroadPanel extends JPanel {
    private final int SIZE = 9;
    private JLabel[][] boardLabel = new JLabel[9][9];
    private int[][] board;
    private int[][] boardFill;
    private int row;
    private int column;
    private boolean start;
    private boolean isWin;

    public SudokuBroadPanel() {
        this.setPreferredSize(new Dimension(270, 270));
        this.setLayout(new GridLayout(9, 9));

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.boardLabel[i][j] = new JLabel();
                Font font = new Font("Arial", 1, 20);
                this.boardLabel[i][j].setFont(font);
                this.boardLabel[i][j].setBackground(Color.WHITE);
                this.boardLabel[i][j].setOpaque(true);
                this.boardLabel[i][j].setHorizontalAlignment(0);
                this.boardLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                this.add(this.boardLabel[i][j]);
            }
        }

    }

    public void createSudokuBroad(String level) throws ContradictionException, TimeoutException {
        this.start = false;
        this.isWin = true;
        CreateBoard create = new CreateBoard(level);
        this.board = create.getBoard();
        this.boardFill = create.getBoardFill();

        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.boardLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                if(this.board[i][j] > 0) {
                    this.boardLabel[i][j].setForeground(Color.BLACK);
                    this.boardLabel[i][j].setText("" + this.board[i][j]);
                } else {
                    this.boardLabel[i][j].setText("");
                }

                this.mouseClickedCell(i, j);
            }
        }

    }

    private void mouseClickedCell(final int i, final int j) {
        final JLabel cell = this.boardLabel[i][j];
        cell.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(cell.getForeground() != Color.BLACK || cell.getText() == "") {
                    SudokuBroadPanel.this.boardLabel[SudokuBroadPanel.this.row][SudokuBroadPanel.this.column].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                    cell.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    SudokuBroadPanel.this.row = i;
                    SudokuBroadPanel.this.column = j;
                    SudokuBroadPanel.this.start = true;
                }

            }
        });
    }

    public void solve() {
        for(int i = 0; i < 9; ++i) {
            for(int j = 0; j < 9; ++j) {
                JLabel cell = this.boardLabel[i][j];
                String s = cell.getText();
                if(s == "") {
                    this.boardLabel[i][j].setForeground(Color.RED);
                    this.boardLabel[i][j].setText("" + this.boardFill[i][j]);
                    this.isWin = false;
                }

                if(cell.getForeground() == Color.BLUE) {
                    int val = Integer.parseInt(s);
                    if(val != this.boardFill[i][j]) {
                        this.boardLabel[i][j].setForeground(Color.RED);
                        this.boardLabel[i][j].setText("" + this.boardFill[i][j]);
                        this.isWin = false;
                    }
                }
            }
        }

    }

    public void setCell(int i) {
        JLabel cell = this.boardLabel[this.row][this.column];
        if(this.start) {
            if(cell.getForeground() != Color.BLACK || cell.getText() == "") {
                cell.setForeground(Color.BLUE);
                cell.setText("" + i);
            }

        }
    }

    public boolean isWin() {
        return this.isWin;
    }
}
