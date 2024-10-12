package Program.Model;

public class NewAnimal extends AnimalCreator {
    @Override
    protected Animal createNewAnimal (AnimalType type) {

        switch (type) {
            case Cat:
                return new Cat();
            case Dog:
                return new Dog();
            case Horse:
                return new Horse();
        }
        return null;
    }
}
