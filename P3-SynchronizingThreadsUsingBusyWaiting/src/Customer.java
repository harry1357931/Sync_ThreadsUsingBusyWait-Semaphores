
/* Project 3
 * CS 340 
 * Summer, 2012
 * @param Number  Id of object
 * @param CashOrCredit   Chosen randomly....0 for cash and 1 for credit
 * @param WaitingForFloorClerk  True when waiting for floor clerk
 * @param ReceiveSlipYet  True after receiving slip
 * @param PaidToCashier  
 * @param PaymentMethod  String value for "Cash" or "Credit"
 * @param AllDone  True when all Customers done Paying and Arrived in Cafeteria
 * @param AllCustomersTerminated True when all customers terminated or Go Home
 * @author Gurpreet Singh
 */
public class Customer extends Thread{
	
	public int Number;
	public int CashOrCredit;             // 0 for Cash, any other number for Credit...Chosen randomly
	public boolean WaitingForFloorClerk;
	public boolean ReceiveSlipYet;
	public boolean PaidToCashier; 
	public String PaymentMethod;
	public static boolean AllDone = false;                       // All Customers arrive in Cafeteria or not...
	public static boolean AllCustomersTerminated = false;
	
	public Customer(int num){
		Number = num;
		CashOrCredit = random_Num_between(0,1);
		ReceiveSlipYet = false;
		PaidToCashier = false;
		WaitingForFloorClerk = false;
		setName("Customer: " + num);
		
		if(CashOrCredit ==0)
		    PaymentMethod = "Cash";
		else
			PaymentMethod = "Credit Card";
	}
	
	public void msg(String m)
	{
		System.out.println(Project3Main.age() + getName() + " :" + m);
	}
	
	public void run()
	{   
		WalksIntoStore();
		LookingForItemAndFoundItem();
		WaitingForFloorClerkAndGetSlip();
		WentToCashierAndJoinRespectiveCashierMoneyTypeLine();
		PaidToCashierByHisMoneyType();
		WaitingInCafeteriaToFinishAll();
		
		if(Number!= Project3Main.NumOfCustomers-1){
			try{ 
					
			    msg("Joined the Queue to Leave from Store in Descending Order");
				Project3Main.Customers[Number+1].join();  
				Me_Sleeping(random_Num_between(200, 500));
				msg(" LEAVES the Cafe and Store");
			}
			catch(InterruptedException ex){}
		}
		else{
			Terminate();
			msg(" LEAVES the Cafe and Store");
		}
		
		LeaveForHome();
		
	}
	
	public void LeaveForHome(){
		msg(" have left the Store....and now heading towards home...(TERMINATED)");
		if(Number == 0)
			AllCustomersTerminated = true;
			
	}
	
	public void Terminate(){
		   Me_Sleeping(367*Project3Main.NumOfCustomers);
		   msg("Will Leave the Store First");
		   
	}
	
	public void WalksIntoStore(){
		msg("Walked Into Store....");	
	}
	
    public void LookingForItemAndFoundItem(){
    	Me_Sleeping(random_Num_between(20,25));
    	msg("Looking for Item....Looking at Cd's, Games, Laptops, Mobiles....etc");
    	Me_Sleeping(random_Num_between(1000, 2000));
    	msg("Found Item: I like this Item...where is hell is floor clerk ?...oh there he is...");
    	
    }
    
	public void WaitingForFloorClerkAndGetSlip(){
		
		WaitingForFloorClerk = true;
		msg("Waiting for Floor Clerk to get free...");
		
		while(WaitingForFloorClerk != false && ReceiveSlipYet != true ){
		   Me_Sleeping(random_Num_between(100, 200));
		   msg("Still Waiting for Floor Clerk...");
		}
	}
	
	public void WentToCashierAndJoinRespectiveCashierMoneyTypeLine(){
		 
		msg("Waiting for "+PaymentMethod+" taker Cashier to Pay by: "+PaymentMethod);	
		
	}
	
	public void PaidToCashierByHisMoneyType(){
		while(PaidToCashier != true){
			  Me_Sleeping(random_Num_between(100, 200));
			  msg("Still Waiting to Pay to Cashier...");
		}
		msg("Paid to Cashier...Confirmed");
	}
	
	public void WaitingInCafeteriaToFinishAll(){
	   msg("Waiting in Cafeteria");
	   int Count=0;
	   while(AllDone == false){
		  Count = 0; 
		  for(int i=0; i< Project3Main.Customers.length; i++){
			  if(Project3Main.Customers[i]!=null){
		          if(Project3Main.Customers[i].PaidToCashier == true){
		        	  Count++;
		          }
			  }
		  }
		 
		  if(Count == Project3Main.Customers.length)
			 AllDone = true;
		  
		  Me_Sleeping(random_Num_between(1000, 2000));
		  msg("Still Waiting in Cafeteria...");
	   } // while loop ends here...	
    
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

}
