
public class Tank extends Part{
	public double fuelMass;


	public Tank(String name, double mass, double fuelMass) {
		this.name = name;
		this.fuelMass = fuelMass;
		this.mass = mass;
		this.ft = Fuel.LIQUID;
	}
	
	public Tank(String name, double mass, double fuelMass, 
			Fuel ft) {
		this.name = name;
		this.fuelMass = fuelMass;
		this.mass = mass;
		this.ft = ft;
	}

	
	

}
