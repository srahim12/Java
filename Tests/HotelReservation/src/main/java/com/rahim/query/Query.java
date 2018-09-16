package com.rahim.query;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
//import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.rahim.domain.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Query {
	 ResultSet rs;
	 int ex;
	 final String url = "jdbc:mysql://localhost:3307/hulton_hotel_db";
	 final String user = "root";
	 final String password = "Bballman123!";
	 //final String url = "jdbc:mysql://hultondb.cvnsn2lsck5a.us-east-1.rds.amazonaws.com:3306/hulton_db";
	 //final String user = "hultondbgroup23";
	 //final String password = "Hultondb101";
	 int roomP = 100;
	 int breakP = 20;
	 int drop = 50;
	 int park = 100;
	 int pick = 50;
	 QueryStatement qr = new QueryStatement();
	public void registerCustomer(Customer customer) {
		customer.setCID(getRandom()+1);
		System.out.println(customer.getCID());
		/*String query = "insert into customer values("+ customer.getCID()
									+","+
							"'"+customer.getName() + "'"
									+","+
							"'"+customer.getAddress()+"'"
									+","+ 
							"'"+customer.getPhone()+"'"
									+","+
							"'"+customer.getEmail()+"'"
									+");";*/
		
		String query = qr.getCustomerQ(customer);
		update(query);
	}
	
	public boolean checkLogin(Login login) {
		boolean exists;
		/*String query = "select * " +
	        				"from customer c " +
	        				"where c.cid "+
	        				"= " +
	        				login.getCid()+
	        				";";*/
		String query = qr.getCheckLoginQ(login);
	    exists = search(query);   
	    if(exists) {
        	return true;
        }
		else{
			return false;
		}
	}
	
	public int findHotel(Search search) {
		boolean empty = true;
		String hotel="";
		Connection conn = null;
		System.out.println(search.getCountry() + "," + search.getState());
		try{
			// 1. Get connection to database    
	      	conn = (Connection) DriverManager.getConnection(url,user,password);
	      	// 2. Create a statement
	        Statement stmt = (Statement) conn.createStatement();
	        // 3. Execute SQL Query
	        /*String query = "select * " +
	        				"from hotel h " + 
	        				"where h.country = "+
	        				"'" + search.getCountry()+ "'" +
	        				" and h.state = " +
	        				"'" + search.getState() + "'" + 
	        				";";*/
	        String query = qr.findHotelQ(search);
	        rs = stmt.executeQuery(query);	        
	        // 4. Process the result set
	        while(rs.next()){
	        	hotel= rs.getString("hotelID");
	        	empty = false;
            }
		 }		
	     catch (Exception exc){
	      	exc.printStackTrace();
	     }finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) { // Use SQLException class instead.
					System.err.println(e);
				}
			}
		
		if(empty) {
			System.out.println("empty");
        	return 0;
        }
		else{
			System.out.println(" NOT empty");
			System.out.println(Integer.parseInt(hotel));
			return Integer.parseInt(hotel);
		}
	}	
	
	public List<Room> findRooms(FindRoom find, int hotelId){
		List<Room> rooms = new ArrayList<Room>();
		boolean empty = true;
		Connection conn = null;
		System.out.println(find.getInDate() +"-->" + find.getOutDate() + " hotel: " + hotelId);
		try{
			// 1. Get connection to database    
	      	conn = (Connection) DriverManager.getConnection(url,user,password);
	      	// 2. Create a statement
	        Statement stmt = (Statement) conn.createStatement();
	        // 3. Execute SQL Query
	        /*String query = "select * " +
			        				"from Room r " +
			        				"where r.hotelID = "+
			        				 hotelId +
			        				" and r.Rtype = " +
			        				"'" + find.getrType() + "'" +
			        				" and r.roomNo not in(select rr.roomNo" +
			        				" " + "from Room_Reservation rr where" +
			        				" " + "rr.hotelID=" + " " + hotelId +
			        				" and " + "("+"'"+find.getInDate()+"'" + "<= rr.checkOutDate"+")" +
			        				" and " + "(" +"'"+find.getOutDate()+"'" + ">= rr.checkInDate));";*/
	        
	        String query = qr.findRoomQ(find, hotelId);
	        rs = stmt.executeQuery(query);
	        // 4. Process the result set
	        while(rs.next()){
	        	Room room = new Room();
	        	room.setHotel(Integer.parseInt(rs.getString("HotelID")));
	        	room.setRoomNo(Integer.parseInt(rs.getString("RoomNo")));
	        	room.setType(rs.getString("Rtype"));
	        	room.setPrice(Integer.parseInt(rs.getString("Price")));
	        	room.setDescription(rs.getString("Description"));
	        	room.setFloor_no(Integer.parseInt(rs.getString("Floor")));
	        	room.setCapacity(Integer.parseInt(rs.getString("Capacity")));
	        	rooms.add(room);
	        	System.out.println("Hotel: " + rs.getString("HotelID") +
	        			" RoomNo: "+ rs.getString("RoomNo"));
	        	empty = false;
            }
		 }		
	      catch (Exception exc){
	      	exc.printStackTrace();
	      }finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) { // Use SQLException class instead.
					System.err.println(e);
				}
			}
		
		if(empty) {
			System.out.println("empty");
        	return null;
		}
		return rooms;
		
	}
	
	public boolean cardExists(Payment s) {
		boolean cExists = false;
		if(s.getOldNumber()!=null && !s.getOldNumber().equals("")) {
			System.out.println("Entered old credit card");
			/*String query = "select * " +
		        				"from credit_card c " + 
		        				"where c.Cnumber = "+
		        				"'" + s.getOldNumber()+ "'" +
		        					";";*/
			String query = qr.cardExistsQ(s);
			cExists =search(query);
			
			
		}
		if(cExists){
			print("Card exists");
			return true;
		}
		print("Card does not exist");
		return false;
		
	}
	
	public void newCard(Payment s) {
		/*String query = "insert into credit_card values("+ "'" + s.getcNumber() + "'"
				+","+
		"'" + s.getcType() + "'"
				+","+
		"'"+ s.getbAddress()+ "'"
				+","+ 
		"'" + s.getCode() + "'"
				+","+
		"'" + s.getDate() + "'"
				+","+
    	"'" + s.getName() + "'"
				+");";*/
		String query = qr.newCardQ(s);
		update(query);  
		
	}
		
	public Reciept makeReservation(int hotelID, int cusId,String cNum, List<FindRoom> f, List<RoomSelection> r,List<Integer> n) {
		int hotel = hotelID;
		int cid = cusId;
		int invoiceNum = getRandom();
		String cNumber = cNum;
		String rDate = getDate();
		int total = 0;
		String reciept = "";
		/*String resQuery = "insert into reservation values("+ invoiceNum
				+ "," +
				  cid
				+ "," +
			"'" + cNumber + "'"
				+ "," + 
			"'" + rDate + "'"
				+ ");";*/
		String resQuery = qr.resQ(invoiceNum, cid, cNumber, rDate);
		if(update(resQuery)==false) {
			return null;
		}
		reciept += "invoice: " + invoiceNum+ "\n"
					+"cid: " + cid +"\n"
					+"RDate: " + rDate +"\n";
		for(int j = 0;j<r.size();j++) {
			/*String roomResQuery = "insert into room_reservation values("+ invoiceNum
				+ "," +
				  hotel
				+ "," +
				r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ "," + 
			"'" + f.get(j).getOutDate() + "'"
				+ ");";*/
			String roomResQuery = qr.roomResQ(hotel, invoiceNum, f, r, j);
			update(roomResQuery);
			total = total + (100*n.get(j));
			reciept += "Room: " + r.get(j).getRoomNo()+ " CheckIn: " + f.get(j).getInDate() + " CheckOut: " + f.get(j).getOutDate() + " Type: " + f.get(j).getrType() + "\n";
					
			if(r.get(j).getAmerican()!=null&&!r.get(j).getAmerican().equals("")) {
				/*String amResQuery = "insert into breakfast_reservation values("+ "'"+ r.get(j).getAmerican()+"'"
						+ "," +
						hotel
						+ "," +
					 r.get(j).getRoomNo()
						+ "," + 
					"'" + f.get(j).getInDate() + "'"
						+ "," + 
					r.get(j).getAmericanAmt()
						+ ");";*/
				String amResQuery = qr.amBreakQ(hotel, invoiceNum, f, r, j);
					update(amResQuery);
				total = total + (breakP*r.get(j).getAmericanAmt());
				reciept += "American: " + r.get(j).getAmericanAmt() + "\n";
			}
			if(r.get(j).getFrench()!=null&&!r.get(j).getFrench().equals("")) {
				/*String frResQuery = "insert into breakfast_reservation values("+ "'"+ r.get(j).getFrench()+"'"
						+ "," +
						hotel
						+ "," +
					 r.get(j).getRoomNo()
						+ "," + 
					"'" + f.get(j).getInDate() + "'"
						+ "," + 
					r.get(j).getFrenchAmt()
						+ ");";*/
				String frResQuery = qr.frBreakQ(hotel, invoiceNum, f, r, j);
					update(frResQuery);
					total = total+ (breakP * r.get(j).getFrenchAmt());
					reciept += "French: " + r.get(j).getFrenchAmt() + "\n";
			}
			if(r.get(j).getItalian()!=null&&!r.get(j).getItalian().equals("")) {
				/*String itResQuery = "insert into breakfast_reservation values("+ "'"+ r.get(j).getItalian()+"'"
						+ "," +
						hotel
						+ "," +
					 r.get(j).getRoomNo()
						+ "," + 
					"'" + f.get(j).getInDate() + "'"
						+ "," + 
					r.get(j).getItalianAmt()
						+ ");";*/
				String itResQuery = qr.itBreakQ(hotel, invoiceNum, f, r, j);
					update(itResQuery);
					total = total +(breakP * r.get(j).getItalianAmt());
					reciept += "Italian: " + r.get(j).getItalianAmt() + "\n";
			}
			if(r.get(j).getParking()!=null&&!r.get(j).getParking().equals("")) {
				/*String parkResQuery = "insert into service_reservation values("+ "'"+ r.get(j).getParking()+"'"
						+ "," +
						hotel
						+ "," +
					 r.get(j).getRoomNo()
						+ "," + 
					"'" + f.get(j).getInDate() + "'"
						+ ");";*/
				String parkResQuery = qr.parkQ(hotel, invoiceNum, f, r, j);
					update(parkResQuery);
					total = total +(park * 1);
					reciept += "Parking " + "\n";
			}
			if(r.get(j).getPickUp()!=null&&!r.get(j).getPickUp().equals("")) {
				/*String pickResQuery = "insert into service_reservation values("+ "'"+ r.get(j).getPickUp()+"'"
						+ "," +
						hotel
						+ "," +
					 r.get(j).getRoomNo()
						+ "," + 
					"'" + f.get(j).getInDate() + "'"
						+ ");";*/
				
				String pickResQuery = qr.pickQ(hotel, invoiceNum, f, r, j);		
					update(pickResQuery);
					total = total +(pick * 1);
					reciept += "Pick Up " + "\n";
				
			}
			if(r.get(j).getDropOff()!=null&&!r.get(j).getDropOff().equals("")) {
				/*String dropResQuery = "insert into service_reservation values("+ "'"+ r.get(j).getDropOff()+"'"
						+ "," +
						hotel
						+ "," +
					 r.get(j).getRoomNo()
						+ "," + 
					"'" + f.get(j).getInDate() + "'"
						+ ");";*/
				String dropResQuery = qr.dropQ(hotel, invoiceNum, f, r, j);
					update(dropResQuery);
					total = total +(drop * 1);
					reciept += "Drop Off "+ "\n";
			}
			
			
		}
		reciept+= "#############################################################";
		List<Discount> discount = getDiscount(invoiceNum,hotel);
		if(discount!=null) {
		for(int i = 0; i<discount.size();i++) {
			reciept += " Room " + discount.get(i).getRoomNo() + " is discounted for " + discount.get(i).getDays();
			total = total -(int)(.2*discount.get(i).getDays()*100);
		}
		}
		
		reciept += "#######################################################################################################" + "\n" +
					"Total: " + total + "\n";
		Reciept rr = new Reciept();
		rr.setTotal(total);
		String totalQuery = qr.totalSpentQ(cid, invoiceNum, total, rDate);
		update(totalQuery);
		rr.setReciept(reciept);
		print(rr.getReciept());
		return rr;
	}
	
	public List<RoomReviewInfo> findRoomRev(int cid,int hotelId, String resType) {
		print("Room info called");
		List<RoomReviewInfo> rr = new ArrayList<RoomReviewInfo>();
		Connection conn = null;
		boolean empty = true;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();
		   	/*String q = "select * from Room_Reservation rr, Reservation r" +
					" where r.cid ="+ cid + " and rr.invoiceNo=r.invoiceNo and rr.hotelId = " + hotelId+ " ;";*/
		    String q = qr.findRoomRevQ(cid, hotelId);
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	RoomReviewInfo r = new RoomReviewInfo();
	        	r.setHotelId(Integer.parseInt(rs.getString("HotelID")));
	        	r.setRoomNo(Integer.parseInt(rs.getString("RoomNo")));
	        	r.setCheckInDate(rs.getString("CheckInDate"));
	        	rr.add(r);
	        	print(r.getHotelId() + "," + r.getRoomNo()+ "," + r.getCheckInDate());
		      	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			return null;
		}
		return rr;
	}
	
	public List<BreakReviewInfo> findBreakRev(int cid,int hotelId, String resType) {
		List<BreakReviewInfo> rr = new ArrayList<BreakReviewInfo>();
		Connection conn = null;
		boolean empty = true;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();
		    /*String q = "select br.Btype, br.HotelId, br.RoomNo, br.CheckInDate "
		    			+ "from Breakfast_Reservation br, Reservation r, Room_Reservation rr "
		    			+ "where r.cid=" + cid
		    			+ " and rr.invoiceNo= r.invoiceNo"
		    			+ " and rr.hotelId = " + hotelId
		    			+ " and br.hotelId = " + hotelId
		    			+ " and br.RoomNo = rr.RoomNo;";*/
		    String q = qr.findBreakRevQ(cid, hotelId);
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	BreakReviewInfo r = new BreakReviewInfo();
	        	r.setHotelId(Integer.parseInt(rs.getString("HotelID")));
	        	r.setRoomNo(Integer.parseInt(rs.getString("RoomNo")));
	        	r.setCheckInDate(rs.getString("CheckInDate"));
	        	r.setbType(rs.getString("Btype"));
	        	rr.add(r);
		      	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			return null;
		}
		return rr;
	}
	
	public List<SerReviewInfo> findSerRev(int cid,int hotelId, String resType) {
		List<SerReviewInfo> rr = new ArrayList<SerReviewInfo>();
		Connection conn = null;
		boolean empty = true;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();
		   	/*select br.Stype, br.HotelId, br.RoomNo, br.CheckInDate 
				from Service_Reservation br, Reservation r, Room_Reservation rr
				where r.cid=14 and rr.invoiceNo=r.invoiceNo and rr.hotelId = 123
				and br.hotelId=123 and br.RoomNo = rr.RoomNo;*/
		    /*String q = "select br.Stype, br.HotelId, br.RoomNo, br.CheckInDate "
		    			+ "from Service_Reservation br, Reservation r, Room_Reservation rr "
		    			+ "where r.cid=" + cid
		    			+ " and rr.invoiceNo= r.invoiceNo"
		    			+ " and rr.hotelId = " + hotelId
		    			+ " and br.hotelId = " + hotelId
		    			+ " and br.RoomNo = rr.RoomNo;";*/
		    String q = qr.findServRevQ(cid, hotelId);
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	SerReviewInfo r = new SerReviewInfo();
	        	r.setHotelId(Integer.parseInt(rs.getString("HotelID")));
	        	r.setRoomNo(Integer.parseInt(rs.getString("RoomNo")));
	        	r.setCheckInDate(rs.getString("CheckInDate"));
	        	r.setsType(rs.getString("Stype"));
	        	rr.add(r);
		      	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			return null;
		}
		return rr;
	}	
	
	public boolean roomReview(int hotelId, int cid, Room_Review r) {
		int revId = getRandom();
		/*insert into room_reservation values(rid, hotelId, roomNo, checkInDate, cid, rating, comment);*/
		Connection conn = null;
		boolean success=true;
		try{
            // 1. Get connection to database    
        	conn = (Connection) DriverManager.getConnection(url,user,password);
          	// 2. Create a statement
            Statement stmt = (Statement) conn.createStatement();
            // 3. Execute SQL Query
            /*String query = "insert into room_review values("+ revId
										+","+
									 hotelId
										+","+
									r.getRoomNo()
										+","+ 
									"'"+r.getCheckInDate()+"'"
										+","+
										+ cid 
										+","+
									+ r.getRating()
										+","+ 
									"'"+r.getComment()+"'"
										+");";*/
            String query = qr.roomRevQ(hotelId, cid, r, revId);
            System.out.println(query);
            ex = stmt.executeUpdate(query);
        }
        catch (Exception exc){
        	success= false;
        	exc.printStackTrace();
        	
        }finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(!success) {
			return false;
		}
		return true;
		
	}
	
	public boolean breakReview(int hotelId, int cid, Breakfast_Review r) {
		int revId = getRandom();
		/*insert into breakast_review(revId, hotelId, r.getRoomNo(), r.getCheckInDate(), r.getbType(), cid, r.getRating(), r.getComment());*/
		Connection conn = null;
		boolean success=true;
		try{
            // 1. Get connection to database    
        	conn = (Connection) DriverManager.getConnection(url,user,password);
          	// 2. Create a statement
            Statement stmt = (Statement) conn.createStatement();
            // 3. Execute SQL Query
            /*String query = "insert into breakfast_review values("+ revId
										+","+
									 hotelId
										+","+
									r.getRoomNo()
										+","+ 
									"'"+r.getCheckInDate()+"'"
										+","+ 
									"'"+r.getbType()+"'"
										+","+
										+ cid 
										+","+
									+ r.getRating()
										+","+ 
									"'"+r.getComment()+"'"
										+");";*/
            String query = qr.breakRevQ(hotelId, cid, r, revId);
            System.out.println(query);
            ex = stmt.executeUpdate(query);
        }
        catch (Exception exc){
        	success= false;
        	exc.printStackTrace();
        	
        }finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(!success) {
			return false;
		}
		return true;
		
		

	}
	
	public boolean servReview(int hotelId, int cid, Service_Review r) {
		int revId = getRandom();
		/*insert into breakast_review(revId, hotelId, r.getRoomNo(), r.getCheckInDate(), r.getbType(), cid, r.getRating(), r.getComment());*/
		Connection conn = null;
		boolean success=true;
		try{
            // 1. Get connection to database    
        	conn = (Connection) DriverManager.getConnection(url,user,password);
          	// 2. Create a statement
            Statement stmt = (Statement) conn.createStatement();
            // 3. Execute SQL Query
            /*String query = "insert into service_review values("+ revId
										+","+
									 hotelId
										+","+
									r.getRoomNo()
										+","+ 
									"'"+r.getCheckInDate()+"'"
										+","+ 
									"'"+r.getsType()+"'"
										+","+
										+ cid 
										+","+
									+ r.getRating()
										+","+ 
									"'"+r.getComment()+"'"
										+");";*/
            String query = qr.servRevQ(hotelId, cid, r, revId);
            System.out.println(query);
            ex = stmt.executeUpdate(query);
        }
        catch (Exception exc){
        	success= false;
        	exc.printStackTrace();
        	
        }finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(!success) {
			return false;
		}
		return true;
		
	}
	
	public List<HighestRate> highestRated(StatDate d) {
		List<HighestRate> h = new ArrayList<HighestRate>();
		h.add(highestR(d.getStart(),d.getEnd(),123));
		h.add(highestR(d.getStart(),d.getEnd(),124));
		h.add(highestR(d.getStart(),d.getEnd(),125));
		return h;
		
	}
	
	public HighestRate highestR(String start, String end, int hotel) {
		boolean empty = true;
		HighestRate h = new HighestRate();;
		Connection conn = null;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();

		    /*String q = "select MAX(k.rating), r.Rtype, r.hotelId"  
		    			+" From room_review k, room r, room_reservation t" 
		    			+" Where t.hotelId = k.hotelId" 
		    			+" AND"
		    			+" t.RoomNo = k.RoomNo"
		    			+" AND"
		    			+" k.hotelId = " + hotel
		    			+" AND"
		    			+" k.RoomNo = r.RoomNo"
		    			+" and (k.CheckInDate >" + "'"+start+"'" 	
						+" and k.CheckInDate < " + "'"+end+"'" +")" 	
						+" GROUP BY r.HotelId;";*/
		    String q = qr.highestRQ(start, end, hotel);
		   
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	h.setHotel(""+hotel);
		    	h.setMax(rs.getString("MAX(k.rating)"));
		    	h.setrType(rs.getString("Rtype"));
		    	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			h = new HighestRate();
			h.setHotel(""+hotel);
			h.setMax("No Rating");
			h.setrType("No Rating");
		}		
		return h;
		
	}

	public RateRes highestBreak(StatDate d) {
		boolean empty = true;
		RateRes h = new RateRes();;
		Connection conn = null;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();

		    /*String q ="Select MAX(b.rating), br.Btype, b.hotelId"
		    		+" From breakfast_review b, breakfast_reservation br, room_reservation t"
		    		+" Where t.hotelId = b.hotelId"
		    		+" AND"
		    		+" t.RoomNo = b.RoomNo"
		    		+" AND"
		    		+" b.hotelId = br.hotelId"
		    		+" AND"
		    		+" b.RoomNo = br.RoomNo"
		    		+" and (b.CheckInDate >=" + "'"+d.getStart()+"'"+")" 
		    		+" and (b.CheckInDate <=" + "'"+d.getEnd()+"');";*/
		    String q = qr.highestBQ(d);

		   
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	h.setHotel(rs.getString("hotelId"));
		    	h.setMax(rs.getString("MAX(b.rating)"));
		    	h.setType(rs.getString("Btype"));
		    	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			h.setHotel("No Rating");
			h.setMax("No Rating");
			h.setType("No Rating");
		}		
		return h;
	}
	
	public RateRes highestServ(StatDate d) {
		boolean empty = true;
		Connection conn = null;
		RateRes h = new RateRes();;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();

		    /*String q = "Select MAX(b.rating), br.Stype, b.hotelId"
		    		+" From service_review b, service_reservation br, room_reservation t"
		    		+" Where t.hotelId = b.hotelId"
		    		+" AND"
		    		+" t.RoomNo = b.RoomNo"
		    		+" AND"
		    		+" b.hotelId = br.hotelId"
		    		+" AND"
		    		+" b.RoomNo = br.RoomNo"
		    		+" and (b.CheckInDate >="+ "'"+d.getStart()+"')" 
		    		+" and (b.CheckInDate <="+ "'"+d.getEnd()+"');";*/
		    String q = qr.highestSQ(d);

		   
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	h.setHotel(rs.getString("hotelId"));
		    	h.setMax(rs.getString("MAX(b.rating)"));
		    	h.setType(rs.getString("Stype"));
		    	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			h.setHotel("No Rating");
			h.setMax("No Rating");
			h.setType("No Rating");
		}		
		return h;
	}

	public List<RatedCus> highestCus(StatDate d){
		boolean empty = true;
		Connection conn = null;
		List<RatedCus> h = new ArrayList<RatedCus>();
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();
		    String q = qr.highestCusQ(d);
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	RatedCus r = new RatedCus();
		    	r.setCid(rs.getString("CID"));
		    	r.setTotalSpent(rs.getString("MoneySpent"));
		    	empty = false;
		    	h.add(r);
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			return null;
		}		
		return h;
		
	}
	
	
