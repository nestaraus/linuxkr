package Program.Interface;

import java.util.List;
import java.util.Scanner;
import Program.Model.*;

public class ConsoleView implements View<Animal> {
    
    Scanner in;

    public ConsoleView()
    {

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public String getBirth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBirth'");
    }

    @Override
    public <U> void printAll(Program.Interface.List<U> list) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printAll'");
    }

    @Override
    public void showMessage(String s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showMessage'");
    }

}
