package view;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import controller.AlgorithmController;
import controller.MapDataLoader;
import controller.ParkController;
import model.Graph;
import model.Station;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class MapViewer 
{
	private JFrame frame;
	private JPanel mapPanel;
	private JPanel controlPanel;
	private JPanel stationNamePanel;
	private JMapViewer _map;
	
	private JTextField txtTimer;
	private JTextField txtFrom;
	private JTextField txtTo;
	private JTextField txtWeight;
	private JTextField txtAddress;
	private JTextField txtEnvironment;
	
	private JButton primButton;
	private JButton kruskalButton;
	private JButton clearButton;
	private JButton loadButton;
	private JButton loadPathButton;
	
	private boolean arePathsDrawn;
	
	private AlgorithmController AController;
	private ParkController PController;
	private MapDrawer drawer;
	private MapDataLoader loader;
	private String inputStationName;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					MapViewer window = new MapViewer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MapViewer() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame("Generador de senderos con mínimo impacto ambiental");
		frame.setBounds(100, 100, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		mapPanel = new JPanel();
		mapPanel.setBounds(10, 10, 720, 670);
		frame.getContentPane().add(mapPanel);
		
		_map = new JMapViewer();
		_map.setDisplayPosition(new Coordinate(-40.83333333,-71.61666667), 15);
		_map.setSize(720, 670); 
		mapPanel.add(_map);

		controlPanel = new JPanel();
		controlPanel.setBounds(740, 10, 320, 670);
		frame.getContentPane().add(controlPanel);		
		controlPanel.setLayout(null);

		stationNamePanel = new JPanel();
		stationNamePanel.setBounds(10, 10, 100, 70);
		stationNamePanel.setVisible(true);
		frame.getContentPane().add(stationNamePanel);
		
		kruskalButton = createButton("Opción 1", 0, 280, 195, 30, controlPanel);
		kruskalButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		kruskalButton.setEnabled(false);

		primButton = createButton("Opción 2", 0, 330, 195, 30, controlPanel);
		primButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		primButton.setEnabled(false);

		clearButton = createButton("Borrar Mapa", 0, 505, 195, 30, controlPanel);
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

		loadButton = createButton("Cargar", 195, 195, 85, 25, controlPanel);
		loadButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

		loadPathButton = createButton("Cargar Sendero", 80, 125, 130, 25, controlPanel);
		loadPathButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

		txtTimer = createTextField(0, 380, 195, 40, controlPanel);
		txtTimer.setEditable(false);
		txtTimer.setVisible(false);
		txtTimer.setFont(new Font("Tahoma", Font.BOLD, 12));

		txtFrom = createTextField(0, 90, 90, 25, controlPanel);
		txtTo = createTextField(100, 90, 90, 25, controlPanel);
		txtWeight = createTextField(200, 90, 90, 25, controlPanel);
		txtAddress = createTextField(0, 195, 180, 25, controlPanel);
		txtAddress.setText("src/FileReader/mapa1.xml");
		
		txtEnvironment = createTextField(0, 430, 195, 40, controlPanel);
		txtEnvironment.setEditable(false);
		txtEnvironment.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtEnvironment.setVisible(false);
		
		JLabel lblInstructions = createLabel("1)Cliquee el mapa para las coordenadas", 0, 15, 270, 25, new Font("Tahoma", Font.BOLD, 12), controlPanel);
		JLabel lblGenerateTrails = createLabel("Generar Senderos Óptimos:", 0, 250, 290, 25, new Font("Tahoma", Font.BOLD, 12), controlPanel);
		JLabel lblAddEdge = createLabel("2)Agregar Sendero:", 0, 50, 150, 20, new Font("Tahoma", Font.BOLD, 12), controlPanel);
		JLabel lblFrom = createLabel("Estación A", 0, 70, 80, 20, new Font("Tahoma", Font.PLAIN, 12), controlPanel);
		JLabel lblTo = createLabel("Estación B", 100, 70, 80, 20, new Font("Tahoma", Font.PLAIN, 12), controlPanel);
		JLabel lblWeight = createLabel("Impacto ambiental", 200, 70, 110, 20, new Font("Tahoma", Font.PLAIN, 12), controlPanel);
		JLabel lblAddPark = createLabel("Agregar parque mediante archivo:", 0, 165, 250, 25, new Font("Tahoma", Font.BOLD, 12), controlPanel);

		drawer = new MapDrawer(_map);
		PController = new ParkController();
		
		_map.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        Coordinate clickedCoord = (Coordinate) _map.getPosition(e.getPoint());
		        showStationNamePanel(clickedCoord);
		        kruskalButton.setEnabled(true);
		        primButton.setEnabled(true);
		    }
		});
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String address = txtAddress.getText();
				loader = new MapDataLoader(address);
				if (address.isEmpty()) {
					JOptionPane.showMessageDialog(_map, "No se puede encontrar ruta", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				loader.loadPark(address);
				if (loader.getPark() == null) {
					JOptionPane.showMessageDialog(_map, loader.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}else {
					drawer.clearMap();
					PController.addNewParkFromFile(loader.getPark());
					
					drawer.drawPark(PController.getPark());
					kruskalButton.setEnabled(true);
				    primButton.setEnabled(true);
					kruskalButton.setVisible(true);
					primButton.setVisible(true);
					
					AController = new AlgorithmController();
				}
				
				
			}
		});
		
		loadPathButton.addActionListener(new ActionListener() {
			int from;
			int to;
			int weight;
			
			public void actionPerformed(ActionEvent e) {
				from = Integer.parseInt(txtFrom.getText());
				to = Integer.parseInt(txtTo.getText());
				weight = Integer.parseInt(txtWeight.getText());
				PController.addNewPath(from-1, to-1, weight);
				drawer.drawPark(PController.getPark());
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawer.clearMap();
				if (PController.parkExists()) {
					PController.removePark();
				}
				
				primButton.setEnabled(false);
				kruskalButton.setEnabled(false);
			}
		});
		
		kruskalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				drawer.clearMap();
				Graph mstGraph = AController.doMSTWithKruskal(PController.getGraph());
				
				double time = AController.getKruskalTime(mstGraph);
				List<Station> originalStations = PController.getStationList(); 
				PController.removePark();
				PController.createNewParkFromGraph(mstGraph,originalStations);
				
				drawer.drawPark(PController.getPark());
				int ed = PController.calculateEnviromentalDamage();
				placeAlgorithmSummaryOnScreen(time,ed);
			}
		});
		
		primButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawer.clearMap();
				Graph mstGraph = AController.doMSTWithPrim(PController.getGraph());
				
				double time = AController.getKruskalTime(mstGraph);
				List<Station> originalStations = PController.getStationList(); 
				PController.removePark();
				PController.createNewParkFromGraph(mstGraph,originalStations);
				
				drawer.drawPark(PController.getPark());
				int ed = PController.calculateEnviromentalDamage();
				placeAlgorithmSummaryOnScreen(time,ed);
				
			}
		});

	
		
		/*
		//Hacemos Kruskal y Prim de antemano por temas del compilador de Java.
		doMSTwithKruskal();
		clearMap();
		doMSTwithPrim();
		clearMap();
		*/
	}
	private JLabel createLabel(String text, int x, int y, int width, int height, Font font, JPanel panel) {
	    JLabel label = new JLabel(text);
	    label.setFont(font);
	    label.setBounds(x, y, width, height);
	    panel.add(label);
	    return label;
	}
	
	private JButton createButton(String text, int x, int y, int width, int height, JPanel panel) {
	    JButton button = new JButton(text);
	    button.setBounds(x, y, width, height);
	    panel.add(button);
	    return button;
	}
	
	private JTextField createTextField(int x, int y, int width, int height, JPanel panel) {
	    JTextField textField = new JTextField();
	    textField.setBounds(x, y, width, height);
	    textField.setColumns(10);
	    panel.add(textField);
	    return textField;
	}
	private void placeAlgorithmSummaryOnScreen(Double time, int ed) {
		txtTimer.setText("    Tiempo Total: " + time + " segs.");
		txtTimer.setVisible(true);
		txtEnvironment.setText("   Impacto Ambiental: " + ed);
		txtEnvironment.setVisible(true);
		
	}
	
	private void showStationNamePanel(Coordinate coord) {

	    mapPanel.setEnabled(false);
	    controlPanel.setEnabled(false);

	    JPanel overlayPanel = new JPanel();
	    overlayPanel.setLayout(null);
	    overlayPanel.setBackground(new Color(100, 100, 100));
	    overlayPanel.setBounds(200, 200, frame.getWidth() / 2, frame.getHeight() / 2);

	    JPanel inputPanel = new JPanel();
	    inputPanel.setLayout(null);
	    inputPanel.setBounds(75, 100, 400, 150);
	    inputPanel.setBackground(Color.WHITE);
	    
	    JLabel label = createLabel("Nombre de la estación:", 20, 20, 200, 20, new Font("Arial", Font.PLAIN, 14), inputPanel);
	    JTextField inputField = createTextField(20, 50, 360, 25,inputPanel);
	    JButton aceptarButton = createButton("Aceptar", 70, 90, 100, 25, inputPanel);
	    JButton cancelarButton = createButton("Cancelar", 220, 90, 100, 25, inputPanel);

	    overlayPanel.add(inputPanel);
	    frame.getLayeredPane().add(overlayPanel, JLayeredPane.MODAL_LAYER);
	    frame.getLayeredPane().repaint();

	    aceptarButton.addActionListener(e -> {
	        inputStationName = inputField.getText();

	        if (PController.parkExists()) {
	            AController = new AlgorithmController();
	            PController.addNewStation(inputStationName, coord);
	        } else {
	            PController.createNewPark();
	            AController = new AlgorithmController();
	            PController.addNewStation(inputStationName, coord);
	            
	            //hago kruskal y prim por primera vez para sacar el valor erroneo del principio
	       
	        }

	        drawer.drawPark(PController.getPark());
	        frame.getLayeredPane().remove(overlayPanel);
	        frame.getLayeredPane().repaint();
	    });

	    cancelarButton.addActionListener(e -> {
	        frame.getLayeredPane().remove(overlayPanel);
	        frame.getLayeredPane().repaint();
	    });
	}
}
		