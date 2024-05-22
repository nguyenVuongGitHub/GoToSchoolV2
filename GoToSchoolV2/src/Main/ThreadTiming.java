package Main;

public class ThreadTiming implements Runnable {

    int second;
    long startTime = System.nanoTime();
    static long deltaTime = 0;
    final int FPS = 30;
    @Override
    public void run() {
        while (true) {
            long currentTime = System.nanoTime();
            // Sử dụng synchronized: Đảm bảo rằng việc cập nhật và đọc deltaTime
            // là an toàn giữa các luồng. Điều này ngăn ngừa các vấn đề về đồng bộ hóa
            // khi deltaTime được cập nhật trong phương thức run và
            // đọc trong phương thức isSecondPassTime.
            synchronized (this) {
                deltaTime += (currentTime - startTime);
            }
            startTime = currentTime; // Cập nhật startTime cho lần lặp tiếp theo
            try {
                Thread.sleep(100); // Tạm dừng 100ms để giảm tải CPU
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Kiểm tra nếu một giây đã trôi qua
    public static boolean isOneSecondElapse() {
        if(deltaTime >= 1_000_000_000L) {
            deltaTime -= 1_000_000_000L;
            return true;
        }
        return false;
    }
}
