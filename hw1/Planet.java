public class Planet {
    public double x;
    public double y;
    public double xVelocity;
    public double yVelocity;
    public double mass;
    public String img;
    public double xNetForce;
    public double yNetForce;
    public double acceleration;
    public double xAccel;
    public double yAccel;

    public Planet(double x, double y, double xVelocity,
                  double yVelocity, double mass, String img) {
        this.x = x;
        this.y = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.mass = mass;
        this.img = img;
    }

    /* Returns the distance between this Planet and OTHER
     * using the Pythagorean Theorem.
     */
    public double calcDistance(Planet other) {
        double xDistance = Math.abs(this.x - other.x);
        double yDistance = Math.abs(this.y - other.y);
        return Math.sqrt(xDistance * xDistance +
                         yDistance * yDistance);
    }
    
    /* Returns the pairwise force of this Planet and
     * OTHER where K is the product of their masses,
     * D is the distance between them, and G is the
     * gravitational constant (6.67e10-11)
     */
    public double calcPairwiseForce(Planet other) {
        double k = this.mass * other.mass;
        double d = this.calcDistance(other);
        double G = 6.67e-11;
        return G * (k / (d * d));
    }

    /* Returns the x-component of the pairwise force of
     * this planet and OTHER where XDIST is the distance
     * between the planets on the x-axis, R is the 
     * distance between the two planets, and F is the
     * pairwise force of the two planets.
     */
    public double calcPairwiseForceX(Planet other) {
        double f = this.calcPairwiseForce(other);
        double r = this.calcDistance(other);
        double xDist = Math.abs(this.x - other.x);
        return f * xDist / r;
    }

    /* Returns the y-component of the pairwise force of
     * this planet and OTHER where YDIST is the distance
     * between the planets on the y-axis, R is the 
     * distance between the two planets, and F is the
     * pairwise force of the two planets.
     */
    public double calcPairwiseForceY(Planet other) {
        double f = this.calcPairwiseForce(other);
        double r = this.calcDistance(other);
        double yDist = Math.abs(this.y - other.y);
        return f * yDist / r;
    }

    /* Sets the x net force and the y net force of this planet
     * to match the total amount of force exerted by the planets
     * in the array PLANETS by summing the pairwise components of
     * each axis.  Will ignore this planet if it is in the array.
     */
    public void setNetForce(Planet[] planets) {
        double xForce = 0.0;
        double yForce = 0.0;
        for (Planet planet : planets) {
            if (planet != this) {
                xForce = xForce + this.calcPairwiseForceX(planet);
                yForce = yForce + this.calcPairwiseForceY(planet);
            }
        }
        this.xNetForce = xForce;
        this.yNetForce = yForce;
    }

    /* Draws this planet at its appropriate location using the
     * StdDraw API
     */
    public void draw() {
        StdDraw.picture(this.x, this.y, this.img);
    }

    /* Updates the acceleration, velocity, and the position
     * of this planet using DT as the length of time between
     * intervals.
     */
    public void update(double dt) {
        this.xAccel = xNetForce / mass;
        this.yAccel = yNetForce / mass;
        this.xVelocity = this.xVelocity + dt * this.xAccel;
        this.yVelocity = this.yVelocity + dt * this.yAccel;
        this.x = this.x + dt * this.xVelocity;
        this.y = this.y + dt * this.yVelocity;
    }
}
