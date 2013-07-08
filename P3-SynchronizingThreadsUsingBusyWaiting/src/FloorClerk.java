
/* Project 3
 * CS 340 
 * Summer, 2012
 * @param Number  Id of a object
 * @param ServingCustomerOrNot  True when serving Customer
 * @param NumberOfCustomersAssisted Static variable to count the number of customers assisted by All FloorClerks
 * @author Gurpreet Singh
 */
public class FloorClerk extends Thread{
	public int Number;
	public boolean ServingCustomerOrNot;
	public static int NumberOfCustomersAssisted; 
	
	public FloorClerk(int num){
		Number = num;
		ServingCustomerOrNot = false;               // Initially "false"
		NumberOfCustomersAssisted = 0;
		setName("Floor Clerk: " + num);
	}
	
	public void msg(String m)
	{
		System.out.println(Project3Main.age() + getName() + " :" + m);
	}
	
	public void run(){
		
		ArrivedInStore();
		WaitingAndGivingSlipsToAllCustomers();
		WaitingForStoreClose();
		Terminate();
	}
	
	public void Terminate(){
		
		msg("heading for Home (TERMINATED)....Good Night ");
		this.stop();
	}
	
	public void ArrivedInStore(){
		 Me_Sleeping(random_Num_between(50,100));
		 msg("Arrived and On Duty Now");
	 }
	
	public void WaitingAndGivingSlipsToAllCustomers(){
		
		while(NumberOfCustomersAssisted != Project3Main.Customers.length) 
		{  
			 for(int i=0; i<Project3Main.Customers.length; i++){
		      	if(Project3Main.Customers[i] != null){
				   if(Project3Main.Customers[i].WaitingForFloorClerk == true && Project3Main.Customers[i].ReceiveSlipYet == false )
				   {   // Assisting ith Customer...
					   Project3Main.Customers[i].msg("Finally, Floor Clerk Wait Over...");
					   Project3Main.Customers[i].WaitingForFloorClerk = false;
					   Project3Main.Customers[i].ReceiveSlipYet = true;
					   msg("Gave Slip to Customer:"+i);
					   Project3Main.Customers[i].msg("Received Slip from floor clerk...");
					   NumberOfCustomersAssisted++;
				   }
		      	}
		    }
		    
		    msg("Waiting for Customers...");
		    Me_Sleeping(random_Num_between(800,1000));   // Increase Time here...
		   
		}// while loop...
		
		msg("Done with all the Customers...");
	
	}
	
	public void WaitingForStoreClose(){
		msg("Waiting for Store Close Now...");
		while(Project3Main.StoreOpen != false){
		   Me_Sleeping(random_Num_between(1500,1600));
		   msg("Still Waiting for Close of Store...");
		}
		
		msg("Finally! Store is Closed...Let's get ready to Leave back home");
	}
	
	public void Me_Sleeping(int sleep_time){
		try{ 
			  Thread.sleep(sleep_time);       
		  }
		catch(InterruptedException e){}
		
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

 }  // Class ends here...
