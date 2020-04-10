package server.abcTest;

public class ABCTest {

    public volatile char manager = 'A';
    public volatile int y;                  // Сделал y общим, но этого оказалось недостаточно

    public synchronized void abc_method (char x) {
        try {
            for (int i = 1; i <=5; i++) {
                y = (manager % x) % 3;
                while (y != 0) {
                    wait();
                    y = (manager % x) % 3;  // Пришлось делать так, иначе после пробуждения y оставался прежним
                }
                String str = ((Character) x).toString();
                System.out.print(str);
                manager++;
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ABCTest abcTest = new ABCTest();

        new Thread( () -> abcTest.abc_method('A')).start();
        new Thread( () -> abcTest.abc_method('B')).start();
        new Thread( () -> abcTest.abc_method('C')).start();
    }
}
