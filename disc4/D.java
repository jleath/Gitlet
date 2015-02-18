class A {
    public int x = 5;
    public void m1() {System.out.println("Am1-> " + x);}
    public void m2() {System.out.println("Am2-> " + this.x);}
    public void update() {x = 99;}
}

class B extends A {
    public int x = 10;
    public void m2() {System.out.println("Bm2-> " + x);}
    public void m3() {System.out.println("Bm3-> " + super.x);}
    public void m4() {System.out.println("Bm4-> "); super.m2();}
}

class C extends B {
    public int y = x + 1;
    public void m2() {System.out.println("Cm2-> " + super.x);}
    public void m4() {System.out.println("Cm4-> " + y);}
}

class D {
    public static void main(String[] args) {
        A b0 = new B();
        System.out.println(b0.x);
        System.out.println("\tShould be 5");
        b0.m1();
        b0.m2();
        B b1 = new B();
        b1.m3();
        b1.m4();
        A c0 = new C();
        c0.m1();
        A a1 = (A) c0;
        C c2 = (C) a1;
        c2.m4();
        c2.m5()
    }
}
