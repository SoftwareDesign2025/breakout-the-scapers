package GalagaGameElemtents;
// Author: Jose Andres Besednjak Izquierdo
import GalagaGameElemtentsEnemies.EnemyBase;
import java.util.Iterator;
import java.util.List;

public class GalagaCollissionManager {
	
	public static void handleBallPaddle(List<GalagaBall> balls, List<GalagaPaddle> paddles) {
        
    }

    // returns the total points scored
    // For Galaga balls: applies damage and removes ball immediately on hit
    public static int handleBallBricks(List<GalagaBall> balls, List<EnemyBase> enemies) {
        int score = 0;

        Iterator<GalagaBall> ballIterator = balls.iterator();


        while (ballIterator.hasNext()) {
            GalagaBall galagaBall = ballIterator.next();

            // for every ball we reset the iterator at the beggining
            Iterator<EnemyBase> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                // Check collision
                EnemyBase enemy = enemyIterator.next();

                if (enemy.getView().getBoundsInParent().intersects(galagaBall.getView().getBoundsInParent())) {
                    // Apply damage from the ball
                    int damage = galagaBall.getProjectileDamage();

                    // for every hit add to the score
                    score += enemy.getPoints();
                    
                    // remove enemy if dead
                    if (enemy.onHit(damage)){
                        enemy.eraseFromScreen();
                        enemyIterator.remove();
                    }

                    // Remove ball immediately from list and scene (ball removes itself)
                    galagaBall.eraseFromScreen();
                    ballIterator.remove();
                    // only one hit per ball
                    break;
                }
            }
        }
        return score;
    }
    
    public static void handleBallBall(List<GalagaBall> balls) {
       
    }
}
