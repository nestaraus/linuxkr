package Program.Interface;

import java.util.Scanner;
import Program.Controller.*;
import Program.Exception.*;
import Program.Model.AnimalType;

public class Menu {
    
    PetsController petsController;

    public Menu(PetsController controller){
        this.petsController = controller;
    }

}
