package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;
import views.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the File Hider Service");
        System.out.println("1. Existing User (login): ");
        System.out.println("2. New User (signup): ");
        System.out.println("Press 0 to exit");
        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();
        try {
            if(UserDAO.isExists(email)) {
                if(UserDAO.validatePassword(email, password)) {
                    String genOTP = GenerateOTP.getOTP();
                    SendOTPService.sendOTP(email, genOTP);
                    System.out.println("Enter the OTP sent on mail:");
                    String otp = sc.next();
                    sc.nextLine();
                    if(otp.equals(genOTP)) {
                        new UserView(email).home();

                    } else {
                        System.out.println("Wrong OTP entered");
                    }
                } else {
                    System.out.println("Incorrect password");
                }
            } else {
                System.out.println("User not found ");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = sc.nextLine();
        System.out.println("Enter email: ");
        String email = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP sent on mail: ");
        String otp = sc.next();
        sc.nextLine();
        if(otp.equals(genOTP)) {
            User user = new User(name, email, password);
            int response = UserService.saveUser(user);
            switch (response) {
                case 1 -> {
                    System.out.println("User registered");
                    UserView uv = new UserView(email);
                    uv.home();
                }
                case 0 -> System.out.println("User already exists");
            }
        } else {
            System.out.println("Wrong OTP entered");
        }

    }
}
