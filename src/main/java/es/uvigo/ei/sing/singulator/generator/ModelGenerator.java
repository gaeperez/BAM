package es.uvigo.ei.sing.singulator.generator;

import java.nio.file.Path;

import es.uvigo.ei.sing.singulator.model.JsonSingulator;

public interface ModelGenerator {
	public void generateModel(final JsonSingulator jsonSingulator, final Path output, final String packageName);
}
