public class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
        noise = "Meow!";
    }

    @Override
    public void greet() {
        System.out.println("Cat " + name + " says " + makeNoise());
    }
        
}