/*###################################################################################################################*/
	
	public List<Discount> getDiscount(int invoice, int hotel){
		List<Discount> d = new ArrayList<Discount>();
		Connection conn = null;
		boolean empty = true;
		try{
			// 1. Get connection to database    
		   	conn = (Connection) DriverManager.getConnection(url, user, password);
		   	// 2. Create a statement
		    Statement stmt = (Statement) conn.createStatement();
		   	/*select br.Stype, br.HotelId, br.RoomNo, br.CheckInDate 
				from Service_Reservation br, Reservation r, Room_Reservation rr
				where r.cid=14 and rr.invoiceNo=r.invoiceNo and rr.hotelId = 123
				and br.hotelId=123 and br.RoomNo = rr.RoomNo;*/
		    /*String q = ""
		    		+ "select r1.RoomNo, case when r1.CheckOutDate > d.EndDate then d.EndDate else r1.CheckOutDate end - "
		    		+ "case when r1.CheckInDate < d.StartDate then d.StartDate else r1.CheckInDate end as discount_days "
		    		+ "from room_reservation r1 "
		    		+ "join discounted_room d "
		    		+ "on r1.HotelID = d.HotelID "
		    		+ "and d.RoomNo = r1.RoomNo "
		    		+ "and r1.CheckInDate < d.EndDate "
		    		+ "and r1.CheckOutDate > d.StartDate "
		    		+ "where r1.InvoiceNo ="+ invoice + " " 
		    		+ "and r1.HotelID ="+ hotel+";";*/
		    String q = qr.getDiscountQ(invoice, hotel);
		    // 3. Execute SQL Query
		    rs = stmt.executeQuery(q);
		    // 4. Process the result set
		    while(rs.next()){
		    	Discount dd = new Discount();
		    	dd.setRoomNo(Integer.parseInt(rs.getString("RoomNo")));
		    	dd.setDays(Integer.parseInt(rs.getString("discount_days")));
		    	d.add(dd);
		      	print("Room " + dd.getRoomNo()+ " Days " + dd.getDays());
		    	empty = false;
		    }
		 }		
		 catch (Exception exc){
		   	exc.printStackTrace();
		}finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(empty) {
			return null;
		}
		return d;
		
		
	}
	
	public void print(String str) {
    	System.out.println(str);
    }
		
	public int getRandom() {
    	Random rand = new Random();
    	int value = rand.nextInt(10000);
    	return value;
    }
	
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String string  = dateFormat.format(new Date());
		return string;		
	}
	
	public boolean update(String query) {
		boolean success=true;
		Connection conn = null;
		try{
            // 1. Get connection to database    
        	conn = (Connection) DriverManager.getConnection(url,user,password);
          	// 2. Create a statement
            Statement stmt = (Statement) conn.createStatement();
            // 3. Execute SQL Query
            System.out.println(query);
            ex = stmt.executeUpdate(query);
        }
        catch (Exception exc){
        	success= false;
        	exc.printStackTrace();
        	
        }finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) { // Use SQLException class instead.
				System.err.println(e);
			}
		}
		if(!success) {
			return false;
		}
		return true;
	}
	
	public boolean search(String query) {
		boolean empty = true;
		Connection conn = null;
		try{
			// 1. Get connection to database    
	      	conn = (Connection) DriverManager.getConnection(url, user, password);
	      	// 2. Create a statement
	        Statement stmt = (Statement) conn.createStatement();
	        // 3. Execute SQL Query
	        rs = stmt.executeQuery(query);
	        // 4. Process the result set
	        while(rs.next()){
	        	empty = false;
	        }
		 }		
	     catch (Exception exc){
	      	exc.printStackTrace();
	     }finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) { // Use SQLException class instead.
					System.err.println(e);
				}
			}
		if(!empty) {
			return true;
		}
		return false;
	}
	
	public String searchRoomRev() {
		boolean empty = true;
		Connection conn = null;
		try{
			// 1. Get connection to database    
	      	conn = (Connection) DriverManager.getConnection(url, user, password);
	      	// 2. Create a statement
	        Statement stmt = (Statement) conn.createStatement();
	        
	        //String query = 
	        // 3. Execute SQL Query
	        //rs = stmt.executeQuery(query);
	        // 4. Process the result set
	        while(rs.next()){
	        	empty = false;
	        }
		 }		
	     catch (Exception exc){
	      	exc.printStackTrace();
	     }finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) { // Use SQLException class instead.
					System.err.println(e);
				}
			}
		return "";
	}

}
