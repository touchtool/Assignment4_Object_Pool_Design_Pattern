import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {

    private int width = 600;
    private int height = 600;

    private List<Bullet> bullets;
    private BulletPool bulletPool;
    private Thread mainLoop;
    private boolean alive;

    public Game() {
        alive = true;
        bullets = new ArrayList<Bullet>();
        bulletPool = new BulletPool();
        mainLoop = new Thread() {
            @Override
            public void run() {
                while(alive) {
                    tick();
                    setChanged();
                    notifyObservers();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (true) {
                        if (System.currentTimeMillis() - bulletPool.getTime() >= 30000 && bulletPool.bullets.size() > 30) {
                            bulletPool.bullets.remove(0);
                        }
                        else {
                            break;
                        }
                    }
                }
            }
        };
        mainLoop.start();
    }

    public void tick() {
        moveBullets();
        cleanupBullets();
    }

    private void moveBullets() {
        for(Bullet bullet : bullets) {
            bullet.move();
        }
    }

    private void cleanupBullets() {
        List<Bullet> toRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets) {
            if(bullet.getX() <= 0 ||
                    bullet.getX() >= width ||
                    bullet.getY() <= 0 ||
                    bullet.getY() >= height) {
                toRemove.add(bullet);
            }
        }
        for(Bullet bullet : toRemove) {
            bullets.remove(bullet);
            bulletPool.releaseBullet(bullet);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void burstBullets(int x, int y) {
        bullets.add(bulletPool.requestBullet(x, y, 1, 0));
        bullets.add(bulletPool.requestBullet(x, y, 0, 1));
        bullets.add(bulletPool.requestBullet(x, y, -1, 0));
        bullets.add(bulletPool.requestBullet(x, y, 0, -1));
        bullets.add(bulletPool.requestBullet(x, y, 1, 1));
        bullets.add(bulletPool.requestBullet(x, y, 1, -1));
        bullets.add(bulletPool.requestBullet(x, y, -1, 1));
        bullets.add(bulletPool.requestBullet(x, y, -1, -1));
    }
}
