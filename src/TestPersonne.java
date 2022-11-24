import activeRecord.DBConnection;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPersonne {
    Connection connection;

    @BeforeEach
    public void init() throws SQLException {
        Personne.createTable();
        connection = DBConnection.getConnection();
        String[][] NomPrenom = {{"Spielberg", "Steven"},
                {"Scott", "Ridley"},
                {"Kubrick", "Stanley"},
                {"Fincher", "David"}};
        for (String[] n : NomPrenom) {
            new Personne(n[0], n[1]).save();
        }
    }

    @AfterEach
    public void end() throws SQLException {
        Personne.deleteTable();
        DBConnection.closeConnection();
    }

    @Test
    public void testFind() throws SQLException {
        //FinById
        Personne p = Personne.findById(3);
        Personne test = new Personne("Kubrick", "Stanley");
        assertTrue(p.equals(test));
        //FindByName
        ArrayList ps = Personne.findByName("Kubrick");
        int i = 0;
        for (Object o : ps) {
            p = (Personne) o;
            assertTrue(p.getNom() == "Kubrick");
            i++;
        }
        //findAll
        ps = Personne.findAll();
        assertTrue(ps.size() == 4);
    }

    @Test
    public void testSaveNew() throws SQLException {
        Personne p = new Personne("Jerome", "Jerome");
        int i = Personne.findAll().size();
        p.save();
        int j = i + 1;
        assertEquals(p.getId(), j);
        assertEquals(j, Personne.findAll().size());
    }

    @Test
    public void testSaveUpdate() throws SQLException {
        List personnes = Personne.findAll();
        Personne j = (Personne) personnes.get(2);
        int i = Personne.findAll().size();
        j.save();
        assertEquals(i, Personne.findAll().size());
    }

}
