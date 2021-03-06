import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        // Load the SQLite JDBC driver (JDBC class implements java.sql.Driver)
        Class.forName("org.sqlite.JDBC");

        // Create a DB connection
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:contactngr.db")) {

            // Create a SQL statement
            Statement statement = connection.createStatement();

            // Create a DB table
            statement.executeUpdate("DROP TABLE IF EXISTS contacts");
            statement.executeUpdate("CREATE TABLE contacts (id INTEGER PRIMARY KEY, firstname STRING, lastname STRING, email STRING, phone INT(10))");

            // Insert a couple contacts
            Contact k = new Contact("Kenneth", "Black",
                    "lawliet@email.com", 5418675309L);
            Contact b = new Contact("Brigette", "Eckert",
                    "brigette@email.com", 5418675308L);

            save(k, statement);
            save(b, statement);


            // Fetch all the records from the contacts table
            ResultSet rs = statement.executeQuery("SELECT * FROM contacts");

            // TODO: Iterate over the ResultSet & display contact info
            while(rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                System.out.printf("%s %s (%s)%n", firstname, lastname, id);
            }

        } catch (SQLException ex) {
            // Display connection or query errors
            System.err.printf("There was a database error: %s%n",ex.getMessage());
        }
    }

    public static void save(Contact contact, Statement statement) throws SQLException {
        String sql = "INSERT INTO contacts (firstname, lastname, email, phone) VALUES ('%s', '%s', '%s', " +
                contact.getPhone() + ")";
        sql = String.format(sql,
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail());
        statement.executeUpdate(sql);
    }
}