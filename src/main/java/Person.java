public class Person {
    private final PersonType type;
    private final long sleepTime;

    public Person(PersonType type, long sleepTime) {
        this.type = type;
        this.sleepTime = sleepTime;
    }

    public void act() {
        String name = Thread.currentThread().getName();
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.printf("Thread: %s - sleeping for %s", name, ((double)sleepTime)/1000);
        }
    }
}
