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
public class GamePanel extends JPanel implements Runnable{
    
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH*(0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int PUCK_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Puck puck;
    Score score;
    
    GamePanel(){
        newPaddles();
        newPuck();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void newPuck(){
        random = new Random();
        puck = new Puck((GAME_WIDTH/2)-(PUCK_DIAMETER/2), random.nextInt(GAME_HEIGHT-PUCK_DIAMETER), PUCK_DIAMETER, PUCK_DIAMETER);
    }
    public void newPaddles(){
        paddle1 = new Paddle(0, (GAME_HEIGHT/2)- (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT/2)- (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
       
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }
    
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        puck.draw(g);
        score.draw(g);
    }
    
    public void move(){
        paddle1.move();
        paddle2.move();
        puck.move();
    }
    
    public void checkCollision(){
        // bounces puck off top and bottom window edges
        if(puck.y <=0){
            puck.setYDirection(-puck.yVelocity);
        }
        if(puck.y >= GAME_HEIGHT-PUCK_DIAMETER){
            puck.setYDirection(-puck.yVelocity);
        }
        //bounce puck off paddles
        if(puck.intersects(paddle1)){
            puck.xVelocity = Math.abs(puck.xVelocity);
            puck.xVelocity++;
            if(puck.yVelocity > 0)
                puck.yVelocity++;
            else
                puck.yVelocity--;
            puck.setXDirection(puck.xVelocity);
            puck.setYDirection(puck.yVelocity);
        }
        
        if(puck.intersects(paddle2)){
            puck.xVelocity = Math.abs(puck.xVelocity);
            puck.xVelocity++;
            if(puck.yVelocity > 0)
                puck.yVelocity++;
            else
                puck.yVelocity--;
            puck.setXDirection(-puck.xVelocity);
            puck.setYDirection(puck.yVelocity);
        }
        
        //stops paddles at window edges
        if(paddle1.y <= 0){
            paddle1.y=0;
        }
        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        
        if(paddle2.y <= 0){
            paddle2.y=0;
        }
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
        }
        
        //give a player 1 point a creates new paddles & ball
        if(puck.x <= 0){
            score.player2Score++;
            newPaddles();
            newPuck();
            System.out.println("Player 2: "+ score.player2Score);
        }
        
        if(puck.x >= GAME_WIDTH-PUCK_DIAMETER){
            score.player1Score++;
            newPaddles();
            newPuck();
            System.out.println("Player 1: "+ score.player1Score);
        }
    }
    
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1){
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
