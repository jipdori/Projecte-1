package empresa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;


public class Empresa {

    static final String PATHPENDENTS = "files/ENTRADES PENDENTS/";
    static final String PATHPROCESSADES = "files/ENTRADES PROCESSADES/";
    static final String COMANDES = "files/COMANDES/";

    static Scanner teclat = new Scanner(System.in);
    static String opcio;

    static int[] proveidors = new int[100];
    static int[] prodProveidor = new int[100];
    
    static int contprov = 0;
    
    public static void main(String[] args) throws IOException, FileNotFoundException, SQLException {

        Scanner teclat = new Scanner(System.in);

        boolean sortir = false;
        String opcio;

        while (!sortir) {
            System.out.println("===================== MENU PRINCIPAL =======================");
            System.out.println("|                                                          |");
            System.out.println("|                 1. GESTIÓ PRODUCTES (A,B,M,C)            |");
            System.out.println("|                 2. ACTUALITZAR STOCK                     |");
            System.out.println("|                 3. PREPARAR COMANDES                     |");
            System.out.println("|                 4. ANALITZAR COMANDES                    |");
            System.out.println("|                 5. SORTIR                                |");
            System.out.println("|                                                          |");
            System.out.println("============================================================");

            opcio = teclat.next();

            switch (opcio.charAt(0)) {

                case '1':
                    System.out.println("Et trobes en gestió de productes");
                    gestioProductes();
                    break;
                case '2':
                    System.out.println("Et trobes en actualitzar stock");
                    actualitzarStock();
                    break;
                case '3':
                    System.out.println("Et trobes en preparar comandes");
                    prepararComandes();
                    break;
                case '4':
                    System.out.println("Et trobes en analitzar comandes");
                    analitzarComandes();
                    break;
                case '5':
                    System.out.println("Fins una altra.");
                    sortir = true;
                    break;

                default:
                    System.out.println("FORMAT ERRONI");
            }

        }

    }
    
    //==========================================================================

    private static void gestioProductes() {

        System.out.println("=============== GESTIÓ PRODUCTES ===============");
        System.out.println("|                                              |");
        System.out.println("|         1. LLISTA de tots els productes      |");
        System.out.println("|         2. ALTA productes                    |");
        System.out.println("|         3. MODIFICA producte                 |");
        System.out.println("|         4. ESBORRA producte                  |");
        System.out.println("|                                              |");
        System.out.println("================================================");
        System.out.println("\nTRIA UNA OPCIÓ: ");

        int opcio = teclat.nextInt();
        teclat.nextLine();

        switch (opcio) {
            case 1:
                llistarTotsProductes();
                break;
            case 2:
                altaProducte();
                break;
            case 3:
                modificaProducte();
                break;
            case 4:
                esborraProducte();
                break;
        }

    }
    
    //==========================================================================

    static void llistarTotsProductes() {

        System.out.println("Estas accedint a LLISTAR PRODUCTES");

        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "");

            System.out.println("\nMOSTRANT TOTS ELS PRODUCTES...");
            String query = "SELECT * FROM productes";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("==========================================================================================================================");
            System.out.format("%-20s %-20s %-20s %-20s %-20s %-25s", "CODI", "STOCK", "NOM", "MATERIAL", "PREU", "DNI PROVEIDOR");
            System.out.println("");
            System.out.println("==========================================================================================================================");

