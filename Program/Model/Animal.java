package Program.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Animal {
    protected int animalId;
    protected String name;
    protected LocalDate birth;


public void setAnimalId(int AnimalId) {
    this.animalId = AnimalId;
}

public int getAnimalId() {
    return animalId;
}

public void setName(String name) {
    this.name = name;
}

public String getName() {
    return name;
}

public void setBirth(LocalDate date) {
    this.birth = date;
}

public LocalDate getBirthDate(){
    return birth;
}

public String getBirth() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return formatter.format(birth);
}

@Override
public String toString() {
    return String.format("%d. %s: имя: %s, дата рождения: %s ", getAnimalId(), getClass().getSimpleName(), name, getBirth());
}

}