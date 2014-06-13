import java.util.*;

public class Stock extends PcParts{
	private PcParts thing;
	private int availableStock;
	
	public Stock(){
		thing = null;
		availableStock = 0;
	}
	public Stock(PcParts thing){
		this.thing = thing;
		availableStock = 2;
	}
	public PcParts getThing(){
		return thing;
	}
	public void setThing(PcParts thing){
		this.thing = thing;
	}
	
	public int getAvailableStock(){
		return availableStock;
	}
	public void setAvailableStock(int availableStock){
		this.availableStock = availableStock;
	}
	public String toString(){
		System.out.println();
		return (thing + "\n\t\tPIECES: " + getAvailableStock() + "\n");
	}
	
}
