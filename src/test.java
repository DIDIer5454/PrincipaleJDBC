import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test
    public void testNbconnexion() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertTrue(c1 == c2);
    }

    @Test
    public void testSetConnexion() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        DBConnection.setNomDB("test");
        c2 = DBConnection.getConnection();
        //Si une nouvelle connexion est genere alors
        //un nouvelle objet connexion est créé
        // après getConnection() les reference sont donc differentes
        assertFalse(c1 == c2);
    }

   }
