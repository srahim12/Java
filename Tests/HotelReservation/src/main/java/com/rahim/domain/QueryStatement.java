package com.rahim.domain;

import java.util.List;

public class QueryStatement {
	
	public String getCustomerQ(Customer customer) {
		String query = "insert into CUSTOMER values("+ customer.getCID()
								+","+
						"'"+customer.getName() + "'"
								+","+
						"'"+customer.getAddress()+"'"
								+","+ 
						"'"+customer.getPhone()+"'"
								+","+
						"'"+customer.getEmail()+"'"
								+");";
		return query;
	}
	
	public String getCheckLoginQ(Login login) {
		String query = "select * " +
				"from CUSTOMER c " +
				"where c.CID "+
				"= " +
				login.getCid()+
				";";
		
		return query;
	}
	
	public String findHotelQ(Search search) {
		 String query = "select * " +
 				"from HOTEL h " + 
 				"where h.Country = "+
 				"'" + search.getCountry()+ "'" +
 				" and h.State = " +
 				"'" + search.getState() + "'" + 
 				";";
		 return query;
	}
	
	public String findRoomQ(FindRoom find, int hotelId) {
		String query = "select * " +
				"from ROOM r " +
				"where r.HotelID = "+
				 hotelId +
				" and r.Rtype = " +
				"'" + find.getrType() + "'" +
				" and r.RoomNo not in(select rr.RoomNo" +
				" " + "from ROOM_RESERVATION rr where" +
				" " + "rr.HotelID=" + " " + hotelId +
				" and " + "("+"'"+find.getInDate()+"'" + "<= rr.CheckOutDate"+")" +
				" and " + "(" +"'"+find.getOutDate()+"'" + ">= rr.CheckInDate));";
		
		return query;
	}
	
	public String cardExistsQ(Payment s) {
		String query = "select * " +
				"from CREDIT_CARD c " + 
				"where c.Cnumber = "+
				"'" + s.getOldNumber()+ "'" +
					";";
		
		return query;
	}
	
	public String newCardQ(Payment s) {
		String query = "insert into CREDIT_CARD values("+ "'" + s.getcNumber() + "'"
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
				+");";
		return query;  
		
	}
	
	public String isDiscountQ(int hotelId, int invoice) {
		String query = ""
        		+ "select case when r1.CheckOutDate > d.EndDate then d.EndDate else r1.CheckOutDate end - "
        		+ "case when r1.CheckInDate < d.StartDate then d.StartDate else r1.CheckInDate end as discount_days "
        		+ "from ROOM_RESERVATION r1 "
        		+ "join DISCOUNTED_ROOM d "
        		+ "on r1.HotelID = d.HotelID "
        		+ "and d.RoomNo = r1.RoomNo "
        		+ "and r1.CheckInDate < d.EndDate "
        		+ "and r1.CheckOutDate > d.StartDate "
        		+ "where r1.InvoiceNo ="+ invoice
        		+ "and r1.HotelID ="+ hotelId+";";
		
		return query;
	}
	
	public String resQ(int invoiceNum, int cid, String cNumber, String rDate) {
		String resQuery = "insert into RESERVATION values("+ invoiceNum
				+ "," +
				  cid
				+ "," +
			"'" + cNumber + "'"
				+ "," + 
			"'" + rDate + "'"
				+ ");";
		
		return resQuery;
	}
	
	public String roomResQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String roomResQuery = "insert into ROOM_RESERVATION values("+ invoiceNum
				+ "," +
				  hotel
				+ "," +
				r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ "," + 
			"'" + f.get(j).getOutDate() + "'"
				+ ");";
		
