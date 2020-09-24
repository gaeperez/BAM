package es.uvigo.ei.sing.singulator.generator.json;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import es.uvigo.ei.sing.singulator.generator.ModelGenerator;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;
import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public class JsonModelGenerator implements ModelGenerator {

	private JsonModelGenerator() {
	}

	public static JsonModelGenerator jsonModelGenerator() {
		return new JsonModelGenerator();
	}

	@Override
	public void generateModel(final JsonSingulator jsonSingulator, final Path outputDir, final String packageName) {
		try (FileWriter file = new FileWriter(
				outputDir + "/" + jsonSingulator.getGeneralConfiguration().getFileOutput() + ".json")) {
			file.write(Functions.singulatorToJson(jsonSingulator));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
