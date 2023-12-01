public class MemoryUtil {

    private MemoryUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static long getUsedMemoryBeforeGC() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
