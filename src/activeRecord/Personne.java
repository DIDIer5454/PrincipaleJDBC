package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Personne {
    int id;
    String nom;
    String prenom;

    public Personne(String n, String p) {
        this.id = -1;
        this.nom = n;
        this.prenom = p;
    }


    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList Personnes = new ArrayList<Personne>();
        //On genere une connection
        Connection connection = DBConnection.getConnection();
        Statement stm = connection.createStatement();
        ResultSet r = stm.executeQuery("SELECT * from Personne");
        while (r.next()) {
            String n = r.getString("nom");
            String p = r.getString("prenom");
            String i = r.getString("id");
            Personne pers = new Personne(n, p);
            pers.id = r.getInt("id");
            Personnes.add(pers);
        }
        return Personnes;
    }

    public static Personne findById(int j) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, j);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String n = rs.getString("nom");
            String p = rs.getString("prenom");
            int id = rs.getInt("id");
            return new Personne(n, p);
        }

        return null;
    }

    public static ArrayList<Personne> findByName(String n) throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList Personnes = new ArrayList<Personne>();
        String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1, n);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String p = rs.getString("prenom");
            Personnes.add(new Personne(n, p));
        }
        return Personnes;
    }
////////////////////////////////////

    /**
     * 7.4)La gestion de la connexion a permis de simplifier l'accès
     * à la base de donnée dans le sens où on peut remarquer
     * que toute l'étape de configuration n'est plus à refaire
     * et il suffit juste de récupérer la connexion chaque fois que necessaire.
     */
    ////////////////////////
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        // creation de la table Personne
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Personne";
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
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep;
        // l'option RETURN_GENERATED_KEYS permet de recuperer l'id (car
        // auto-increment)
        prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        //On recupere l'id
        String idAuto = "SELECT id from Personne where nom =? and prenom =?";
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
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prepa = connect.prepareStatement(SQLprep);
        prepa.setString(1, this.nom);
        prepa.setString(2, this.prenom);
        prepa.setInt(3, this.id);
        prepa.execute();
    }

    public boolean equals(Personne p){
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
