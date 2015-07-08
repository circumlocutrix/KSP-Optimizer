import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.*;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.factories.*;
import org.uncommons.watchmaker.framework.operators.*;
import org.uncommons.watchmaker.framework.selection.*;
import org.uncommons.watchmaker.framework.termination.*;


public class SampleLauncher {
	public static final double deltaV = 1700;
	public static final double payloadM = 4.42;
	public static final double relativeG = .0001;
	public static final boolean atmosphere = false;
	public static final double mutationPercent = 0.05;
	public static ArrayList<Part> partList;
	public static final int partSwaps = 9;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		partList = new ArrayList<Part>();

		/*partList.add(new Booster("S1 SRB-KD25k",21.75,18.75,650.0,250.0,230.0, Fuel.SOLID));
		partList.add(new Booster("Sepratron I", .0725,0.06,18,100,100, Fuel.SOLID));
		partList.add(new Booster("RT-10 Solid Fuel Booster",3.7475,3.25,250,240,225,Fuel.SOLID));
		partList.add(new Booster("Rockomax BACC Solid Fuel Booster",7.875,6.37,315,250,230,Fuel.SOLID));*/

		partList.add(new Booster("LFB KR-1x2",42.0,32.0,2000.0,360.0,320.0,Fuel.LIQUID));

		partList.add(new Engine("Kerbodyne KR-2L Advanced Engine",6.5, 2500.0, 380,280));
		partList.add(new Engine("S3 KS-25x4 Engine Cluster",9.75,3200.0,360,320));
		partList.add(new Engine("Rockomax \"Skipper\" Liquid Engine",4.0,650,350,300));
		partList.add(new Engine("LV-T30 Liquid Fuel Engine",1.25,215,370,320));
		partList.add(new Engine("Rockomax \" Mainsail\" Liquid Engine",6,1500,330,280));
		partList.add(new Engine("LV-T45 Liquid Fuel Engine",1.5,200,370,320));
		partList.add(new Engine("Rockomax \"Poodle\" Liquid Engine",2.5,220,390,270));
		partList.add(new Engine("LV-909 Liquid Fuel Engine",.5,50,390,300));
		partList.add(new Engine("Rockomax 48-7S",.1,30,350,30));
		partList.add(new Engine("LV-1 Liquid Fuel Engine",.03,4,290,220));
		partList.add(new Engine("LV-N Atomic Rocket Motor",2.25,60,800,220));
		partList.add(new Engine("Rockomax Mark 55 Radial Mount Liquid Engine",.9,120,320,290));
		partList.add(new Engine("R.A.P.I.E.R. Engine",1.75,175,360,800));		
		partList.add(new Engine("Rockomax 24-77", 0.09, 20, 300, 250));		
		partList.add(new Engine("Toroidal Aerospike Rocket",1.5,175,390,388));

		//partList.add(new Engine("TurboJet Engine"))
		//partList.add(new Engine("Basic Jet Engine (1/2 Max Thrust)",1.0,75,1000,0));

		partList.add(new Tank("Kerbodyne S3-14400 Tank",82,72));
		partList.add(new Tank("Kerbodyne S3-7200 Tank",41, 36));
		partList.add(new Tank("Kerbodyne S3-3600 Tank",20.5,18));
		partList.add(new Tank("Rockomax Jumbo-64 Fuel Tank",36,32));
		partList.add(new Tank("Rockomax X200-32 Fuel Tank",18,16));
		partList.add(new Tank("Rockomax X200-16 Fuel Tank",9,8));
		partList.add(new Tank("Rockomax X200-8 Fuel Tank",4.5,4));
		partList.add(new Tank("FL-T800 Fuel Tank",4.5,4));
		partList.add(new Tank("FL-T200 Fuel Tank",1.125,1));
		partList.add(new Tank("FL-T400 Fuel Tank",2.25,2));
		partList.add(new Tank("FL-T100 Fuel Tank",.5625,.5));
		partList.add(new Tank("Round-8 Toroidal Fuel Tank",.136,.11));
		partList.add(new Tank("Oscar-B Fuel Tank",0.078675,.063675));

		/*partList.add(new Engine("PB-ION", 
				.31 //.25 + 12*.005 for 12 solar panels
				,2, 4200,4200, Fuel.XENON));
		partList.add(new Tank("PB-X150 Xenon Container",.12,.07,Fuel.XENON));
		partList.add(new Tank("PB-X50R Xenon Container",.07,.04,Fuel.XENON));*/


		List <EvolutionaryOperator <HashMap<Part,Integer>>> operators = new LinkedList<EvolutionaryOperator<HashMap<Part,Integer>>>();
		//operators.add(new IntArrayCrossover(8));

