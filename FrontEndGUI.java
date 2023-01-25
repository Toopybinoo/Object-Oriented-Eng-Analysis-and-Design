import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Shams Kadri
 */
public class FrontEndGUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Store Application");
        GridPane g = new GridPane();
        g.setAlignment(Pos.TOP_CENTER);
        g.setHgap(10);//horizontal gap between grid cells i think
        g.setVgap(2);//vertical gap
        g.setPadding(new Insets(20,20,20,20));//margin around the grid (top/right/bottom/left)
        
        Text title = new Text("Sign In");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 36));
        g.add(title, 0, 0, 2, 1);
        g.add(new Label("Username: "), 0, 1);
        TextField username = new TextField();
        g.add(username, 0, 3, 2, 1);
        g.add(new Label("Password: "), 0, 4);
        PasswordField password = new PasswordField();
        g.add(password, 0, 5, 2, 1);
        Button btn = new Button("Log in");
        g.add(btn, 0, 6, 2, 1);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String user = username.getText();
                String pass = password.getText();
                
                int points = 0;
                if(user.equals("admin") && pass.equals("admin")){
                    OwnerStartScreen(primaryStage);
                    System.out.println("Owner logged in.");
                }else if(!(user.equals("admin"))){
                    boolean valid = false;
                    File f = new File("customer.txt");
                        try{
                        Scanner reader = new Scanner(f);
                        while (reader.hasNextLine()){
                            String in = reader.nextLine();
                            String[] data = in.split(", ");
                            if(user.equals(data[0]) && pass.equals(data[1])){
                                valid = true;
                                
                                points = Integer.parseInt(data[2]);
                            }
                        }
                        
                        if(valid == true){
                            Customer currentCustomer = new Customer(user, pass);
                            CustomerStartScreen(primaryStage, currentCustomer);
                            System.out.println("Customer logged in.");
                            
                        }else{
                            System.out.println("This customer does not exist");
                            g.add(new Label("Invalid login."), 2, 4, 2, 1);
                        }
                        
                    }catch(FileNotFoundException e){
                        System.out.println("File does not exist!");
                    }
                }
            }
        });
        
        Scene login = new Scene(g, 400, 400);
        primaryStage.setScene(login);
        primaryStage.show();
        
        //this creates text docs
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Exited the book store");
            try {
                //files.clearBookArray();
                //files.clearCustomerArray();
                //System.out.println("Files reset");
                files.writeBookArray(Owner.books);
                files.writeCustomerArray(owner.getCustomers());
                System.out.println("Files updated with current array data");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        //takes book info from "book.txt" file and writes into Arraylists
       try {
            Scanner sc = new Scanner(new FileReader("book.txt"));
            while(sc.hasNext()) {
                String[] bookInfo = sc.nextLine().split(",");
                tempArrayName.add(bookInfo[0]);
                tempArrayPrice.add(Double.parseDouble(bookInfo[1]));;
            }
        } catch (IOException exception) {
                exception.printStackTrace();
            }
    }
    //added 2 ArrayLists for name and price to be added to table later
    public static ArrayList<String> tempArrayName = new ArrayList<>();
    public static ArrayList<Double> tempArrayPrice = new ArrayList<>();
    
    
    public void OwnerStartScreen(Stage primaryStage){
        System.out.println("owner screen");
        
        primaryStage.setTitle("Welcome, Owner!");
        Button books = new Button("Books");
        Button customers = new Button("Customers");
        Button logout = new Button("Logout");
        
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(8);
        gridPane.add(books, 1, 10, 1, 1);
        gridPane.add(customers, 2, 10, 1, 1);
        gridPane.add(logout, 3, 10, 1, 1);
        
        books.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("clicked book button");
                OwnerBookScreen(primaryStage);
            }
        });
        customers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("clicked customer buton");
                OwnerCustomerScreen(primaryStage);
            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start(primaryStage);
            }
        });
        
        Scene owner = new Scene(gridPane, 290, 250);
        primaryStage.setScene(owner);
        primaryStage.show();

    }

    

    
    public void OwnerBookScreen(Stage primaryStage){
        System.out.println("Owner Book Screen");
        
        TableView table = new TableView<>(); //Create table
        
        TableColumn nameColumn = new TableColumn<Book, String>("Book Name"); //Create book name column
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        
        TableColumn priceColumn = new TableColumn<Book, Double>("Book Price"); //Create book price column
        priceColumn.setCellValueFactory(new PropertyValueFactory<Book, Double>("bookPrice"));
        
        table.getColumns().add(nameColumn);
        table.getColumns().add(priceColumn);
        
        TextField nameBook = new TextField(); //Create book name textfield
        nameBook.setPromptText("Book Name");
        TextField priceBook = new TextField(); //Create book price textfield
        priceBook.setPromptText("Book Price");
              
        Button addBtn = new Button("Add"); //add books button
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                String a = nameBook.getText(); //temporary string for book name
                ArrayList<Book> tempBooks = new ArrayList<Book>(); //temporary array for book
                Double b = Double.parseDouble(priceBook.getText()); //temporary double for book price
                
                table.getItems().add(new Book(a, b));
                nameBook.clear();
                priceBook.clear();
                Book bk = new Book(a,b); //temporary book to add to array
                tempBooks.add(bk);
                try {
                    files.writeBookArray(tempBooks); //add books to book.txt
                System.out.println("Files updated with current array data");
                } catch (IOException exception) {
                    exception.printStackTrace();    
            }
                }
        });
        
        Button delBtn = new Button("Delete"); //delete books button
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("Deleted");
                    int row = table.getSelectionModel().getSelectedIndex();
                    if(row>=0){
                        table.getItems().remove(row);
                        table.getSelectionModel().clearSelection();
                    }
                    }
                });
        
        Button backBtn = new Button("Back"); //back to OwnerStartScreenButton
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("back");
                    OwnerStartScreen(primaryStage);
                    }
                });
        
        VBox root = new VBox(); //create VBox
        
        
        Scene b = new Scene(root, 500, 500);
        primaryStage.setScene(b);
        primaryStage.show();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        table.getItems().add(new Book("A death in the family", 200.0));
        //add books from "book.txt" to table
        int sizeOne =0;
        int sizeTwo = tempArrayName.size();
        while(sizeOne<sizeTwo){
            table.getItems().add(new Book(tempArrayName.get(sizeOne), tempArrayPrice.get(sizeOne)));
            sizeOne++;
        }
        
        
        HBox mid = new HBox(); //middle HBox for name and price textfields and add button
        mid.setSpacing(10);
        mid.setPadding(new Insets(10, 10, 10, 10));
        mid.getChildren().addAll(nameBook,priceBook,addBtn);
        mid.setAlignment(Pos.TOP_CENTER);
                
        HBox bottom = new HBox(); //bottom HBox for deleting books and back button
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.getChildren().addAll(delBtn,backBtn);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        
        root.getChildren().addAll(table,mid,bottom);
        root.setAlignment(Pos.TOP_CENTER);
         
    }
    
    //added this
    ObservableList<Book> books = FXCollections.observableArrayList();
    public static final TextFiles files = new TextFiles();
    public final Owner owner = new Owner();
    
    public void OwnerCustomerScreen(Stage primaryStage){
        System.out.println("Owner Customer Screen");
        
        TableView table = new TableView<>(); //Create table
        
        TableColumn usernameColumn = new TableColumn<Customer, String>("Username"); //Create Username column
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("username"));
        
        TableColumn passwordColumn = new TableColumn<Customer, String>("Password"); //Create Password column
        passwordColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("password"));
        
        TableColumn pointsColumn = new TableColumn<Customer, Integer>("Points"); //Create Points column
        pointsColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("points"));
        
        table.getColumns().add(usernameColumn);
        table.getColumns().add(passwordColumn);
        table.getColumns().add(pointsColumn);
        
        TextField username = new TextField(); //Create username textfield
        username.setPromptText("Username");
        TextField password = new TextField(); //Create password textfield
        password.setPromptText("Password");
              
        Button addBtn = new Button("Add"); //add customers button
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                String a = username.getText(); //temporary string for username
                ArrayList<Customer> tempCustomers = new ArrayList<Customer>(); //temporary array for customer
                String b = password.getText(); //temporary string for password
                
                table.getItems().add(new Customer(a, b));
                username.clear();
                password.clear();
                Customer cust = new Customer(a,b); //temporary customer to add to array
                tempCustomers.add(cust);
                try {
                    files.writeCustomerArray(tempCustomers); //add customers to customer.txt
                System.out.println("Files updated with current array data");
                } catch (IOException exception) {
                    exception.printStackTrace();    
            }
                }
            });
        
        
        Button delBtn = new Button("Delete"); //delete customers button
        delBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("Deleted");
                    int row = table.getSelectionModel().getSelectedIndex();
                    if(row>=0){
                        table.getItems().remove(row);
                        table.getSelectionModel().clearSelection();
                    }
                    }
                });
        
        Button backBtn = new Button("Back"); //back to OwnerStartScreenButton
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("back");
                    OwnerStartScreen(primaryStage);
                    }
                });
        
        VBox root = new VBox(); //create VBox
        
        
        Scene b = new Scene(root, 500, 500);
        primaryStage.setScene(b);
        primaryStage.show();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

        
        HBox mid = new HBox(); //middle HBox for username and password textfields and add button
        mid.setSpacing(10);
        mid.setPadding(new Insets(10, 10, 10, 10));
        mid.getChildren().addAll(username,password,addBtn);
        mid.setAlignment(Pos.TOP_CENTER);
                
        HBox bottom = new HBox(); //bottom HBox for deleting customers and back button
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.getChildren().addAll(delBtn,backBtn);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        
        root.getChildren().addAll(table,mid,bottom);
        root.setAlignment(Pos.TOP_CENTER);
         
    }
        
    
    public void CustomerStartScreen(Stage primaryStage, Customer currentCustomer){
        System.out.println("Customer screen");
             
        primaryStage.setTitle("FrontEndGUI");
        GridPane g = new GridPane();
        g.setAlignment(Pos.TOP_LEFT);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(20,20,20,20));
                
        Label welcome = new Label("Welcome " + currentCustomer.getUsername() + ".\nYou have " + currentCustomer.getPoints() + " points. Your status is " + currentCustomer.getStatus());                   
        welcome.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        
        TableView table = new TableView<>(); //Create table
        
        TableColumn nameColumn = new TableColumn<Book, String>("Book Name"); //Create book name column
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        nameColumn.setMinWidth(200);
        
        TableColumn priceColumn = new TableColumn<Book, Double>("Book Price"); //Create book price column
        priceColumn.setCellValueFactory(new PropertyValueFactory<Book, Double>("bookPrice"));
        priceColumn.setMinWidth(100);
        
        TableColumn selectColumn = new TableColumn("Select"); //Create Select column
        selectColumn.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("select"));
        selectColumn.setMinWidth(50);
        
        table.getColumns().add(nameColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(selectColumn);
        
        table.getItems().add(new Book("Harry Potter", 200.0));
        
        Button buyBtn = new Button("Buy"); //Go to CustomerCostScreen
        buyBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("Buy");
                    CustomerCostScreen(primaryStage, currentCustomer);
                    }
                    
                });
        
        Button redeemBtn = new Button("Redeem points and Buy"); //go to CustomerCostScreen
        redeemBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("redeem");
                    CustomerCostScreen(primaryStage, currentCustomer);
                    }
                });
        
        Button logoutBtn = new Button("Logout"); //back to login screen
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("logout");
                    start(primaryStage);
                    }
                });
        VBox root = new VBox(); //create VBox
        
        
        Scene b = new Scene(root, 500, 500);
        primaryStage.setScene(b);
        primaryStage.show();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

        HBox top = new HBox(10);
        top.setAlignment(Pos.TOP_CENTER);
        top.getChildren().addAll(welcome);
                
        HBox bottom = new HBox(); //bottom HBox for deleting customers and back button
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.getChildren().addAll(buyBtn,redeemBtn,logoutBtn);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        
        root.getChildren().addAll(top,table,bottom);
        root.setAlignment(Pos.TOP_CENTER);
               
    }
    
    public void CustomerCostScreen(Stage primaryStage, Customer currentCustomer){
        System.out.println("Customer screen");
             
        primaryStage.setTitle("FrontEndGUI");
        GridPane g = new GridPane();
        g.setAlignment(Pos.TOP_LEFT);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(20,20,20,20));
                
        Label totalCost = new Label("Total Cost: TC");                   
        totalCost.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        
        Label pointsStatus = new Label("Points: " + currentCustomer.getPoints() + ", Status: " + currentCustomer.getStatus());                   
        pointsStatus.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        
        Button logoutBtn = new Button("Logout"); //back to login screen
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent event){
                    System.out.println("logout");
                    start(primaryStage);
                    }
                });
        VBox root = new VBox(); //create VBox
        
        
        Scene b = new Scene(root, 500, 500);
        primaryStage.setScene(b);
        primaryStage.show();

        HBox top = new HBox(10);
        top.setSpacing(10);
        top.setPadding(new Insets(100, 100, 50, 100));
        top.setAlignment(Pos.TOP_LEFT);
        top.getChildren().addAll(totalCost);
        
        HBox mid = new HBox(); 
        mid.setSpacing(10);
        mid.setPadding(new Insets(50, 100, 100, 100));
        mid.getChildren().addAll(pointsStatus);
        mid.setAlignment(Pos.TOP_LEFT);
                
        HBox bottom = new HBox(); //bottom HBox for deleting customers and back button
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(100, 50, 10, 50));
        bottom.getChildren().addAll(logoutBtn);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        
        root.getChildren().addAll(top,mid,bottom);
        root.setAlignment(Pos.TOP_CENTER);
             
    }
    
    public static void main(String[] args) {
        launch(args);
    } 
}
