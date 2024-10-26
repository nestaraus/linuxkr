package Program.Interface;

import java.util.Scanner;
import Program.Controller.*;
import Program.Exception.*;
import Program.Model.Animal;
import Program.Model.AnimalType;

public class Menu {
    
    AnimalController animalController;

    public Menu(AnimalController controller){
        this.animalController = controller;
    }
    public void start(){
        System.out.println("An");
        try (Scanner in = new Scanner(System.in, "ibm866"); 
            Counter count = new Counter()) {
            boolean flag = true;
            int id;
            while (flag){
                System.out.println("\n1 - Список всех животных\n2 - Новое животное\n3 - Изменить данные о животном\n4 - Что умеет животное\n5 - Дрессировка\n6 - Удалить запись\n0 - Выход");
                String key = in.next();
                switch (key) {
                    case "0":
                        flag = false;
                        break;
                    case "1":
                        animalController.getAllAnimal();
                        break;
                    case "2":
                        AnimalType type = menuChoise(in);
                        if (type != null) {
                            try{
                                animalController.createAnimal(type);
                                count.add();
                                System.out.println("Done");
                            }
                            catch(UncorrectDataException e){
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "3":
                        while (true) {
                            id = menuChoiseAnimal(in);
                            if (id != 0) 
                            try{
                                animalController.updateAnimal(id);
                            }
                            catch(UncorrectDataException e){
                                System.out.println(e.getMessage());
                            }
                            else
                            break;
                        }
                        break;
                    case "4":
                        while (true) {
                            id = menuChoiseAnimal(in);
                            if (id != 0) 
                                animalController.getCommands(id);
                            else
                                break;
                        }
                        break;
                    case "5":
                        id = menuChoiseAnimal(in);
                        if (id != 0) 
                            menuTrainAnimal(id,in);
                        break;
                    case "6":
                        id = menuChoiseAnimal(in);
                        if (id != 0) 
                            animalController.delete(id);
                        break;
                    default:
                            System.out.println("Такого варианта нет");
                            break;
                    
                }
            }
        }
    }
    private AnimalType menuChoise(Scanner in){
        System.out.println(("Какое животное добавить:\n1 - Кошка\n2 - Собака\n3 - Лошадь\n0 - Возврат в основное меню"));
        while (true) {
            String key = in.next();
            switch (key) {
                case "0":
                    return null;
                case "1":
                    return AnimalType.Cat;
                case "2":
                    return AnimalType.Dog;
                case "3":
                    return AnimalType.Horse;
            
                default:
                    System.out.println("Такого варианта нет, введите число от 0 до 3");
                    break;
            }
        }
    }
    private int menuChoiseAnimal(Scanner in){
        System.out.println("\nВведите id животного, 0 для возврата в основное меню: ");
        while (true) {
            int id = in.nextInt();
            in.nextLine();
            if (id == 0) 
                return id;
            
            if (animalController.getById(id) == null) {
                System.out.println("Животного с таким id нет, попробуйте еще раз, 0 для возврата в основное меню:"); 
            }
            else
            return id;
        }
    }
    private void menuTrainAnimal(int animalId, Scanner in){
        Scanner scanner = in;
        while (true) {
            System.out.println("Введите новую команду, 0 для возврата в основное меню: ");
            String command = scanner.nextLine();
            if (command.length() == 1 && command.equals("0"))
                return;
            if (animalController.trainAnimal(animalId, command))
            System.out.println("Done");
    }
    }
}

