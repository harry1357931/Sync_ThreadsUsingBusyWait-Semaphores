
/* Project 3
 * CS 340 
 * Fall, 2012
 * @param Number Id of Object
 * @param CashierType  Type of Cashier
 * @param ServingOrNot
 * @param NumberofCustomerServed  Keep Tracks of All Customers Served by all Clerk Objects
 * @author Gurpreet Singh
 */
public class Cashier extends Thread{
	
	public int Number;
	public String CashierType;       // Cash or Credit
	public boolean ServingOrNot;
	public static int NumberOfCustomersServed;
	
	public Cashier(String Type, int num){
		Number = num;
		CashierType = Type;
		ServingOrNot = false;
		setName("Cashier: " + num);
	}
	
	public void msg(String m)
	{
		System.out.println(Project3Main.age() + getName() + " :" + m);
	}
	
	public void run(){
		ArrivedInStore();	
		ServingCustomersFirstComeFirstServed();
		WaitingForStoreClose();
		Terminate();
	}
    
	public void Terminate(){
		msg("heading for Home(TERMINATED)...Good Night");
		this.stop();
	}
	
     public void ArrivedInStore(){
		 msg("Arrived and On Cashier Desk Now");
		 Me_Sleeping(random_Num_between(500,1000));
	 }
	
	 public void ServingCustomersFirstComeFirstServed(){
		 while(NumberOfCustomersServed != Project3Main.Customers.length) 
			{  
				 for(int i=0; i<Project3Main.Customers.length; i++){
			      	if(Project3Main.Customers[i] != null){
					   if(Project3Main.Customers[i].PaidToCashier != true && Project3Main.Customers[i].ReceiveSlipYet == true )
					   {   // Serving i-th Customer...
						   if(this.CashierType.equalsIgnoreCase(Project3Main.Customers[i].PaymentMethod)){
							   Project3Main.Customers[i].msg("finally, long line to cashier ends...");
							   Project3Main.Customers[i].msg("Paying to Cashier by: "+Project3Main.Customers[i].PaymentMethod);
							   msg("Received the "+Project3Main.Customers[i].PaymentMethod+" Payment by Customer:"+i);
							   Project3Main.Customers[i].PaidToCashier = true;
							   NumberOfCustomersServed++;
						   } 
					   }
			      	}
			    }
			    
			    msg("Waiting for Customers...");
			    Me_Sleeping(random_Num_between(800,1000));   // Increase Time here...
			   
			}// while loop...
			
			msg("Received Payment from All the Customers...");
	 }
	 
	 public void WaitingForStoreClose(){
		 msg("Waiting for Store Close Now...");
		 while(Project3Main.StoreOpen != false){
			 Me_Sleeping(random_Num_between(1500,1600));
			 msg("Still Waiting for Close of Store...");
		 }
		 msg("Thank God...Store is Closed Now...Let's leave for home");
	 }
	 
	 public int random(){
	    	int temp = 0;
			temp = (int)(Math.random() * 1000) + 1;
	    	return temp;
	 } 
	    
	 public int random_Num_between(int a, int b){
			
			int num;
			while(true){
			    num = a+random()/100;
				if(num >=a && num<=b){
				   break;	
				}
			 }
			
			return num;	
	  }
	 
	 public void Me_Sleeping(int sleep_time){
			try{ 
				  Thread.sleep(sleep_time);       
			  }
			catch(InterruptedException e){}
			
	 }


} // Class ends here...
