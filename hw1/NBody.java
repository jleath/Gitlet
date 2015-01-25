public class NBody {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid arguments!");
            System.exit(0);
        }
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        In in = new In(filename);
        int numPlanets = in.readInt();
        double universeRadius = in.readDouble();

        Planet[] planets = new Planet[numPlanets];
        for (int i = 0; i < numPlanets; i = i + 1) {
            planets[i] = getPlanet(in); 
        }

        StdDraw.setScale(-universeRadius, universeRadius);
        StdDraw.picture(0.0, 0.0, "images/starfield.jpg");
        for (Planet p : planets) {
            p.draw();
        }

        double time = 0.0;
        while (time < T) {
            for (Planet p : planets) 
                p.setNetForce(planets);
            for (Planet p : planets)
                p.update(dt);
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : planets)
                p.draw();
            StdDraw.show(10);
            time += dt;
        }
        printState(numPlanets, universeRadius, planets);
    }

    public static void printState(int numPlanets, double r, Planet[] planets) {
        StdOut.printf("%d\n", numPlanets);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < numPlanets; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].x, planets[i].y, planets[i].xVelocity,
                          planets[i].yVelocity, planets[i].mass, planets[i].img);
        }
    }

    public static Planet getPlanet(In in) {
        double xPos = in.readDouble();
        double yPos = in.readDouble();
        double xVel = in.readDouble();
        double yVel = in.readDouble();
        double mass = in.readDouble();
        String img = in.readString();
        Planet newPlanet = new Planet(xPos, yPos, xVel, yVel, mass, img);
        return newPlanet;
    }
}
