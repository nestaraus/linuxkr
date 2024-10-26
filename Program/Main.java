package Program;
import Program.Controller.*;
import Program.Model.*;
import Program.Service.*;
import Program.Interface.*;

public class Main {
    public static void main(String[] args) throws Exception{
        Repository <Animal> myFerm = new AnimalRepository();
        AnimalController controller = new AnimalController(myFerm);
        new Menu(controller).start();
    }
}
