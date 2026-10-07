/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

/**
 *
 * @author mmt
 */
import DataAccessLayer.Login_DAO;

public class Login_BLL {

    private final Login_DAO loginDAO;

    // Constructor Injection
    public Login_BLL(Login_DAO loginDAO) {

        this.loginDAO = loginDAO;
    }

    public boolean login(
            String email,
            String password) {

        if (email == null ||
            email.trim().isEmpty()) {

            return false;
        }

        if (password == null ||
            password.trim().isEmpty()) {

            return false;
        }

        return loginDAO.validateLogin(
                email.trim(),
                password
        );
    }
}