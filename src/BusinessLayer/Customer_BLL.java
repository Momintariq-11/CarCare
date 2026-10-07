package BusinessLayer;

import DataAccessLayer.Customer_DAO;
import java.util.List;

public class Customer_BLL {
    private Customer_DAO dao = new Customer_DAO();

    public List<Customer> getAllCustomers() { return dao.getAllCustomers(); }
    public boolean updateCustomer(Customer c) { return dao.updateCustomer(c); }
    public boolean deleteCustomer(int id) { return dao.deleteCustomer(id); }
}
