package com.sunado;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;

public class SudokuFrame implements KeyListener {
    private JFrame frame;
    private final ButtonGroup buttonGroupLevel = new ButtonGroup();
    private SudokuBroadPanel broadPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SudokuFrame e = new SudokuFrame();
                    e.frame.setVisible(true);
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        });
    }

    public SudokuFrame() {
        this.initialize();
    }

    private void initialize() {
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 524, 332);
        this.frame.setDefaultCloseOperation(3);
        this.frame.getContentPane().setLayout((LayoutManager)null);
        this.broadPanel = new SudokuBroadPanel();
        this.broadPanel.setBounds(0, 0, 329, 294);
        this.frame.getContentPane().add(this.broadPanel);
        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(330, 0, 178, 294);
        this.frame.getContentPane().add(controlPanel);
        controlPanel.setLayout((LayoutManager)null);
        JButton btnNewGame = new JButton("New Game");
        btnNewGame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                try {
                    String e = SudokuFrame.this.buttonGroupLevel.getSelection().getActionCommand();
                    System.out.println(e);
                    SudokuFrame.this.broadPanel.createSudokuBroad(e);
                } catch (TimeoutException | ContradictionException var3) {
                    var3.printStackTrace();
                }

            }
        });
        btnNewGame.setBounds(40, 50, 108, 23);
        controlPanel.add(btnNewGame);
        JRadioButton rdbtnEasy = new JRadioButton("Easy");
        rdbtnEasy.setActionCommand("Easy");
        this.buttonGroupLevel.add(rdbtnEasy);
        rdbtnEasy.setSelected(true);
        rdbtnEasy.setBounds(62, 113, 79, 23);
        controlPanel.add(rdbtnEasy);
        JRadioButton rdbtnNormal = new JRadioButton("Normal");
        rdbtnNormal.setActionCommand("Normal");
        this.buttonGroupLevel.add(rdbtnNormal);
        rdbtnNormal.setBounds(62, 139, 79, 23);
        controlPanel.add(rdbtnNormal);
        JRadioButton rdbtnHard = new JRadioButton("Hard");
        rdbtnHard.setActionCommand("Hard");
        this.buttonGroupLevel.add(rdbtnHard);
        rdbtnHard.setBounds(62, 165, 79, 23);
        controlPanel.add(rdbtnHard);
        final JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setFont(new Font("Tahoma", 1, 12));
        lblNewLabel.setBounds(40, 223, 108, 28);
        controlPanel.add(lblNewLabel);
        JButton btnSolve = new JButton("Solve");
        btnSolve.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                SudokuFrame.this.broadPanel.solve();
                if(SudokuFrame.this.broadPanel.isWin()) {
                    lblNewLabel.setText("You Win!");
                } else {
                    lblNewLabel.setText("You are stupid");
                }

            }
        });
        btnSolve.setBounds(40, 83, 108, 23);
        controlPanel.add(btnSolve);
        rdbtnEasy.addKeyListener(this);
        rdbtnNormal.addKeyListener(this);
        rdbtnHard.addKeyListener(this);
        btnNewGame.addKeyListener(this);
        btnSolve.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode() - 48;
        if(i >= 1 && i <= 9) {
            this.broadPanel.setCell(i);
        }

    }

    public void keyTyped(KeyEvent e) {
    }
}
