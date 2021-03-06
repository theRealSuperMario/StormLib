package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dataStructure.StormData;

public abstract class ProcessingStepsPanel extends JPanel implements PropertyChangeListener, Transferable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	public JButton parameterButton;
	public JButton removeButton;
	private MainFrame mf;
	private ProcessingStepsPanel thisPanel;
	private Color color;
	private boolean visibilityOptionPanel = false;
	private JPanel optionPanel;
	private JProgressBar progressbar;
	JRadioButton rb = new JRadioButton();
	
	public ProcessingStepsPanel(final MainFrame mf){
		thisPanel = this;
		this.mf = mf;
		Box horizontalBox = Box.createHorizontalBox();
		Box verticalBox = Box.createVerticalBox();
		final Dimension d = new Dimension(mf.style.getWidthProcessingStepsPanel(),mf.style.getHeightProcessingStepsPanel());
		Component ui = Box.createVerticalStrut(mf.style.getUpperIndent());
		verticalBox.setPreferredSize(d);
		verticalBox.add(ui);
		verticalBox.add(horizontalBox);
		this.add(verticalBox);
		this.addMouseListener(new MyDraggableMouseListener());
		this.setTransferHandler(new DragAndDropTransferHandler());
		this.setBackground(Color.cyan);
		this.setMaximumSize(d);
		this.setPreferredSize(d);

		parameterButton = new JButton();
		parameterButton.setBackground(null);
		parameterButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				mf.hideAllOptionPanels();
				setVisibilityOptionPanel(true);
				mf.repaint();
			}
		});
				
		removeButton = new JButton();
		
		ImageIcon icon = createImageIcon("/Resources/removeButtonNormalAlternativ2.png","lol");
		removeButton.setIcon(icon);
		ImageIcon iconPressed = createImageIcon("/Resources/removeButtonPressedAlternativ2.png","lol");
		removeButton.setPressedIcon(iconPressed);
		removeButton.setBorder(null);
		removeButton.setBackground(null);
		//removeButton.setForeground(mf.style.getRemoveButtonColor());
		removeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.optionPanel.remove(optionPanel);
				mf.removePanel(thisPanel);
				mf.repaint();
			}
		});
		Component ls = Box.createHorizontalStrut(mf.style.getLeftIndent());
		horizontalBox.add(ls);
		horizontalBox.add(rb);
		rb.setBackground(null);
		horizontalBox.add(Box.createHorizontalGlue());
		horizontalBox.add(parameterButton);
		Component hg = Box.createHorizontalGlue();
		horizontalBox.add(hg);
		horizontalBox.add(removeButton);
		Component rs = Box.createHorizontalStrut(mf.style.getRightIndent());
		horizontalBox.add(rs);
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalBox.add(verticalGlue);
		Box hb2 = Box.createHorizontalBox();
		hb2.add(Box.createHorizontalStrut(mf.style.getLeftIndent()));
		progressbar = new JProgressBar(0,100);
		progressbar.setValue(0);
		progressbar.setPreferredSize(new Dimension(d.width,20));
		hb2.add(progressbar);
		hb2.add(Box.createHorizontalStrut(mf.style.getRightIndent()));
		verticalBox.add(hb2);
		Component li = Box.createVerticalStrut(mf.style.getLowerIndent());
		verticalBox.add(li);
	}
	public JRadioButton getRadioButton(){
		return this.rb;
	}
	
	public ProcessingStepsPanel(){}
	
	public void setParameterButtonsName(String name){
		parameterButton.setText(name);
	}
	
	public void setColor(Color color){
		this.color = color;
		this.setBackground(color);
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] flavors ={null};
		 try {
	            flavors[0] = MainFrame.getDragAndDropPanelDataFlavor();
	        } catch (Exception ex) {
	            ex.printStackTrace(System.err);
	            return null;
	        }
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		DataFlavor[] flavors = {null};
        try {
            flavors[0] = MainFrame.getDragAndDropPanelDataFlavor();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }

        for (DataFlavor f : flavors) {
            if (f.equals(flavor)) {
                return true;
            }
        }

        return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
       
        DataFlavor thisFlavor = null;

        try {
            thisFlavor = MainFrame.getDragAndDropPanelDataFlavor();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        }
        
        if (thisFlavor != null && flavor.equals(thisFlavor)) {
            return ProcessingStepsPanel.this;
        }

        return null;
	}

	public JPanel getOptionPanel() {
		return optionPanel;
	}

	public void setOptionPanel(JPanel optionPanel) {
		this.optionPanel = optionPanel;
		mf.optionPanel.add(optionPanel);
		optionPanel.setVisible(visibilityOptionPanel);
		mf.hideAllOptionPanels();
		setVisibilityOptionPanel(true);
		mf.repaint();
		
	}

	public boolean getIsVisibilityOptionPanel() {
		return visibilityOptionPanel;
	}

	public void setVisibilityOptionPanel(boolean visibilityOptionPanel) {
		this.visibilityOptionPanel = visibilityOptionPanel;
		optionPanel.setVisible(visibilityOptionPanel);
	}
	
	public void setProgressbarValue(int val){
		//System.out.println(val);
		progressbar.setValue(val);
	}
	
	public void getTextFieldTexts(JTextField[] listTextFields, int nbrOtherComponents, String[] parameterList){
		for (int i = 0; i<listTextFields.length; i++){
			parameterList[i+nbrOtherComponents] = listTextFields[i].getText();
		}
	}
		
	public void setTextFieldTexts(JTextField[] listTextFields, int nbrOtherComponents, String[] parameterList){
		for (int i = 0; i<listTextFields.length; i++){
			listTextFields[i].setText(parameterList[i+nbrOtherComponents]);
		}
	}
			
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		int progress = (Integer) evt.getNewValue();
        setProgressbarValue(progress);
	}
	abstract public ProcessingStepsPanel getFunction(MainFrame mf);
	abstract public String[] getSettings();
	abstract public void setSettings(String[] tempString);
	abstract public ProcessingStepsPanel getProcessingStepsPanelObject(ProcessingStepsPanel processingStepsPanelObject, MainFrame mf);
	abstract public String getFunctionName();
	abstract public void process(StormData sd1, StormData sd2);
	
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
}
