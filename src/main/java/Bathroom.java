import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bathroom {
    @Getter
    @Setter
    private PersonType currentlyUsedBy;
    private int currentCount;
    private final int maximumCount;
    private final ReentrantLock lock;
    private final Condition condition;

    public Bathroom(int maximumCount) {
        currentlyUsedBy = PersonType.NONE;
        this.currentCount = 0;
        this.maximumCount = maximumCount;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void useMaleBathroom() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            while (PersonType.WOMEN.equals(currentlyUsedBy) || maximumCount<= currentCount) {
                condition.await();
            }
            currentCount++;
            currentlyUsedBy = PersonType.MAN;
            System.out.printf("Thread: %s - Man acquired%n", name);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void leaveMaleBathroom() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            currentCount--;
            if(currentCount == 0) {
                currentlyUsedBy = PersonType.NONE;
            }
            System.out.printf("Thread: %s Man leaves%n", name);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void useFemaleWashroom() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            while(PersonType.MAN.equals(currentlyUsedBy) || maximumCount<=currentCount) {
                condition.await();
            }
            currentCount++;
            currentlyUsedBy = PersonType.WOMEN;
            System.out.printf("Thread: %s - Woman acquired%n", name);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void leaveWomenBathroom() {
        try {
            lock.lock();
            String name = Thread.currentThread().getName();
            currentCount--;
            if (currentCount == 0) {
                currentlyUsedBy = PersonType.NONE;
            }
            System.out.printf("Thread: %s Woman leaves%n", name);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
