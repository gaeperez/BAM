package es.uvigo.ei.sing.singulator.generator.java;

import static es.uvigo.ei.sing.singulator.generator.java.utils.JavaParser.parseLines;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import es.uvigo.ei.sing.singulator.generator.ModelGenerator;

public class JavaSimpleModelGenerator extends JavaModelGenerator {

	private JavaSimpleModelGenerator() {
		super();
	}

	public static ModelGenerator javaSimpleModelGenerator() {
		return new JavaSimpleModelGenerator();
	}

	@Override
	protected String getModelMode() {
		return "simple";
	}

	@Override
	protected void generateGeneral() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/GeneralConfiguration.scheme");
		Path out = Paths.get(outputDir.toString() + "/general/GeneralConfiguration.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Environment.scheme");
		out = Paths.get(outputDir.toString() + "/general/Environment.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Unity.scheme");
		out = Paths.get(outputDir.toString() + "/general/Unity.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

	}

	@Override
	protected void generateCommon() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/SubcellularLocation.scheme");
		Path out = Paths.get(outputDir.toString() + "/common/SubcellularLocation.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/DiffusionRate.scheme");
		out = Paths.get(outputDir.toString() + "/common/DiffusionRate.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}

	@Override
	protected void generateCells() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/Cell.scheme");
		Path out = Paths.get(outputDir.toString() + "/cells/Cell.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/CellShape.scheme");
		out = Paths.get(outputDir.toString() + "/cells/CellShape.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/CellMembrane.scheme");
		out = Paths.get(outputDir.toString() + "/cells/CellMembrane.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}

	@Override
	protected void generateMolecules() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/Agent.scheme");
		Path out = Paths.get(outputDir.toString() + "/agents/Agent.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Molecule.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/Molecule.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Macromolecule.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/macromolecules/Macromolecule.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/MacromoleculeType.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/macromolecules/MacromoleculeType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/LZW.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/lzws/LZW.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/LZWType.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/lzws/LZWType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/BuildingBlock.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/buildingblocks/BuildingBlock.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/BuildingBlockType.scheme");
		out = Paths.get(outputDir.toString() + "/agents/molecules/buildingblocks/BuildingBlockType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}

	@Override
	protected void generateRibosomes() {
		final InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/Ribosome.scheme");
		final Path out = Paths.get(outputDir.toString() + "/agents/ribosomes/Ribosome.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);

	}

	@Override
	protected void generateTransporters() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/Transporter.scheme");
		Path out = Paths.get(outputDir.toString() + "/transporters/Transporter.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/TransporterType.scheme");
		out = Paths.get(outputDir.toString() + "/transporters/TransporterType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}

	@Override
	protected void generateFeeders() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/FeederType.scheme");
		Path out = Paths.get(outputDir.toString() + "/feeders/FeederType.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Feeder.scheme");
		out = Paths.get(outputDir.toString() + "/feeders/Feeder.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}

	@Override
	protected void generateEvents() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("java/simple/Event.scheme");
		Path out = Paths.get(outputDir.toString() + "/events/Event.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/EventTrigger.scheme");
		out = Paths.get(outputDir.toString() + "/events/EventTrigger.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Kill.scheme");
		out = Paths.get(outputDir.toString() + "/events/Kill.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Reaction.scheme");
		out = Paths.get(outputDir.toString() + "/events/Reaction.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
		is = getClass().getClassLoader().getResourceAsStream("java/simple/Transform.scheme");
		out = Paths.get(outputDir.toString() + "/events/Transform.java");
		writeToFile(parseLines(is, new String[] { packageName }), out);
	}
}