            while (rs.next()) {
                int codi = rs.getInt("codi");
                int stock = rs.getInt("stock");
                String nom = rs.getString("nom");
                String material = rs.getString("material");
                String preu = rs.getString("preu");
                int dni_proveidor = rs.getInt("dni_proveidor");

                System.out.format("%-20s %-20s %-20s %-20s %-20s %-25s\n", codi, stock, nom, material, preu + "€", dni_proveidor);

            }
            st.close();

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            e.printStackTrace();
        }

        System.out.println("\nApreta ENTER per sortir....");

        teclat.nextLine();
    }
    
    //==========================================================================

    static void altaProducte() {

        System.out.println("ALTA PRODUCTE");
        System.out.println("Introdueix el codi del producte");
        String query = "INSERT INTO productes (codi, stock, nom, material, preu, dni_proveidor) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "");

            PreparedStatement st = conn.prepareStatement(query);

            System.out.println("Codi: ");
            int codi = teclat.nextInt();
            st.setInt(1, codi);
            teclat.nextLine();

            System.out.println("Stock: ");
            int stock = teclat.nextInt();
            st.setInt(2, stock);
            teclat.nextLine();

            System.out.println("Nom: ");
            String nom = teclat.nextLine();
            st.setString(3, nom);

            System.out.println("Material: ");
            String material = teclat.nextLine();
            st.setString(4, material);

            System.out.println("Preu: ");
            String preu = teclat.nextLine();
            st.setString(5, preu);

            System.out.println("dni_proveidor: ");
            int dni_proveidor = teclat.nextInt();
            st.setInt(6, dni_proveidor);
            teclat.nextLine();

            System.out.println("Donant d'alta al producte: ");

            st.executeUpdate();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            e.printStackTrace();
        }

        System.out.println("\nApreta ENTER per sortir....");

        teclat.nextLine();
    }
    
    //==========================================================================

    static void modificaProducte() {

        System.out.println("MODIFICAR PRODUCTE");
        String query = "UPDATE productes SET stock = ?, nom = ?, material = ?, preu = ?, dni_proveidor= ? "
                + "WHERE codi = ?";

        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "");

            PreparedStatement st = conn.prepareStatement(query);

            System.out.println("Stock: ");
            int stock = teclat.nextInt();
            st.setInt(1, stock);
            teclat.nextLine();

            System.out.println("Nom: ");
            String nom = teclat.nextLine();
            st.setString(2, nom);

            System.out.println("Material: ");
            String material = teclat.nextLine();
            st.setString(3, material);

            System.out.println("Preu: ");
            String preu = teclat.nextLine();
            st.setString(4, preu);

            System.out.println("dni_proveidor: ");
            int dni_proveidor = teclat.nextInt();
            st.setInt(5, dni_proveidor);

            System.out.println("Codi: ");
            int codi = teclat.nextInt();
            st.setInt(6, codi);
            teclat.nextLine();

            System.out.println("Modificant producte: ");

            st.executeUpdate();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            e.printStackTrace();
        }

        System.out.println("\nApreta ENTER per sortir....");

        teclat.nextLine();
    }
    
    //==========================================================================

    static void esborraProducte() {

        System.out.println("Escriu el codi de Producte a eliminar:");

        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "");

            String query = "DELETE FROM productes WHERE codi = ?";
            PreparedStatement st = conn.prepareStatement(query);

            opcio = teclat.next();
            System.out.println("Eliminant producte: " + opcio);
            st.setInt(1, Integer.parseInt(opcio));

            st.executeUpdate();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            e.printStackTrace();
        }

        System.out.println("\nApreta ENTER per sortir....");

        teclat.nextLine();
    }

    //==========================================================================
    
    private static void actualitzarStock() throws IOException, FileNotFoundException, SQLException {

        System.out.println("ACTUALITZACIÓ STOCK");

        File file = new File(PATHPENDENTS);
        //file.renameTo(file);
        //System.out.println(file.getAbsolutePath());

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                System.out.println("fitxer: " + files[i]);
                visualitzarActualitzarFitxer(files[i]);
                moureFitxerAProcessat(files[i]);
            }
        } else {
            System.out.println("No es un directori");
        }

    }

    static void visualitzarActualitzarFitxer(File file) throws FileNotFoundException, IOException, SQLException {

        // Llegeix caràcter per caràcter
        FileReader reader = new FileReader(file);
        // Llegeix linea per linea
        BufferedReader buffer = new BufferedReader(reader);

        String linea;
        while ((linea = buffer.readLine()) != null) {
            System.out.println("linea: " + linea);
            int posSep = linea.indexOf(":");
            int codi = Integer.parseInt(linea.substring(0, posSep));
            int stock = Integer.parseInt(linea.substring(posSep + 1));
            System.out.println("El codi del producte es: " + codi + ". El stock es: " + stock);
            actualitzarStockProducte(codi, stock);
        }
        reader.close();
        buffer.close();
    }

    static void moureFitxerAProcessat(File file) throws IOException {

        FileSystem sistemaFitxers = FileSystems.getDefault();
        Path origen = sistemaFitxers.getPath(PATHPENDENTS + file.getName());
        Path desti = sistemaFitxers.getPath(PATHPROCESSADES + file.getName());

        Files.move(origen, desti, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("S'ha mogut a PROCESSATS el fitxer: " + file.getName());

    }

    static void actualitzarStockProducte(int codi, int stock) throws SQLException {

        String query = "UPDATE productes SET stock = stock + ? " + "WHERE codi = ?";

        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "");

            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, stock);
            st.setInt(2, codi);

            st.executeUpdate();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            e.printStackTrace();
        }

        System.out.println("\nApreta ENTER per sortir....");

        teclat.nextLine();
    }

    //==========================================================================

    private static void prepararComandes() throws IOException {

        FileWriter fw = null;
        BufferedWriter bf = null;
        PrintWriter escritor = null;

        

        try {

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "");

            System.out.println("\nPREPARANT COMANDES...");
            String query = "SELECT * FROM productes WHERE stock<20 ORDER BY dni_proveidor";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            int articles=0;
            
            if (rs.next()) {

                int dni_proveidor = rs.getInt("dni_proveidor");

                escritor = escriureCapçaleraComanda(dni_proveidor);
                
                proveidors[contprov] = dni_proveidor;
                contprov++;
                
            
            do {
                

                if (dni_proveidor!= rs.getInt("dni_proveidor")) {
                    
                    dni_proveidor = rs.getInt("dni_proveidor");
                    
                    prodProveidor[contprov - 1] = articles;
                    proveidors[contprov] = dni_proveidor;
                    contprov++;
                    articles=0;
                    
                    escritor.close();
                    escritor = escriureCapçaleraComanda(dni_proveidor);
                    
                }
                articles++;
                escritor.printf("%-20s %-20s %-20s %-20s %-20s %-25s\n", rs.getInt("codi"), rs.getInt("stock"), rs.getString("nom"), rs.getString("material"), rs.getString("preu") + "€", rs.getInt("dni_proveidor"));
                
            } while (rs.next());
            prodProveidor[contprov - 1] = articles;
            escritor.close();
            }
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            e.printStackTrace();
        }

        System.out.println("\nApreta ENTER per sortir....");

        teclat.nextLine();

    }

    static PrintWriter escriureCapçaleraComanda(int dni_proveidor) throws IOException {

        FileWriter fw = new FileWriter(COMANDES + dni_proveidor + "_" + LocalDate.now() + ".txt");
        BufferedWriter bf = new BufferedWriter(fw);
        PrintWriter escritor = new PrintWriter(bf);

        escritor.println("============== COMANDES ==============");
        escritor.println("=                                    =");
        escritor.println("=     Nom Empresa: Yosuku clothes    =");
        escritor.println("=     Localització: Tarrega          =");
        escritor.println("=     Telefon: +34 900323300         =");
        escritor.println("=                                    =");
        escritor.println("======================================");
        escritor.println("");
        escritor.println("==========================================================================================================================");
        escritor.format("%-20s %-20s %-20s %-20s %-20s %-25s\n", "CODI", "STOCK", "NOM", "MATERIAL", "PREU", "DNI PROVEIDOR");
        escritor.println("==========================================================================================================================");

        return escritor;

    }

    //==========================================================================

    private static void analitzarComandes() {
        
      double minim = prodProveidor[0];
      double maxim = prodProveidor[0];
      double mitjana = prodProveidor[0];
      int proveidormin = proveidors[0];
      int proveidormax = proveidors[0];
      double total=0;
      
      for (int i=0;i<contprov;i++){
          
          if (prodProveidor[i]<minim){
              minim = prodProveidor[i];
              proveidormin = proveidors[i];
          }
          
          if (prodProveidor[i]>maxim){
              maxim = prodProveidor[i];
              proveidormax = proveidors[i];
          }
          
           total += prodProveidor[i];
           mitjana = total/contprov;
           
      }
      
        System.out.println("El proveidor amb menys productes es " + proveidormin + " amb " + minim + " productes.");
        
        System.out.println("El proveidor amb més productes es " + proveidormax + " amb " + maxim + " productes.");
        
        System.out.println("La mitjana del total de tots els productes es: " + mitjana);
        
    }
    
    //==========================================================================
}
