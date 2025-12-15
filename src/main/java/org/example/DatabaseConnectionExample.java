package org.example;

import org.example.enums.ExceptionMessagesEnum;
import org.example.enums.UserRoleEnum;
import org.example.exceptions.*;
import org.example.model.user.User;
import org.example.model.user.UserDAOInterface;


import org.example.enums.ExceptionMessagesEnum;
import org.example.enums.UserRoleEnum;
import org.example.exceptions.UserNotFoundException;
import org.example.exceptions.DAOException;
import org.example.exceptions.PropertyException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.exceptions.UnrecognizedRoleException;
import org.example.exceptions.ObjectNotFoundException;
import org.example.exceptions.MissingAuthorizationException;
import org.example.exceptions.WrongListQueryIdentifierValue;
import org.example.instances_management_abstracts.DAODBAbstract;
import org.example.model.role.Amministratore.AmministratoreLazyFactory;
import org.example.model.role.Cliente.ClienteLazyFactory;
import org.example.model.role.Kebabbaro.KebabbaroLazyFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.List;

 public abstract class DatabaseConnectionExample extends DAODBAbstract<User> implements UserDAOInterface {




    public static void main(String[] args) {
        // Parametri di connessione
        String url = "jdbc:mysql://localhost:3306/ispwtwo";
        String username = "root";
        String password = "rootroot";

        String query = "SELECT ID, name, surname, codice_fiscale, email, password, registration_date, role FROM USER WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Imposta il parametro della query
            statement.setString(1, "mario.rossi@example.com");

            // Esegui la query
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String codiceFiscale = rs.getString("codice_fiscale");
                    String email = rs.getString("email");
                    String pwd = rs.getString("password");
                    Date registrationDate = rs.getDate("registration_date");
                    int role = rs.getInt("role");

                    System.out.println("ID: " + id);
                    System.out.println("Nome: " + name);
                    System.out.println("Cognome: " + surname);
                    System.out.println("Codice Fiscale: " + codiceFiscale);
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + pwd);
                    System.out.println("Data Registrazione: " + registrationDate);
                    System.out.println("Ruolo: " + role);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore connessione DB: " + e.getMessage());
            e.printStackTrace();
        }

















    }
}
