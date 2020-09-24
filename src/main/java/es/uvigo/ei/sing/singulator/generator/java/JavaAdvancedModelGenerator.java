package es.uvigo.ei.sing.singulator.generator.java;

import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseCellLocation;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseCellMembranes;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseCellsContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseClassName;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseDiffussionRate;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseEnum;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseFeederContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseKillContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseLines;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseMoleculesArray;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseMoleculesContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseReactionContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseRibosomesContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseStaticConstructorName;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseStringArray;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseTransformContainer;
import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseTransportersContainer;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import es.uvigo.ei.sing.singulator.generator.ModelGenerator;
import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonEnvironment;
import es.uvigo.ei.sing.singulator.model.JsonEvent;
import es.uvigo.ei.sing.singulator.model.JsonFeeder;
import es.uvigo.ei.sing.singulator.model.JsonGeneralConfiguration;
import es.uvigo.ei.sing.singulator.model.JsonKill;
import es.uvigo.ei.sing.singulator.model.JsonLzw;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;
import es.uvigo.ei.sing.singulator.model.JsonReaction;
import es.uvigo.ei.sing.singulator.model.JsonRibosome;
import es.uvigo.ei.sing.singulator.model.JsonTransform;
import es.uvigo.ei.sing.singulator.model.JsonTransporter;
import es.uvigo.ei.sing.singulator.model.JsonUnity;

public class JavaAdvancedModelGenerator extends JavaModelGenerator {
	private JavaAdvancedModelGenerator() {
		super();
	}

	public static ModelGenerator javaAdvancedModelGenerator() {
		return new JavaAdvancedModelGenerator();
	}

	@Override
	protected String getModelMode() {
		return "advanced";
	}

	@Override
	protected void generateGeneral() {
		generateGeneralConfiguration();
		generateEnvironment();
		generateUnity();
	}

