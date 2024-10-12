package Program.Model;

public enum AnimalType {
   
    Cat,
    Dog,
    Horse;

    public static AnimalType getType (int TypeId){
        switch (TypeId){
            case 1:
                return AnimalType.Cat;
            case 2:
                return AnimalType.Dog;
            case 3:
                return AnimalType.Horse;
            default:
                return null;
        }
    }
}
