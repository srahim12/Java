package com.rahim.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.rahim.domain.BreakReviewInfo;
import com.rahim.domain.Breakfast_Review;
import com.rahim.domain.Customer;
import com.rahim.domain.FindRoom;
import com.rahim.domain.HighestRate;
import com.rahim.domain.Login;
import com.rahim.domain.Payment;
import com.rahim.domain.RateRes;
import com.rahim.domain.RatedCus;
import com.rahim.domain.Reciept;
import com.rahim.domain.ReviewType;
import com.rahim.domain.Room;
import com.rahim.domain.RoomReviewInfo;
import com.rahim.domain.RoomSelection;
import com.rahim.domain.Room_Review;
import com.rahim.domain.Search;
import com.rahim.domain.SerReviewInfo;
import com.rahim.domain.Service_Review;
import com.rahim.domain.StatDate;
import com.rahim.query.Query;

@Controller

//The main controller for this project
public class Hulton_Controller {
	Logger log = LoggerFactory.getLogger(this.getClass());
	Query q = new Query();
	int hotel, cid,daysStaying;
    FindRoom f;
    List<FindRoom> findRooms = new ArrayList<FindRoom>();
    List<Integer> validRooms;
    List<Integer> daysStay = new ArrayList<Integer>();
    SessionStatus sess;
    List<RoomSelection> roomOrders = new ArrayList<RoomSelection>();
    List<Room> roomList;
    boolean sameRes=false;
    Payment payment = new Payment();
    int revHotel;
    
    //When the project boots up, the webpage displayed is the start home page
    @RequestMapping("/")
	public String Hulton_Start(ModelMap model){
		System.out.println("Hulton_Start1");
		//return "Hulton_Start_Home";
		return "Hulton_Start_Home";
	}


    //If the user makes a request for the start home page, they will be redirected to it
    @RequestMapping("Hulton_Start_Home")
	public String Hulton_Start_Home(ModelMap model){
		System.out.println("Hulton_Start2");
		return "Hulton_Start_Home";
	}
    
    //This is the customer Home page which is served when requested for
    @RequestMapping("Hulton_Home")
	public String Hulton_Login_Home(ModelMap model){
    	System.out.println("Hulton_Home");
    	findRooms.clear();
    	roomOrders.clear();
    	daysStay.clear();
		return "Hulton_Home";
	}
    
    /*When the user makes a get request for the registration page, they will be served the page with the
    	customer object initialized with this webpage for html injection*/
    @RequestMapping(value="Hulton_Register", method=RequestMethod.GET)
    public String customerForm(Model model) {
		System.out.println("Hulton_Register");
		model.addAttribute("customer", new Customer());
        findRooms.clear();
    	roomOrders.clear();
    	daysStay.clear();
        return "Hulton_Register";
    }   
    