	private void generateGeneralConfiguration() {
		final JsonGeneralConfiguration jgc = singulator.getGeneralConfiguration();
		final InputStream is = getClass().getClassLoader()
				.getResourceAsStream("java/advanced/GeneralConfiguration.scheme");
		final Path out = Paths.get(outputDir.toString() + "/general/GeneralConfiguration.java");
		final String[] fields = new String[] { jgc.getProcCreation().toString(), jgc.getProcSim().toString(),
				jgc.getTotalTries().toString(), jgc.getDirOutput(), jgc.getFileOutput(), jgc.getSimName(),
				parseStringArray(jgc.getEmailTo()), Boolean.toString(jgc.isActivateGUI()), jgc.getReadFromCheckpoint(),
				jgc.getNumberOfJobs().toString(), jgc.getNumberOfSteps().toString(), jgc.getSaveSimEvery().toString(),
				jgc.getWriteResultsEvery().toString(), packageName };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateEnvironment() {
		final JsonEnvironment environment = singulator.getEnvironment();
		final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Environment.scheme");
		final Path out = Paths.get(outputDir.toString() + "/general/Environment.java");
		final String[] fields = new String[] { Double.toString(environment.getWidth()),
				Double.toString(environment.getHeight()), Double.toString(environment.getLenght()), packageName };
		writeToFile(parseLines(is, fields), out);

	}

	private void generateUnity() {
		final JsonUnity unity = singulator.getUnity();
		final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Unity.scheme");
		final Path out = Paths.get(outputDir.toString() + "/general/Unity.java");
		final String[] fields = new String[] { unity.getName(), Double.toString(unity.getMolecularWeight()),
				Double.toString(unity.getRadius()), parseDiffussionRate(unity.getDiffusionRate()), packageName };
		writeToFile(parseLines(is, fields), out);
	}

	@Override
	protected void generateCommon() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/SubcellularLocation.scheme");
		Path out = Paths.get(outputDir.toString() + "/common/SubcellularLocation.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/DiffusionRate.scheme");
		out = Paths.get(outputDir.toString() + "/common/DiffusionRate.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}

	@Override
	protected void generateCells() {
		final JsonCell[] cells = singulator.getCells();
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractCell.scheme");
		Path out = Paths.get(outputDir.toString() + "/cells/Cell.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractAdvancedContainer.scheme");
		out = Paths.get(outputDir.toString() + "/containers/AdvancedContainer.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/CellShape.scheme");
		out = Paths.get(outputDir.toString() + "/cells/CellShape.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/CellMembrane.scheme");
		out = Paths.get(outputDir.toString() + "/cells/CellMembrane.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

		generateCellsContainer(cells, outputDir);

		for (JsonCell cell : cells) {
			is = getClass().getClassLoader().getResourceAsStream("java/advanced/Cell.scheme");
			out = Paths.get(outputDir.toString() + "/cells/" + parseClassName(cell.getCellName()) + ".java");
			final String[] fields = new String[] { parseClassName(cell.getCellName()),
					parseStaticConstructorName(cell.getCellName()), cell.getForm().toUpperCase(),
					parseCellMembranes(cell), packageName };
			writeToFile(parseLines(is, fields), out);
		}
	}

	@Override
	protected void generateMolecules() {
		final JsonMolecule molecules = singulator.getAgents().getMolecules();

		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Agent.scheme");
		Path out = Paths.get(outputDir.toString() + "/agents/Agent.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractMolecule.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/Molecule.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractMacromolecule.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/macromolecules/Macromolecule.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/MacromoleculeType.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/macromolecules/MacromoleculeType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractLZW.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/lzws/LZW.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/LZWType.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/lzws/LZWType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractBuildingBlock.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/buildingblocks/BuildingBlock.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/BuildingBlockType.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/buildingblocks/BuildingBlockType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

		generateMoleculesContainer(molecules, outputDir);

		generateMacromolecules(molecules.getMacromolecules(), outputDir);
		generateLZWs(molecules.getLzw(), outputDir);
		generateBuildingBlocks(molecules.getBuildingBlocks(), outputDir);

	}

	private void generateMacromolecules(JsonMacromolecule[] mms, Path outputDir) {
		for (JsonMacromolecule m : mms) {
			final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Molecule.scheme");
			final Path out = Paths.get(
					outputDir.toString() + "/agents/molecules/macromolecules/" + parseClassName(m.getName()) + ".java");
			final String[] fields = new String[] { parseClassName(m.getName()), parseStaticConstructorName(m.getName()),
					Double.toString(m.getMolecularWeight()), Double.toString(m.getRadius()), m.getColor(),
					parseEnum(m.getMaxLayer()), parseEnum(m.getMinLayer()), parseCellLocation(m.getCellLocalization()),
					parseEnum(m.getLayerLocalization()), parseDiffussionRate(m.getDiffusionRate()),
					parseEnum(m.getType()), "Macromolecule", packageName, "macromolecules" };
			writeToFile(parseLines(is, fields), out);
		}
	}

	private void generateLZWs(JsonLzw[] lzws, Path outputDir) {
		for (JsonLzw m : lzws) {
			final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Molecule.scheme");
			final Path out = Paths
					.get(outputDir.toString() + "/agents/molecules/lzws/" + parseClassName(m.getName()) + ".java");
			final String[] fields = new String[] { parseClassName(m.getName()), parseStaticConstructorName(m.getName()),
					Double.toString(m.getMolecularWeight()), Double.toString(m.getRadius()), m.getColor(),
					parseEnum(m.getMaxLayer()), parseEnum(m.getMinLayer()), parseCellLocation(m.getCellLocalization()),
					parseEnum(m.getLayerLocalization()), parseDiffussionRate(m.getDiffusionRate()),
					parseEnum(m.getType()), "LZW", packageName, "lzws" };
			writeToFile(parseLines(is, fields), out);
		}
	}

	private void generateBuildingBlocks(JsonBuildingblock[] bbs, Path outputDir) {
		for (JsonBuildingblock m : bbs) {
			final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Molecule.scheme");
			final Path out = Paths.get(
					outputDir.toString() + "/agents/molecules/buildingblocks/" + parseClassName(m.getName()) + ".java");
			final String[] fields = new String[] { parseClassName(m.getName()), parseStaticConstructorName(m.getName()),
					Double.toString(m.getMolecularWeight()), Double.toString(m.getRadius()), m.getColor(),
					parseEnum(m.getMaxLayer()), parseEnum(m.getMinLayer()), parseCellLocation(m.getCellLocalization()),
					parseEnum(m.getLayerLocalization()), parseDiffussionRate(m.getDiffusionRate()),
					parseEnum(m.getType()), "BuildingBlock", packageName, "buildingblocks" };
			writeToFile(parseLines(is, fields), out);
		}
	}

	@Override
	protected void generateRibosomes() {
		final JsonRibosome[] ribosomes = singulator.getAgents().getRibosomes();
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractRibosome.scheme");
		Path out = Paths.get(outputDir.toString() + "/agents/ribosomes/Ribosome.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

		generateRibosomesContainer(ribosomes, outputDir);

		for (JsonRibosome ribosome : ribosomes) {
			is = getClass().getClassLoader().getResourceAsStream("java/advanced/Ribosome.scheme");
			out = Paths.get(outputDir.toString() + "/agents/ribosomes/" + parseClassName(ribosome.getName()) + ".java");
			final String[] fields = new String[] { parseClassName(ribosome.getName()),
					parseStaticConstructorName(ribosome.getName()), Double.toString(ribosome.getMolecularWeight()),
					Double.toString(ribosome.getRadius()), ribosome.getColor(), parseEnum(ribosome.getMaxLayer()),
					parseEnum(ribosome.getMinLayer()), parseCellLocation(ribosome.getCellLocalization()),
					parseEnum(ribosome.getLayerLocalization()), Double.toString(ribosome.getDiffusionRate()),
					ribosome.getmRNA(), packageName };
			writeToFile(parseLines(is, fields), out);
		}
	}

	@Override
	protected void generateTransporters() {
		final JsonTransporter[] transporters = singulator.getTransporters();
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractTransporter.scheme");
		Path out = Paths.get(outputDir.toString() + "/transporters/Transporter.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/TransporterType.scheme");
		out = Paths.get(outputDir.toString() + "/transporters/TransporterType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

		generateTransportersContainer(transporters, outputDir);

		for (JsonTransporter transporter : transporters) {
			is = getClass().getClassLoader().getResourceAsStream("java/advanced/Transporter.scheme");
			out = Paths.get(outputDir.toString() + "/transporters/" + parseClassName(transporter.getName()) + ".java");
			final String[] fields = new String[] { parseClassName(transporter.getName()),
					parseStaticConstructorName(transporter.getName()), parseClassName(transporter.getCellName()),
					Double.toString(transporter.getRadius()), Double.toString(transporter.getDiffusionRate()),
					transporter.getColor(), parseEnum(transporter.getOuterLayer()),
					parseEnum(transporter.getInnerLayer()), parseEnum(transporter.getGetFrom()),
					parseEnum(transporter.getPutTo()), transporter.getType().toUpperCase(),
					parseMoleculesArray(transporter.getInputs()), parseMoleculesArray(transporter.getOutputs()),
					packageName };
			writeToFile(parseLines(is, fields), out);
		}
	}

	@Override
	protected void generateFeeders() {
		final JsonFeeder[] feeders = singulator.getFeeder();
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/FeederType.scheme");
		Path out = Paths.get(outputDir.toString() + "/feeders/FeederType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/AbstractSimpleContainer.scheme");
		out = Paths.get(outputDir.toString() + "/containers/SimpleContainer.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/Feeder.scheme");
		out = Paths.get(outputDir.toString() + "/feeders/Feeder.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

		is = getClass().getClassLoader().getResourceAsStream("java/advanced/SimpleContainer.scheme");
		out = Paths.get(outputDir.toString() + "/containers/FeederContainer.java");
		final String[] fields = new String[] { "Feeder", "feeder", parseFeederContainer(feeders),
				"import java.util.Optional;\nimport " + packageName + ".cells.*;", packageName, "feeders" };
		writeToFile(parseLines(is, fields), out);
	}

	@Override
	protected void generateEvents() {
		final JsonEvent events = singulator.getEvents();
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/Event.scheme");
		Path out = Paths.get(outputDir.toString() + "/events/Event.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/EventTrigger.scheme");
		out = Paths.get(outputDir.toString() + "/events/EventTrigger.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/OnInner.scheme");
		out = Paths.get(outputDir.toString() + "/events/OnInner.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/OnRebound.scheme");
		out = Paths.get(outputDir.toString() + "/events/OnRebound.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/Kill.scheme");
		out = Paths.get(outputDir.toString() + "/events/Kill.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/Reaction.scheme");
		out = Paths.get(outputDir.toString() + "/events/Reaction.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/advanced/Transform.scheme");
		out = Paths.get(outputDir.toString() + "/events/Transform.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

		generateKills(events.getKill(), outputDir);
		generateReactions(events.getReaction(), outputDir);
		generateTransform(events.getTransform(), outputDir);

	}

	private void generateKills(final JsonKill[] kills, final Path outputDir) {
		final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/SimpleContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/KillContainer.java");

		final String[] fields = new String[] { "Kill", "kill", parseKillContainer(kills),
				"import static " + packageName + ".events.EventTrigger.eventTrigger;", packageName, "events" };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateReactions(final JsonReaction[] reactions, final Path outputDir) {
		final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/SimpleContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/ReactionContainer.java");

		final String[] fields = new String[] { "Reaction", "reaction", parseReactionContainer(reactions),
				"@SuppressWarnings(\"unchecked\")", packageName, "events" };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateTransform(final JsonTransform[] transforms, final Path outputDir) {
		final InputStream is = getClass().getClassLoader().getResourceAsStream("java/advanced/SimpleContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/TransformContainer.java");

		final String[] fields = new String[] { "Transform", "transform", parseTransformContainer(transforms),
				"import static " + packageName + ".events.EventTrigger.eventTrigger;", packageName, "events" };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateCellsContainer(final JsonCell[] cells, final Path outputDir) {
		final InputStream is = getClass().getClassLoader()
				.getResourceAsStream("java/advanced/AdvancedContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/CellContainer.java");
		final String[] fields = new String[] { "CellContainer", "Cell", "cellContainer", parseCellsContainer(cells),
				packageName, "cells" };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateTransportersContainer(final JsonTransporter[] transporters, final Path outputDir) {
		final InputStream is = getClass().getClassLoader()
				.getResourceAsStream("java/advanced/AdvancedContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/TransporterContainer.java");
		final String[] fields = new String[] { "TransporterContainer", "Transporter", "transporterContainer",
				parseTransportersContainer(transporters), packageName, "transporters" };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateRibosomesContainer(final JsonRibosome[] ribosomes, final Path outputDir) {
		final InputStream is = getClass().getClassLoader()
				.getResourceAsStream("java/advanced/AdvancedContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/RibosomeContainer.java");
		final String[] fields = new String[] { "RibosomeContainer", "Ribosome", "ribosomeContainer",
				parseRibosomesContainer(ribosomes), packageName, "agents.ribosomes" };
		writeToFile(parseLines(is, fields), out);
	}

	private void generateMoleculesContainer(final JsonMolecule molecules, final Path outputDir) {
		final InputStream is = getClass().getClassLoader()
				.getResourceAsStream("java/advanced/MoleculeContainer.scheme");
		final Path out = Paths.get(outputDir.toString() + "/containers/MoleculeContainer.java");
		final String[] fields = new String[] { parseMoleculesContainer(molecules), packageName };
		writeToFile(parseLines(is, fields), out);
	}
}
