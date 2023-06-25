import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ToDoListWithReminder {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<Task> tasks = new ArrayList<>();
    private static final Timer timer = new Timer();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("To-Do List with Reminder");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Exiting the program...");
                break;
            }

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    viewTasks();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        timer.cancel();
        scanner.close();
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter the task description: ");
        scanner.nextLine();
        String description = scanner.nextLine();

        System.out.print("Enter the task due date and time (YYYY-MM-DD HH:MM:SS): ");
        String dueDateTimeString = scanner.nextLine();
        LocalDateTime dueDateTime = LocalDateTime.parse(dueDateTimeString, formatter);

        Task task = new Task(description, dueDateTime);
        tasks.add(task);

        System.out.println("Task added successfully.");

        // Schedule a reminder for the task
        scheduleReminder(task);
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("Tasks:");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void scheduleReminder(Task task) {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                System.out.println("Reminder: " + task.getDescription() + " (Due: " +
                        formatter.format(task.getDueDateTime()) + ")");
            }
        };

        timer.schedule(timerTask, task.getDueDateTime().toLocalDate().atStartOfDay());
    }
}

class Task {
    private final String description;
    private final LocalDateTime dueDateTime;

    public Task(String description, LocalDateTime dueDateTime) {
        this.description = description;
        this.dueDateTime = dueDateTime;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    @Override
    public String toString() {
        return "Task: " + description + " (Due: " + formatter.format(dueDateTime) + ")";
    }
}
