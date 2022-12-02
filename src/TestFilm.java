import activeRecord.Film;
import activeRecord.Personne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFilm {
    Film p;

    @BeforeEach
    public void init() throws SQLException {
        new TestPersonne().init();
        Film.createTable();
        String[] titres = {"Arche perdue", "Alien", "Temple Maudit", "Blade Runner", "Alien3", "Fight Club", "Orange Mecanique"};
        int[] realisateurs = {1, 2, 1, 2, 4, 4, 3};
        for (int i = 0; i < 7; i++) {
            new Film(titres[i], Personne.findById(realisateurs[i])).save();
        }
    }

    @AfterEach
    public void end() throws SQLException {
        new TestPersonne().end();
        Film.deleteTable();
    }

    @Test
    public void testFindById() throws SQLException {
        //FinById
        p = Film.findById(2);
        System.out.println(p);
        assertEquals(2, p.getId());
        assertEquals("Alien", p.getTitre());
        assertEquals(2, p.getRealisateur().getId());
    }

     @Test
      public void testFindRealisateur() throws SQLException {
  //FindByName
          ArrayList ps = Film.findByRealisateur(Personne.findById(4));
          for (Object o : ps) {
              p = (Film) o;
              assertTrue(p.getRealisateur().equals(Personne.findById(4)));
          }
      }
    @Test
    public void TestFindAll() throws SQLException {
        //findAll
        ArrayList ps = Film.findAll();
        assertTrue(ps.size() == 7);
    }

    @Test
    public void testSaveNew() throws SQLException {
        Film p = new Film("Medusa", Personne.findById(2));
        int i = Film.findAll().size();
        p.save();
        assertEquals(p.getId(), i + 1);
        assertEquals(i + 1, Film.findAll().size());
    }

    @Test
    public void testSaveUpdate() throws SQLException {
        List Films = Film.findAll();
        Film j = (Film) Films.get(2);
        int i = Film.findAll().size();
        j.save();
        assertEquals(i, Film.findAll().size());
    }
@Test
    public void testConstructeurFilmPersonnePasBd() throws SQLException {
        Personne p1=new Personne("Test","test");
        Film f1=new Film("medusa",p1);
        assertEquals(f1.getRealisateur().getId(),p1.getId());
}
}
