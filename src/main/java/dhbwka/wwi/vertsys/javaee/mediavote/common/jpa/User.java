/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.common.jpa;

import dhbwka.wwi.vertsys.javaee.mediavote.episode.web.EpisodeListServlet;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Datenbankklasse für einen Benutzer.
 */
@Entity
@Table(name = "MEDIAVOTE_USER")
public class User implements Serializable {
    
    private static final Logger logger = Logger.getLogger(EpisodeListServlet.class.getName());

    private static final long serialVersionUID = 1L;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Der Benutzername muss zwischen fünf und 64 Zeichen lang sein.")
    @NotNull(message = "Der Benutzername darf nicht leer sein.")
    private String username;
    
    public class Password {
        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
        public String password = "";
    }
    @Transient
    private final Password password = new Password();

    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwordHash;
    
    @Column(name = "FIRSTNAME", length = 64)
    @Size(min = 2, max = 64, message = "Der Vorname muss zwischen zwei und 64 Zeichen lang sein.")
    private String firstName;
    
    //Nachname: Optional
    @Column(name = "LASTNAME", length = 64)
    @Size(min = 0, max = 64, message = "Der Nachname darf maximal 64 Zeichen lang sein.")
    private String lastName;

    @ElementCollection
    @CollectionTable(
            name = "MEDIAVOTE_USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public User() {
    }
    //Ursprünglicher Konstruktor
    public User(String username, String password) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }
    //Konstruktor mit Vor- und Nachname
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet der Hash-Wert zu einem Passwort.
     *
     * @param password Passwort
     * @return Hash-Wert
     */
    private String hashPassword(String password) {
        byte[] hash;

        if (password == null) {
            password = "";
        }

        // Hashwert zum Passwort berechnen
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        // Hashwert in einen Hex-String umwandeln
        // Vgl. https://stackoverflow.com/a/9855338
        char[] hashHex = new char[hash.length * 2];

        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            hashHex[i * 2] = HEX_ARRAY[v >>> 4];
            hashHex[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return new String(hashHex);
    }

    /**
     * Berechnet einen Hashwert aus dem übergebenen Passwort und legt ihn im
     * Feld passwordHash ab. Somit wird das Passwort niemals als Klartext
     * gespeichert.
     * 
     * Gleichzeitig wird das Passwort im nicht gespeicherten Feld password
     * abgelegt, um durch die Bean Validation Annotationen überprüft werden
     * zu können.
     *
     * @param password Neues Passwort
     */
    public void setPassword(String password) {
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }

    /**
     * Nur für die Validierung bei einer Passwortänderung!
     * @return Neues, beim Speichern gesetztes Passwort
     */
    public Password getPassword() {
        return this.password;
    }
    
    /**
     * Prüft, ob das übergebene Passwort korrekt ist.
     *
     * @param password Zu prüfendes Passwort
     * @return true wenn das Passwort stimmt sonst false
     */
    public boolean checkPassword(String password) {
        return this.passwordHash.equals(this.hashPassword(password));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Zuordnung zu Benutzergruppen">
    /**
     * @return Eine unveränderliche Liste aller Benutzergruppen
     */
    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        if(this.groups == null || this.groups.isEmpty()) {
            logger.log(Level.INFO, "UserName: " + this.username);
            logger.log(Level.INFO, "Keine Gruppen");
        }
        else {
            logger.log(Level.INFO, "Liste der Gruppen:" + this.groups);
            this.groups.forEach((groupname) -> {
            logger.log(Level.INFO, "Gruppe" + groupname);
            groupsCopy.add(groupname);
        });
        }
        

        return groupsCopy;
    }

    /**
     * Fügt den Benutzer einer weiteren Benutzergruppe hinzu.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void addToGroup(String groupname) {
        logger.log(Level.INFO, "Gruppe hinzufügen");
        if (!this.groups.contains(groupname)) {
            logger.log(Level.INFO, "Gruppe hinzugefügt");
            this.groups.add(groupname);
        }
    }

    /**
     * Entfernt den Benutzer aus der übergebenen Benutzergruppe.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }
    //</editor-fold>

}