    //When the user makes a post request from the register page, the html document will take the forms input
    // and map the input the customer object's fields and redirect the user to a user home page
    @RequestMapping(value="/Hulton_Register", method=RequestMethod.POST)
    public String customerSubmit(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);
        System.out.println(customer.getCID());
        q.registerCustomer(customer);
        cid = customer.getCID();
        return "Hulton_User_Home";
    }
    
    @RequestMapping(value="/Hulton_Login", method=RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("login", new Login());
        findRooms.clear();
    	roomOrders.clear();
    	daysStay.clear();
        return "Hulton_Login";
    } 
    
    @RequestMapping(value="/Hulton_Login", method=RequestMethod.POST)
    public String loginSubmit(@ModelAttribute Login login, Model model) {
    	model.addAttribute("login", login);
    	if(q.checkLogin(login)==false) {
    		return "Hulton_Login";
    	}
      	System.out.println(q.checkLogin(login));
    	cid = login.getCid();
    	return "Hulton_Login_Home";
    }
     
    @RequestMapping(value="/Hulton_Reservation_LookUp", method=RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("search", new Search());
        return "Hulton_Reservation_LookUp";
    }   
       
    @RequestMapping(value="/Hulton_Reservation_LookUp", method=RequestMethod.POST)
    public String searchSubmit(@ModelAttribute Search search, Model model) {
    	model.addAttribute("search", search);
    	hotel = q.findHotel(search);
    	if(hotel==0) {
    		return "Hulton_Reservation_LookUp";
    	}
    	System.out.println(hotel + "--->" + cid);
    	return "Hulton_Found_Hotel";
    }    
    
    @RequestMapping(value="/Hulton_Type_Selection", method=RequestMethod.GET)
    public String findForm(Model model) {
        model.addAttribute("find", new FindRoom());
        findRooms.clear();
    	roomOrders.clear();
    	daysStay.clear();
        return "Hulton_Type_Selection";
    }       
    
    @RequestMapping(value="/Hulton_Type_Selection", method=RequestMethod.POST)
    public String findSubmit(@ModelAttribute FindRoom find, Model model, SessionStatus session) {
    	List<Room> rooms = new ArrayList<>(); 
    	model.addAttribute("find",find );
    	FindRoom f = new FindRoom();
    	f.setInDate(find.getInDate());
    	f.setOutDate(find.getOutDate());
    	f.setrType(find.getrType());
    	sess = session;
    	if(toDays(f.getInDate(),f.getOutDate())<1) {
    		System.out.println("Staying 0 or negative days");
    		return "Hulton_Type_Selection";
    	}
    	daysStaying = (int)toDays(f.getInDate(),f.getOutDate());
        roomList =q.findRooms(find, hotel);
        if(roomList==null) {
        	return "Hulton_Type_Selection";
        }
        validRooms=getValidRooms(roomList);
        findRooms.add(f);
        daysStay.add(daysStaying);
        model.addAttribute("rooms",roomList);
        System.out.println(hotel + "--->" + cid);
    	
        return "Hulton_Display_Room";
    }
    
    @RequestMapping(value="/Hulton_ReType_Selection", method=RequestMethod.GET)
    public String reFindForm(Model model) {
        model.addAttribute("find", new FindRoom());
        sameRes=true;
        printRes();
        return "Hulton_ReType_Selection";
    }    
    
    @RequestMapping(value="/Hulton_ReType_Selection", method=RequestMethod.POST)
    public String reFindSubmit(@ModelAttribute FindRoom find, Model model, SessionStatus session) {
    
    	List<Room> rooms = new ArrayList<>(); 
    	model.addAttribute("find",find );
    	FindRoom f = new FindRoom();
    	f.setInDate(find.getInDate());
    	f.setOutDate(find.getOutDate());
    	f.setrType(find.getrType());
    	
    	sess = session;
    	if(toDays(f.getInDate(),f.getOutDate())<1) {
    		System.out.println("Staying 0 or negative days");
    		return "Hulton_ReType_Selection";
    	}
    	daysStaying = (int)toDays(f.getInDate(),f.getOutDate());
        roomList =q.findRooms(find, hotel);
        if(roomList==null) {
        	print("Not empty roomList");
        	return "Hulton_ReType_Selection";
        }
        validRooms=getValidRooms(roomList);
        if(roomList==null) {
        	print("Not empty roomList");
        	return "Hulton_ReType_Selection";
        }
        print("Before");
        printRoom();
        roomFree(f.getInDate(),f.getOutDate());
        print("After");
        printRoom();
        if(roomList.isEmpty()) {
        	return "Hulton_ReType_Selection";
        }
        model.addAttribute("rooms",roomList);
        System.out.println(hotel + "--->" + cid);
        findRooms.add(f);
        daysStay.add(daysStaying);
        return "Hulton_Display_Room";
    }
    
    
    @RequestMapping(value="/Hulton_Choose_Order", method=RequestMethod.GET)
    public String chooseForm(Model model) {
        model.addAttribute("roomSelection", new RoomSelection());
        return "Hulton_Choose_Order";
    }
    
    @RequestMapping(value="/Hulton_Choose_Order", method=RequestMethod.POST)
    public String chooseSubmit(@ModelAttribute RoomSelection roomSelection, Model model, SessionStatus session) {
    	boolean transaction;
    	int totalB;
    	model.addAttribute("roomSelection",roomSelection);
    	totalB = roomSelection.getAmericanAmt()+roomSelection.getFrenchAmt()+ roomSelection.getItalianAmt();
    	if(!enteredValidRoom(roomSelection.getRoomNo())) {
    		System.out.println("Not a valid room");
    		return "Hulton_Choose_Order";
    	}
       	else if(totalB>daysStaying) {
    		System.out.println("Too many breakfasts" + "Total days are: "+ daysStaying);
    		return "Hulton_Choose_Order";
    	}
    	roomOrders.add(roomSelection);
    	
       	print("RoomOrders size: " + roomOrders.size());
       	print("FindRooms size: " +findRooms.size());
       	print("DaysSty size: " + daysStay.size());
    	return "Order_Result";
    	
    }
    
    @RequestMapping(value="/Hulton_Choose_Payment", method=RequestMethod.GET)
    public String paymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        print("RoomOrders size: " + roomOrders.size());
       	print("FindRooms size: " +findRooms.size());
       	print("DaysStay size: " + daysStay.size());
        return "Hulton_Choose_Payment";
    }
    
    @RequestMapping(value="/Hulton_Choose_Payment", method=RequestMethod.POST)
    public String paymentSubmit(@ModelAttribute Payment payment, Model model, SessionStatus session) {
    	boolean exists = false;
    	int transaction;
    	String cardNumber;
    	model.addAttribute("payment", payment);
    	print("RoomOrders size: " + roomOrders.size());
       	print("FindRooms size: " +findRooms.size());
       	print("DaysStay size: " + daysStay.size());
       	Reciept r = new Reciept();
       	if(!payment.getOldNumber().equals("")&&payment.getOldNumber()!=null) {
    		if(q.cardExists(payment)) {
    			cardNumber=payment.getOldNumber();
    		}
    		else {
    			return "Hulton_Choose_Payment";
    		}
    	}
    	else {
    		q.newCard(payment);
    		cardNumber = payment.getcNumber();
    		
    	}
    	r = q.makeReservation(hotel, cid, cardNumber, findRooms, roomOrders,daysStay);
    	if(r==null) {
    		return "Hullton_Choose_Payment";
    	}
    	else {
    		findRooms.clear();
    		roomOrders.clear();
    		daysStay.clear();
    	}
    	//print("Total is: " + transaction);
    	
    	//r.setTotal(transaction);
    	model.addAttribute("reciept",r);
    	model.addAttribute("newLineChar", '\n');
    	return "Hulton_Reservation_Success";
    	
    }
    
    @RequestMapping(value="/Hulton_Review_Type", method=RequestMethod.GET)
    public String reviewType(Model model) {
        model.addAttribute("reviewType", new ReviewType());
        return "Hulton_Review_Type";
    }
    
    @RequestMapping(value="/Hulton_Review_Type", method=RequestMethod.POST)
    public String reviewTypeSubmit(@ModelAttribute ReviewType reviewType, Model model, SessionStatus session) {
    	model.addAttribute("reviewType", reviewType);
    	print("Review Type" + reviewType.getReviewType() + " Hotel " + reviewType.getHotelId());
    	revHotel = reviewType.getHotelId();
    	if(reviewType.getReviewType().equals("Room")) {
    		List<RoomReviewInfo> rr = new ArrayList<RoomReviewInfo>();
    		rr=q.findRoomRev(cid, reviewType.getHotelId(), reviewType.getReviewType());
    		model.addAttribute("roomInfo", rr);
    		print("Added attribute");
    		return "Hulton_Room_Info";
    	}
    	
    	else if(reviewType.getReviewType().equals("Breakfast")) {
    		List<BreakReviewInfo> rr = new ArrayList<BreakReviewInfo>();
    		rr=q.findBreakRev(cid, reviewType.getHotelId(), reviewType.getReviewType());
    		model.addAttribute("roomInfo", rr);
    		return "Hulton_Breakfast_Info";
    	}
    	
    	else if(reviewType.getReviewType().equals("Service")) {
    		List<SerReviewInfo> rr = new ArrayList<SerReviewInfo>();
    		rr=q.findSerRev(cid, reviewType.getHotelId(), reviewType.getReviewType());
    		model.addAttribute("roomInfo", rr);
    		return "Hulton_Service_Info";
    	}
    	print("Should not have gotten to this point");
    	return "Hulton_Review_Type";
    	
    }
    
    @RequestMapping(value="/Hulton_Room_Review", method=RequestMethod.GET)
    public String reviewRoom(Model model) {
        model.addAttribute("roomReview", new Room_Review());
        return "Hulton_Room_Review";
    }
    
    @RequestMapping(value="/Hulton_Room_Review", method=RequestMethod.POST)
    public String roomRevSubmit(@ModelAttribute Room_Review roomReview, Model model, SessionStatus session) {
    	model.addAttribute("roomReview", roomReview);
    	boolean transaction;
//    	if(roomReview.getCheckInDate().equals("")||roomReview)
    	transaction = q.roomReview(revHotel, cid, roomReview);
    	if(!transaction) {
    		return "Hulton_Room_Review";
    	}
    	return "Hulton_RoomRev_Display";
    }
    
    @RequestMapping(value="/Hulton_Breakfast_Review", method=RequestMethod.GET)
    public String reviewBreakfast(Model model) {
        model.addAttribute("breakReview", new Breakfast_Review());
        return "Hulton_Breakfast_Review";
    }
    
    @RequestMapping(value="/Hulton_Breakfast_Review", method=RequestMethod.POST)
    public String breakRevSubmit(@ModelAttribute Breakfast_Review breakReview, Model model, SessionStatus session) {
    	model.addAttribute("breakReview", breakReview);
    	boolean transaction;
    	transaction = q.breakReview(revHotel, cid,breakReview );
    	if(!transaction) {
    		print("Not a valid review");
    		return "Hulton_Breakfast_Review";
    	}
    	return "Hulton_BreakRev_Display";
    }
    
    @RequestMapping(value="/Hulton_Service_Review", method=RequestMethod.GET)
    public String reviewService(Model model) {
        model.addAttribute("servReview", new Service_Review());
        return "Hulton_Service_Review";
    }
    
    @RequestMapping(value="/Hulton_Service_Review", method=RequestMethod.POST)
    public String servRevSubmit(@ModelAttribute Service_Review servReview, Model model, SessionStatus session) {
    	model.addAttribute("servReview", servReview);
    	boolean transaction;
    	transaction = q.servReview(revHotel, cid,servReview );
    	if(!transaction) {
    		return "Hulton_Service_Review";
    	}
    	return "Hulton_ServRev_Display";
    	
    }
    
    @RequestMapping(value="/Hulton_Stat_Dash", method=RequestMethod.GET)
    public String statDash(Model model) {
        //model.addAttribute("servReview", new Service_Review());
        return "Hulton_Stat_Dash";
    }
    
    @RequestMapping(value="/Hulton_Rated_Room", method=RequestMethod.GET)
    public String ratedForm(Model model) {
        model.addAttribute("ratedRoom", new StatDate());
        return "Hulton_Rated_Room";
    }
    
   @RequestMapping(value="/Hulton_Rated_Room", method=RequestMethod.POST)
    public String ratedSubmit(@ModelAttribute StatDate ratedRoom, Model model, SessionStatus session) {
    	List<HighestRate> h = new ArrayList<HighestRate>();
    	h = q.highestRated(ratedRoom);
    	model.addAttribute("ratedRoom", h);
    	return "Hulton_RatedRoom_Result";
    	
    }
   
   @RequestMapping(value="/Hulton_Rated_Customer", method=RequestMethod.GET)
   public String cusForm(Model model) {
       model.addAttribute("ratedCus", new StatDate());
       return "Hulton_Rated_Customer";
   }
   
  @RequestMapping(value="/Hulton_Rated_Customer", method=RequestMethod.POST)
   public String cusSubmit(@ModelAttribute StatDate ratedCus, Model model, SessionStatus session) {
   	  List<RatedCus> r = q.highestCus(ratedCus);
	  if(r==null) {
		  return "Hulton_Rated_Customer";
	  }
	  model.addAttribute("ratedCus",r);
	  return "Hulton_RatedCus_Result";
   }
   
   @RequestMapping(value="/Hulton_Rated_Breakfast", method=RequestMethod.GET)
   public String breakForm(Model model) {
       model.addAttribute("ratedBreak", new StatDate());
       return "Hulton_Rated_Breakfast";
   }
   
  @RequestMapping(value="/Hulton_Rated_Breakfast", method=RequestMethod.POST)
   public String breakSubmit(@ModelAttribute StatDate ratedBreak, Model model, SessionStatus session) {
   	  RateRes r = q.highestBreak(ratedBreak);
	  model.addAttribute("ratedBreak",r);
	  return "Hulton_RatedBreak_Result";
   }
  
  @RequestMapping(value="/Hulton_Rated_Service", method=RequestMethod.GET)
  public String servForm(Model model) {
      model.addAttribute("ratedServ", new StatDate());
      return "Hulton_Rated_Service";
  }
  
 @RequestMapping(value="/Hulton_Rated_Service", method=RequestMethod.POST)
  public String servSubmit(@ModelAttribute StatDate ratedService, Model model, SessionStatus session) {
  	RateRes s = q.highestServ(ratedService);
  	model.addAttribute("ratedServ", s);
  	return "Hulton_RatedServ_Result";
  	
  }
     
    
