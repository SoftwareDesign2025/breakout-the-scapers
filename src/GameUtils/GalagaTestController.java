package GameUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.Timeline;
import GalagaGameElemtents.GalagaBall;
import GalagaGameElemtents.GalagaPaddle;
import GalagaGameElemtents.GalagaCollissionManager;
import GalagaGameElemtentsEnemies.EnemyBase;
import GalagaGameElemtentsEnemies.EnemyRegular;
import GalagaGameElemtentsEnemies.EnemyTank;
import GalagaGameElemtentsEnemies.EnemyMoving;
import GalagaGameElemtentsEnemies.EnemyFast;
import GameElemtents.Brick;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GalagaTestController {
    
    public final int LIVES_START = 3;
    
    private List<GalagaPaddle> paddles;
    private List<GalagaBall> balls; 
    private List<EnemyBase> enemies;

    private int score;
    private int lives;
    private Text scoreLabel;
    private Text livesLabel;
    private Color textColor = GameColors.TEXT_COLOR.getColor(); 
    
    private double width;
    private double height;
    
    private Timeline animation;
    
    private Group root;
    
    public void setAnimation(Timeline animation) {
        this.animation = animation;
    }

    // Create game objects and set up the initial scene layout
    public Group createRoot(int windowWidth, int windowHeight) {
        width = windowWidth;
        height = windowHeight;

        root = new Group();
        
        // Initialize lists
        enemies = new ArrayList<>();
        paddles = new ArrayList<>();
        balls = new ArrayList<>();

        score = 0;
        lives = LIVES_START;

        // Set up the test level
        setupTestLevel();
        setTextAndLabels();
        
        return root;
    }

    private void setupTestLevel() {
        // Create Galaga paddle at the bottom center
        GalagaPaddle paddle = new GalagaPaddle(width / 2 - 50, height - 80, 100, 100, root);
        paddles.add(paddle);
        root.getChildren().add(paddle.getView());
        
        // Create a test formation of different enemy types
        createEnemyFormation();
    }
    
    private void createEnemyFormation() {
        double enemyWidth = 50;
        double enemyHeight = 40;
        double spacing = 60;
        double startX = 100;
        double startY = 100;
        int hp = 2;
        
        // Row 1: Regular enemies
        for (int i = 0; i < 6; i++) {
            EnemyRegular enemy = new EnemyRegular(
                startX + i * spacing, 
                startY, 
                enemyWidth, 
                enemyHeight, 
                hp, 
                root
            );
            enemies.add(enemy);
            root.getChildren().add(enemy.getView());
        }
        
        // Row 2: Tank enemies (more HP)
        for (int i = 0; i < 7; i++) {
            EnemyTank enemy = new EnemyTank(
                startX + i * spacing + spacing / 2, 
                startY + spacing, 
                enemyWidth, 
                enemyHeight, 
                hp + 8, 
                root
            );
            enemies.add(enemy);
            root.getChildren().add(enemy.getView());
        }
        
        // Row 3: Moving enemies
        for (int i = 0; i < 6; i++) {
            EnemyMoving enemy = new EnemyMoving(
                startX + i * spacing, 
                startY - spacing, 
                enemyWidth, 
                enemyHeight, 
                hp, 
                root
            );
            enemies.add(enemy);
            root.getChildren().add(enemy.getView());
        }
        
        // Row 4: Fast enemies
        for (int i = 0; i < 7; i++) {
            EnemyFast enemy = new EnemyFast(
                startX + i * spacing + spacing / 2, 
                startY - spacing * 2, 
                enemyWidth, 
                enemyHeight, 
                hp, 
                root
            );
            enemies.add(enemy);
            root.getChildren().add(enemy.getView());
        }
    }
    
    private void setTextAndLabels() {
        // Score and lives text setup
        scoreLabel = new Text(20, 20, "Score: 0");
        scoreLabel.setFill(textColor);
        livesLabel = new Text(500, 20, "Lives: " + lives);
        livesLabel.setFill(textColor);

        root.getChildren().addAll(scoreLabel, livesLabel);
    }
    
    public void setMoveLeft(boolean value) {
        for (GalagaPaddle paddle : paddles) {
            paddle.setMoveLeft(value);
        }
    }

    public void setMoveRight(boolean value) {
        for (GalagaPaddle paddle : paddles) {
            paddle.setMoveRight(value);
        }
    }
    
    public void spawnBall() {
        if (!paddles.isEmpty()) {
            GalagaPaddle paddle = paddles.get(0);
            // Spawn ball at the center top of the paddle, slightly above to avoid immediate collision
            double ballX = paddle.getX() + paddle.getView().getBoundsInLocal().getWidth() / 2;
            double ballY = paddle.getY() - 20; // Spawn slightly above the paddle
            GalagaBall ball = new GalagaBall(ballX, ballY, root);
            // Set direction upward (270 degrees = straight up)
            ball.setDirection(270);
            balls.add(ball);
            root.getChildren().add(ball.getView());
        }
    }
    
    public void step(double elapsedTime) {
        // Collisions with ball paddle/enemies
        // Use Galaga-specific collision manager with Galaga types
        List<Brick> enemyList = new ArrayList<>(enemies);
        
        GalagaCollissionManager.handleBallPaddle(balls, paddles);
        addScore(GalagaCollissionManager.handleBallBricks(balls, enemyList));
        
        // Update enemies (they may move or fall)
        for (EnemyBase enemy : enemies) {
            enemy.update(elapsedTime);
        }

        // Move the ball based on its velocity and the elapsed frame time
        for (GalagaBall ball : balls) {
            ball.update(elapsedTime);
        }
        for (GalagaPaddle paddle : paddles) {
            paddle.update(elapsedTime);
        }
        
        // Remove dead enemies from the scene
        Iterator<EnemyBase> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            EnemyBase enemy = enemyIterator.next();
            if (enemy.deadBrick()) {
                root.getChildren().remove(enemy.getView());
                enemyIterator.remove();
            }
        }

        // Check if all enemies are cleared
        if (enemies.isEmpty()) {
            // All enemies defeated - could restart or show win message
            System.out.println("All enemies defeated! Score: " + score);
        }

        // Remove balls that hit top of screen or fell below screen (balls that hit bricks are already removed by collision manager)
        Iterator<GalagaBall> ballIterator = balls.iterator();
        while (ballIterator.hasNext()) {
            GalagaBall ball = ballIterator.next();
            // If ball reaches the top of the screen, remove it
            if (ball.getY() <= ball.getRadius()) {
                ball.eraseFromScreen();
                ballIterator.remove();
            }
        }
        
        scoreLabel.setText("Score: " + score);
        livesLabel.setText("Lives: " + lives);
    }

    public void addScore(int points) {
        score += points;
    }
}

