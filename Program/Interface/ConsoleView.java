package Program.Interface;

import java.util.List;
import java.util.Scanner;
import Program.Model.*;

public class ConsoleView implements View <Animal> {
    
    Scanner in;

    public ConsoleView()
    {
        in = new Scanner(System.in, "ibm866");
    }

    @Override
    public String getName() {
        System.out.printf("Имя: ");
        return in.nextLine();
    }

    @Override
    public String getBirth() {
        System.out.printf("Введите дату рождения в формате 'dd.mm.yyyy': ");
        return in.nextLine();
    }

    @Override
    public <T> void printAll (List <T> list, Class <T> clazz) {
        System.out.print("An");
        if (list.isEmpty()) 
            System.out.println("Пустой список");
        else{
            if (clazz == Animal.class) 
                System.out.println("\n Список животных");
            for (T item : list){
                System.out.println(item);
            }
        }
    }

    @Override
    public void showMessage(String massage) {
        System.out.println(massage);
    }

}
