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

import org.gephi.graph.api.Column;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphView;

import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.layout.plugin.noverlap.NoverlapLayout;
import org.gephi.layout.plugin.noverlap.NoverlapLayoutBuilder;

import org.gephi.appearance.api.AppearanceController;
import org.gephi.appearance.api.AppearanceModel;
import org.gephi.appearance.api.Function;
import org.gephi.appearance.api.Partition;
import org.gephi.appearance.api.PartitionFunction;
import org.gephi.appearance.plugin.RankingElementColorTransformer;
import org.gephi.appearance.plugin.PartitionElementColorTransformer;
import org.gephi.appearance.plugin.RankingNodeSizeTransformer;
import org.gephi.appearance.plugin.palette.Palette;
import org.gephi.appearance.plugin.palette.PaletteManager;

import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder.DegreeRangeFilter;

import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.EdgeColor;
import org.gephi.preview.types.DependantOriginalColor;

import org.openide.util.Lookup;

import org.gephi.io.processor.plugin.DefaultProcessor;

import org.gephi.statistics.plugin.Modularity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
//import java.io.ByteArrayOutputStream;



//A lot of this code is taken from the gephi-toolkit-demos on their github
//Specifically, the importExport example
public class interactGephi{
	public static void main (String[] args){
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();

		//various controllers that do god knows what. cobbled together desperately from the gephi-toolkit-demos page
		
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		FilterController filterController =  Lookup.getDefault().lookup(FilterController.class);
		PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
		AppearanceController appearanceController = Lookup.getDefault().lookup(AppearanceController.class);
		AppearanceModel appearanceModel = appearanceController.getModel();
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();

		Container container;
		try{
			File file = new File(interactGephi.class.getResource("./generated_data/servant_voices.gv").toURI());
			container = importController.importFile(file);
			container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);
			container.getLoader().setAllowAutoNode(false);

		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		importController.process(container, new DefaultProcessor(), workspace);
		
		//Once we are done importing, we mess with the graph properties a bit. 


		DirectedGraph graph = graphModel.getDirectedGraph();
//		System.out.println("Nodes: " + graph.getNodeCount());
//		System.out.println("Edges: " + graph.getEdgeCount());

		//Filtering out those elements with zero degree
		DegreeRangeFilter degreeFilter = new DegreeRangeFilter();
		degreeFilter.init(graph);
		degreeFilter.setRange(new Range(1, Integer.MAX_VALUE));
		Query query = filterController.createQuery(degreeFilter);
		GraphView view = filterController.filter(query);
		graphModel.setVisibleView(view);

		
		//setting layout (initial forceatlas) set to what i shuffle on gephi
		ForceAtlas2 layout = new ForceAtlas2(new ForceAtlas2Builder()); 
		layout.setGraphModel(graphModel);
		layout.resetPropertiesValues();
		layout.setLinLogMode(true);
		layout.setScalingRatio(Double.valueOf(0.5));
		

		layout.initAlgo();
		for(int i = 0; i < 30000 && layout.canAlgo(); i++){
			layout.goAlgo();
		}
		layout.endAlgo();
		
		//running a no overlap
		NoverlapLayout noLayout = new NoverlapLayout(new NoverlapLayoutBuilder());
		noLayout.setGraphModel(graphModel);
		noLayout.resetPropertiesValues();
		noLayout.initAlgo();
		for(int i = 0; i < 3000 && noLayout.canAlgo(); i++){
			noLayout.goAlgo();
		}
		noLayout.endAlgo();

		//Something about this modularity section sets off an illegal reflective access, I don't want to deal with it
		Modularity modularity = new Modularity();
		modularity.setResolution(0.75);
		modularity.execute(graphModel);
		
		//Then, calculate the modularity to set colours
		Column modColumn = graphModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS);
		Function func2 = appearanceModel.getNodeFunction(modColumn, PartitionElementColorTransformer.class);
		Partition partition2 = ((PartitionFunction) func2).getPartition();
		System.out.println(partition2.size(graph) + " partitions found");
		Palette palette2 = PaletteManager.getInstance().generatePalette(partition2.size(graph));
		partition2.setColors(graph, palette2.getColors());
		appearanceController.transform(func2);

		
		//now, adding the other stuff
		Function degreeRanking = appearanceModel.getNodeFunction(graphModel.defaultColumns().degree(), RankingNodeSizeTransformer.class);
		RankingNodeSizeTransformer sizeTransformer = (RankingNodeSizeTransformer) degreeRanking.getTransformer();
		sizeTransformer.setMinSize(5);
		sizeTransformer.setMaxSize(20);
		appearanceController.transform(degreeRanking);




		//then, setting preview properties
	//	model.getProperties().putValue(PreviewProperty)
		model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.SHOW_EDGES, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, Float.valueOf(1f));
		model.getProperties().putValue(PreviewProperty.EDGE_OPACITY, Float.valueOf(20f));
		model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(3f));
		model.getProperties().putValue(PreviewProperty.NODE_LABEL_PROPORTIONAL_SIZE, Boolean.TRUE);
		model.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.BLACK);
		model.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.WHITE));
		model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.WHITE));

		ExportController ec = Lookup.getDefault().lookup(ExportController.class);

		try {
			ec.exportFile(new File("output.png"));
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



