		return roomResQuery;
	}
								
	public String amBreakQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String amResQuery = "insert into BREAKFAST_RESERVATION values("+ "'"+ r.get(j).getAmerican()+"'"
				+ "," +
				hotel
				+ "," +
			 r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ "," + 
			r.get(j).getAmericanAmt()
				+ ");";
		
		return amResQuery;
	}
	
	public String frBreakQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String frResQuery = "insert into BREAKFAST_RESERVATION values("+ "'"+ r.get(j).getFrench()+"'"
				+ "," +
				hotel
				+ "," +
			 r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ "," + 
			r.get(j).getFrenchAmt()
				+ ");";
		return frResQuery;
	}
	
	public String itBreakQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String itResQuery = "insert into BREAKFAST_RESERVATION values("+ "'"+ r.get(j).getItalian()+"'"
				+ "," +
				hotel
				+ "," +
			 r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ "," + 
			r.get(j).getItalianAmt()
				+ ");";
		return itResQuery;
	}
	
	public String parkQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String parkResQuery = "insert into SERVICE_RESERVATION values("+ "'"+ r.get(j).getParking()+"'"
				+ "," +
				hotel
				+ "," +
			 r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ ");";
		return parkResQuery;
	}
	
	public String pickQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String pickResQuery = "insert into SERVICE_RESERVATION values("+ "'"+ r.get(j).getPickUp()+"'"
				+ "," +
				hotel
				+ "," +
			 r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ ");";
		return pickResQuery;
	}
	
	public String dropQ(int hotel,int invoiceNum, List<FindRoom> f, List<RoomSelection> r,int j) {
		String dropResQuery = "insert into SERVICE_RESERVATION values("+ "'"+ r.get(j).getDropOff()+"'"
				+ "," +
				hotel
				+ "," +
			 r.get(j).getRoomNo()
				+ "," + 
			"'" + f.get(j).getInDate() + "'"
				+ ");";
		return dropResQuery;
	}
	
	public String findRoomRevQ(int cid, int hotelId) {
		String q = "select * from ROOM_RESERVATION rr, RESERVATION r" +
				" where r.CID ="+ cid + " and rr.InvoiceNo=r.InvoiceNo and rr.HotelID = " + hotelId+ " ;";
		return q;
	}
	
	public String findBreakRevQ(int cid, int hotelId) {
		 String q = "select br.Btype, br.HotelID, br.RoomNo, br.CheckInDate "
	    			+ "from BREAKFAST_RESERVATION br, RESERVATION r, ROOM_RESERVATION rr "
	    			+ "where r.CID=" + cid
	    			+ " and rr.InvoiceNo= r.InvoiceNo"
	    			+ " and rr.HotelID = " + hotelId
	    			+ " and br.HotelID = " + hotelId
	    			+ " and br.CheckInDate = rr.CheckInDate"
	    			+ " and br.RoomNo = rr.RoomNo;";
		 
		 return q;
	}
	
	public String findServRevQ(int cid, int hotelId) {
		 String q = "select br.Stype, br.HotelId, br.RoomNo, br.CheckInDate "
	    			+ "from SERVICE_RESERVATION br, RESERVATION r, ROOM_RESERVATION rr "
	    			+ "where r.CID=" + cid
	    			+ " and rr.InvoiceNo= r.InvoiceNo"
	    			+ " and rr.HotelID = " + hotelId
	    			+ " and br.HotelID = " + hotelId
	    			+ " and br.CheckInDate = rr.CheckInDate"
	    			+ " and br.RoomNo = rr.RoomNo;";
		 
		 return q;
	}
	
	
	public String roomRevQ(int hotelId, int cid, Room_Review r, int revId) {
		 String query = "insert into ROOM_REVIEW values("+ revId
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
					+");";
		 return query;
	}
	
	public String breakRevQ(int hotelId, int cid, Breakfast_Review r, int revId) {
		String query = "insert into BREAKFAST_REVIEW values("+ revId
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
				+");";
		 return query;
	}
	
	public String servRevQ(int hotelId, int cid, Service_Review r, int revId) {
		String query = "insert into SERVICE_REVIEW values("+ revId
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
				+");";
		 return query;
	}
	
	public String highestRQ(String start, String end, int hotel) {
		 String q = "select MAX(k.Rating), r.Rtype, r.HotelId"  
	    			+" From ROOM_REVIEW k, ROOM r, ROOM_RESERVATION t" 
	    			+" Where t.HotelID = k.HotelID" 
	    			+" AND"
	    			+" t.RoomNo = k.RoomNo"
	    			+" AND"
	    			+" k.HotelID = " + hotel
	    			+" AND"
	    			+" k.RoomNo = r.RoomNo"
	    			+" and (k.CheckInDate >" + "'"+start+"'" 	
					+" and k.CheckInDate < " + "'"+end+"'" +")" 	
					+" GROUP BY r.HotelId;";
		 
		 return q;
	}
	
	public String highestBQ(StatDate d) {
		 String q ="Select MAX(b.Rating), br.Btype, b.HotelID"
		    		+" From BREAKFAST_REVIEW b, BREAKFAST_RESERVATION br, ROOM_RESERVATION t"
		    		+" Where t.HotelID = b.HotelID"
		    		+" AND"
		    		+" t.RoomNo = b.RoomNo"
		    		+" AND"
		    		+" b.HotelID = br.HotelID"
		    		+" AND"
		    		+" b.RoomNo = br.RoomNo"
		    		+" and (b.CheckInDate >=" + "'"+d.getStart()+"'"+")" 
		    		+" and (b.CheckInDate <=" + "'"+d.getEnd()+"');";
		 
		 return q;
	}
	
	
	public String highestSQ(StatDate d) {
		String q = "Select MAX(b.Rating), br.Stype, b.HotelID"
	    		+" From SERVICE_REVIEW b, SERVICE_RESERVATION br, ROOM_RESERVATION t"
	    		+" Where t.HotelID = b.HotelID"
	    		+" AND"
	    		+" t.RoomNo = b.RoomNo"
	    		+" AND"
	    		+" b.HotelID = br.HotelID"
	    		+" AND"
	    		+" b.RoomNo = br.RoomNo"
	    		+" and (b.CheckInDate >="+ "'"+d.getStart()+"')" 
	    		+" and (b.CheckInDate <="+ "'"+d.getEnd()+"');";
		 
		 return q;
	}
	
	public String getDiscountQ(int invoice, int hotel) {
		 String q = ""
		    		+ "select r1.RoomNo, case when r1.CheckOutDate > d.EndDate then d.EndDate else r1.CheckOutDate end - "
		    		+ "case when r1.CheckInDate < d.StartDate then d.StartDate else r1.CheckInDate end as discount_days "
		    		+ "from ROOM_RESERVATION r1 "
		    		+ "join DISCOUNTED_ROOM d "
		    		+ "on r1.HotelID = d.HotelID "
		    		+ "and d.RoomNo = r1.RoomNo "
		    		+ "and r1.CheckInDate < d.EndDate "
		    		+ "and r1.CheckOutDate > d.StartDate "
		    		+ "where r1.InvoiceNo ="+ invoice + " " 
		    		+ "and r1.HotelID ="+ hotel+";";
		 
		 return q;
	}
   
	/*#########################################################################################################*/
	
	public String totalSpentQ(int cid,int invoiceNum, int total, String rDate) {
		String totalQuery = "insert into TOTAL_SPENT values("+ cid
				+ "," +
				invoiceNum
				+ "," +
					total
				+ "," + 
			"'" + rDate + "'"
				+ ");";
		return totalQuery;
	}
	
	public String highestCusQ(StatDate d) {
		String q = "select CID, MoneySpent"
	    		+" from TOTAL_SPENT"
	    		+" where (RDate >"+ " "+ "'"+d.getStart()+"'"
	    		+" and RDate <"+ " "+ "'"+d.getEnd()+"')"
	    		+" order by MoneySpent desc"
	    		+" limit 5;";
		return q;
	}
	

}
