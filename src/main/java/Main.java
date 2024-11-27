public class Main {
    public static void main(String[] args) {
        WashRoomManager manager = new WashRoomManager(4);
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                manager.men();
            } else {
                manager.women();
            }
        }
    }
}
