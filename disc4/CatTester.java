public class CatTester {
    public static void main(String[] args) {
        Animal a = new Animal("Pluto", 10);
        Cat c = new Cat("Garfield", 6);
        Dog d = new Dog("Fido", 4);

        a.greet();
        System.out.println("Animal says Huh?");
        c.greet();
        System.out.println("Cat says Meow!");
        d.greet();
        System.out.println("Dog says WOOF!");
        
        a = c;
        a.greet();
        System.out.println("Cat says Meow!");
        ((Cat) a).greet();
        System.out.println("Cat says Meow!");

        a = new Dog("Hieronymus", 10);
        d = (Dog)a;
    }
}
