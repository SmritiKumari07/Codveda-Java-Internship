import java.util.*;

class Employee {

    int id;
    String name, department;

    Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public String toString() {
        return id + " | " + name + " | " + department;
    }
}

public class EmployeeManagement {
    public static void main(String[] args) {
    
        System.out.println("Task 1 of Level 2 By Smriti Kumari ");
        Scanner sc = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();

        while (true) {
            System.out.println("\n-- Employee Management --");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Remove Employee");
            System.out.println("4. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    employees.add(new Employee(id, name, dept));
                    System.out.println("Employee added!");
                    break;

                case 2:
                    System.out.println("All Employees:");
                    for (Employee e : employees) System.out.println(e);
                    break;

                case 3:
                    System.out.print("Enter ID to remove: ");
                    int removedId = sc.nextInt();
                    employees.removeIf(e -> e.id == removedId);
                    System.out.println("Removed if existed.");
                    break;

                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
