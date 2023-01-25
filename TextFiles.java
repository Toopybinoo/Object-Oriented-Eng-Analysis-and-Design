import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TextFiles {

    public void writeBookArray(ArrayList<Book> bookTextFile) throws IOException{
        FileWriter textBook = new FileWriter("book.txt", true);
        for(Book books: bookTextFile){
            String bookInfo = books.getBookName() + ", " + books.getBookPrice() + "\n";
            textBook.write(bookInfo);
        }
        textBook.close();
    }//takes book info from arraylist and writes into "book.txt" file

    public void writeCustomerArray(ArrayList<Customer> customerTextFile) throws IOException{
        FileWriter textCustomer = new FileWriter("customer.txt", true);
        for(Customer customers: customerTextFile){
            String customerInfo = customers.getUsername() + ", " + customers.getPassword() + ", " + customers.getPoints() + "\n";
            textCustomer.write(customerInfo);
        }
        textCustomer.close();
    }//takes customer info from arraylist and writes into "customer.txt" file 

    public void clearBookArray() throws IOException {
        FileWriter textBook = new FileWriter("book.txt", false);
        textBook.close();
    }//clears out "book.txt"

    public void clearCustomerArray() throws IOException {
        FileWriter textCustomer = new FileWriter("customer.txt", false);
        textCustomer.close();
    }//clears out "customer.txt"

    public ArrayList<Book> readBookFile() throws IOException{
        Scanner sc = new Scanner(new FileReader("book.txt"));
        ArrayList<Book> tempBookArray = new ArrayList<>();

        while(sc.hasNext()) {
            String[] bookInfo = sc.nextLine().split(",");
            String title = bookInfo[0];
            double price = Double.parseDouble(bookInfo[1]);
            tempBookArray.add(new Book(title, price));
        }
        return tempBookArray;
    }//transfers book data from "book.txt" to book array

    public ArrayList<Customer> readCustomerFile() throws IOException{
        Scanner sc = new Scanner(new FileReader("customer.txt"));
        ArrayList<Customer> tempCustomerArray = new ArrayList<>();

        while(sc.hasNext()) {
            String[] customerInfo = sc.nextLine().split(", ");
            String username = customerInfo[0];
            String password = customerInfo[1];
            int points = Integer.parseInt(customerInfo[2]);
            tempCustomerArray.add(new Customer(username, password));
            tempCustomerArray.get(tempCustomerArray.size()-1).setPoints(points);
        }
        return tempCustomerArray;
    }//transfers customer data from "customer.txt" to customer array

}
