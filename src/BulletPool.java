import java.util.ArrayList;
import java.util.List;

public class BulletPool {
    List<Bullet> bullets = new ArrayList<Bullet>();

    public BulletPool(){
        int size = 30;
        for (int i = 0; i < size; i++) {
            bullets.add(new Bullet(-999, -999, 0, 0));
        }
    }

    public Bullet requestBullet(int x, int y, int dx, int dy){
        try {
            Bullet bullet = bullets.remove(0);
            bullet.refreshState(x, y, dx, dy);
            return bullet;
        } catch (Exception e) {
            bullets.add(new Bullet(-999, -999, 0, 0));
            System.out.println("Add new bullet");
            Bullet bullet = bullets.remove(0);
            bullet.refreshState(x, y, dx, dy);
            return bullet;
        }
    }

    public void releaseBullet(Bullet bullet){
        bullets.add(new Bullet(-999, -999, 0, 0));
    }
}
