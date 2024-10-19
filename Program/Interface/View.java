package Program.Interface;

public interface View <T> {

    String getName();
    String getBirth();
    <U> void printAll(List <U> list, Class <U> class);
    void showMessage(String s);
    
}
