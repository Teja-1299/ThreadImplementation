/*Level 4: Thread Communication
Question: Create a producer-consumer problem where one thread produces numbers (1 to 10) and another thread consumes them. Use wait() and notify() for communication between threads.
Hint: Use a shared buffer and synchronize the methods that add to and remove from the buffer. */
class MyData {
    int value;
    boolean flag = true;

    synchronized public void set(int v) {
        System.out.println("6. Entering set method");
        while (flag != true) { // i.e when flag is false.
            try {
                System.out.println("7. Producer waiting");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = v;
        flag = false;
        System.out.println("8. Produced: " + value);
        notify();
        System.out.println("9. Exiting set method");
    }

    synchronized public int get() {
        System.out.println("10. Entering get method");
        while (flag != false) { // i.e when flag is true.
            try {
                System.out.println("11. Consumer waiting");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flag = true;
        System.out.println("12. Consumed: " + value);
        notify();
        System.out.println("13. Exiting get method");
        return value;
    }
}

class Producer extends Thread {
    MyData data;

    public Producer(MyData d) {
        data = d;
    }

    public void run() {
        int count = 1;
        while (count <= 10) {
            System.out.println("5. Producer setting value: " + count);
            data.set(count);
            count++;
        }
    }
}

class Consumer extends Thread {
    MyData data;

    public Consumer(MyData d) {
        data = d;
    }

    public void run() {
        int value;
        while (true) {
            System.out.println("14. Consumer getting value");
            value = data.get();
            System.out.println("15. Consumer: " + value);
        }
    }
}

class Threadone {
    public static void main(String[] args) {
        MyData data = new MyData();
        System.out.println("1. Creating MyData instance");
        Producer p = new Producer(data);
        System.out.println("2. Creating Producer instance");
        Consumer c = new Consumer(data);
        System.out.println("3. Creating Consumer instance");
        
        c.start();
        System.out.println("5. Starting Consumer thread");
        p.start();
        System.out.println("4. Starting Producer thread");
    }
}
