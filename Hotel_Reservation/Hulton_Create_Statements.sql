create database test124;
use test124;

/*################################################################################################################################*/

create table HOTEL(HotelID int not null,Street varchar(100) not null,Country varchar(30) not null,State varchar(40),ZIP varchar(10),primary key(HotelID));

insert into HOTEL values (123,'Brotherhood road','USA','New Jersey','08837');
insert into HOTEL values (124,'Cool Kid street','Argentina','New Kids','08998');
insert into HOTEL values (125,'Coolio Cat', 'Barbuda', 'Back Again', '08101');

/*################################################################################################################################*/

create table ROOM(HotelID int not null,RoomNo int not null,Rtype varchar(30) not null,Price int not null,Description varchar(300),Floor int not null,Capacity int not null,primary key(HotelID,RoomNo), foreign key(HotelID) references HOTEL(HotelID) on delete cascade on update cascade); 

INSERT INTO ROOM VALUES (123,1,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (123,2,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (123,3,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (123,4,'Double',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (123,5,'Double',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (123,6,'Double',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (123,7,'Suite',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (123,8,'Suite',100, 'Room with two queen beds',3,4);
INSERT INTO ROOM VALUES (123,9,'Deluxe',100, 'Room with two queen beds',3,4);
INSERT INTO ROOM VALUES (123,10,'Deluxe',100, 'Room with two queen beds',3,4);

INSERT INTO ROOM VALUES (124,1,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (124,2,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (124,3,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (124,4,'Double',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (124,5,'Double',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (124,6,'Double',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (124,7,'Suite',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (124,8,'Suite',100, 'Room with two queen beds',3,4);
INSERT INTO ROOM VALUES (124,9,'Deluxe',100, 'Room with two queen beds',3,4);
INSERT INTO ROOM VALUES (124,10,'Deluxe',100, 'Room with two queen beds',3,4);	

INSERT INTO ROOM VALUES (125,1,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (125,2,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (125,3,'Standard',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (125,4,'Double',100, 'Room with two queen beds',1,4);
INSERT INTO ROOM VALUES (125,5,'Double',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (125,6,'Double',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (125,7,'Suite',100, 'Room with two queen beds',2,4);
INSERT INTO ROOM VALUES (125,8,'Suite',100, 'Room with two queen beds',3,4);
INSERT INTO ROOM VALUES (125,9,'Deluxe',100, 'Room with two queen beds',3,4);
INSERT INTO ROOM VALUES (125,10,'Deluxe',100, 'Room with two queen beds',3,4);

/*##############################################################################################################################*/

create table DISCOUNTED_ROOM(HotelID int not null,RoomNo int not null,Discount int not null,StartDate date not null,EndDate date not null,primary key(HotelID,RoomNo),foreign key(HotelID,RoomNo) references ROOM(HotelID,RoomNo)on delete cascade on update cascade); 



insert into DISCOUNTED_ROOM values(123,1,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,2,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,3,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,4,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,5,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,6,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,7,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,8,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,9,20,'2017-12-21','2018-03-21');
insert into DISCOUNTED_ROOM values(123,10,20,'2017-12-21','2018-03-21');

insert into DISCOUNTED_ROOM values(124,1,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,2,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,3,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,4,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,5,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,6,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,7,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,8,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,9,20,'2018-03-21','2018-06-21');
insert into DISCOUNTED_ROOM values(124,10,20,'2018-03-21','2018-06-21');

insert into DISCOUNTED_ROOM values(125,1,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,2,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,3,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,4,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,5,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,6,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,7,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,8,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,9,20,'2018-06-21','2018-09-21');
insert into DISCOUNTED_ROOM values(125,10,20,'2018-06-21','2018-09-21');



/*############################################################################################################################*/

create table SERVICE(HotelID int not null,Stype varchar(40) not null,Sprice int not null,primary key(HotelID,Stype), foreign key(HotelID) references HOTEL(HotelID)on delete cascade on update cascade); 

insert into SERVICE values (123,'Parking',100);
insert into SERVICE values (123,'Pick Up',50);
insert into SERVICE values (123,'Drop Off',50);

insert into SERVICE values (124,'Parking',100);
insert into SERVICE values (124,'Pick Up',50);
insert into SERVICE values (124,'Drop Off',50);

insert into SERVICE values (125,'Parking',100);
insert into SERVICE values (125,'Pick Up',50);
insert into SERVICE values (125,'Drop Off',50);

/*###########################################################################################################################*/

create table BREAKFAST(HotelID int not null,Btype varchar(40) not null,Bprice int not null,Description varchar(300),primary key(HotelID,Btype),foreign key(HotelID) references HOTEL(HotelID)on delete cascade on update cascade); 


insert into BREAKFAST values (123,'American',20,'Ham Egg and Cheese');
insert into BREAKFAST values (123,'Italian',20,'Spaggethi with Sauce');
insert into BREAKFAST values (123,'French', 20,'French Toast');

insert into BREAKFAST values (124,'American',20,'Ham Egg and Cheese');
insert into BREAKFAST values (124,'Italian',20,'Spaggethi with Sauce');
insert into BREAKFAST values (124,'French', 20,'French Toast');

insert into BREAKFAST values (125,'American',20,'Ham Egg and Cheese');
insert into BREAKFAST values (125,'Italian',20,'Spaggethi with Sauce');
insert into BREAKFAST values (125,'French', 20,'French Toast');

/*##########################################################################################################################*/

create table CUSTOMER(CID int not null,Name varchar(30) not null,Address varchar(100),Phone_No varchar(15) not null,Email varchar(30) not null,primary key(CID)); 

/*###########################################################################################################################*/

create table CREDIT_CARD(Cnumber varchar(20) not null,Ctype varchar(20) not null,Baddress varchar(100) not null,Code varchar(5) not null,ExpDate date not null,Name varchar(30) not null,primary key(Cnumber));

/*###########################################################################################################################*/

create table RESERVATION(InvoiceNo int not null,CID int not null,Cnumber varchar(20) not null,Rdate date not null,primary key(InvoiceNo),foreign key(CID) references CUSTOMER(CID) on delete cascade on update cascade,foreign key(Cnumber) references CREDIT_CARD(Cnumber) on delete cascade on update cascade);

/*###########################################################################################################################*/

create table ROOM_RESERVATION(InvoiceNo int not null,HotelID int not null,RoomNo int not null,CheckInDate date not null,CheckOutDate date not null,primary key(HotelID,RoomNo,CheckInDate),foreign key(HotelID,RoomNo) references ROOM(HotelID,RoomNo) on delete cascade on update cascade, foreign key(InvoiceNo) references RESERVATION(InvoiceNo) on delete cascade on update cascade);

/*###########################################################################################################################*/

create table BREAKFAST_RESERVATION(Btype varchar(40) not null, HotelID int not null,RoomNo int not null,CheckInDate date not null,NoOfOrders int not null,primary key (Btype,HotelID,RoomNo,CheckInDate),foreign key(HotelID,Btype) references BREAKFAST(HotelID,Btype) on delete cascade on update cascade,foreign key(HotelID,RoomNo,CheckInDate) references ROOM_RESERVATION(HotelID,RoomNo,CheckInDate)on delete cascade on update cascade);

/*###########################################################################################################################*/

create table SERVICE_RESERVATION(Stype varchar(40) not null, HotelID int not null,RoomNo int not null,CheckInDate date not null,primary key (Stype,HotelID,RoomNo,CheckInDate),foreign key(HotelID,Stype) references SERVICE(HotelID,Stype) on delete cascade on update cascade,foreign key(HotelID,RoomNo,CheckInDate)references ROOM_RESERVATION(HotelID,RoomNo,CheckInDate)on delete cascade on update cascade);

/*###########################################################################################################################*/

create table ROOM_REVIEW(RID int not null,HotelID int not null,RoomNo int not null,CheckInDate date not null,CID int not null,Rating int not null check(Rating>=1 AND Rating <=10),Text varchar(500),primary key(RID),foreign key(CID) references CUSTOMER(CID)on delete cascade on update cascade,foreign key(HotelID,RoomNo) references ROOM(HotelID,RoomNo)on delete cascade on update cascade,foreign key (HotelID,RoomNo,CheckInDate) references ROOM_RESERVATION(HotelID,RoomNo,CheckInDate)on delete cascade on update cascade);

/*###########################################################################################################################*/

create table SERVICE_REVIEW(RID int not null,HotelID int not null,RoomNo int not null,CheckInDate date not null,Stype varchar(40) not null,CID int not null,Rating int not null check(Rating >= 1 AND Rating <=10),Text varchar(500),primary key(RID),foreign key(CID) references CUSTOMER(CID)on delete cascade on update cascade,foreign key(HotelID,Stype) references SERVICE(HotelID,Stype)on delete cascade on update cascade,foreign key(HotelID,RoomNo,CheckInDate) references SERVICE_RESERVATION(HotelID,RoomNo,CheckInDate)on delete cascade on update cascade);

/*###########################################################################################################################*/

create table BREAKFAST_REVIEW(RID int not null,HotelID int not null,RoomNo int not null,CheckInDate date not null,Btype varchar(40) not null,CID int not null,Rating int not null check(Rating >= 1 AND Rating <=10),Text varchar(500),primary key(RID),foreign key(CID) references CUSTOMER(CID)on delete cascade on update cascade,foreign key(HotelID,Btype) references BREAKFAST(HotelID,Btype)on delete cascade on update cascade,foreign key (HotelID,RoomNo,CheckInDate) references BREAKFAST_RESERVATION(HotelID,RoomNo,CheckInDate)on delete cascade on update cascade);

/*###########################################################################################################################*/

create table TOTAL_SPENT(CID int not null, InvoiceNo int not null, MoneySpent int not null, RDate date not null, primary key(CID,InvoiceNo), foreign key(CID) references CUSTOMER(CID) on delete cascade on update cascade, foreign key(InvoiceNo) references RESERVATION(InvoiceNo) on delete cascade on update cascade);

/*###########################################################################################################################*/



/*###########################################################################################################################*/


/*###########################################################################################################################*/




