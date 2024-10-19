package Program.Controller;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import Program.Exception.*;

import Program.Exception.UncorrectDataException;

public class Validator {
    
    public void validate (String [] data){

        StringBuilder sb = new StringBuilder();
        boolean flag = true;

        for (int i = 0; i < data.length; i++){
            try{
                if (i==0) 
                {
                    ValidName(data[i]);    
                }
                if (i==1) 
                {
                    ValidDate(data[i]);
                }
            }
            catch (UncorrectDataException e){
                sb.append("\n");
                sb.append(e.getMessage());
                flag = false;
            }
        }
        if (flag=false) 
        {
            throw new UncorrectDataException(sb.toString());     
        }
    }

    private boolean ValidName (String name){
        for (int i = 0; i < name.length(); i++){
            if (! Character.UnicodeBlock.of(name.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                throw new UncorrectDataException(String.format("Пожалуйста, напишите имя с помощью русской раскладки"));
            }
        }
        return true;
    }

    private boolean ValidDate (String birth){

        LocalDate date;
        Integer [] month = {4, 6, 9, 11};
        int day;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy");
            date = LocalDate.parse(birth, formatter);
            day = date.getDayOfMonth();
        } catch (DateTimeParseException e) {
            throw new UncorrectDataException("Некорректный формат даты");
        }

        if ((Arrays.asList(month).contains(date.getMonthValue())&& day > 30) || 
        (date.isLeapYear() && date.getMonthValue() == 2 && day > 29) ||
        (!date.isLeapYear() && date.getMonthValue() == 2 && day > 28)) {
            throw new UncorrectDataException("Введена некорректная дата");
        }
        else
            return true;
    }
}
