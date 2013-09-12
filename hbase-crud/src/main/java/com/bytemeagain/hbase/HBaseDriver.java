package com.bytemeagain.hbase;


public class HBaseDriver {
	/*private static Logger logger = Logger.getLogger(HBaseDriver.class);*/


	public static void main(String[] args) throws Exception{

		/*System.out.println(">>>>>>>>>>>>> ASUP_CALCULATION exists? :"+HBaseCrud.INSTANCE.isTableExists("ASUP_CALCULATION"));
		System.out.println(">>>>>>>>>>>>> ASUP_CALCULATIONS exists? :"+HBaseCrud.INSTANCE.isTableExists("ASUP_CALCULATIONS"));*/
		
		
		
		
		HBaseCrud.INSTANCE.scanTable("ASUP_CURRENT", "OBJECT", "VSERVER");
		
		
		
	}

}
