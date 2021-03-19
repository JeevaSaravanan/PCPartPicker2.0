package com.zoho.object;

public class ShoppingCart {
	public Processor cpu;
	public MotherBoard board;
	public GraphicCard gpu;
	public PowerSupply power;
	public Cabinets cabinets;
	public Ups ups;
	public MemoryModule ram;
	public Storage storage;
	public int total=0;
	public int calculateTotal() {
		if(cpu!=null && board==null && gpu==null && power==null && cabinets==null && ups==null && ram==null && storage==null)
			total+=cpu.rate;
		if(cpu!=null && board!=null && gpu==null && power==null && cabinets==null && ups==null && ram==null && storage==null)
			total+=board.rate;
		if(cpu!=null && board!=null && gpu!=null && power==null && cabinets==null && ups==null && ram==null && storage==null)
			total+=gpu.rate;
		if(cpu!=null && board!=null && gpu!=null && power!=null && cabinets==null && ups==null && ram==null && storage==null)
			total+=power.rate;
		if(cpu!=null && board!=null && gpu!=null && power!=null && cabinets!=null && ups==null && ram==null && storage==null)
			total+=cabinets.rate;
		if(cpu!=null && board!=null && gpu!=null && power!=null && cabinets!=null && ups!=null && ram==null && storage==null)
			total+=ups.rate;
		if(cpu!=null && board!=null && gpu!=null && power!=null && cabinets!=null && ups!=null && ram!=null && storage==null)
			total+=ram.rate;
		if(cpu!=null && board!=null && gpu!=null && power!=null && cabinets!=null && ups!=null && ram!=null && storage!=null)
			total+=storage.rate;
		
		return total;
	}
}
