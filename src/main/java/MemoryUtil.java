public class MemoryUtil {
    public static long getUsedMemoryBeforeGC() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 10; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
