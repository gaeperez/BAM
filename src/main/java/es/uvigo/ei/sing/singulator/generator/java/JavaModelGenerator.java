package es.uvigo.ei.sing.singulator.generator.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import es.uvigo.ei.sing.singulator.generator.ModelGenerator;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

abstract class JavaModelGenerator implements ModelGenerator {
	protected Path outputDir;
	protected String packageName;
	protected JsonSingulator singulator;

	protected JavaModelGenerator() {
	}

	@Override
	public void generateModel(final JsonSingulator jsonSingulator, final Path outputDir, final String packageName) {
		this.outputDir = createDirs(outputDir.toString(), packageName);
		this.packageName = packageName;
		this.singulator = jsonSingulator;

		generateGeneral();
		generateCommon();
		generateCells();
		generateAgents();
		generateTransporters();
		generateFeeders();
		generateEvents();
	}

	protected void writeToFile(final List<String> lines, final Path output) {
		try {
			Files.write(output, lines, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void copyFile(final InputStream in, final Path target) {
		try {
			Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Path createDirs(final String outputDir, final String packageName) {
		String output = outputDir + "/java/" + getModelMode();
		final String[] dirs = packageName.split("\\.");
		for (String dir : dirs) {
			output += "/" + dir;
		}
		final List<String> create = new ArrayList<>();
		create.add(output + "/agents/molecules/buildingblocks");
		create.add(output + "/agents/molecules/lzws");
		create.add(output + "/agents/molecules/macromolecules");
		create.add(output + "/agents/ribosomes");
		create.add(output + "/cells");
		create.add(output + "/common");
		create.add(output + "/events");
		create.add(output + "/feeders");
		create.add(output + "/general");
		create.add(output + "/transporters");
		create.add(output + "/containers");
		create.forEach(d -> new File(d).mkdirs());

		return Paths.get(output);
	}

	protected void generateAgents() {
		generateMolecules();
		generateRibosomes();
	}

	protected abstract String getModelMode();

	protected abstract void generateGeneral();

	protected abstract void generateCells();

	protected abstract void generateCommon();

	protected abstract void generateRibosomes();

	protected abstract void generateMolecules();

	protected abstract void generateTransporters();

	protected abstract void generateFeeders();

	protected abstract void generateEvents();
}
