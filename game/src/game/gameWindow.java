package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;

public class gameWindow extends JFrame {
    private static int max = 300;
    private static int min = 200;
    Random random = new Random();
    private int i = random.nextInt((max - min) + 1);
    private static gameWindow game_Window;
    private static long lastFrameTime;
    private static Image background;
    private static Image gameOver;
    private static Image drop;
    private static float dropLeft = 20;
    private static float dropTop = 0;
    private static float dropSpeed = 200;
    private static int limitTop = 0;
    private static int limitLeft = 200;
    private static int count = 0;


    public static void main(String[] args) throws IOException {
        background = ImageIO.read(gameWindow.class.getResourceAsStream("background.png"));
        gameOver = ImageIO.read(gameWindow.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(gameWindow.class.getResourceAsStream("drop.png"));
        game_Window = new gameWindow();
        game_Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_Window.setLocation(200, 100);
        game_Window.setSize(906,478);
        game_Window.setResizable(false);
        lastFrameTime = System.nanoTime();
        gameField game_field = new gameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float dropRight = dropLeft + drop.getWidth(null);
                float dropBottom = dropTop + drop.getHeight(null);
                boolean isDrop = x >= dropLeft && x <= dropRight || y >= dropTop && y <= dropBottom;
                if (isDrop) {
                    dropTop = -100;
                    dropLeft = (int) (Math.random() * (game_field.getWidth() - drop.getWidth(null)));
                    dropSpeed = dropSpeed + 20;
                    count++;
                    game_Window.setTitle("Очки: " + count);
                }
            }
        }) ;
        game_Window.add(game_field);
        game_Window.setVisible(true);
    }

    private static void position (int j){
        for (int i = 0; i <= j; j++) {

        }
    }

    private static void onRepaint(Graphics g) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = currentTime;
        dropTop = dropTop + dropSpeed * deltaTime;
     /*   dropLeft = dropLeft + dropSpeed * deltaTime;*/
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) dropLeft, (int) dropTop, null);

        if (dropTop > game_Window.getHeight()) g.drawImage(gameOver, 280, 120, null);
    }

    private static class  gameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
