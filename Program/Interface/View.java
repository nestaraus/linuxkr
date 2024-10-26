package Program.Interface;
import java.util.List;
public interface View <T> {

    String getName();
    String getBirth();
    <U> void printAll(List <U> list, Class <U> clazz);
    void showMessage(String s);
    
}
