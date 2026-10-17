import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TaskManager manager = new TaskManager(Path.of("data", "tasks.txt"));
            boolean running = true;

            while (running) {
                System.out.println("\n=== TO-DO LIST MANAGER ===");
                System.out.println("1. Show tasks");
                System.out.println("2. Add task");
                System.out.println("3. Mark task as completed");
                System.out.println("4. Delete task");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine().trim();

                if (choice.equals("1")) {
                    showTasks(manager.getTasks());
                } else if (choice.equals("2")) {
                    System.out.print("Task title: ");
                    String title = scanner.nextLine().trim();
                    if (title.isEmpty()) {
                        System.out.println("Title cannot be empty.");
                    } else {
                        manager.addTask(title);
                        System.out.println("Task added.");
                    }
                } else if (choice.equals("3")) {
                    int id = readId(scanner);
                    if (id > 0) {
                        if (manager.completeTask(id)) {
                            System.out.println("Task completed.");
                        } else {
                            System.out.println("Task not found.");
                        }
                    }
                } else if (choice.equals("4")) {
                    int id = readId(scanner);
                    if (id > 0) {
                        if (manager.deleteTask(id)) {
                            System.out.println("Task deleted.");
                        } else {
                            System.out.println("Task not found.");
                        }
                    }
                } else if (choice.equals("5")) {
                    running = false;
                } else {
                    System.out.println("Please select 1 to 5.");
                }
            }

            System.out.println("Goodbye!");
        } catch (IOException exception) {
            System.err.println("Could not read or save tasks: " + exception.getMessage());
        }
    }

    private static void showTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }

        for (Task task : tasks) {
            String status = " ";
            if (task.isCompleted()) {
                status = "x";
            }
            System.out.println(task.getId() + ". [" + status + "] " + task.getTitle());
        }
    }

    private static int readId(Scanner scanner) {
        System.out.print("Task ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (id < 1) {
                System.out.println("ID must be a positive number.");
                return -1;
            }
            return id;
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
}
