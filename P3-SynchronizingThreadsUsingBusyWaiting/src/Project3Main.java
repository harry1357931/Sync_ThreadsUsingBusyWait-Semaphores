
/* Project 3
 * Key Words:
 *   Concurrent Programming, Synchronizing with Threads using Busy Wait
 * CS 340 
 * Summer, 2012
 * @param StoreOpen Boolean value...tells whether store opens or not
 * @param NumOfCustomers  Tells about the number of customers
 * @param NumOfFloorClerks Tells about the number of floor clerks
 * @param NumOfCashiers  Tells about the number of cashiers
 * @param Customers Array of Customer class objects
 * @param FlClerks  Array of FloorClerk class objects
 * @param Cashiers Array of Cashier class objects
 * 
 * @author Gurpreet Singh 
 */

public class Project3Main {
	
	public static boolean StoreOpen = true;
	public static int NumOfCustomers;              // 10 Default Values
	public static int NumOfFloorClerks;             // 3 Default Values
	public static int NumOfCashiers;                // 2  Default Values
	
	public static Customer[] Customers;
	public static FloorClerk[] FlClerks;
	public static Cashier[] Cashiers;
	public static long time = System.currentTimeMillis();
    
	public static void main(String[] args) {
		 
	 	   NumOfCustomers = Integer.parseInt(args[0]);              
	 	   NumOfFloorClerks = Integer.parseInt(args[1]);            
		   NumOfCashiers = Integer.parseInt(args[2]);               
		
	       Customers = new Customer[NumOfCustomers];
		   FlClerks = new FloorClerk[NumOfFloorClerks];
		   Cashier[] Cashiers = new Cashier[NumOfCashiers];
	
		   System.out.println("Welcome To Sky Shop Store!\n");
		   System.out.println(age() + "Store is Open for Workers Now...");
			
		   for(int i=0; i< FlClerks.length; i++){
			    FlClerks[i] = new FloorClerk(i);
			    FlClerks[i].start();
			    Sleeping_inMain((int)(Math.random() * 100) + 1);
		   }
		   for(int i=0; i< Cashiers.length; i++)
		   {   if(i%2 == 0)
				   Cashiers[i] = new Cashier("Cash", i);
			   else 
				   Cashiers[i] = new Cashier("Credit Card", i);
		       Cashiers[i].start();
		       Sleeping_inMain((int)(Math.random() * 1000) + 1);
		   }
		   	   
		   System.out.println(age() + "All Cashiers & Floor Clerks have arrived and On their duty now...");
		   System.out.println("\n"+age() + "STORE is OPEN for CUSTOMERS NOW...\n");
		   
           for(int i=0; i< Customers.length; i++){
			    Customers[i] = new Customer(i);
			    Customers[i].start();
			    int temp = 0;
				temp = (int)(Math.random() * 1000) + 1;
			    Sleeping_inMain(temp); 
           }	    
           
           while(Customer.AllCustomersTerminated!=true){      //busy wait
        	   Sleeping_inMain(1000);
           }
           
           StoreOpen = false;
           System.out.println(age()+"Store is CLOSED now...Workers Can Leave Now...");
           Sleeping_inMain(1800);
           System.out.println(age()+"Store is CLOSED...All Cashiers, Floor Clerks left home...");
           System.out.println("\n"+age()+"Program Ends here...if U liked it...U Can Run Again...ThankYou!!!");
           
	} // main function ends here...
	
	public static String age(){
        return "[" + (System.currentTimeMillis() - time) + "]";	
	}
		
	public static void Sleeping_inMain(int sleep_time){
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
	}

}// Class ends here...
