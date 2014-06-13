import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class secondlevelStock  implements  MouseListener
{
	private static  java.util.List<String> scrollListCategory = new ArrayList<String>();
	private DefaultListModel ListModel;
	private JList list;
	private JTextArea Text;
	private JPanel paneButton;
	private Container cp;
	private JFrame frame;
	
	public secondlevelStock
	{
		start();
		list.addMouseListener(this);
		frame.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent event)
	{
		if ((event.getClickCount() == 2) && (!event.isConsumed())) 
		{
			event.consume();
			int i = list.getSelectedIndex();
			if (i != -1) 
			{
				if(list.getSelectedValue().equals("HARDWARE")) 
				{
					thirdlevelStock("HARDWARE");
				}
				else if(list.getSelectedValue().equals("PERIPHERALS"))
				{
					thirdlevelStock("PERIPHERALS");
				}
				
			}
		}
	}
	public void mouseExited(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseReleased(MouseEvent event){}
	public void mousePressed(MouseEvent event){}
	
	private void start()
	{
			scrollListCategory.add("HARDWARE");
			scrollListCategory.add("PERIPHERALS");
			ListModel = new DefaultListModel();
			for (String type : scrollListCategory)
			{
				ListModel.addElement(type);
			}
			list = new JList(ListModel);
			list.setSelectedIndex(0);
			JScrollPane listScroller = new JScrollPane(list);
			listScroller.setPreferredSize(new Dimension(height, 200));
			Text = new JTextArea("Please choose a category");
			Text.setFont(new Font("Serif", Font.ITALIC, 20));
			Text.setForeground(Color.BLACK);
			Text.setEditable(false);
			cp = frame.getContentPane();
			cp.setLayout(new BorderLayout());
			paneButton = new JPanel();
			paneButton.setLayout(new FlowLayout());
			paneButton.add(Text);
			cp.add(listScroller, BorderLayout.LINE_START);
			cp.add(paneButton , BorderLayout.LINE_END);
			frame.pack();

	}
}