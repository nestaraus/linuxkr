package Program.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import Program.Model.*;

public class AnimalRepository implements Repository<Animal> {
    
    private Creator animalCreator;
    private Statement sqlST;
    private SetResult setresult;
    private String sqlstr;

    public AnimalRepository(){
        this.animalCreator = new AnimalCreator(); 
    };

    @Override 
    public List<Animal> getAll(){
        List<Animal> ferm = new ArrayList<Animal>();
        Animal animal;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                sqlST = dbConnection.createStatement();
                sqlstr = "SELECT GenusId, Id, AnimalName, Birth FROM animal_list ORDER BY Id";
                setresult = sqlST.executeQuery(sqlstr);
                while (setresult.next()){

                    AnimalType type = AnimalType.getType(setresult.getInt(1));
                    int id = setresult.getInt(2);
                    String name = setresult.getString(3);
                    LocalDate birth = setresult.getDate(4).toLocalDate();

                    animal = animalCreator.createAnimal(type, name, birth);
                    animal.setAnimalId(id);
                    ferm.add(animal);                    
                }
                return ferm;
            }
        }
        catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        } 
    }

    @Override

    public Animal getById(int animalId){
        Animal animal = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                sqlstr = "SELECT GenusId, Id, AnimalName, Birth FROM Animal_list WHERE Id = ?";
                PreparedStatement prepst = dbConnection.prepareStatement(sqlstr);
                prepst.setInt(1, animalId);
                setresult = prepst.executeQuery();

                if (setresult.next()) 
                {
                    
                    AnimalType type = AnimalType.getType(setresult.getInt(1));
                    int id = setresult.getInt(2);
                    String name = setresult.getString(3);
                    LocalDate birth = setresult.getDate(4).toLocalDate();

                    animal = animalCreator.createAnimal(type, name, birth);
                    animal.setAnimalId(id);
                }
                return animal;                
            }
        }
        catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override

    public int create(Animal animal) {
        int rows;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){

                sqlstr = "INSERT INTO animal_list (AnimalName, Birth, GenusId) SELECT ?, ?, (SELECT Id FROM animal_types WHERE Genus_name = ?)";
                PreparedStatement prepst =dbConnection.prepareStatement(sqlstr);
                prepst.setString(1, animal.getName());
                prepst.setDate(2, Date.valueOf(animal.getBirth()) );
                prepst.setString(3, animal.getClass().getSimpleName());

                rows = prepst.executeUpdate();
                return rows;
            }
        }
        catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void train (int id, String command){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                String sqlstr = "INSERT INTO animal_command (AnimalId, CommandId) SELECT ?, (SELECT Id FROM commands WHERE Command_name = ?)";
                PreparedStatement prepst = dbConnection.prepareStatement(sqlstr);
                prepst.setInt(1, id);
                prepst.setString(2, command);

                prepst.executeUpdate();
            }
        }
        catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<String> getCommandsById (int animalId, int commandsType){
        List<String> commands = new ArrayList<>();
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection dbConnection = getConnection()){
            if (commandsType == 1) 
            {
                sqlstr = "SELECT Command_name FROM animal_command pc JOIN commands c ON pc.CommandId = c.Id WHERE pc.AnimalId = ?";
            }
            else
            {
                sqlstr = "SELECT Command_name FROM commands c JOIN Genus_command gc ON c.Id = gc.CommandId WHERE gc.GenusId = (SELECT GenusId FROM animal_list WHERE Id = ?)";
            }
            PreparedStatement prepst = dbConnection.prepareStatement(sqlstr);
            prepst.setInt(1, animalId);
            setresult = prepst.executeQuery();
            while (setresult.next()) {
                commands.add(setresult.getString(1));
            }
            return commands;
        }
    }
    catch (ClassNotFoundException | IOException | SQLException ex)
    {
        Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null
        , ex);
        throw new RuntimeException(ex.getMessage());
    }
    }

    @Override
    public int update(Animal animal) 
    {
        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                sqlstr = "UPDATE animal_list SET AnimalName = ?, Birth = ? WHERE Id = ?";
                PreparedStatement prepst = dbConnection.prepareStatement(sqlstr);

                prepst.setString(1, animal.getName());
                prepst.setDate(2, Date.valueOf(animal.getBirthDate()));
                prepst.setInt(3, getAnimalId());

                rows = prepst.executeUpdate();
                return rows;
            }
        }
        catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void delete (int id){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()){
                sqlstr = "DELETE FROM animal_list WHERE Id = ?";
                PreparedStatement prepst = dbConnection.prepareStatement(sqlstr);
                prepst.setInt(1, id);
                prepst.executeUpdate();
            }
        }
        catch (ClassNotFoundException | IOException | SQLException ex){
            Logger.getLogger(AnimalRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException, IOException {

        Properties properties = new Properties();
        try (FileInputStream file = new FileInputStream("Program/Resource/database.properties")) {

            properties.load(file);
            String url = properties.getProperty("url");
            String login = properties.getProperty("login");
            String password = properties.getProperty("password");

            return DriverManager.getConnection(url, login, password);
        }
    }
}



