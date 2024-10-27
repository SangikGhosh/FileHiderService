package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;

    UserView(String email) {
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("Welcome " + this.email);
            System.out.println("1. to show hidden files: ");
            System.out.println("2. to hide a new file: ");
            System.out.println("3. to visible a file: ");
            System.out.println("How to hide the file: ");
            System.out.println("0. to exit: ");
            System.out.println("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("\n\nID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("\n\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("\n\nEnter the file path: ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                        System.out.println("File hide successfully...\n\n");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);

                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("Enter the ID of file to visible: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidID = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidID = true;
                                break;
                            }
                        }
                        if (isValidID) {
                            DataDAO.visible(id);
                        } else {
                            System.out.println("Wrong ID");
                        }
                    } catch (SQLException  e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } case 4 -> {
                    System.out.println("To hide a file, follow these steps:\n" +
                            "\n" +
                            "1. **Go to the Location of the File**: First, find the folder where your file is stored. You can do this by:\n" +
                            "   - Opening the **File Explorer** (on Windows) or **Finder** (on Mac).\n" +
                            "   - Navigating through the folders until you reach the folder that contains the file you want to hide.\n" +
                            "\n" +
                            "2. **Copy the Command**: Use the command or method to hide files (e.g., typing a command in the terminal or command prompt).\n" +
                            "\n" +
                            "3. **Paste and Run the Command**: Finally, paste the command in the necessary place (like Command Prompt on Windows) and press **Enter** to execute it.\n" +
                            "\n" +
                            "This will hide your file.");
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        } while (true);
    }
}
