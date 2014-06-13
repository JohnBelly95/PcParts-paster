import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class thirdlevelStock implements  MouseListener
	private DefaultListModel ListModel;
	private JList list;
	private JList list2;
	private Container cp;
	private JFrame frame;
	private static  java.util.List<Stock> Stored = new ArrayList<Stock>();
	private String a;
	public static mainApp ma = new mainApp();
	
	public thirdlevelStock(String a)
	{
		this.a = a;
		Stored = ma.load(0);
		start(a);
		list.addMouseListener(this);
		frame.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent event)
	{
		if (event.getClickCount() == 2 && !event.isConsumed() ) 
		{
			event.consume();
			int i = list.getSelectedIndex();
			if (i != -1) 
			{
				
			}
		}
	}
	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	public void mousePressed(MouseEvent event){}
	
	private void start(String a)
	{
		ListModel = new DefaultListModel();
		DefaultListModel  ListModel2 = new DefaultListModel();
		for (Stock prod : Stored)
		{
			if(a.equals("HARDWARE"))
			{
				if(prod.getTitle().equals("MOTHERBOARD") || prod.getTitle().equals("RAM") || prod.getTitle().equals("CPU") || prod.getTitle().equals("GPU") || prod.getTitle().equals("HARDDRIVE"))
				{
					ListModel.addElement(prod.getTitle());
					ListModel2.addElement(prod.toString());
				}
			}
			else if(a.equals("PERIPHERALS"))
			{
				if(prod.getTitle().equals("MONITOR") || prod.getTitle().equals("MOUSE") || prod.getTitle().equals("KEYBOARD") || prod.getTitle().equals("PRINTER"))
				{
					ListModel.addElement(prod.getTitle());
					ListModel2.addElement(prod.toString());
				}
			}
		}
		list = new JList(ListModel);
		list.setSelectedIndex(0);
		list2 = new JList(ListModel2);
		list2.setSelectedIndex(0);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(height, 250));
		JScrollPane listScroller2 = new JScrollPane(list2);
		listScroller2.setPreferredSize(new Dimension(height, 250));
		cp.removeAll();
		cp.add(listScroller, BorderLayout.LINE_START);
		cp.add(listScroller2, BorderLayout.LINE_END);
		frame.pack();
	}