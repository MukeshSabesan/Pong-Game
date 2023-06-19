/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 *
 * @author mukes
 */
public class Score extends Rectangle{
    
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1Score;
    int player2Score;
    
    Score(int GAME_WIDTH, int GAME_HEIGHT){
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        
        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        g.drawString(String.valueOf(player1Score/10)+String.valueOf(player1Score%10), (GAME_WIDTH/2)-80, 50);
        g.drawString(String.valueOf(player2Score/10)+String.valueOf(player2Score%10), (GAME_WIDTH/2)+20, 50);
    }
}
