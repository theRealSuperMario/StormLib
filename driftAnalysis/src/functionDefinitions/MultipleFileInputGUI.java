package functionDefinitions;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import StormLib.Utilities;
import dataStructure.StormData;
import gui.ImportModules;
import gui.MainFrame;
import gui.ProcessingStepsPanel;

public class MultipleFileInputGUI extends ImportModules{
	JTextField path = new JTextField();
	JTextField pattern = new JTextField();
	JTextField basename = new JTextField();
	private static String name = "Multiple File Input";
	transient MainFrame mf;
	public MultipleFileInputGUI(MainFrame mf) {
		super(mf);
		this.mf = mf;
		this.setParameterButtonsName(name);
		this.setColor(mf.style.getColorInput());
		path.setPreferredSize(mf.style.getDimensionPathFields());
		this.setOptionPanel(createOptionPanel());
	}
	
	public MultipleFileInputGUI(){}
	
	private JPanel createOptionPanel(){
		JPanel retPanel = new JPanel();
		retPanel.setSize(300, 500);
		Box verticalBox = Box.createVerticalBox();
		Dimension d = new Dimension(350,22);
		path.setPreferredSize(d);
		pattern.setPreferredSize(d);
		basename.setPreferredSize(d);
		verticalBox.add(new JLabel("Path:"));
		verticalBox.add(path);
		verticalBox.add(new JLabel("Pattern:"));
		verticalBox.add(pattern);
		verticalBox.add(new JLabel("Basename:"));
		verticalBox.add(basename);
		basename.setAlignmentX(0);
		path.setAlignmentX(0);
		pattern.setAlignmentX(0);
		retPanel.add(verticalBox);
		return retPanel;
	}
	
	public String getPath(){
		return path.getText();
	}
	public void setPath(String importPath){
		path.setText(importPath);
	}
	public String getPattern(){
		return pattern.getText();
	}
	public String[] getSettings(){
		String[] tempString = {path.getText(), pattern.getText(), basename.getText()};
		return tempString;
	}
	public void setSettings(String[] tempString){
		path.setText(tempString[0]);
		pattern.setText(tempString[1]);
		basename.setText(tempString[2]);
	}
	public MultipleFileInputGUI getProcessingStepsPanelObject(ProcessingStepsPanel processingStepsPanelObject, MainFrame mf){
		if (processingStepsPanelObject instanceof MultipleFileInputGUI){
			MultipleFileInputGUI returnObject = new MultipleFileInputGUI(mf);
			return returnObject;
		}
		return null;
	}
	public ProcessingStepsPanel getFunction(MainFrame mf){
	return new MultipleFileInputGUI(mf);
	}
	public String getFunctionName(){
		return name;
	}

	@Override
	public void process(StormData sd1, StormData sd2) {		
		sd1.setFname(getPattern());
		sd1.setPath(getPath());		
		if (mf.isBatchprocessingUsed()){
			sd1.setBasename(sd1.getMeassurement());
			basename.setText(sd1.getMeassurement());
		}
		else {
			sd1.setBasename(basename.getText());
		}
		
//		sd1.setLocs(sd1.importData(getPath()+getPattern()));		
//		sd1.setLocs(sd1.importData(getPath()+getFilename()));
//		PropertyChangeListener pcl = new Controler();
//		Utilities.addPropertyChangeListener(pcl);
		sd1.copyStormData(Utilities.openSeries(getPath(), getPattern()));
		setProgressbarValue(100);
	}
}