//###############################################################################################################################
    
    public void roomFree(String inDate,String outDate) {
    	List<Integer> invalid = new ArrayList<>();
    	for(int i = 0;i<roomOrders.size();i++) {
    		for(int j=0;j<validRooms.size();j++) {
    			print("Room Order: "+ roomOrders.get(i).getRoomNo()+ " checking: " + validRooms.get(j));
    			if(roomOrders.get(i).getRoomNo()==validRooms.get(j)&&overLaps(inDate,outDate,i)) {
    				roomList.remove(j);
    				validRooms.remove(j);
    				print("There is an actual collision");
    			}
    		}
    	}
    }    
    
    public boolean overLaps(String in, String out, int index) {
    	boolean overlaps=false;
    	DateTime start1 = parseDate(in);
		DateTime end1 = parseDate(out);
    	for(int i=0;i<findRooms.size();i++) {
    		DateTime start2 = parseDate(findRooms.get(i).getInDate());
    		DateTime end2 = parseDate(findRooms.get(i).getOutDate());
    		Interval interval = new Interval( start1, end1 );
    		Interval interval2 = new Interval( start2, end2 );
    		if(interval.overlaps(interval2) &&i==index) {
    			System.out.println("Overlaps at RoomOrder: "+ index + " and FindRoom: " + i );
    			overlaps=true;
    		}
    	}
    	if(overlaps) {
    		print("Overlaps");
    		return true;
    	}
    	return false;
    }
    
    public float toDays(String date1, String date2) {
	    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	    float daysBetween=0;
	    try{
		    Date dateBefore = ft.parse(date1);
		    Date dateAfter = ft.parse(date2);
		    long difference = dateAfter.getTime() - dateBefore.getTime();
		    daysBetween = (difference / (1000*60*60*24));
		    return daysBetween;
		}catch (Exception e){
			e.printStackTrace();
		}
		return daysBetween;
	}
    
    public List<Integer> getValidRooms(List<Room> rooms){
    	List<Integer> roomNos = new ArrayList<Integer>();
    	if(rooms.size()==0) {
    		return null;
    	}
    	for(int i = 0;i<rooms.size();i++) {
    		roomNos.add(rooms.get(i).getRoomNo());
    		System.out.println(rooms.get(i).getRoomNo());
    	}
    	return roomNos;
    }    
    
    public boolean enteredValidRoom(int room) {
    	boolean valid = false;
    	for(int i = 0;i<validRooms.size();i++){
    		if(validRooms.get(i)==room) {
    			valid = true;
    		}
      	}
    	if(valid) {
    		return true;
    	}    	
    	return false;
    }
    
    public DateTime parseDate(String date) {
    	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    	Date d;
    	DateTime dt = new DateTime();
    	try{
		    d = ft.parse(date);
		    dt = new DateTime(d);
		}catch (Exception e){
			e.printStackTrace();
		}
    	return dt;
    }
    
    public void printRes() {
    	for(int i =0;i<roomOrders.size();i++) {
    		System.out.print("Room: " +roomOrders.get(i).getRoomNo() + "is booked from " +
    					findRooms.get(i).getInDate() + " to: "+ findRooms.get(i).getOutDate());
    		System.out.println();
    	}
    }
    
    public void print(String str) {
    	System.out.println(str);
    }
   
    public void printRoom() {
    	print("The room list is-->");
    	for(int i =0;i<roomList.size();i++) {
    		print("Room" + roomList.get(i).getRoomNo());
    	}
    }
}


