import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoListApp {
    private List<String> tasks;
    private Scanner scanner;

    public ToDoListApp() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\nTo-Do List Application");
        System.out.println("1. Add Task");
        System.out.println("2. Edit Task");
        System.out.println("3. Delete Task");
        System.out.println("4. View Tasks");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    public void addTask() {
        System.out.print("Enter the task: ");
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("Task added: " + task);
    }

    public void editTask() {
        viewTasks();
        System.out.print("Enter the task number to edit: ");
        int taskNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (taskNumber >= 0 && taskNumber < tasks.size()) {
            System.out.print("Enter the new task: ");
            String newTask = scanner.nextLine();
            tasks.set(taskNumber, newTask);
            System.out.println("Task updated.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void deleteTask() {
        viewTasks();
        System.out.print("Enter the task number to delete: ");
        int taskNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (taskNumber >= 0 && taskNumber < tasks.size()) {
            String removedTask = tasks.remove(taskNumber);
            System.out.println("Task deleted: " + removedTask);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    public void run() {
        while (true) {
            displayMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    editTask();
                    break;
                case 3:
                    deleteTask();
                    break;
                case 4:
                    viewTasks();
                    break;
                case 5:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ToDoListApp app = new ToDoListApp();
        app.run();
    }
}