		operators.add(new EvolutionaryOperator<HashMap<Part,Integer>>(){

			@Override
			public List<HashMap<Part,Integer>> apply(List<HashMap<Part,Integer>> selectedCandidates, Random rng) {
				// TODO Auto-generated method stub
				List<HashMap<Part,Integer>> newCandidates = new ArrayList<HashMap<Part,Integer>>();
				for (HashMap<Part,Integer> candi : selectedCandidates){
					HashMap<Part,Integer> i = deepClone(candi);
					if (rng.nextFloat()<=mutationPercent){
						do{
							Part p = partList.get(rng.nextInt(partList.size()));
							if (i.get(p)>0){
								if (rng.nextBoolean()){
									i.put(p, i.get(p)+1);
								}
								else{
									i.put(p, i.get(p)-1);
								}
							}
							else{
								i.put(p, 1);
							}
						}while(rng.nextBoolean());
					}
					newCandidates.add(i);
				}
				return newCandidates;
			}
		});

			operators.add(new EvolutionaryOperator<HashMap<Part,Integer>>(){

			@Override
			public List<HashMap<Part,Integer>> apply(List<HashMap<Part,Integer>> selectedCandidates, Random rng) {
				List<HashMap<Part,Integer>> newCandidates = new ArrayList<HashMap<Part,Integer>>();
				for (int i =0 ; i<selectedCandidates.size();i+=2){
					HashMap<Part,Integer> a = deepClone(selectedCandidates.get(i));
					HashMap<Part,Integer> b = deepClone(selectedCandidates.get(i+1));

					for (int j=0; j<partSwaps;j++){
						Part p = partList.get(rng.nextInt(partList.size()));
						int d = a.get(p).intValue();
						int e = b.get(p).intValue();
						a.put(p,e);
						b.put(p,d);
					}
					
					newCandidates.add(a);
					newCandidates.add(b);
				}
				return newCandidates;
			}
		});

		EvolutionaryOperator<HashMap<Part,Integer>> pipeline
		= new EvolutionPipeline<HashMap<Part,Integer>>(operators);

		EvolutionEngine<HashMap<Part,Integer>> engine = new GenerationalEvolutionEngine<HashMap<Part,Integer>>(new AbstractCandidateFactory<HashMap<Part,Integer>>(){

			@Override
			public HashMap<Part,Integer> generateRandomCandidate(Random rng) {
				// TODO Auto-generated method stub
				HashMap<Part,Integer> cand = new HashMap<Part,Integer>();
				int s = partList.size();
				for (int i = 0;i<partList.size();i++){
					cand.put(partList.get(i),rng.nextInt(1));
				}
				return cand;
			}
		}, pipeline, new FitnessEvaluator<HashMap<Part,Integer>>(){

			@Override
			public double getFitness(HashMap<Part,Integer> candidate,
					List<? extends HashMap<Part,Integer>> population) {

				/*double thrust = candidate[0]*4.0+candidate[1]*30+candidate[2]*50+candidate[3]*120+
						candidate[4]*215+candidate[5]*175+candidate[6]*60+candidate[7]*220+candidate[8]*650+candidate[9]*1500;
				double impulse;
				if (atmosphere)
					impulse=thrust/(
							candidate[0]*4.0/220.0+candidate[1]*30/300.0+candidate[2]*50.0/300.0+candidate[3]*120.0/290.0+
							candidate[4]*215/320.0+candidate[5]*175/388.0+candidate[6]*60.0/220.0+candidate[7]*220.0/270.0+candidate[8]*650.0/300.0+candidate[9]*1500.0/280.0
							);
				else
					impulse=thrust/(
							candidate[0]*4.0/290.0+candidate[1]*30/350.0+candidate[2]*50.0/390.0+candidate[3]*120.0/320.0+
							candidate[4]*215/370.0+candidate[5]*175/390.0+candidate[6]*60.0/800.0+candidate[7]*220.0/390.0+candidate[8]*650.0/350.0+candidate[9]*1500.0/330.0
							);
				double mFull= payloadM + candidate[0]*0.03+candidate[1]*0.1+candidate[2]*0.5+candidate[3]*0.9+
						candidate[4]*1.25+candidate[5]*1.5+candidate[6]*2.25+candidate[7]*2.5+candidate[8]*4.0+candidate[9]*6.0
						+candidate[10]*.078675+candidate[11]*.136+candidate[12]*.5625;
				double mEmpty= payloadM + candidate[0]*0.03+candidate[1]*0.1+candidate[2]*0.5+candidate[3]*0.9+
						candidate[4]*1.25+candidate[5]*1.5+candidate[6]*2.25+candidate[7]*2.5+candidate[8]*4.0+candidate[9]*6.0
						+candidate[10]*.0152+candidate[11]*.025+candidate[12]*.0625;
				double dV=9.82*impulse*Math.log(mFull/mEmpty);
				int dVReq = 0;
				if (dV > deltaV) dVReq = 1;
				int TWRReq = 0;
				if (thrust/(mFull*9.82)>1) TWRReq = 1;
				if (candidate[5]>0) return 1000000;
				if (thrust==0) return 10000000;
				//return mFull+.1*Math.max(0, (deltaV-dV))+Math.max(0, 100000*(1-(thrust/(mFull*relativeG*9.82))));
				//return (mFull+.1*Math.max(0, (deltaV-dV)))/Math.sqrt(thrust/(mFull*relativeG*9.82));
				if ((thrust/(mFull*relativeG*9.82))<1) return 100000;
				//if (dV < deltaV) return 100000;
				//return mFull;
				return (mFull+100000*Math.max(0, (deltaV-dV)));//-100000*Math.min(0, (1-(thrust/(mFull*relativeG*9.82))));*/

				double thrust = 0;
				double massFuel=0;
				double massFull=payloadM;
				double massEmpty=payloadM;

				double numParts=0;

				double impDen = 0;
				for (Part p : candidate.keySet()){
					massFull += candidate.get(p)*p.mass;
					numParts += candidate.get(p);

					if (p instanceof Engine){
						thrust+=candidate.get(p)*((Engine)p).thrust;
						if (!atmosphere)
							impDen+=candidate.get(p)*((Engine)p).thrust/((Engine)p).impulse;
						else
							impDen+=candidate.get(p)*((Engine)p).thrust/((Engine)p).atmoImpulse;
					}

					else if (p instanceof Booster){
						thrust+=candidate.get(p)*((Booster)p).thrust;
						massFuel += candidate.get(p)*((Booster)p).fuelMass;
						if (!atmosphere)
							impDen+=candidate.get(p)*((Booster)p).thrust/((Booster)p).impulse;
						else
							impDen+=candidate.get(p)*((Booster)p).thrust/((Booster)p).atmoImpulse;
					}
					else if (p instanceof Tank){
						massFuel += candidate.get(p)*((Tank)p).fuelMass;
					}
				}

				massEmpty = massFull-massFuel;
				double impulse = thrust/impDen;
				double twr = thrust/(massFull*relativeG*9.82);
				double dV=9.82*impulse*Math.log(massFull/massEmpty);

				if (numParts < 1 || twr < 1 || dV < deltaV)
					return Double.MAX_VALUE;

				return massFull+(Math.log10(numParts)/10);

			}

			@Override
			public boolean isNatural() {
				return false;
			}

		}, new RouletteWheelSelection(), new MersenneTwisterRNG());

