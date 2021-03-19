package com.zoho.controller;

import com.zoho.main.*;
import com.zoho.object.*;
import java.util.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.JOptionPane;



/**
 * Servlet implementation class Controller
 */

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ShoppingCart shop=new ShoppingCart();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String page = request.getParameter("page");
		System.out.println("page "+page);
		if(page == null || page.equals("Welcome")) {
			request.getRequestDispatcher("Welcome.jsp").forward(request, response);
		}
		else {
			System.out.println("page "+page);
			doPost(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String page = request.getParameter("page");
		String product = request.getParameter("product");
		String item = request.getParameter("item");
		System.out.println("page "+page);
		
		if(page.equals("login")) {
			request.setAttribute("Login",0);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		if(page.equals("sign-up")) {
			request.setAttribute("Signup",0);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
		if(page.equals("login-form")) {
			DB db=new DB();
			String email=request.getParameter("email");
			String psw=request.getParameter("password");
			User u;
			HttpSession session=request.getSession();
			try {
				u=db.loginUser(email, psw);
				session.setAttribute("uid",u.uid);
				System.out.println("LoginSucess"+session);
			
				response.sendRedirect("Home");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(page.equals("signup-form")) {
			DB db=new DB();
			String firstName=request.getParameter("firstName");
			String lastName=request.getParameter("lastName");
			String email=request.getParameter("email");
			String psw=request.getParameter("password");
			String phone=request.getParameter("phone");
			String address=request.getParameter("address");
			String state=request.getParameter("state");
			String city=request.getParameter("city");
			String zip=request.getParameter("zip");
			String country=request.getParameter("country");
			
			User u=new User();
			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setEmail(email);
			u.setPassword(psw);
			u.setPhone(phone);
			u.setCountry(country);
			u.setCity(city);
			u.setAddress(address);
			u.setState(state);
			u.setZip(zip);
			u.generateUid();
			try {
				if(db.signupUser(u)==1) {
					request.setAttribute("Login",1);
					System.out.println("Signup Success");
					
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				else {
					request.setAttribute("Signup",1);
					System.out.println("Signup Failed");
					
					request.getRequestDispatcher("signup.jsp").forward(request, response);
				}

			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(page.equals("logout")) {
			HttpSession session = request.getSession();
			session.invalidate();
			
			 session = request.getSession();
			 shop=new ShoppingCart();
			 
			 System.out.println(shop);
			 response.sendRedirect("Home");
		}
		if(page.equals("addToCart")) {
			 HttpSession session = request.getSession();
			 if(session.getAttribute("uid")==null) {
				 request.getRequestDispatcher("login.jsp").forward(request, response);
			 }
			 else {
				 DB db=new DB();
					if(product.equals("cpu")) {
						shop.cpu=new Processor();
						try {
							Processor p=db.fetchTheProcessor(item);
							if(p.checkAvailability()) {
								shop.cpu.PID=p.getPID();
								shop.cpu.Model=p.getModel();
								shop.cpu.Details=p.getDetails();
								shop.cpu.brand=p.getBrand();
								shop.cpu.rate=p.getRate();
								shop.cpu.qty=p.getQty();
								shop.cpu.gc=p.getGc();
								shop.cpu.gid=p.getGid();
								shop.cpu.img=p.getImage();
								shop.cpu.actualQuantity=p.getActualQuantity();
								shop.cpu.schemaID=p.getSchemaID();
								
								System.out.println(shop.cpu.Model);
								session.setAttribute("getAlert", "Yes");
								session.setAttribute("cpuImage",p.getImage());
								session.setAttribute("cpuName",p.getBrand()+" "+p.getModel());
								session.setAttribute("cpuRate",p.getRate());
								request.setAttribute("product","CPU");
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");
								 request.getRequestDispatcher("AllProcessor.jsp").forward(request, response);

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} 
					if(product.equals("mother-board")) {
						shop.board=new MotherBoard();
						if(shop.cpu!=null) {
							try {
								MotherBoard m=db.fetchTheMotherBoard(item);
								if(m.checkAvailability()) {
									shop.board.PID=m.getPID();
									shop.board.Model=m.getModel();
									shop.board.brand=m.getBrand();
									shop.board.rate=m.getRate();
									shop.board.qty=m.getQty();
									shop.board.seriesid=m.getSeriesId();
									shop.board.img=m.getImage();
									shop.board.actualQuantity=m.getActualQuantity();
									shop.board.schemaID=m.getSchemaID();
									System.out.println(shop.board.Model);
									session.setAttribute("getAlert", "Yes");
									session.setAttribute("MotherBoardImage",m.getImage());
									session.setAttribute("MotherBoardName",m.getBrand()+" "+m.getModel());
									session.setAttribute("MotherBoardRate",m.getRate());
									request.setAttribute("product","Mother Board");
									int total=shop.calculateTotal();
									session.setAttribute("total",total);
									 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

								}
								else {
									request.setAttribute("error","Product is Not Available");

									 request.getRequestDispatcher("MotherBoard.jsp").forward(request, response);

								}
							}
							catch(Exception e) {
								e.printStackTrace();
							}
							
						}
						else {
							request.setAttribute("error","Choose CPU first");

							 request.getRequestDispatcher("MotherBoard.jsp").forward(request, response);
						}
						
					}
					
					if(product.equals("graphic-card")) {
						shop.gpu=new GraphicCard();
						try {
							GraphicCard g=db.fetchTheGraphicCard(item);
							if(g.checkAvailability()) {
								shop.gpu.PID=g.getPID();
								shop.gpu.Model=g.getModel();
								shop.gpu.brand=g.getBrand();
								shop.gpu.rate=g.getRate();
								shop.gpu.qty=g.getQty();
								shop.gpu.gpuChip=g.getgpuChip();
								shop.gpu.img=g.getImage();
								shop.gpu.actualQuantity=g.getActualQuantity();
								shop.gpu.schemaID=g.getSchemaID();
								System.out.println(shop.gpu.Model);
								session.setAttribute("getAlert", "Yes");
								request.setAttribute("product","Graphic Card");
								session.setAttribute("GraphicImage",g.getImage());
								session.setAttribute("GraphicCardName",g.getBrand()+" "+g.getModel());
								session.setAttribute("GraphicCardRate",g.getRate());
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");

								 request.getRequestDispatcher("GraphicCard.jsp").forward(request, response);

							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					if(product.equals("cabinets")) {
						shop.cabinets=new Cabinets();
						try {
							Cabinets c=db.fetchTheCabinets(item);
							if(c.checkAvailability()) {
								shop.cabinets.PID=c.getPID();
								shop.cabinets.Model=c.getModel();
								shop.cabinets.brand=c.getBrand();
								shop.cabinets.rate=c.getRate();
								shop.cabinets.qty=c.getQty();
								shop.cabinets.img=c.getImage();
								shop.cabinets.actualQuantity=c.getActualQuantity();
								shop.cabinets.schemaID=c.getSchemaID();
								System.out.println(shop.cabinets.Model);
								session.setAttribute("getAlert", "Yes");
								request.setAttribute("product","Graphic Card");
								session.setAttribute("CabinetsImage",c.getImage());
								session.setAttribute("CabinetsName",c.getBrand()+" "+c.getModel());
								session.setAttribute("CabinetsRate",c.getRate());
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");

								 request.getRequestDispatcher("Cabinets.jsp").forward(request, response);

							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					
					if(product.equals("memory-module")) {
						shop.ram=new MemoryModule();
						try {
							MemoryModule m=db.fetchTheMemoryModule(item);
							if(m.checkAvailability()) {
								shop.ram.PID=m.getPID();
								shop.ram.Model=m.getModel();
								shop.ram.brand=m.getBrand();
								shop.ram.rate=m.getRate();
								shop.ram.qty=m.getQty();
								shop.ram.img=m.getImage();
								shop.ram.actualQuantity=m.getActualQuantity();
								shop.ram.schemaID=m.getSchemaID();
								System.out.println(shop.ram.Model);
								session.setAttribute("getAlert", "Yes");
								request.setAttribute("product","Memory Module");
								session.setAttribute("ramImage",m.getImage());
								session.setAttribute("ramName",m.getBrand()+" "+m.getModel());
								session.setAttribute("ramRate",m.getRate());
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");

								 request.getRequestDispatcher("MemoryModule.jsp").forward(request, response);

							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					
					if(product.equals("power-supply")) {
						shop.power=new PowerSupply();
						try {
							PowerSupply p=db.fetchThePowerSupply(item);
							if(p.checkAvailability()) {
								shop.power.PID=p.getPID();
								shop.power.Model=p.getModel();
								shop.power.brand=p.getBrand();
								shop.power.rate=p.getRate();
								shop.power.qty=p.getQty();
								shop.power.img=p.getImage();
								shop.power.Details=p.getDetails();
								shop.power.actualQuantity=p.getActualQuantity();
								shop.power.schemaID=p.getSchemaID();
								System.out.println(shop.power.Model);
								session.setAttribute("getAlert", "Yes");
								request.setAttribute("product","Power Supply");
								session.setAttribute("powerImage",p.getImage());
								session.setAttribute("powerName",p.getBrand()+" "+p.getModel());
								session.setAttribute("powerRate",p.getRate());
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");

								 request.getRequestDispatcher("PowerSupply.jsp").forward(request, response);

							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					
					if(product.equals("storage")) {
						shop.storage=new Storage();
						try {
							Storage s=db.fetchTheStorage(item);
							if(s.checkAvailability()) {
								shop.storage.PID=s.getPID();
								shop.storage.Model=s.getModel();
								shop.storage.brand=s.getBrand();
								shop.storage.rate=s.getRate();
								shop.storage.qty=s.getQty();
								shop.storage.img=s.getImage();
								shop.storage.Capacity=s.getCapacity();
								shop.storage.actualQuantity=s.getActualQuantity();
								shop.storage.schemaID=s.getSchemaID();
								shop.storage.SID=s.getSID();
								System.out.println(shop.storage.Model);
								session.setAttribute("getAlert", "Yes");
								request.setAttribute("product","Storage");
								session.setAttribute("storageImage",s.getImage());
								session.setAttribute("storageName",s.getBrand()+" "+s.getModel()+" "+s.getType());
								session.setAttribute("storageRate",s.getRate());
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");

								 request.getRequestDispatcher("Storage.jsp").forward(request, response);

							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					
					if(product.equals("ups")) {
						shop.ups=new Ups();
						try {
							Ups u=db.fetchTheUps(item);
							if(u.checkAvailability()) {
								shop.ups.PID=u.getPID();
								shop.ups.Model=u.getModel();
								shop.ups.brand=u.getBrand();
								shop.ups.rate=u.getRate();
								shop.ups.qty=u.getQty();
								shop.ups.img=u.getImage();
								shop.ups.actualQuantity=u.getActualQuantity();
								shop.ups.schemaID=u.getSchemaID();

								System.out.println(shop.ups.Model);
								session.setAttribute("getAlert", "Yes");
								request.setAttribute("product","UPS");
								session.setAttribute("upsImage",u.getImage());
								session.setAttribute("upsName",u.getBrand()+" "+u.getModel());
								session.setAttribute("upsRate",u.getRate());
								int total=shop.calculateTotal();
								session.setAttribute("total",total);
								 request.getRequestDispatcher("Welcome.jsp").forward(request, response);

							}
							else {
								request.setAttribute("error","Product is Not Available");

								 request.getRequestDispatcher("Ups.jsp").forward(request, response);

							}
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					
			 }
			
		}
		if(page.equals("place-order")) {
			HttpSession session=request.getSession();
			Order o=new Order(session.getAttribute("uid").toString(),shop.total);
			o.generateOrderID();
			DB db=new DB();
			User u=null;
			int qty=1;
			try {
				if(db.checkOut(o.orderID,o.userID,o.total)==1) {
					db.putinOrder(shop.cpu.PID+o.orderID,o.orderID,shop.cpu.PID,"CPU",shop.cpu.Model,shop.cpu.rate,qty);
					db.update(shop.cpu.PID,qty,shop.cpu.schemaID);
					
					db.putinOrder(shop.board.PID+o.orderID,o.orderID,shop.board.PID,"Mother Board",shop.board.Model,shop.board.rate,qty);
					db.update(shop.board.PID,qty,shop.board.schemaID);
					
					db.putinOrder(shop.gpu.PID+o.orderID,o.orderID,shop.gpu.PID,"Graphic Card",shop.gpu.Model,shop.gpu.rate,qty);
					db.update(shop.gpu.PID,qty,shop.gpu.schemaID);
					
					db.putinOrder(shop.power.PID+o.orderID,o.orderID,shop.power.PID,"Power Supply",shop.power.Model,shop.power.rate,qty);
					db.update(shop.power.PID,qty,shop.power.schemaID);
					
					db.putinOrder(shop.cabinets.PID+o.orderID,o.orderID,shop.cabinets.PID,"Cabinets",shop.cabinets.Model,shop.cabinets.rate,qty);
					db.update(shop.cabinets.PID,qty,shop.cabinets.schemaID);
					
					db.putinOrder(shop.ups.PID+o.orderID,o.orderID,shop.ups.PID,"UPS",shop.ups.Model,shop.ups.rate,qty);
					db.update(shop.ups.PID,qty,shop.ups.schemaID);
					
					db.putinOrder(shop.ram.PID+o.orderID,o.orderID,shop.ram.PID,"Memory Module",shop.ram.Model,shop.ram.rate,qty);
					db.update(shop.ram.PID,qty,shop.ram.schemaID);
					
					db.putinOrder(shop.storage.PID+o.orderID,o.orderID,shop.storage.PID,"Storage",shop.storage.Model,shop.storage.rate,qty);
					db.update(shop.storage.PID,qty,shop.storage.schemaID);
					u=db.fetchAddress(session.getAttribute("uid").toString());
					
					System.out.println("The order is placed Successfully");
					
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("Order", 1);
			request.setAttribute("firstName",u.getFirstName());
			request.setAttribute("lastName",u.getLastName());
			request.setAttribute("Email",u.getEmail());
			request.setAttribute("Phone",u.getPhone());
			request.setAttribute("Address",u.getAddress());
			request.setAttribute("City",u.getCity());
			request.setAttribute("State",u.getState());
			request.setAttribute("Zip",u.getZip());
			request.setAttribute("Country",u.getCountry());
			request.getRequestDispatcher("ViewOrder.jsp").forward(request, response);
			shop=null;
			shop=new ShoppingCart();
		}
		if(page.equals("selectProcessor")) {
			System.out.println("SelectProcessor");
			
				String value[]=request.getParameterValues("chk_box");
				System.out.println(value);
				DB db = new DB();
				HashMap<String,Processor> processorMap=new HashMap<String,Processor>();
				if(value.length==2) {
					 try {
						 System.out.println("Working Controller");
						processorMap=db.fetchAllProcessor();
						
						for (Map.Entry<String, Processor> entry : processorMap.entrySet()) {
						    String key = entry.getKey();
						    Processor valuea = entry.getValue();
						    System.out.println(key+" "+valuea.Model);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					 HttpSession session = request.getSession();
					 session.setAttribute("AllProcessor",processorMap);
					
					request.getRequestDispatcher("AllProcessor.jsp").forward(request, response);
				}
				else {
					if(value[0].equals("Intel")) {
						

						 try {
							 System.out.println("Working Controller");
							processorMap=db.fetchIntelProcessor();
							
							for (Map.Entry<String, Processor> entry : processorMap.entrySet()) {
							    String key = entry.getKey();
							    Processor valuea = entry.getValue();
							    System.out.println(key+" "+valuea.Model);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						 HttpSession session = request.getSession();
						 session.setAttribute("IntelProcessor",processorMap);
						 
						request.getRequestDispatcher("intelProcessor.jsp").forward(request, response);
					}
					else {
						try {
							 System.out.println("Working Controller");
							processorMap=db.fetchAMDProcessor();
							
							for (Map.Entry<String, Processor> entry : processorMap.entrySet()) {
							    String key = entry.getKey();
							    Processor valuea = entry.getValue();
							    System.out.println(key+" "+valuea.Model);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						 HttpSession session = request.getSession();
						 session.setAttribute("AMDProcessor",processorMap);
						 
						request.getRequestDispatcher("amdProcessor.jsp").forward(request, response);
					}
				}
			}
			
		
		if(page.equals("AllProcessor")) {
			DB db = new DB();
			HashMap<String,Processor> processorMap=new HashMap<String,Processor>();
			 try {
				 System.out.println("Working Controller");
				processorMap=db.fetchAllProcessor();
				
				for (Map.Entry<String, Processor> entry : processorMap.entrySet()) {
				    String key = entry.getKey();
				    Processor value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("AllProcessor",processorMap);
			 
			request.getRequestDispatcher("AllProcessor.jsp").forward(request, response);
		}
		
		if(page.equals("intelProcessor")) {
			DB db = new DB();
			HashMap<String,Processor> processorMap=new HashMap<String,Processor>();

			 try {
				 System.out.println("Working Controller");
				processorMap=db.fetchIntelProcessor();
				
				for (Map.Entry<String, Processor> entry : processorMap.entrySet()) {
				    String key = entry.getKey();
				    Processor value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("IntelProcessor",processorMap);
			 
			request.getRequestDispatcher("intelProcessor.jsp").forward(request, response);
		}
		if(page.equals("amdProcessor")) {
			DB db = new DB();
			HashMap<String,Processor> processorMap=new HashMap<String,Processor>();

			 try {
				 System.out.println("Working Controller");
				processorMap=db.fetchAMDProcessor();
				
				for (Map.Entry<String, Processor> entry : processorMap.entrySet()) {
				    String key = entry.getKey();
				    Processor value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("AMDProcessor",processorMap);
			 
			request.getRequestDispatcher("amdProcessor.jsp").forward(request, response);
		}
		
		if(page.equals("MotherBoard")) {
			DB db = new DB();
			HashMap<String,MotherBoard> motherBoardMap=new HashMap<String,MotherBoard>();
			if(shop.cpu!=null) {
				 try {
					 System.out.println("Working Controller");
					 motherBoardMap=db.fetchMotherBoard(shop.cpu.gid);
					
					for (Map.Entry<String, MotherBoard> entry : motherBoardMap.entrySet()) {
					    String key = entry.getKey();
					    MotherBoard value = entry.getValue();
					    System.out.println(key+" "+value.Model);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			else {
				try {
					 System.out.println("Working Controller");
					 motherBoardMap=db.fetchAllMotherBoard();
					
					for (Map.Entry<String, MotherBoard> entry : motherBoardMap.entrySet()) {
					    String key = entry.getKey();
					    MotherBoard value = entry.getValue();
					    System.out.println(key+" "+value.Model);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
			
			 HttpSession session = request.getSession();
			 session.setAttribute("MotherBoard",motherBoardMap);
			 
			request.getRequestDispatcher("MotherBoard.jsp").forward(request, response);
		}
		
		if(page.equals("GraphicCard")) {
			DB db = new DB();
			HashMap<String,GraphicCard> graphicCardMap=new HashMap<String,GraphicCard>();

			 try {
				 System.out.println("Working Controller");
				 graphicCardMap=db.fetchGraphicCard();
				
				for (Map.Entry<String, GraphicCard> entry : graphicCardMap.entrySet()) {
				    String key = entry.getKey();
				    GraphicCard value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("GraphicCard",graphicCardMap);
			 if(shop.cpu!=null)
			 request.setAttribute("gc",shop.cpu.gc);
			request.getRequestDispatcher("GraphicCard.jsp").forward(request, response);
		}
		
		if(page.equals("Cabinets")) {
			DB db = new DB();
			HashMap<String,Cabinets> cabinetsMap=new HashMap<String,Cabinets>();

			 try {
				 System.out.println("Working Controller");
				 cabinetsMap=db.fetchCabinets();
				
				for (Map.Entry<String, Cabinets> entry : cabinetsMap.entrySet()) {
				    String key = entry.getKey();
				    Cabinets value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("Cabinets",cabinetsMap);
			request.getRequestDispatcher("Cabinets.jsp").forward(request, response);
		}
		
		if(page.equals("MemoryModule")) {
			DB db = new DB();
			HashMap<String,MemoryModule> memoryModuleMap=new HashMap<String,MemoryModule>();

			 try {
				 System.out.println("Working Controller");
				 memoryModuleMap=db.fetchMemoryModule();
				
				for (Map.Entry<String, MemoryModule> entry : memoryModuleMap.entrySet()) {
				    String key = entry.getKey();
				    MemoryModule value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("MemoryModule",memoryModuleMap);
			request.getRequestDispatcher("MemoryModule.jsp").forward(request, response);
		}
		
		if(page.equals("PowerSupply")) {
			DB db = new DB();
			HashMap<String,PowerSupply> powerSupplyMap=new HashMap<String,PowerSupply>();

			 try {
				 System.out.println("Working Controller");
				 powerSupplyMap=db.fetchPowerSupply();
				
				for (Map.Entry<String, PowerSupply> entry : powerSupplyMap.entrySet()) {
				    String key = entry.getKey();
				    PowerSupply value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("PowerSupply",powerSupplyMap);
			request.getRequestDispatcher("PowerSupply.jsp").forward(request, response);
		}
		if(page.equals("selectStorage")) {
			System.out.println("SelectStorgae");
			
				String value[]=request.getParameterValues("chk_box");
				System.out.println(value);
				DB db = new DB();
				HashMap<String,Storage> StorageMap=new HashMap<String,Storage>();

				if(value.length==2) {
					 try {
						 System.out.println("Working Controller");
						 StorageMap=db.fetchStorage();
						
						for (Map.Entry<String, Storage> entry : StorageMap.entrySet()) {
						    String key = entry.getKey();
						    Storage valuea = entry.getValue();
						    System.out.println(key+" "+valuea.Model);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					 HttpSession session = request.getSession();
					 session.setAttribute("Storage",StorageMap);
					request.getRequestDispatcher("Storage.jsp").forward(request, response);
				}
				else {
					if(value[0].equals("HDD")) {
						 try {
							 System.out.println("Working Controller");
							 StorageMap=db.fetchHDDStorage();
							
							for (Map.Entry<String, Storage> entry : StorageMap.entrySet()) {
							    String key = entry.getKey();
							    Storage valuea = entry.getValue();
							    System.out.println(key+" "+valuea.Model);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						 HttpSession session = request.getSession();
						 session.setAttribute("Storage",StorageMap);
						request.getRequestDispatcher("Storage.jsp").forward(request, response);
					}
					else {
						 try {
							 System.out.println("Working Controller");
							 StorageMap=db.fetchSSDStorage();
							
							for (Map.Entry<String, Storage> entry : StorageMap.entrySet()) {
							    String key = entry.getKey();
							    Storage valuea = entry.getValue();
							    System.out.println(key+" "+valuea.Model);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						 HttpSession session = request.getSession();
						 session.setAttribute("Storage",StorageMap);
						request.getRequestDispatcher("Storage.jsp").forward(request, response);
					}
				}
			}
		if(page.equals("Storage")) {
			DB db = new DB();
			HashMap<String,Storage> StorageMap=new HashMap<String,Storage>();

			 try {
				 System.out.println("Working Controller");
				 StorageMap=db.fetchStorage();
				
				for (Map.Entry<String, Storage> entry : StorageMap.entrySet()) {
				    String key = entry.getKey();
				    Storage value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("Storage",StorageMap);
			request.getRequestDispatcher("Storage.jsp").forward(request, response);
		}

		if(page.equals("Ups")) {
			DB db = new DB();
			HashMap<String,Ups> UpsMap=new HashMap<String,Ups>();

			 try {
				 System.out.println("Working Controller");
				 UpsMap=db.fetchUps();
				
				for (Map.Entry<String,Ups> entry :UpsMap.entrySet()) {
				    String key = entry.getKey();
				    Ups value = entry.getValue();
				    System.out.println(key+" "+value.Model);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 HttpSession session = request.getSession();
			 session.setAttribute("Ups",UpsMap);
			request.getRequestDispatcher("Ups.jsp").forward(request, response);
		}

		
	}

}
