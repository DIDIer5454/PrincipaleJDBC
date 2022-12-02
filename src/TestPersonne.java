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
    Personne p;

    /**/  @BeforeEach
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
/**/
      @AfterEach
      public void end() throws SQLException {
          Personne.deleteTable();
          DBConnection.closeConnection();
      }
  /**/
    @Test
    public void testFindById() throws SQLException {
        //FinById
         p = Personne.findById(3);
        Personne test = new Personne("Kubrick", "Stanley");
        System.out.println();
        assertEquals(test.getNom(), p.getNom());
        assertEquals(test.getPrenom(), p.getPrenom());
        assertEquals(3, p.getId());
    }
    @Test
    public void testFindByName() throws SQLException {
//FindByName
        ArrayList ps = Personne.findByName("Kubrick");
        for (Object o : ps) {
            p = (Personne) o;
            assertTrue(p.getNom() == "Kubrick");
        }
    }
        @Test
        public void TestFindAll() throws SQLException {
        //findAll
        ArrayList ps = Personne.findAll();
        assertTrue(ps.size() == 4);
    }

    @Test
    public void testSaveNew() throws SQLException {
        Personne p = new Personne("Jerome", "Jerome");
        int i = Personne.findAll().size();
        p.save();
        assertEquals(p.getId(), i+1);
        assertEquals(i+1, Personne.findAll().size());
    }

    @Test
    public void testSaveUpdate() throws SQLException {
        List personnes = Personne.findAll();
        Personne j = (Personne) personnes.get(2);
        int i = Personne.findAll().size();
        j.save();
        assertEquals(i, Personne.findAll().size());
    }
@Test
    public void testDelete() throws SQLException {
        int i=Personne.findAll().size();
        Personne pers=Personne.findById(2);
        pers.delete();
        assertEquals(Personne.findAll().size(),i-1);
}
}
