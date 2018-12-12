package windows;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import christmastreeinfo.Keys;
import christmastreeinfo.Lang;
import customer.Customer;
import customer.DataPoint;
import customer.DataType;
import customer.SaveDataConverter;

public class DataInputWindow extends JFrame {
	
	/**/ private static final long serialVersionUID = -8900248693643765822L;

	// Customer data
	private Customer cst;
		
	private String dates[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Any"};
	private String times[] = {"Morning", "Afternoon"};
	
	// JPanel stuffs
	private JPanel master = new JPanel();
	
	private JPanel datePanel;
	private JPanel dateSelectPanel;
	private JLabel dateLabel;
	private JRadioButton dateBox[];
	private ButtonGroup dateButtonGroup;
	private GridLayout dateLayout;
	
	private JPanel timesPanel;
	private JPanel timesSelectPanel;
	private JLabel timesLabel;
	private JRadioButton timesBox[];
	private ButtonGroup timesButtonGroup;
	private GridLayout timeLayout;
	
	private JButton cancelButton;
	private JButton editButton;
	private JButton saveButton;
	private JButton sceduleButton;
	private JButton refreshButton;
	private JButton exitButton;
	private JPanel buttonPanel;
	
	private JFrame me;
	//Editing vars	
	HashMap<DataType, JTextArea> dataMap;
	
	public DataInputWindow(Customer c, String[] dates, String[] times) {
		me = this;
		if(c != null) {
			cst = c;
		} else {
			cst = new Customer(new DataPoint(DataType.NAME, "John Doe"), new DataPoint(DataType.ADDRESS, "null, made new"));
		}
		setTitle();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dataMap = new HashMap<DataType, JTextArea>();
		this.dates = dates;
		this.times = times;
		display();
	}
	public DataInputWindow(Customer c) {
		this(c, Lang.DEFAULT_DATES, Lang.DEFAULT_TIMES);
	}
	
	public DataInputWindow() {
		this(null);
	}

	public void display() {
		master = new JPanel();
		this.setTitle(cst.get(DataType.NAME));
		
		master.setLayout(new BoxLayout(master, BoxLayout.Y_AXIS));
		
		addTextZone(DataType.UUID);
		addTextZone(DataType.NAME);
		addTextZone(DataType.PHONE_NUMBER);
		addTextZone(DataType.EMAIL_ADDRESS);
		
		datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
		dateSelectPanel = new JPanel();
		dateLabel = new JLabel(Lang.PICKUP_DATE + ":");
		dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		dateBox = new JRadioButton[dates.length];
		int w = dates.length / 2;
		if((2*w) != dates.length) {
			w++;
		}
		dateLayout = new GridLayout(2, w);
		dateSelectPanel.setLayout(dateLayout);
		dateButtonGroup = new ButtonGroup();
		for(int i = 0; i < dates.length; i++) {
			dateBox[i] = new JRadioButton(dates[i]);
			dateBox[i].setText(dates[i]);
			dateBox[i].setActionCommand(Integer.toString(i));
			if(dates[i].equals(cst.get(DataType.PICKUP_DATE))) {
				dateBox[i].setSelected(true);
			}
			dateSelectPanel.add(dateBox[i]);
			dateButtonGroup.add(dateBox[i]);
		}
		addPanelToMaster(datePanel, dateLabel, dateSelectPanel);
		
		timesPanel = new JPanel();
		timesPanel.setLayout(new BoxLayout(timesPanel, BoxLayout.Y_AXIS));
		timesSelectPanel = new JPanel();
		timesLabel = new JLabel(Lang.PICKUP_TIME + ":");
		timesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timesPanel.add(timesLabel);
		timesBox = new JRadioButton[times.length];
		timeLayout = new GridLayout(1, times.length);
		timesSelectPanel.setLayout(timeLayout);
		timesButtonGroup = new ButtonGroup();
		for(int i = 0; i < times.length; i++) {
			timesBox[i] = new JRadioButton(times[i]);
			timesBox[i].setText(times[i]);
			timesBox[i].setActionCommand(Integer.toString(i));
			if(times[i].equals(cst.get(DataType.PICKUP_TIME))) {
				timesBox[i].setSelected(true);
			}
			timesSelectPanel.add(timesBox[i]);
			timesButtonGroup.add(timesBox[i]);
		}
		addPanelToMaster(timesPanel, timesLabel, timesSelectPanel);
		
		addTextZone(DataType.ADDRESS);
		addTextZone(DataType.CITY);
		addTextZone(DataType.ZONE);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		cancelButton = new JButton(Lang.CANCEL);
		cancelButton.addActionListener(cancelButtonActionLisener());
		cancelButton.setEnabled(false);
		editButton = new JButton(Lang.EDIT);
		editButton.addActionListener(editButtonActionListener());
		saveButton = new JButton(Lang.SAVE);
		saveButton.addActionListener(saveButtonActionLisener());
		saveButton.setEnabled(false);
		sceduleButton = new JButton(Lang.SCEDULE);
		sceduleButton.addActionListener(sceduleButtonActionListener());
		refreshButton = new JButton(Lang.REFRESH);
		refreshButton.addActionListener(refreshButtonActionListener());
		exitButton = new JButton(Lang.EXIT);
		exitButton.addActionListener(exitButtonActionListener());
		addToMaster(buttonPanel, cancelButton, editButton, saveButton);
		addToMaster(buttonPanel, sceduleButton, refreshButton, exitButton);
		
		setEditable(false);
		
		this.add(master);
		this.validate();
		this.pack();
		this.setVisible(true);
	}
	
	public void update() {
		for (Map.Entry<DataType, JTextArea> e : dataMap.entrySet()) {
		    e.getValue().setText(cst.get(e.getKey()));
		}
	}
	private void addTextZone(DataType type) {
		JLabel label = new JLabel(type.getText() + ":");
		JTextArea area = new JTextArea(cst.get(type));
		dataMap.put(type, area);
		addToMaster(label, area);
	}
	
	private void setEditable(boolean editable) {
		for (Map.Entry<DataType, JTextArea> e : dataMap.entrySet()) {
		    e.getValue().setEditable(editable);
		}
	}
	
	private void addToMaster(Component... parts) {
		addPanelToMaster(new JPanel(), parts);
	}
	private void addPanelToMaster(JPanel panel, Component... parts) {
		for(Component c : parts) {
			panel.add(c);
		}
		panel.setPreferredSize(panel.getPreferredSize());
		master.add(panel);
	}
	
	private void setTitle() {
		this.setTitle(cst.get(DataType.NAME));
	}
	
	private void startEditing() {
		if(!cst.get(DataType.DATA_LOCKED).equals(Keys.TRUE)) {
			if(!cst.get(DataType.DATA_EDIT_LOCKED).equals(Keys.TRUE)) {
				setTitle(cst.get(DataType.NAME) + " [" + Lang.EDITING + "]");
				cst.set(DataType.DATA_EDIT_LOCKED, Keys.TRUE);
				setEditable(true);
				saveButton.setEnabled(true);
				cancelButton.setEnabled(true);
				sceduleButton.setEnabled(false);
				editButton.setEnabled(false);
				refreshButton.setEnabled(false);
				exitButton.setEnabled(false);
				Enumeration<AbstractButton> btn = dateButtonGroup.getElements();
				while(btn.hasMoreElements()) {
					btn.nextElement().setEnabled(false);
				}
				btn = timesButtonGroup.getElements();
				while(btn.hasMoreElements()) {
					btn.nextElement().setEnabled(false);
				}
			} else {
				new InfoWindow(Lang.CONCURRENT_EDIT_MSG, Lang.PROGRAM_NAME);
			}
		} else {
			new InfoWindow(Lang.EDIT_LOCKED_MSG, Lang.PROGRAM_NAME);
		}
	}
	private void endEditing() {
		cst.set(DataType.DATA_EDIT_LOCKED, Keys.FALSE);
		setEditable(false);
		editButton.setEnabled(true);
		sceduleButton.setEnabled(true);
		cancelButton.setEnabled(false);
		saveButton.setEnabled(false);
		refreshButton.setEnabled(true);
		exitButton.setEnabled(true);
		Enumeration<AbstractButton> btn = dateButtonGroup.getElements();
		while(btn.hasMoreElements()) {
			btn.nextElement().setEnabled(true);
		}
		btn = timesButtonGroup.getElements();
		while(btn.hasMoreElements()) {
			btn.nextElement().setEnabled(true);
		}
		setTitle();
	}
	private ActionListener editButtonActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startEditing();
			}
		};
	}
	
	private ActionListener saveButtonActionLisener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endEditing();
				String changes = "{";
				for (Map.Entry<DataType, JTextArea> e : dataMap.entrySet()) {
					if(!e.getValue().getText().equals(cst.get(e.getKey()))) {
						changes = changes + ",\"" + e.getKey().toString() + "\":\"" + e.getValue().getText() + "\"";
					}
				    cst.set(e.getKey(), SaveDataConverter.getFromString(e.getValue().getText(), e.getKey()));
				}
				System.out.println(changes);
				cst.set(DataType.CHANGE_INFO, cst.get(DataType.CHANGE_INFO) + changes + "}");
				update();
			}
		};
	}
	
	private ActionListener cancelButtonActionLisener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(saveButton.isEnabled()) {
					new InfoWindow(Lang.ARE_YOU_SURE_MSG + Lang.CANCEL, Lang.PROGRAM_NAME,
							new InfoWindowButton(Lang.YES, new Runnable() { @Override public void run() { endEditing(); update(); } }, true),
							new InfoWindowButton(Lang.NO, null, true));
				} else {
					
				}
			}
		};
	}
	
	private ActionListener sceduleButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(dateButtonGroup.getSelection() != null) {
					cst.set(DataType.PICKUP_DATE, dates[Integer.parseInt(dateButtonGroup.getSelection().getActionCommand())]);
				}
				if(timesButtonGroup.getSelection() != null) {
					cst.set(DataType.PICKUP_TIME, times[Integer.parseInt(timesButtonGroup.getSelection().getActionCommand())]);
				}
				new InfoWindow(Lang.SCEDULED, Lang.PROGRAM_NAME);
			}
		};
	}

	private ActionListener refreshButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}
		};
	}
	private ActionListener exitButtonActionListener() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(editButton.isEnabled() != true) {
					new InfoWindow(Lang.EDIT_NOT_SAVED_MSG, Lang.PROGRAM_NAME);
				} else {
					String date = "";
					String time = "";
					if(dateButtonGroup.getSelection() != null) {
						date = dates[Integer.parseInt(dateButtonGroup.getSelection().getActionCommand())];
					}
					if(timesButtonGroup.getSelection() != null) {
						time =  times[Integer.parseInt(timesButtonGroup.getSelection().getActionCommand())];
					}
					if(!date.equals(cst.get(DataType.PICKUP_DATE)) || !time.equals(cst.get(DataType.PICKUP_TIME))) {
						new InfoWindow(Lang.SCEDULE_NOT_SAVED_MSG, Lang.PROGRAM_NAME);
					} else {
						me.dispose();
					}
				}
			}
		};
	}
	public DataInputWindow clone() {
		return new DataInputWindow(cst, dates, times);
	}
}
