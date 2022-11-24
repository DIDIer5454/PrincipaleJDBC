package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    /**
     * Constructeur de film
     * @param t
     * @param p
     */
    public Film(String t, Film p) {
        this.titre = t;
        this.id_real = p.getId();
        this.id=-1;
    }

    /**
     * Constructeur privé de film ,il servira à creer des film
     * à partir des requêtes
     * @param t
     * @param id1
     * @param id_real1
     */
    private Film(String t,int id1,int id_real1){
        this.titre=t;
        this.id=id1;
        this.id_real=id_real1;
    }

    /**
     * Methode retournant la liste de tous les films de la base de donnée
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
            films.add(new Film(r.getString("titre"),r.getInt("id"),r.getInt("id_real")));
        }
        return films;
    }

    /**
     * Methode retournant un objet film correspondant à la bd
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
           return  new Film(r.getString("titre"),r.getInt("id"),r.getInt("id_real"));
        }

        return null;
    }

    /**
     * Methode retournant la liste des films avec le même réalisateur
     * @param n
     * @return
     * @throws SQLException
     */
    public static ArrayList<Film> findByRealisateur(String n) throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList films = new ArrayList<Film>();
        String SQLPrep = "SELECT * FROM Film WHERE nom=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1, n);
        prep1.execute();
        ResultSet r = prep1.getResultSet();
        // s'il y a un resultat
        while (r.next()) {
            films.add(new Film(r.getString("titre"),r.getInt("id"),r.getInt("id_real")));
        }
        return films;
    }

    /**
     * Methode creant une table film
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        // creation de la table Film
        String createString = "CREATE TABLE `Film` (`id` int(11) NOT NULL,`titre` varchar(40) NOT NULL,`id_rea` int(11) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=latin1;" +
                "PRIMARY KEY (ID))"+"ALTER TABLE `Film" +
                "  ADD PRIMARY KEY (`id`)," +
                "  ADD KEY `id_rea` (`id_rea`);"+
                "ALTER TABLE `Film`" +
                "  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public void save() throws SQLException {
        if (this.id == -1) {
            this.saveNew();
        } else if (!findById(this.id).equals(this)) {
            System.out.println("update");
            this.update();
        }
    }

    private void saveNew() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Film (nom, prenom) VALUES (?,?);";
        PreparedStatement prep;
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        //On recupere l'id
        String idAuto = "SELECT id from Film where nom =? and prenom =?";
        PreparedStatement prep1 = connect.prepareStatement(idAuto);
        prep1.setString(1, this.nom);
        prep1.setString(2, this.prenom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat on recupere le dernier de meme nom et prenom
        while (rs.next()) {
            int i=0;
            System.out.println(rs.getInt("id"));
            this.id = rs.getInt("id");
        }
    }

    private void update() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Film set nom=?, prenom=? where id=?;";
        PreparedStatement prepa = connect.prepareStatement(SQLprep);
        prepa.setString(1, this.nom);
        prepa.setString(2, this.prenom);
        prepa.setInt(3, this.id);
        prepa.execute();
    }

    public boolean equals(Film p){
        return (this.nom.equals(p.nom)&&this.prenom.equals(p.prenom)&&this.id==p.id);
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }
}
    
}
