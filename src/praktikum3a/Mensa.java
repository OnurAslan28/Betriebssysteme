package praktikum3a;

import java.util.LinkedList;

import java.util.Scanner;

public class Mensa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the amount of Students: ");
        int students = sc.nextInt();
        System.out.print("Please enter the amount of Registers: ");
        int registers = sc.nextInt();

        LinkedList<Student> studentList = new LinkedList<>();
        LinkedList<Register> registerList = new LinkedList<>();

        for (int i = 0; i < registers; i++) {
            Register register = new Register();
            register.setName("Register " + i);
            registerList.add(register);
        }

        for (int i = 0; i < students; i++) {
            Student student = new Student(registerList);
            student.setName("Student " + i);
            studentList.add(student);
            student.start();
    }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}

        for (Student student : studentList) {
            student.interrupt();
        }
        for (Student student : studentList) {
            try {
            student.join();
        } catch (InterruptedException ignored) {}
        }

        for (Register register : registerList) {
            System.out.println(register.getName() + " was visited " + register.getCounter() + " Times.");
        }
        sc.close();
    }
}