		engine.addEvolutionObserver(new EvolutionObserver<HashMap<Part,Integer>>()
				{
			public void populationUpdate(PopulationData<? extends HashMap<Part,Integer>> data)
			{
				if (data.getGenerationNumber()%100==0){
					System.out.printf("Generation %d: Fitness %f: ",data.getGenerationNumber(), data.getBestCandidateFitness());
					HashMap<Part,Integer> result = data.getBestCandidate();

					System.out.print(candidateString(result));


					HashMap<Part,Integer> candidate = result;
					double thrust = 0;
					double massFuel=0;
					double massFull=payloadM;
					double massEmpty=payloadM;

					double numParts=0;

					double impDen = 0;
					for (Part p : candidate.keySet()){
						massFull += candidate.get(p)*p.mass;
						numParts += candidate.get(p);

						if (p instanceof Engine){
							thrust+=candidate.get(p)*((Engine)p).thrust;
							if (!atmosphere)
								impDen+=candidate.get(p)*((Engine)p).thrust/((Engine)p).impulse;
							else
								impDen+=candidate.get(p)*((Engine)p).thrust/((Engine)p).atmoImpulse;
						}

						else if (p instanceof Booster){
							thrust+=candidate.get(p)*((Booster)p).thrust;
							if (!atmosphere)
								impDen+=candidate.get(p)*((Booster)p).thrust/((Booster)p).impulse;
							else
								impDen+=candidate.get(p)*((Booster)p).thrust/((Booster)p).atmoImpulse;
						}
						else if (p instanceof Tank){
							massFuel += candidate.get(p)*((Tank)p).fuelMass;
						}
					}

					massEmpty = massFull-massFuel;
					double impulse = thrust/impDen;
					double twr = thrust/(massFull*relativeG*9.82);
					double dV=9.82*impulse*Math.log(massFull/massEmpty);

					System.out.print(" Thrust: "+thrust+"kN, Full Mass: "+massFull+" Fuel Mass: "+massFuel+"  Empty Mass: "+massEmpty+", delta V: "+dV+", impulse: "+impulse+" # Parts: "+numParts);


					System.out.print("\n");
				}
			}
				});

		final HashMap<Part,Integer> result1 = engine.evolve(2500, 10, new Stagnation(3000,false));

	}

	public static String candidateString (HashMap<Part,Integer> cand){
		String ret = "";

		for (Part p : cand.keySet()){
			if (cand.get(p)>0){
				ret += cand.get(p);
				ret += " ";
				ret += p.name;
				ret += ", ";
			}
		}

		return ret;
	}

	public static HashMap<Part,Integer> deepClone (HashMap<Part,Integer> a){
		HashMap<Part,Integer> b = new HashMap<Part,Integer>();

		for (Part p : a.keySet()){
			b.put(p, a.get(p).intValue());
		}

		return b;
	}

}
