import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Boy
 */
public class Boy extends ImageView implements Runnable {

    private static List<Image> imagesIdle;
    private static List<Image> imagesDead;
    private static List<Image> imagesJump;
    private static List<Image> imagesRun;
    private static List<Image> imagesWalk;

    private final Thread animationThread;
    private StateBoy state;
    private int health;
    private long timeAnimation;
    private int TIME_OF_ANIMATION = 700;

    static {
        imagesIdle = new LinkedList<>();
        imagesDead = new LinkedList<>();
        imagesJump = new LinkedList<>();
        imagesRun = new LinkedList<>();
        imagesWalk = new LinkedList<>();
        loadSprites();
    }

    Boy() {
        super(imagesIdle.get(0));
        state = StateBoy.IDLE;
        health = 100;
        timeAnimation = 0;
        animationThread = new Thread(this);
        animationThread.start();
    }

    /**
     * change state of boy to dead.
     */
    public void wounded() {
        if (state != StateBoy.IDLE)
            return;
        health = 1;
        startNewAnimation();
        state = StateBoy.DEAD;
    }

    /**
     * change state of boy to jump.
     */
    public void jump() {
        if (state == StateBoy.JUMP)
            return;
        startNewAnimation();
        state = StateBoy.JUMP;
    }

    /**
     * change state of boy to walk.
     */
    public void walk() {
        if (state != StateBoy.IDLE)
            return;
        startNewAnimation();
        state = StateBoy.WALK;
    }

    /**
     * get a boolean about the boy's life.
     */
    public boolean isAlive() {
        return health > 0;
    }

    private void startNewAnimation() {
        timeAnimation = System.currentTimeMillis() + TIME_OF_ANIMATION;
    }

    /**
     * manages animation.
     */
    @Override
    public void run() {
        while (isAlive()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Image image = null;
            switch (state) {
            case DEAD:
                image = getNextImage(getImage(), imagesDead);
                if (timeAnimation < System.currentTimeMillis())
                    health = 0;
                break;
            case IDLE:
                image = getNextImage(getImage(), imagesIdle);
                break;
            case JUMP:
                image = getNextImage(getImage(), imagesJump);
                if (timeAnimation < System.currentTimeMillis())
                    state = StateBoy.IDLE;
                break;
            case RUN:
                image = getNextImage(getImage(), imagesRun);
                break;
            case WALK:
                image = getNextImage(getImage(), imagesWalk);
                if (timeAnimation < System.currentTimeMillis())
                    state = StateBoy.IDLE;
                break;
            }
            setImage(image);
        }
    }

    /**
     * get next image in the list.
     */
    private Image getNextImage(Image image, List<Image> list) {
        int position = list.indexOf(image) + 1;
        if (position >= 15)
            position = 0;
        return list.get(position);
    }

    /**
     * Load srites.
     */
    private static void loadSprites() {
        String path = "/resources/boy_sprite/";
        for (int i = 1; i <= 15; i++) {
            imagesIdle.add(new Image(Boy.class.getResourceAsStream(path + "Idle (" + i + ").png")));
            imagesDead.add(new Image(Boy.class.getResourceAsStream(path + "Dead (" + i + ").png")));
            imagesJump.add(new Image(Boy.class.getResourceAsStream(path + "Jump (" + i + ").png")));
            imagesRun.add(new Image(Boy.class.getResourceAsStream(path + "Run (" + i + ").png")));
            imagesWalk.add(new Image(Boy.class.getResourceAsStream(path + "Walk (" + i + ").png")));
        }
    }

}