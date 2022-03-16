import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class BulletPull {
    List<Bullet> bullets = new ArrayList<Bullet>();

    public BulletPull(){
        int size = 30;
        for (int i = 0; i<size; i++){
            bullets.add(new Bullet(-999, -999, 0, 0));
        }
    }

    public Bullet requestBullet(int x, int y, int dx, int dy){
        Bullet bullet = bullets.remove(0);
        bullet.refreshState(x, y, dx, dy);
        return bullet;
    }

    public void releaseBullet(Bullet bullet){
        bullets.add(new Bullet(-999, -999, 0, 0));
    }
}
