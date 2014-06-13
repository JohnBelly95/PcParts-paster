import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class newWindowStock extends JFrame implements ActionListener, MouseListener
private JButton makeorderbut ;
private JButton buybut;
private DefaultListModel ListModel;
private JTextArea Text;
private JPanel paneButton;
private static  java.util.List<Stock> Stored = new ArrayList<Stock>();

public void newWindowStock(int b)// STOCK BUY OR ORDER
	{
		setTitle("Stock");
		setBounds(x, y, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buybut = new JButton("TO BUY");
		makeorderbut = new JButton("TO ORDER");
		ListModel = new DefaultListModel();
		int c =0;
		for (Stock prod : Stored)
		{
			if((prod.getTitle() != Stored.get(b).getTitle()) && c != 1)
			{
				buybut.setEnabled(false);
				makeorderbut.setEnabled(true);
			}
			else
			{
				c = 1;
				ListModel.addElement(prod.toString());
				buybut.setEnabled(true);
				makeorderbut.setEnabled(false);
			}
		}
		list2 = new JList(ListModel);
		list2.setSelectedIndex(0);
		cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());
		JScrollPane listScroller = new JScrollPane(list2);
		listScroller.setPreferredSize(new Dimension(height, 200));
		paneButton = new JPanel();
		paneButton.setLayout(new FlowLayout());
		paneButton.add(buybut);
		paneButton.add(makeorderbut);
		Text = new JTextArea("If you want to buy a product that allready exists press TO BUY \n If you want to order a product that does not exist in the stock press TO ORDER");
		Text.setFont(new Font("Serif", Font.ITALIC, 20));
		Text.setForeground(Color.BLACK);
		Text.setEditable(false);
		paneButton.add(Text);
		cp.removeAll();
		cp.add(listScroller, BorderLayout.LINE_START);
		cp.add(paneButton, BorderLayout.LINE_END);
		frame.pack();
		frame.setVisible(true);
	}