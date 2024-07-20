import org.gephi.io.importer.api.ImportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;

import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.preview.PDFExporter;
import org.gephi.io.exporter.spi.CharacterExporter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.io.exporter.spi.GraphExporter;

import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;

import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.DirectedGraph;

import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;

import org.openide.util.Lookup;

import org.gephi.io.processor.plugin.DefaultProcessor;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
//import java.io.ByteArrayOutputStream;


//A lot of this code is taken from the gephi-toolkit-demos on their github
//Specifically, the importExport example
public class interactGephi{
	public void script(){
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();

		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		Container container;
		try{
			File file = new File(getClass().getResource("./generated_data/servant_voices.gv").toURI());
			container = importController.importFile(file);
			container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);
			container.getLoader().setAllowAutoNode(false);

		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		importController.process(container, new DefaultProcessor(), workspace);
		
		//Once we are done importing, we mess with the graph properties a bit. 
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
		ForceAtlas2 layout = new ForceAtlas2(new ForceAtlas2Builder()); 

		DirectedGraph graph = graphModel.getDirectedGraph();
		System.out.println("Nodes: " + graph.getNodeCount());
		System.out.println("Edges: " + graph.getEdgeCount());

		AutoLayout autoLayout = new AutoLayout(10, TimeUnit.SECONDS);
		autoLayout.setGraphModel(graphModel);

		layout.setGraphModel(graphModel);
		layout.resetPropertiesValues();
		layout.setLinLogMode(true);

		autoLayout.addLayout(layout, 1f);
		autoLayout.execute();



	
		
//		layout.setGraphModel(graphModel);
//		layout.resetPropertiesValues();
//		layout.setLinLogMode(true);
//
//
//		layout.initAlgo();
//
//		for(int i = 0; i < 1000 && layout.canAlgo(); i++){
//			layout.goAlgo();
//		}
//
		


		ExportController ec = Lookup.getDefault().lookup(ExportController.class);

		try {
			ec.exportFile(new File("output.png"));
			System.out.println("Ignored export");
		} catch (IOException ex){
			ex.printStackTrace();
			return;
		}

		System.out.println("Generated output");

//		Exporter exporterGraphML = ec.getExporter("graphml");
//		exporterGraphML.setWorkspace(workspace);
//		StringWriter stringWriter = new StringWriter();
//		ec.exportWriter(stringWriter, (CharacterExporter) exporterGraphML);

		
			

//		PDFExporter pdfExporter = (PDFExporter) ec.getExporter("pdf");
//		pdfExporter.setPageSize(PDRectangle.A0);
//		pdfExporter.setWorkspace(workspace);
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ec.exportStream(baos, pdfExporter);
//		byte[] pdf = baos.toByteArray();


	}
}



























