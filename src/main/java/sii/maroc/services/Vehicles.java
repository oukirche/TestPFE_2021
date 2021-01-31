package sii.maroc.services;

import java.util.HashMap;
import java.util.Vector;

public class Vehicles {

	
	private HashMap<String, Integer> carburantConsomation;
	private Vector<Integer> doorsClosed;
	
	
	public Vehicles(String carburantConsomationValue) {
		carburantConsomation = new HashMap();
		doorsClosed = new Vector();// doors by car type
		
		loadCarburentConsomation(carburantConsomationValue); //carburentType : ConsumentionPourcentage
		
	}
	// load carburentType : ConsumentionPourcentage MAP
	private void loadCarburentConsomation(String carburantConsomationValue)
	{
		String carburantConsomationTab[] = carburantConsomationValue.split(",");
		for(String s:carburantConsomationTab)
		{
			String tmps[] = s.split(":");
			int pourcentag = Integer.parseInt( tmps[1].substring(0,tmps[1].length()-1 ));
			System.out.println(pourcentag);
			carburantConsomation.put(tmps[0], pourcentag);
		}
	}
	// Load doors Vector by cartype
	private void loadDoorsData(String carType)
	{
		if("CAR".equals(carType))
		{
			doorsClosed.add(1);
			doorsClosed.add(2);
			doorsClosed.add(3);
			doorsClosed.add(4);
		}
		else if("TRUCK".equals(carType))
		{
			doorsClosed.add(1);
			doorsClosed.add(2);
		}
	}
	
	public String move(String carType,String carburantType,String doorsClosed,String distance)
	{
		// load 
		loadDoorsData(carType);
		
		int tmpDistance=0;
		Vector<Integer> tmpDoorsClosed = new Vector();
		Vector<Integer> noClosedDoors = new Vector();
		// get Dooors closed from String
		tmpDoorsClosed =  getDoorsClosedFromString(doorsClosed);
		// get if all doors is closed or not
		String doorsCheck = doorsClosedString(tmpDoorsClosed);
		// get an array for all doors no closed Array
		noClosedDoors = getNoDoorsClosedArray(tmpDoorsClosed);
		// get graphic representation of doors no closed
		if("DOORS KO, BLOCKED \\n".equals(doorsCheck))
			return doorsCheck+getDoorsNoClose(noClosedDoors);
		// get the pourcentage of carburant by carburantType from MAP
		int pourcentage =  getPourcentageOfCarburant(carburantType);
		
		// get just a number of KM FROM distance
		tmpDistance =  getDistance(distance);
		// get the calculat consomation 
		double consomation = calculatConsomation(tmpDistance, pourcentage);//
		return doorsCheck+", MOVING. The "+carType+" will consume "+consomation+"0 L";
	}
	private int getDistance(String distance2) {
		String[] distanceTab = distance2.split(" ");
		return Integer.parseInt(distanceTab[0]);
	}
	private Vector<Integer> getDoorsClosedFromString(String doorsClosed2) {

		Vector<Integer> tmpDoorsClosed = new Vector();
		String[] doorsClosedTab = doorsClosed2.split(" ");
		for(String s : doorsClosedTab)
		{
			tmpDoorsClosed.add(Integer.parseInt(s));
		}
		return tmpDoorsClosed;
	}
	
	private int getPourcentageOfCarburant(String carburantType) {

		if(carburantConsomation.containsKey(carburantType))
			return carburantConsomation.get(carburantType);
		return 0;
	}
	private double calculatConsomation(int distance,int carburantPourcentage)
	{
		double result = (distance*carburantPourcentage)/100;
		
		return result;
	}
	private Vector<Integer> getNoDoorsClosedArray(Vector<Integer> doors)
	{
		Vector<Integer> noClosedDor = new Vector();
		for(int i:doors)
		{
			if(!doorsClosed.contains(i))
			{
				noClosedDor.add(i);
			}
		}
		return noClosedDor;
	}
	
	private String doorsClosedString(Vector<Integer> doors)
	{
		if(doorsClosed.size()==doors.size())
			return "DOORS OK";
		return "DOORS KO, BLOCKED \\n";
		
	}

	public String getDoorsNoClose(Vector<Integer> noCloosedDoors)
	{
		for(int i:noCloosedDoors)
		{
			switch (i) {
			case 1:
				return  " _\n"+
                       " / |\\n"+
                       " |_|";
				
			case 2: 
				return  "  _\n"+
			             "| \\\n"+
                        " |_|";
			case 3:
				return "    _\n"+
		                "  | |\\n"+
                        " \\_|";
			case 4 :
				return "   _\n"+
		                " | |\\n"+
                        " |_/";
			default:
				break;
			}
		}
		return null;
	}
}
