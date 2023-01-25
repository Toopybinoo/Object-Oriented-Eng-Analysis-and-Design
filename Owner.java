/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.io.IOException;

/**
 *
 * @author TubaAdil
 */
public class Owner{
    private static final TextFiles files = new TextFiles();
    protected static ArrayList<Book> books= new ArrayList<>();
    private static final ArrayList<Customer> customers= new ArrayList<>();
    
    public void collectData()throws IOException{
        ArrayList<Book> tempBooks = files.readBookFile();
        ArrayList<Customer> tempCustomers = files.readCustomerFile();
        books.addAll(tempBooks);
        customers.addAll(tempCustomers);
    }
    
    public String getUsername(){
        return "admin";
    }
    
    public String getPassword(){
        return "admin";
    }
    
    public void addCustomer(Customer newCustomer){
        customers.add(newCustomer);
    }
    
    public void deleteCustomer(Customer deleteCustomer){
        customers.remove(deleteCustomer);
    }
    
    public void addBook(Book newRead){
        books.add(newRead);
    }
    
    public void deleteBook(Book deleteRead){
        books.remove(deleteRead);
    }
    //added this from references, remember to change it
    @SuppressWarnings("unchecked")
    public ArrayList<Customer> getCustomers(){
        return (ArrayList<Customer>) customers.clone();
    }
}
