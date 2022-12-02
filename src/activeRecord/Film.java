package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    /**
     * Constructeur de film
     *
     * @param t
     * @param p
     */
    public Film(String t, Personne p) throws SQLException {
        this.titre = t;
        p.save();
        this.id_real = p.getId();
        this.id = -1;
    }

    /**
     * Constructeur privé de film ,il servira à creer des film
     * à partir des requêtes
     *
     * @param t
     * @param id1
     * @param id_real1
     */
    private Film(String t, int id1, int id_real1) {
        this.titre = t;
        this.id = id1;
        this.id_real = id_real1;
    }

    /**
     * Methode retournant la liste de tous les films de la base de donnée
     *
     * @return
     * @throws SQLException
     */
    public static ArrayList<Film> findAll() throws SQLException {
        ArrayList films = new ArrayList<Film>();
        //On genere une connection
        Connection connection = DBConnection.getConnection();
        Statement stm = connection.createStatement();
        ResultSet r = stm.executeQuery("SELECT * from Film");
        while (r.next()) {
            films.add(new Film(r.getString("titre"), r.getInt("id"), r.getInt("id_real")));
        }
        return films;
    }

    /**
     * Methode retournant un objet film correspondant à la bd
     *
     * @param j
     * @return
     * @throws SQLException
     */
    public static Film findById(int j) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Film WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, j);
        prep1.execute();
        ResultSet r = prep1.getResultSet();
        // s'il y a un resultat
        if (r.next()) {
            return new Film(r.getString("titre"), r.getInt("id"), r.getInt("id_real"));
        }

        return null;
    }

    /**
     * Methode retournant la liste des films avec le même réalisateur
     *
     * @param n
     * @return
     * @throws SQLException
     */
    public static ArrayList<Film> findByRealisateur(Personne n) throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList films = new ArrayList<Film>();
        String SQLPrep = "SELECT * FROM Film f inner join Personne p on f.id_real=p.id WHERE p.id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, n.getId());
        prep1.execute();
        ResultSet r = prep1.getResultSet();
        // s'il y a un resultat
        while (r.next()) {
            films.add(new Film(r.getString("titre"), r.getInt("id"), r.getInt("id_real")));
        }
        return films;
    }

    /**
     * Methode creant une table film
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        // creation de la table Film
        String createString = "CREATE TABLE `Film` (`id` int(11) NOT NULL,`titre` varchar(40) NOT NULL,`id_real` int(11) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        String modif1 = "ALTER TABLE `Film`ADD PRIMARY KEY (`id`),ADD KEY `id_real` (`id_real`);";
        String modif2 = "ALTER TABLE `Film` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
        stmt.executeUpdate(modif1);
        stmt.executeUpdate(modif2);
    }


    /**
     * Fonction permettant la suppression de la table Film
     *
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    /**
     * Fonction permettant d'enregistrer ou mettre à jour un film
     *
     * @throws SQLException
     */
    public void save() throws SQLException {
        if (this.id == -1) {
            this.saveNew();
        } else if (findById(this.id) != null) {
            this.update();
        }
    }

    /**
     * Fonction privée enregistrant un nouveau film
     * cette fonction est une sous-fonction de la fonction save()
     * avec le pattern...
     *
     * @throws SQLException
     */
    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Film (titre, id_real) VALUES (?,?);";
        PreparedStatement prep;
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.executeUpdate();
        //On recupere l'id
        String idAuto = "SELECT id from Film where titre =? and id_real =?";
        PreparedStatement prep1 = connect.prepareStatement(idAuto);
        prep1.setString(1, this.titre);
        prep1.setInt(2, this.id_real);

        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat on recupere le dernier de meme titre et id_real
        while (rs.next()) {
            this.id = rs.getInt("id");
        }
    }

    /**
     * Methode privé permettant la mise à jour de l'id si le film est déja dans la base de donné
     *
     * @throws SQLException
     */
    private void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Film set id_real=?, titre=? where id=?;";
        PreparedStatement prepa = connect.prepareStatement(SQLprep);
        prepa.setInt(1, this.id_real);
        prepa.setString(2, this.titre);
        prepa.setInt(3, this.id);
        prepa.execute();
    }

    /**
     * réécriture de equals car on utilisera la methode contains
     *
     * @param p
     * @return
     */
    public boolean equals(Film p) {
        return (this.id == p.id && this.id_real == p.id_real && this.id == p.id);
    }

    public Personne getRealisateur() throws SQLException {
        Connection connection = DBConnection.getConnection();
        Personne pers = null;
        String requete = "SELECT * from Personne p where p.id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(requete);
        preparedStatement.setInt(1, this.id_real);
        ResultSet r = preparedStatement.executeQuery();
        if (r.next()) {
            pers = Personne.findById(r.getInt("id"));
        }
        return pers;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) throws SQLException {
        this.titre = titre;
        this.save();
    }

    @Override
    public String toString() {
        return "Titre: "+this.titre+" id_real: "+this.id_real+" id: "+this.id;
    }
}
