import java.util.Random;

public class WashRoomManager {
    private final Bathroom bathroom;
    private int tName;
    private Random random;

    public WashRoomManager(final int max) {
        this.bathroom = new Bathroom(max);
        tName = 0;
        random = new Random();
    }

    public void men() {
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            final long sTime = getSleepTime();
            Person man = new Person(PersonType.MAN, sTime);
            System.out.printf("Thread: %s Man going%n", name);
            bathroom.useMaleBathroom();
            //System.out.printf("Thread: %s Man acquired washroom- Using for: %s%n", name, ((1.0 * sTime)/1000));
            man.act();
            bathroom.leaveMaleBathroom();
            //System.out.printf("Thread: %s Man leaves bathroom%n", name);

        }, String.valueOf(tName++)).start();
    }

    public void women() {
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            final long sTime = getSleepTime();
            Person woman = new Person(PersonType.WOMEN, sTime);
            System.out.printf("Thread: %s Women going%n", name);
            bathroom.useFemaleWashroom();
            //System.out.printf("Thread: %s Women acquired washroom- Using for: %s%n", name, ((1.0 * sTime)/1000));
            woman.act();
            bathroom.leaveWomenBathroom();
            //System.out.printf("Thread: %s Man leaves bathroom%n", name);

        }, String.valueOf(tName++)).start();
    }

    private long getSleepTime() {
        return random.nextInt(20) * 1000;
    }
}
