import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DVTextEditor {
	private JTextArea editorTextArea;
	private JFrame frame;
	
	public void go() {
		// make frame
		JFrame frame = new JFrame("DVTextEditor");
		
		//make menu bar and menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu formatMenu = new JMenu("Format");
		
		//make menu items for file menu
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem openMenuItem = new JMenuItem("Open");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		
		//add menu items in file menu
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		
		//action listeners for New and Save menu items
		newMenuItem.addActionListener(new NewMenuListener());
		openMenuItem.addActionListener(new OpenMenuListener());
		saveMenuItem.addActionListener(new SaveMenuListener());
		
		//make menu items for format menu
		JCheckBoxMenuItem wrapMenuItem = new JCheckBoxMenuItem("Word Wrap ");
		
		//add menu items in format menu
		formatMenu.add(wrapMenuItem);
		
		//action listener for word Wrap item menu
		wrapMenuItem.addItemListener(new WordWrapListener());
		
		// add menu in menu bar
		menuBar.add(fileMenu);
		menuBar.add(formatMenu);
		
		// make text area and put inside scrollpane
		editorTextArea = new JTextArea(15, 50);
		JScrollPane eScrollBar = new JScrollPane(editorTextArea);
		eScrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		eScrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		// add menu bar and text area in frame
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, eScrollBar);
		
		// enable frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setVisible(true);
				
	}
	
	public class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// confirmation message "would you like to save" if file is not saved yet.
			editorTextArea.setText("");
		}
	} //end New Menu Inner Class
	
	public class OpenMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser openFile = new JFileChooser();
			openFile.showOpenDialog(frame);
			loadFile(openFile.getSelectedFile());
		}
	} // end Open Menu Inner Class
	
	private void loadFile(File file) {
		StringBuilder myDoc = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				myDoc.append(line + "\n");
			}
			reader.close();
		} catch (Exception ex) {
			System.out.println("Could not read file");
			ex.printStackTrace();
		}
		editorTextArea.setText(myDoc.toString());
	}
	
	public class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}
	} // end Save Menu Inner Class
	
	private void saveFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			//writer.write(editorTextArea.getText());  <- does not work write only one line
			//instead of using bufferedWriter's method to write use textarea's method to write
			editorTextArea.write(writer);
			writer.close();
			
		} catch (IOException ex) {
			System.out.println("Could not save file!");
			ex.printStackTrace();
		}
	}
	
	public class WordWrapListener implements ItemListener {
		public void itemStateChanged (ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				editorTextArea.setLineWrap(true);
				editorTextArea.setWrapStyleWord(true);
				frame.repaint();
			} else {
				editorTextArea.setLineWrap(false);
				editorTextArea.setWrapStyleWord(false);
				frame.repaint();
			}
		}
	} // end inner class
	
	public static void main(String[] args) {
		DVTextEditor x = new DVTextEditor();
		x.go();
	}
	
}
