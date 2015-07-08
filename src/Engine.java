
public class Engine extends Part {
	public double thrust;
	public double impulse;
	public double atmoImpulse;
	
	
	public Engine (String name, double mass, 
			double thrust, double impulse, double atmoImpulse){
		this.mass = mass;
		this.name = name;
		this.thrust = thrust;
		this.impulse = impulse;
		ft = Fuel.LIQUID;
		this.atmoImpulse = atmoImpulse;
	}
	
	public Engine (String name, double mass, 
			double thrust, double impulse, double atmoImpulse, 
			Fuel ft){
		this.mass = mass;
		this.name = name;
		this.thrust = thrust;
		this.impulse = impulse;
		this.ft = ft;
		this.atmoImpulse = atmoImpulse;
	}
	

}
