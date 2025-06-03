package services;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class Service{
    protected Scanner scanner;
    protected static final String AUDIT_FILE_NAME = "audit.csv";
    protected FileWriter audit_file_writer;
    protected Runnable[] menu_choices;
    protected final String[] menu_choices_strings;

    protected Service(String[] menu_choices_strings) {
        this.scanner = new Scanner(System.in);
        try{
            this.audit_file_writer = new FileWriter(AUDIT_FILE_NAME, true);
        } catch(IOException e){
            throw new RuntimeException("Error opening " + AUDIT_FILE_NAME + "for appending: " + e);
        }

        this.menu_choices_strings = menu_choices_strings;
    }

    // Can't set it in the constructor unfortunately
    protected void setMenuChoices(Runnable[] menu_choices){
        this.menu_choices = menu_choices;
    } 

    protected void audit(String action){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        try {
            audit_file_writer.append(String.format("%s, %s\n", formattedNow, action));
            audit_file_writer.flush();
        } catch (IOException e){ 
            System.out.println("Error writing to audit file: " + e.getMessage());
        }
    }

    public void menu(){
        menuPrint();
        int choice;
        do{
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > menu_choices.length)
                System.out.println("Invalid choice, try again");
            else
                break;
        } while (true);

        audit(this.menu_choices_strings[choice - 1]);
        this.menu_choices[choice - 1].run();
    }

    protected void menuPrint(){
        for (int i = 0 ; i < this.menu_choices_strings.length; ++i)
            System.out.println((i + 1) + ". " + this.menu_choices_strings[i]);
    }
}