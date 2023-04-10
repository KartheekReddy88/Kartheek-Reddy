import java.util.*;

class Process {
    String name;
    int priority;
    int burstTime;
    int remainingTime;
    int quantum;

    public Process(String name, int priority, int burstTime) {
        this.name = name;
        this.priority = priority;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.quantum = 2;
    }

    public void execute() {
        if (remainingTime > quantum) {
            System.out.println("Executing " + name + " for " + quantum + " units of time");
            remainingTime -= quantum;
        } else {
            System.out.println("Executing " + name + " for " + remainingTime + " units of time");
            remainingTime = 0;
        }
    }

    public boolean isFinished() {
        return remainingTime == 0;
    }

    public boolean hasHigherPriority(Process other) {
        return priority < other.priority;
    }
}

public class Scheduler {
    private Queue<Process> queue1; // priority queue
    private Queue<Process> queue2; // round-robin queue
    private int quantum;

    public Scheduler() {
        queue1 = new PriorityQueue<Process>(new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return p1.priority - p2.priority;
            }
        });
        queue2 = new LinkedList<Process>();
        quantum = 2;
    }

    public void addProcess(String name, int priority, int burstTime) {
        Process process = new Process(name, priority, burstTime);
        queue1.add(process);
    }

    public void run() {
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            if (!queue1.isEmpty()) {
                Process current = queue1.poll();
                System.out.println("Running process " + current.name + " from Queue 1 with priority " + current.priority);
                current.execute();
                if (current.isFinished()) {
                    System.out.println("Process " + current.name + " finished");
                } else {
                    queue2.add(current);
                }
            } else {
                Process current = queue2.poll();
                System.out.println("Running process " + current.name + " from Queue 2 with priority " + current.priority);
                current.execute();
                if (current.isFinished()) {
                    System.out.println("Process " + current.name + " finished");
                } else {
                    queue2.add(current);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.addProcess("P1", 2, 30);
        scheduler.addProcess("P2", 1, 3);
        
        scheduler.run();
    }
}
