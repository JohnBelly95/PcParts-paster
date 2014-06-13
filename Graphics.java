import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Graphics  implements ActionListener, MouseListener {
	//declared variables
	private JButton stockbut = new JButton();
	private JButton orderbut = new JButton();
	private JButton makeorderbut = new JButton();
	private JButton changeorderbut = new JButton(); 
	private JButton sellbut = new JButton();
	private JButton buybut = new JButton(); 
	private DefaultListModel ListModel = new DefaultListModel();
	private DefaultListModel ListModel2 = new DefaultListModel();
	private JList list = new JList(ListModel);
	private JList list2 = new JList(ListModel2);
	private static java.util.List<Stock> Stored = new ArrayList<Stock>();
	private static java.util.List<Order> Ordered = new ArrayList<Order>();
	private static java.util.List<Sell> Sold = new ArrayList<Sell>();
	private static java.util.List<String> scrollListCategory = new ArrayList<String>();
	private static mainApp ma = new mainApp();
	public static TextReader tr = new TextReader();
	public static TextWriter tw = new TextWriter();
	private String a;
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = screen.width  / 3;
	private int height = screen.width  / 3;
	private int x = (screen.width / 3);
	private int y = (screen.height / 3);
	private int scrw1 = screen.width/3;
	private int scrw2 = (screen.width) / 3;
	private int j;
	private int k;
	private JPanel paneButton;
	private Container cp;
	private JFrame frame;
	private JTextArea Text = new JTextArea("Press view products to see the Stock \nOverview orders to see or buy an order\n Overview sales to see the sales list",10,20);
	
	//Methods
	public Graphics(){
		Stored = tr.StockTextReader();
		Ordered = tr.OrderTextReader();
		Sold = tr.SoldTextReader();
		firstLevel();
		stockbut.addActionListener(this);
		orderbut.addActionListener(this);
		sellbut.addActionListener(this);
		buybut.addActionListener(this);
		makeorderbut.addActionListener(this);
		changeorderbut.addActionListener(this);
		list.addMouseListener(this);
	}
	
	//MouseClicked
	public void mouseClicked(MouseEvent event){
		int i = list.getSelectedIndex();
        if (event.getClickCount() == 2) {
			JList list = (JList)event.getSource();
			String item = list.getSelectedValue().toString();
			System.out.println(item);
			if (i != -1) {
				if (j == 1){
					if(i == 0) {
						thirdLevel("Stock","HARDWARE");
						k = 1;
					}else if(i == 1){
						thirdLevel("Stock","PERIPHERALS");
						k = 2;
					}
				}else if(j == 2){
					newWindow("Order",item);
				}else if(j == 3){
					newWindow("Sell",item);
				}else if(j == 4){
					newWindow("Stock",item);
				}
			}
		}else{
			if(j == 0){
				if(i == 0){
					buybut.setEnabled(false);
					makeorderbut.setEnabled(true);
					changeorderbut.setEnabled(false);
				}else{
					buybut.setEnabled(true);
					makeorderbut.setEnabled(false);
					changeorderbut.setEnabled(true);
				}
			}
		}
	}
	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	public void mousePressed(MouseEvent event){}	
	
	//ActionPerformed
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == stockbut){
			secondLevel("stock");
		}else if(e.getSource() == orderbut){
			secondLevel("order");
		}else if(e.getSource() == sellbut){
			secondLevel("sell");
		}else if (e.getSource() == makeorderbut) {
			String modelname = (String)JOptionPane.showInputDialog(this, "Please enter the item's model name");
			String manufacturer = (String)JOptionPane.showInputDialog(this, "Please enter the manufacturer");
			String modelyear = (String)JOptionPane.showInputDialog(this, "Please enter the the year it was realeased");
			String price = (String)JOptionPane.showInputDialog(this, "Please enter the price of the item");
			int year = Integer.parseInt(modelyear);
			int p = Integer.parseInt(price);
			PcParts product = new PcParts(modelname,manufacturer,year, p);
			String name = (String)JOptionPane.showInputDialog(this, "Please type full name");
			String phone = (String)JOptionPane.showInputDialog(this, "Please type  phone");
			String date = (String)JOptionPane.showInputDialog(this, "When do we expect the item to be available ? (dd/mm/yyyy)");
			long ph = Long.parseLong(phone);
			int fp = p   - (p * 20 /100);//akoma dn exw ftoiksei kala tis ekptwseis
			Order ord = new Order(product, name, ph ,date , fp);
			Ordered.add(ord);
			tw.OrderTextWriter(Ordered);
		}else  if (e.getSource() == changeorderbut) {
			int i =list.getSelectedIndex();
			if (i != -1) {
				Ordered.get(i).setStatus(true);
				Sell sl = new Sell(Ordered.get(i).getThing(), Ordered.get(i).getName(), Ordered.get(i).getPhone(), Ordered.get(i).getFP());
				Sold.add(sl);
				tw.SoldTextWriter(Sold);
				Ordered.remove(i);
				tw.OrderTextWriter(Ordered);
			}
   		}
		else  if (e.getSource() == buybut) {
			int i =list.getSelectedIndex();
			if (i != -1) {
				String name = (String)JOptionPane.showInputDialog(this, "Please type full name");
				String phone = (String)JOptionPane.showInputDialog(this, "Please type  phone");
				long ph = Long.parseLong(phone);
				int p = Stored.get(i).getPrice();
				int fp = p   - (p * 20 /100);//akoma dn exw ftoiksei kala tis ekptwseis
				Sell sl = new Sell(Stored.get(i).getThing(), name, ph, fp);
				Sold.add(sl);
				tw.SoldTextWriter(Sold);
				Stored.get(i).setAvailableStock(Stored.get(i).getAvailableStock() - 1);
				if(Stored.get(i).getAvailableStock() < 1){
					Stored.remove(i);
				}
				tw.StockTextWriter(Stored);
			}
   		}
		
	}
	//GUI level 1
	public void firstLevel() {
		frame = new JFrame(" PC parts");
		frame.setBounds(x, y, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stockbut = new JButton("View products available");
		orderbut = new JButton("Overview orders");
		sellbut = new JButton("Overview sales");
		cp = frame.getContentPane();
        cp.setLayout(new BorderLayout());
		paneButton = new JPanel();
		paneButton.setLayout(new FlowLayout());
		paneButton.add(stockbut);
		paneButton.add(orderbut);
		paneButton.add(sellbut);
		Text = new JTextArea("Press view products to see the Stock \nOverview orders to see or buy an order\n Overview sales to see the sales list");
		Text.setFont(new Font("Serif", Font.ITALIC, 20));
		Text.setForeground(Color.BLACK);
		Text.setEditable(false);
		paneButton.add(Text);
		cp.add(paneButton);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	//GUI level 2
	public void secondLevel(String type){
		if(type.equals("stock")){
			j = 1;
			scrollListCategory.add("HARDWARE");
			scrollListCategory.add("PERIPHERALS");
			ListModel = new DefaultListModel();
			for (String category : scrollListCategory)
			{
				ListModel.addElement(category);
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, scrw1));
			Text = new JTextArea("Please choose a category");
			Text.setFont(new Font("Serif", Font.ITALIC, 20));
			Text.setForeground(Color.BLACK);
			Text.setEditable(false);
			cp.removeAll();
			list.addMouseListener(this);
			paneButton = new JPanel();
			paneButton.setLayout(new FlowLayout());
			paneButton.add(Text);
			cp.add(listScroller, BorderLayout.LINE_START);
			cp.add(paneButton , BorderLayout.LINE_END);
			frame.pack();
			frame.setVisible(true);
			
		}else if(type.equals("order")){
			j = 2;
			ListModel = new DefaultListModel();
			DefaultListModel  ListModel2 = new DefaultListModel();
			for (Order prod : Ordered){
				ListModel.addElement(prod.getThing().getTitle());
				ListModel2.addElement(prod.toString());
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			list2 = new JList(ListModel2);
			list2.setSelectedIndex(0);
			JScrollPane listScroller1 = new JScrollPane(list);
			listScroller1.setPreferredSize(new Dimension(height, scrw1));
			JScrollPane listScroller2 = new JScrollPane(list2);
			listScroller2.setPreferredSize(new Dimension(height, scrw2));
			cp.removeAll();
			list.addMouseListener(this);
			cp.add(listScroller1, BorderLayout.LINE_START);
			cp.add(listScroller2, BorderLayout.LINE_END);

			frame.pack();
			frame.setVisible(true);
		}else if(type.equals("sell")){
			j = 3;
			ListModel = new DefaultListModel();
			DefaultListModel  ListModel2 = new DefaultListModel();
			for (Sell prod : Sold){
				ListModel.addElement(prod.getThing().getTitle());
				ListModel2.addElement(prod.toString());
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			list2 = new JList(ListModel2);
			list2.setSelectedIndex(0);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, scrw1));
			JScrollPane listScroller2 = new JScrollPane(list2);
			listScroller2.setPreferredSize(new Dimension(height, scrw2));
			cp.removeAll();
			list.addMouseListener(this);
			cp.add(listScroller, BorderLayout.LINE_START);
			cp.add(listScroller2, BorderLayout.LINE_END);
			frame.pack();
			frame.setVisible(true);
		}
	}
	
	// GUI level 3
	public void thirdLevel(String type, String a){
		j = 4;
		this.a = a;
		if(type.equals("Stock")){
			ListModel = new DefaultListModel();
			ListModel2 = new DefaultListModel();
			for (Stock prod : Stored)
			{
				if(a.equals("HARDWARE"))
				{
					if(prod.getThing().getTitle().equals("MOTHERBOARD") || prod.getThing().getTitle().equals("RAM") || prod.getThing().getTitle().equals("CPU") || prod.getThing().getTitle().equals("GPU") || prod.getThing().getTitle().equals("HARDDRIVE"))
					{
						JTextArea Text2 = new JTextArea(prod.toString());
						ListModel.addElement(prod.getThing().getTitle());
						ListModel2.addElement(Text2);
					}
				}
				else if(a.equals("PERIPHERALS"))
				{
					if(prod.getThing().getTitle().equals("MONITOR") || prod.getThing().getTitle().equals("MOUSE") || prod.getThing().getTitle().equals("KEYBOARD") || prod.getThing().getTitle().equals("PRINTER"))
					{	
						JTextArea Text2 = new JTextArea(prod.toString());
						ListModel.addElement(prod.getThing().getTitle());
						ListModel2.addElement(Text2);
					}
				}
			}
			
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			list2 = new JList(ListModel2);
			list2.setSelectedIndex(0);

			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, scrw1));
			JScrollPane listScroller2 = new JScrollPane(list2);
			listScroller2.setPreferredSize(new Dimension(height, scrw2));
			cp.removeAll();
			cp.add(listScroller, BorderLayout.LINE_START);
			cp.add(listScroller2, BorderLayout.LINE_END);
			list.addMouseListener(this);
			frame.pack();
			frame.setVisible(true);
		}
	}
	
	//New Window
	public void newWindow(String type, String b){
		j = 0;
		if(type.equals("Stock")){
			System.out.println("1");
			frame = new JFrame("Stock");
			frame.setBounds(x, y, width, height);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			buybut = new JButton("TO BUY");
			makeorderbut = new JButton("TO ORDER");
			ListModel = new DefaultListModel();
			ListModel2 = new DefaultListModel();
			ListModel.addElement("NONE");
			
			for (Stock prod : Stored){
				System.out.println("2");
				if((prod.getThing().getTitle() == b)){
					JTextArea Text2 = new JTextArea(prod.toString());
					ListModel.addElement(prod.getThing().getmodelName());
					ListModel2.addElement(Text2);
					System.out.println("3");
				}
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			list2 = new JList(ListModel2);
			list2.setSelectedIndex(0);
			
			cp = new Container();
			cp = frame.getContentPane();
			cp.setLayout(new BorderLayout());
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, scrw2));
			JScrollPane listScroller2 = new JScrollPane(list2);
			listScroller2.setPreferredSize(new Dimension(height, scrw2));
			buybut.setEnabled(false);
			makeorderbut.setEnabled(false);
			paneButton = new JPanel();
			paneButton.setLayout(new FlowLayout());
			paneButton.add(buybut);
			paneButton.add(makeorderbut);
			Text = new JTextArea("If you want to buy a product that allready exists press TO BUY \n If you want to order a product that does not exist in the stock press TO ORDER");
			Text.setFont(new Font("Serif", Font.ITALIC, 20));
			Text.setForeground(Color.BLACK);
			Text.setEditable(false);
			paneButton.add(Text);
			list.addMouseListener(this);
			cp.add(listScroller, BorderLayout.LINE_START);
			cp.add(listScroller2, BorderLayout.CENTER);
			cp.add(paneButton, BorderLayout.LINE_END);
			buybut.addActionListener(this);
			makeorderbut.addActionListener(this);
			frame.pack();
			frame.setVisible(true);
		}else if(type.equals("Order")){
			frame = new JFrame("Order");
			frame.setBounds(x, y, width, height);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			changeorderbut = new JButton("TO BUY");
			ListModel = new DefaultListModel();
			ListModel2 = new DefaultListModel();
			ListModel.addElement("NONE");
			for (Order prod : Ordered){
				if(prod.getThing().getTitle() == b){
					JTextArea Text2 = new JTextArea(prod.toString());
					ListModel.addElement(prod.getThing().getmodelName());
					ListModel2.addElement(Text2);
					System.out.println("3");
				}
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			list2 = new JList(ListModel2);
			list2.setSelectedIndex(0);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, scrw1));
			JScrollPane listScroller2 = new JScrollPane(list2);
			listScroller2.setPreferredSize(new Dimension(height, scrw1));
			paneButton = new JPanel();
			paneButton.setLayout(new FlowLayout());
			paneButton.add(changeorderbut);
			Text = new JTextArea("If you want to buy this product  press TO BUY");
			Text.setFont(new Font("Serif", Font.ITALIC, 20));
			Text.setForeground(Color.BLACK);
			Text.setEditable(false);
			paneButton.add(Text);
			cp = new Container();
			cp = frame.getContentPane();
			cp.setLayout(new BorderLayout());
			list.addMouseListener(this);
			changeorderbut.addActionListener(this);
			cp.add(listScroller, BorderLayout.LINE_START);
			cp.add(listScroller2, BorderLayout.CENTER);
			cp.add(paneButton, BorderLayout.LINE_END);
			frame.pack();
			frame.setVisible(true);
		}else if(type.equals("Sell")){
			frame = new JFrame("Sell");
			frame.setBounds(x, y, width, height);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ListModel = new DefaultListModel();
			for (Sell prod : Sold){
				if(prod.getThing().getTitle() == b){
					JTextArea Text2 = new JTextArea(prod.toString());
					ListModel.addElement(Text2);
				}
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, scrw1));
			cp = new Container();
			cp = frame.getContentPane();
			cp.setLayout(new BorderLayout());
			cp.add(listScroller);
			frame.pack();
			frame.setVisible(true);
		}
	}
	public static int PriceCalculation( int pcPartType, int price){
		if (pcPartType.equals("1")){
			fp = price - price* 10/100;                  
			//System.out.println("The final price is : "+ fp +" Euros.");
		}else if(pcPartType.equals("2")){
			fp = price - price * 20/100;                  
			//System.out.println("The final price is : "+ fp +" Euros.");
		}
		return fp;
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/master
