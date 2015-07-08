
public class Booster extends Part {
	public double fuelMass;
	public double thrust;
	public double impulse;
	public double atmoImpulse;
	
	public Booster(String name, double mass, 
			double fuelMass, 
			double thrust, double impulse, double atmoImpulse,
			Fuel ft) {
		super();
		this.fuelMass = fuelMass;
		this.thrust = thrust;
		this.impulse = impulse;
		this.mass = mass;
		this.name = name;
		this.ft = ft;
		this.atmoImpulse = atmoImpulse;
	}
	
}
