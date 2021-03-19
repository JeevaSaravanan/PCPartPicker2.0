package com.zoho.main;

import com.zoho.confi.*;

import com.zoho.object.*;

import java.sql.*;
import java.util.*;

public class DB {
	public User loginUser(String email,String psw) throws Exception {
		boolean status=false;
		User u=null;
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.user WHERE Email=? AND psw=?");
			stmt.setString(1, email);
			stmt.setString(2, psw);
			ResultSet rs=stmt.executeQuery();
			status=rs.next();
			if(status) {
				u=new User();
				u.setUid(rs.getString("userID"));
				u.setFirstName(rs.getString("FirstName"));
				u.setLastName(rs.getString("LastName"));
				u.setEmail(rs.getString("Email"));
				u.setPassword(rs.getString("psw"));
				u.setPhone(rs.getString("phone"));
				u.setAddress(rs.getString("Address"));
				u.setState(rs.getString("State"));
				u.setCity(rs.getString("City"));
				u.setZip(rs.getString("Zip"));
				u.setCountry(rs.getString("Country"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public int signupUser(User u) throws Exception {
		boolean status=false;
		int rs=0;
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("INSERT INTO pc_part_picker.user(userId,FirstName,LastName,Email,psw,phone,Address,City,State,Zip,Country,EmailVerification,PhoneVerification) VALUES (?,?,?,?,?,?,?,?,?,?,?,1,1)");
            stmt.setString(1,u.uid);
            stmt.setString(2,u.firstName);
            stmt.setString(3,u.lastName);
            stmt.setString(4,u.email);
            stmt.setString(5,u.psw);
            stmt.setString(6,u.phone);
            stmt.setString(7,u.address);
            stmt.setString(8,u.city);
            stmt.setString(9,u.state);
            stmt.setString(10,u.zip);
            stmt.setString(11,u.country);
			rs=stmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public int checkOut(String orderId,String uid,float total) throws Exception {
		int rs=0;
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("INSERT INTO pc_part_picker.order(OrderID,userID,Total) VALUES (?,?,?)");
            stmt.setString(1,orderId);
            stmt.setString(2,uid);
            stmt.setFloat(3,total);
			rs=stmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public void putinOrder(String id,String orderId,String pid,String productName,String productModel,float rate,int qty) {
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("INSERT INTO pc_part_picker.order_details(OrderDetailsID,orderID,PID,ProductName,ProductModel,RATE,QTY) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1,id);
            stmt.setString(2,orderId);
            stmt.setString(3,pid);
            stmt.setString(4,productName);
            stmt.setString(5, productModel);
            stmt.setFloat(6,rate);
            stmt.setInt(7,qty);
            int rs=stmt.executeUpdate();
            if(rs==1) {
            	System.out.println(id+" OrderNo is Confirmed");
            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void update(String PID,int qty,int id) throws Exception {
		try {
			String check="";
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement)connect.prepareStatement("SELECT pc_part_picker.schema_details.schemaName FROM pc_part_picker.schema_details WHERE ID=?");
			stmt.setInt(1,id);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				check=rs.getString("schemaName");
			}
			stmt=(PreparedStatement)connect.prepareStatement("SELECT QTY FROM pc_part_picker."+check+" WHERE PID=?");
			stmt.setString(1,PID);
			rs=stmt.executeQuery();
			int actualQty=0;
			if(rs.next()) {
				actualQty=rs.getInt("QTY");
			}
			stmt=(PreparedStatement)connect.prepareStatement("UPDATE pc_part_picker."+check+" SET QTY=? WHERE PID=?");
			stmt.setInt(1,actualQty-qty);
			stmt.setString(2,PID);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public User fetchAddress(String uid) throws Exception {
		boolean status=false;
		User u=null;
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.user WHERE userID=?");
			stmt.setString(1,uid);
			ResultSet rs=stmt.executeQuery();
			status=rs.next();
			if(status) {
				u=new User();
				u.setUid(rs.getString("userID"));
				u.setFirstName(rs.getString("FirstName"));
				u.setLastName(rs.getString("LastName"));
				u.setEmail(rs.getString("Email"));
				u.setPassword(rs.getString("psw"));
				u.setPhone(rs.getString("phone"));
				u.setAddress(rs.getString("Address"));
				u.setState(rs.getString("State"));
				u.setCity(rs.getString("City"));
				u.setZip(rs.getString("Zip"));
				u.setCountry(rs.getString("Country"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	public ArrayList<OrderDetails> getOrderSummary(String uid) throws Exception{
		ArrayList<OrderDetails> o=new ArrayList<OrderDetails>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.order_details LEFT JOIN pc_part_picker.order ON pc_part_picker.order.OrderID=pc_part_picker.order_details.OrderID WHERE pc_part_picker.order.uid=?");
			stmt.setString(1, uid);			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				OrderDetails d=new OrderDetails();
				d.setOrderDetailsID(rs.getString("OrderDetailsID"));
				d.setOrderID(rs.getString("OrderID"));
				d.setUID(rs.getString("userID"));
				d.setProduct(rs.getString("ProductName"));
				d.setProductModel(rs.getString("ProductModel"));
				d.setRate(rs.getFloat("RATE"));
				o.add(d);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return o;
	}
	public HashMap<String,Processor> fetchAllProcessor() throws Exception{
		HashMap<String,Processor> processorMap=new HashMap<String,Processor>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.cpu INNER JOIN pc_part_picker.gen_type ON pc_part_picker.gen_type.GID=pc_part_picker.cpu.GID INNER JOIN pc_part_picker.vendor_table ON pc_part_picker.vendor_table.ID=pc_part_picker.gen_type.CID;");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Processor p=new Processor();
				p.setPID(rs.getString("PID"));
				p.setModel(rs.getString("MODEL"));
				p.setDetails(rs.getString("DETAILS"));
				p.setRate(rs.getFloat("RATE"));
				p.setQty(rs.getInt("QTY"));
				p.setGid(rs.getInt("GID"));
				p.setGc(rs.getInt("GC"));
				p.setSchemaID(rs.getInt("schemaID"));
				p.setActualQuantity(rs.getInt("QTY"));
				p.setImage(rs.getString("IMAGE"));
				p.setBrand(rs.getString("VendorName"));
				processorMap.put(rs.getString("PID"),p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return processorMap;
	}
	public HashMap<String,Processor> fetchIntelProcessor() throws Exception{
		HashMap<String,Processor> processorMap=new HashMap<String,Processor>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.cpu LEFT JOIN pc_part_picker.gen_type ON pc_part_picker.gen_type.GID=pc_part_picker.cpu.GID LEFT JOIN pc_part_picker.vendor_table ON pc_part_picker.vendor_table.ID=pc_part_picker.gen_type.CID WHERE pc_part_picker.vendor_table.VendorName=\"Intel\";");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Processor p=new Processor();
				p.setPID(rs.getString("PID"));
				p.setModel(rs.getString("MODEL"));
				p.setDetails(rs.getString("DETAILS"));
				p.setRate(rs.getFloat("RATE"));
				p.setQty(rs.getInt("QTY"));
				p.setGid(rs.getInt("GID"));
				p.setGc(rs.getInt("GC"));
				p.setSchemaID(rs.getInt("schemaID"));
				p.setActualQuantity(rs.getInt("QTY"));
				p.setImage(rs.getString("IMAGE"));
				p.setBrand(rs.getString("VendorName"));
				processorMap.put(rs.getString("PID"),p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return processorMap;
	}
	public HashMap<String,Processor> fetchAMDProcessor() throws Exception{
		HashMap<String,Processor> processorMap=new HashMap<String,Processor>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.cpu LEFT JOIN pc_part_picker.gen_type ON pc_part_picker.gen_type.GID=pc_part_picker.cpu.GID LEFT JOIN pc_part_picker.vendor_table ON pc_part_picker.vendor_table.ID=pc_part_picker.gen_type.CID WHERE pc_part_picker.vendor_table.VendorName=\"AMD\";");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Processor p=new Processor();
				p.setPID(rs.getString("PID"));
				p.setModel(rs.getString("MODEL"));
				p.setDetails(rs.getString("DETAILS"));
				p.setRate(rs.getFloat("RATE"));
				p.setQty(rs.getInt("QTY"));
				p.setGid(rs.getInt("GID"));
				p.setGc(rs.getInt("GC"));
				p.setSchemaID(rs.getInt("schemaID"));
				p.setImage(rs.getString("IMAGE"));
				p.setBrand(rs.getString("VendorName"));
				processorMap.put(rs.getString("PID"),p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return processorMap;
	}
	public Processor fetchTheProcessor(String item) throws Exception{
		Processor p=new Processor();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table LEFT JOIN pc_part_picker.gen_type ON pc_part_picker.vendor_table.ID=pc_part_picker.gen_type.CID LEFT JOIN pc_part_picker.cpu ON pc_part_picker.cpu.PID=? WHERE pc_part_picker.gen_type.GID=pc_part_picker.cpu.GID;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				
				p.setPID(rs.getString("PID"));
				p.setModel(rs.getString("MODEL"));
				p.setDetails(rs.getString("DETAILS"));
				p.setRate(rs.getFloat("RATE"));
				p.setQty(rs.getInt("QTY"));
				p.setGid(rs.getInt("GID"));
				p.setGc(rs.getInt("GC"));
				p.setSchemaID(rs.getInt("schemaID"));
				p.setImage(rs.getString("IMAGE"));
				p.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	public HashMap<String,MotherBoard> fetchMotherBoard(int gid) throws Exception{
		HashMap<String,MotherBoard> motherBoardMap=new HashMap<String,MotherBoard>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table LEFT JOIN pc_part_picker.mother_board ON pc_part_picker.mother_board.Brand=pc_part_picker.vendor_table.ID LEFT JOIN pc_part_picker.motherboard_series ON pc_part_picker.motherboard_series.SeriesID=pc_part_picker.mother_board.SeriesID LEFT JOIN pc_part_picker.compatible_board ON pc_part_picker.motherboard_series.SeriesID=pc_part_picker.compatible_board.SeriesID WHERE pc_part_picker.compatible_board.GID=?;");
			stmt.setInt(1,gid);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				MotherBoard m=new MotherBoard();
				m.setPID(rs.getString("PID"));
				m.setModel(rs.getString("MODEL"));
				m.setRate(rs.getFloat("RATE"));
				m.setQty(rs.getInt("QTY"));
				m.setSeriesId(rs.getInt("SeriesID"));
				m.setSchemaID(rs.getInt("schemaID"));
				m.setImage(rs.getString("IMAGE"));
				m.setBrand(rs.getString("VendorName"));
				motherBoardMap.put(rs.getString("PID"),m);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return motherBoardMap;
	}
	public HashMap<String,MotherBoard> fetchAllMotherBoard() throws Exception{
		HashMap<String,MotherBoard> motherBoardMap=new HashMap<String,MotherBoard>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table LEFT JOIN pc_part_picker.mother_board ON pc_part_picker.mother_board.Brand=pc_part_picker.vendor_table.ID LEFT JOIN pc_part_picker.motherboard_series ON pc_part_picker.motherboard_series.SeriesID=pc_part_picker.mother_board.SeriesID LEFT JOIN pc_part_picker.compatible_board ON pc_part_picker.motherboard_series.SeriesID=pc_part_picker.compatible_board.SeriesID;");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				MotherBoard m=new MotherBoard();
				m.setPID(rs.getString("PID"));
				m.setModel(rs.getString("MODEL"));
				m.setRate(rs.getFloat("RATE"));
				m.setQty(rs.getInt("QTY"));
				m.setSeriesId(rs.getInt("SeriesID"));
				m.setSchemaID(rs.getInt("schemaID"));
				m.setImage(rs.getString("IMAGE"));
				m.setBrand(rs.getString("VendorName"));
				motherBoardMap.put(rs.getString("PID"),m);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return motherBoardMap;
	}
	public MotherBoard fetchTheMotherBoard(String item) throws Exception{
		MotherBoard m=new MotherBoard();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table LEFT JOIN pc_part_picker.mother_board ON pc_part_picker.mother_board.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.mother_board.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				
				m.setPID(rs.getString("PID"));
				m.setModel(rs.getString("MODEL"));
				m.setRate(rs.getFloat("RATE"));
				m.setQty(rs.getInt("QTY"));
				m.setSeriesId(rs.getInt("SeriesId"));
				m.setSchemaID(rs.getInt("schemaID"));
				m.setImage(rs.getString("IMAGE"));
				m.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	public HashMap<String,GraphicCard> fetchGraphicCard() throws Exception{
		HashMap<String,GraphicCard> graphicCardMap=new HashMap<String,GraphicCard>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.graphic_card ON pc_part_picker.graphic_card.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				GraphicCard g=new GraphicCard();
				g.setPID(rs.getString("PID"));
				g.setModel(rs.getString("MODEL"));
				g.setRate(rs.getFloat("RATE"));
				g.setQty(rs.getInt("QTY"));
				g.setgpuChip(rs.getString("GPU_CHIP"));
				g.setSchemaID(rs.getInt("schemaID"));
				g.setImage(rs.getString("IMAGE"));
				g.setBrand(rs.getString("VendorName"));
				graphicCardMap.put(rs.getString("PID"),g);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return graphicCardMap;
	}
	public GraphicCard fetchTheGraphicCard(String item) throws Exception{
		GraphicCard g=new GraphicCard();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.graphic_card ON pc_part_picker.graphic_card.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.graphic_card.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				
				g.setPID(rs.getString("PID"));
				g.setModel(rs.getString("MODEL"));
				g.setRate(rs.getFloat("RATE"));
				g.setQty(rs.getInt("QTY"));
				g.setgpuChip(rs.getString("GPU_CHIP"));
				g.setSchemaID(rs.getInt("schemaID"));
				g.setImage(rs.getString("IMAGE"));
				g.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return g;
	}
	public HashMap<String,Cabinets> fetchCabinets() throws Exception{
		HashMap<String,Cabinets> cabinetsMap=new HashMap<String,Cabinets>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.cabinets ON pc_part_picker.cabinets.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Cabinets c=new Cabinets();
				c.setPID(rs.getString("PID"));
				c.setModel(rs.getString("MODEL"));
				c.setRate(rs.getFloat("RATE"));
				c.setQty(rs.getInt("QTY"));
				c.setSchemaID(rs.getInt("schemaID"));
				c.setImage(rs.getString("IMAGE"));
				c.setBrand(rs.getString("VendorName"));
				cabinetsMap.put(rs.getString("PID"),c);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return cabinetsMap;
	}
	public Cabinets fetchTheCabinets(String item) throws Exception{
		Cabinets c=new Cabinets();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.cabinets ON pc_part_picker.cabinets.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.cabinets.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				
				c.setPID(rs.getString("PID"));
				c.setModel(rs.getString("MODEL"));
				c.setRate(rs.getFloat("RATE"));
				c.setQty(rs.getInt("QTY"));
				c.setSchemaID(rs.getInt("schemaID"));
				c.setImage(rs.getString("IMAGE"));
				c.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public HashMap<String,MemoryModule> fetchMemoryModule() throws Exception{
		HashMap<String,MemoryModule> memoryModuleMap=new HashMap<String,MemoryModule>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.memory_module ON pc_part_picker.memory_module.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				MemoryModule m=new MemoryModule();
				m.setPID(rs.getString("PID"));
				m.setModel(rs.getString("MODEL"));
				m.setRate(rs.getFloat("RATE"));
				m.setQty(rs.getInt("QTY"));
				m.setSchemaID(rs.getInt("schemaID"));
				m.setImage(rs.getString("IMAGE"));
				m.setBrand(rs.getString("VendorName"));
				memoryModuleMap.put(rs.getString("PID"),m);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return memoryModuleMap;
	}
	public MemoryModule fetchTheMemoryModule(String item) throws Exception{
		MemoryModule m=new MemoryModule();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.memory_module ON pc_part_picker.memory_module.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.memory_module.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				
				m.setPID(rs.getString("PID"));
				m.setModel(rs.getString("MODEL"));
				m.setRate(rs.getFloat("RATE"));
				m.setQty(rs.getInt("QTY"));
				m.setSchemaID(rs.getInt("schemaID"));
				m.setImage(rs.getString("IMAGE"));
				m.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return m;
	}
	
	public HashMap<String,PowerSupply> fetchPowerSupply() throws Exception{
		HashMap<String,PowerSupply> PowerSupplyMap=new HashMap<String,PowerSupply>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.power_supply ON pc_part_picker.power_supply.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				PowerSupply p=new PowerSupply();
				p.setPID(rs.getString("PID"));
				p.setModel(rs.getString("MODEL"));
				p.setDetails(rs.getString("DETAILS"));
				p.setRate(rs.getFloat("RATE"));
				p.setQty(rs.getInt("QTY"));
				p.setSchemaID(rs.getInt("schemaID"));
				p.setImage(rs.getString("IMAGE"));
				p.setBrand(rs.getString("VendorName"));
				PowerSupplyMap.put(rs.getString("PID"),p);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return PowerSupplyMap;
	}
	public PowerSupply fetchThePowerSupply(String item) throws Exception{
		PowerSupply p=new PowerSupply();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.power_supply ON pc_part_picker.power_supply.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.power_supply.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				p.setPID(rs.getString("PID"));
				p.setModel(rs.getString("MODEL"));
				p.setDetails(rs.getString("DETAILS"));
				p.setRate(rs.getFloat("RATE"));
				p.setQty(rs.getInt("QTY"));
				p.setSchemaID(rs.getInt("schemaID"));
				p.setImage(rs.getString("IMAGE"));
				p.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public HashMap<String,Storage> fetchStorage() throws Exception{
		HashMap<String,Storage> StorageMap=new HashMap<String,Storage>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.storage ON pc_part_picker.storage.BRAND=pc_part_picker.vendor_table.ID INNER JOIN pc_part_picker.storage_type ON pc_part_picker.storage.SID=pc_part_picker.storage_type.SID");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Storage store=new Storage();
				store.setPID(rs.getString("PID"));
				store.setModel(rs.getString("MODEL"));
				store.setCapacity(rs.getString("CAPACITY"));
				store.setRate(rs.getFloat("RATE"));
				store.setQty(rs.getInt("QTY"));
				store.setSchemaID(rs.getInt("schemaID"));
				store.setImage(rs.getString("IMAGE"));
				store.setBrand(rs.getString("VendorName"));
				store.setSID(rs.getInt("SID"));
				store.setType(rs.getString("TYPE"));
				StorageMap.put(rs.getString("PID"),store);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return StorageMap;
	}
	public HashMap<String,Storage> fetchHDDStorage() throws Exception{
		HashMap<String,Storage> StorageMap=new HashMap<String,Storage>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table LEFT JOIN pc_part_picker.storage ON pc_part_picker.storage.BRAND=pc_part_picker.vendor_table.ID LEFT JOIN pc_part_picker.storage_type ON pc_part_picker.storage.SID=pc_part_picker.storage_type.SID WHERE pc_part_picker.storage_type.TYPE IN(\"HDD\");");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Storage store=new Storage();
				store.setPID(rs.getString("PID"));
				store.setModel(rs.getString("MODEL"));
				store.setCapacity(rs.getString("CAPACITY"));
				store.setRate(rs.getFloat("RATE"));
				store.setQty(rs.getInt("QTY"));
				store.setSchemaID(rs.getInt("schemaID"));
				store.setImage(rs.getString("IMAGE"));
				store.setBrand(rs.getString("VendorName"));
				store.setSID(rs.getInt("SID"));
				store.setType(rs.getString("TYPE"));
				StorageMap.put(rs.getString("PID"),store);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return StorageMap;
	}
	public HashMap<String,Storage> fetchSSDStorage() throws Exception{
		HashMap<String,Storage> StorageMap=new HashMap<String,Storage>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table LEFT JOIN pc_part_picker.storage ON pc_part_picker.storage.BRAND=pc_part_picker.vendor_table.ID LEFT JOIN pc_part_picker.storage_type ON pc_part_picker.storage.SID=pc_part_picker.storage_type.SID WHERE pc_part_picker.storage_type.TYPE NOT IN(\"HDD\");");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Storage store=new Storage();
				store.setPID(rs.getString("PID"));
				store.setModel(rs.getString("MODEL"));
				store.setCapacity(rs.getString("CAPACITY"));
				store.setRate(rs.getFloat("RATE"));
				store.setQty(rs.getInt("QTY"));
				store.setType(rs.getString("TYPE"));
				store.setSchemaID(rs.getInt("schemaID"));
				store.setImage(rs.getString("IMAGE"));
				store.setBrand(rs.getString("VendorName"));
				store.setSID(rs.getInt("SID"));
				StorageMap.put(rs.getString("PID"),store);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return StorageMap;
	}
	public Storage fetchTheStorage(String item) throws Exception{
		Storage store=new Storage();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.storage ON pc_part_picker.storage.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.storage.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				store.setPID(rs.getString("PID"));
				store.setModel(rs.getString("MODEL"));
				store.setCapacity(rs.getString("CAPACITY"));
				store.setRate(rs.getFloat("RATE"));
				store.setQty(rs.getInt("QTY"));
				store.setSchemaID(rs.getInt("schemaID"));
				store.setImage(rs.getString("IMAGE"));
				store.setBrand(rs.getString("VendorName"));
				store.setType(rs.getString("TYPE"));
				store.setSID(rs.getInt("SID"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return store;
	}
	
	public HashMap<String,Ups> fetchUps() throws Exception{
		HashMap<String,Ups> upsMap=new HashMap<String,Ups>();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.ups ON pc_part_picker.ups.BRAND=pc_part_picker.vendor_table.ID");
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Ups u=new Ups();
				u.setPID(rs.getString("PID"));
				u.setModel(rs.getString("MODEL"));
				u.setRate(rs.getFloat("RATE"));
				u.setQty(rs.getInt("QTY"));
				u.setSchemaID(rs.getInt("schemaID"));
				u.setImage(rs.getString("IMAGE"));
				u.setBrand(rs.getString("VendorName"));
				upsMap.put(rs.getString("PID"),u);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return upsMap;
	}
	public Ups fetchTheUps(String item) throws Exception{
		Ups u=new Ups();
		try {
			SetConnection s=new SetConnection();
			Connection connect=s.connectDb();
			PreparedStatement stmt=(PreparedStatement) connect.prepareStatement("SELECT * FROM pc_part_picker.vendor_table INNER JOIN pc_part_picker.ups ON pc_part_picker.ups.BRAND=pc_part_picker.vendor_table.ID WHERE pc_part_picker.ups.PID=?;");
			stmt.setString(1, item);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				u.setPID(rs.getString("PID"));
				u.setModel(rs.getString("MODEL"));
				u.setRate(rs.getFloat("RATE"));
				u.setQty(rs.getInt("QTY"));
				u.setSchemaID(rs.getInt("schemaID"));
				u.setImage(rs.getString("IMAGE"));
				u.setBrand(rs.getString("VendorName"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	
}
