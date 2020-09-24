package es.uvigo.ei.sing.singulator.generator.java.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.uvigo.ei.sing.singulator.model.JsonBuildingblock;
import es.uvigo.ei.sing.singulator.model.JsonCell;
import es.uvigo.ei.sing.singulator.model.JsonDiffusionRate;
import es.uvigo.ei.sing.singulator.model.JsonFeeder;
import es.uvigo.ei.sing.singulator.model.JsonKill;
import es.uvigo.ei.sing.singulator.model.JsonLayer;
import es.uvigo.ei.sing.singulator.model.JsonLzw;
import es.uvigo.ei.sing.singulator.model.JsonMacromolecule;
import es.uvigo.ei.sing.singulator.model.JsonMolecule;
import es.uvigo.ei.sing.singulator.model.JsonReaction;
import es.uvigo.ei.sing.singulator.model.JsonRibosome;
import es.uvigo.ei.sing.singulator.model.JsonTransform;
import es.uvigo.ei.sing.singulator.model.JsonTransporter;

public final class JavaParser {

	public static List<String> parseLines(final InputStream is, final String[] fields) {
		final List<String> lines = new ArrayList<String>();
		try (final BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(matchPattern(line, fields));
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	private static String matchPattern(String line, String[] fields) {
		final Matcher matcher = Pattern.compile("\\$([^$]*)\\$").matcher(line);
		final StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, fields[Integer.parseInt(matcher.group(1)) - 1]);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String parseClassName(final String name) {
		final String upper = name.substring(0, 1).toUpperCase() + name.substring(1);
		final String result = upper.replaceAll("\\s+", "");
		return result;
	}

	public static String parseStaticConstructorName(final String name) {
		final String upper = name.substring(0, 1).toLowerCase() + name.substring(1);
		final String result = upper.replaceAll("\\s+", "");
		return result;
	}

	public static String parseStringArray(final String[] strings) {
		String results = "";
		for (int i = 0; i < strings.length; i++) {
			if (i != 0)
				results += ",";
			results += "\"" + strings[i] + "\"";
		}
		return results;
	}

	public static String parseDiffussionRate(final JsonDiffusionRate jdf) {
		return jdf.getExterior() + ", " + jdf.getOuterMembrane() + ", " + jdf.getOuterPeriplasm() + ", "
				+ jdf.getPeptidoglycan() + ", " + jdf.getInnerPeriplasm() + ", " + jdf.getInnerMembrane() + ", "
				+ jdf.getCytoplasm();
	}

	public static String parseCellMembranes(final JsonCell cell) {
		String layers = "cellMembrane(SubcellularLocation." + parseEnum(cell.getLayerName()) + ", " + cell.getRadius()
				+ ", " + "" + cell.getHeight() + ", \"" + cell.getColor() + "\")";
		for (final JsonLayer layer : cell.getLayers()) {
			layers += ", " + parseCellMembrane(layer) + "";
		}
		return layers;
	}

	public static String parseCellMembrane(final JsonLayer layer) {
		return "cellMembrane(SubcellularLocation." + parseEnum(layer.getName()) + ", " + layer.getRadius() + ", " + ""
				+ layer.getHeight() + ", \"" + layer.getColor() + "\")";
	}

	public static String parseEnum(final String enumerated) {
		// FIXME
		if (enumerated.equals("Carbohydrate (polysaccharide)"))
			return "CARBOHYDRATE";
		else
			return enumerated.toUpperCase().replaceAll("\\s+", "_");
	}

	public static String parseCellLocation(final String location) {
		if (location.equals("extracellular"))
			return "Optional.empty()";
		else
			return "Optional.of(" + parseClassName(location) + ".class)";
	}

	public static String parseMaxConcentration(final JsonFeeder feeder) {
		if (feeder.getType().equals("Continuous"))
			return "Optional.empty()";
		else
			return "Optional.of(" + feeder.getMaxConcentration() + ")";
	}

	public static String parseMoleculesArray(final String[] array) {
		String molecules = "";
		for (int i = 0; i < array.length; i++) {
			if (i > 0)
				molecules += ", ";
			molecules += parseClassName(array[i]) + ".class";
		}
		return molecules;
	}

	public static String parseFeederOnInner(final String inner) {
		final String[] innerArray = inner.split(",");
		return parseClassName(innerArray[0]) + ".class, SubcellularLocation." + parseEnum(innerArray[1]);
	}

	public static String parseEventTrigger(final String onInner, final String onRebound) {
		String trigger = "";
		if (onInner.equals(""))
			trigger += "OnInner.withNone(), ";
		else
			trigger += "OnInner.with(SubcellularLocation." + parseEnum(onInner) + "),";

		if (onRebound.equals("")) {
			trigger += "OnRebound.withNone()";
		} else {
			if (onRebound.equals("outer membrane") || onRebound.equals("outer periplasm")
					|| onRebound.equals("cytoplasm") || onRebound.equals("extracellular")) {
				trigger += "OnRebound.withMembrane(SubcellularLocation." + parseEnum(onRebound) + ")";
			} else {
				trigger += "OnRebound.withMolecule(" + parseClassName(onRebound) + ".class)";
			}
		}
		return trigger;
	}

	public static String parseCellsContainer(final JsonCell[] cells) {
		String container = "";
		for (JsonCell cell : cells) {
			container += "		final List<" + parseClassName(cell.getCellName()) + "> "
					+ parseStaticConstructorName(cell.getCellName()) + " = feed(" + parseClassName(cell.getCellName())
					+ ".class, " + cell.getNumber() + ");\n" + "		list.addAll("
					+ parseStaticConstructorName(cell.getCellName()) + ");\n" + "		map.put("
					+ parseClassName(cell.getCellName()) + ".class, " + parseStaticConstructorName(cell.getCellName())
					+ ");\n\n";
		}
		return container;
	}

	public static String parseTransportersContainer(final JsonTransporter[] transporters) {
		String container = "";
		for (JsonTransporter transporter : transporters) {
			container += "		final List<" + parseClassName(transporter.getName()) + "> "
					+ parseStaticConstructorName(transporter.getName()) + " = feed("
					+ parseClassName(transporter.getName()) + ".class, " + transporter.getNumber() + ");\n"
					+ "		list.addAll(" + parseStaticConstructorName(transporter.getName()) + ");\n"
					+ "		map.put(" + parseClassName(transporter.getName()) + ".class, "
					+ parseStaticConstructorName(transporter.getName()) + ");\n\n";
		}
		return container;
	}

	public static String parseRibosomesContainer(final JsonRibosome[] ribosomes) {
		String container = "";
		for (JsonRibosome ribosome : ribosomes) {
			container += "		final List<" + parseClassName(ribosome.getName()) + "> "
					+ parseStaticConstructorName(ribosome.getName()) + " = feed(" + parseClassName(ribosome.getName())
					+ ".class, " + ribosome.getNumber() + ");\n" + "		list.addAll("
					+ parseStaticConstructorName(ribosome.getName()) + ");\n" + "		map.put("
					+ parseClassName(ribosome.getName()) + ".class, " + parseStaticConstructorName(ribosome.getName())
					+ ");\n\n";
		}
		return container;
	}

	public static String parseMoleculesContainer(final JsonMolecule molecules) {
		String container = "";

		final JsonMacromolecule[] macromolecules = molecules.getMacromolecules();
		final JsonBuildingblock[] buildingBlocks = molecules.getBuildingBlocks();
		final JsonLzw[] lzws = molecules.getLzw();

		for (JsonMacromolecule macromolecule : macromolecules) {
			container += "		final List<" + parseClassName(macromolecule.getName()) + "> "
					+ parseStaticConstructorName(macromolecule.getName()) + " = feed("
					+ parseClassName(macromolecule.getName()) + ".class, " + macromolecule.getNumber() + ");\n"
					+ "		macromolecules.addAll(" + parseStaticConstructorName(macromolecule.getName()) + ");\n"
					+ "		map.put(" + parseClassName(macromolecule.getName()) + ".class, "
					+ parseStaticConstructorName(macromolecule.getName()) + ");\n\n";
		}

		for (JsonBuildingblock buildingBlock : buildingBlocks) {
			container += "		final List<" + parseClassName(buildingBlock.getName()) + "> "
					+ parseStaticConstructorName(buildingBlock.getName()) + " = feed("
					+ parseClassName(buildingBlock.getName()) + ".class, " + buildingBlock.getNumber() + ");\n"
					+ "		buildingBlocks.addAll(" + parseStaticConstructorName(buildingBlock.getName()) + ");\n"
					+ "		map.put(" + parseClassName(buildingBlock.getName()) + ".class, "
					+ parseStaticConstructorName(buildingBlock.getName()) + ");\n\n";
		}

		for (JsonLzw lzw : lzws) {
			container += "		final List<" + parseClassName(lzw.getName()) + "> "
					+ parseStaticConstructorName(lzw.getName()) + " = feed(" + parseClassName(lzw.getName())
					+ ".class, " + lzw.getNumber() + ");\n" + "		lzws.addAll("
					+ parseStaticConstructorName(lzw.getName()) + ");\n" + "		map.put("
					+ parseClassName(lzw.getName()) + ".class, " + parseStaticConstructorName(lzw.getName()) + ");\n\n";
		}

		return container;
	}

	public static String parseFeederContainer(final JsonFeeder[] feeders) {
		String container = "";
		for (JsonFeeder feeder : feeders) {
			container += "feeder(";
			container += parseClassName(feeder.getCreate()) + ".class, ";
			container += "FeederType." + parseEnum(feeder.getType()) + ", ";
			container += String.valueOf(feeder.getEveryStep()) + ", ";
			container += String.valueOf(feeder.getProductionNumber()) + ", ";
			container += parseCellLocation(feeder.getLocation()) + ", ";
			container += parseMaxConcentration(feeder) + "), ";
		}

		return container.substring(0, container.length() - 2);
	}

	public static String parseKillContainer(final JsonKill[] kills) {
		String container = "";
		for (JsonKill kill : kills) {
			container += "kill(";
			container += parseClassName(kill.getInput()) + ".class, ";
			container += "eventTrigger(" + parseEventTrigger(kill.getOnInnerWith(), kill.getOnReboundWith()) + ")), ";
		}

		return container.substring(0, container.length() - 2);
	}

	public static String parseTransformContainer(final JsonTransform[] transforms) {
		String container = "";
		for (JsonTransform transform : transforms) {
			container += "transform(";
			container += parseClassName(transform.getTo()) + ".class, ";
			container += parseClassName(transform.getFrom()) + ".class, ";
			container += "eventTrigger(" + parseEventTrigger(transform.getOnInnerWith(), transform.getOnReboundWith())
					+ ")), ";
		}

		return container.substring(0, container.length() - 2);
	}

	public static String parseReactionContainer(final JsonReaction[] reactions) {
		String container = "";
		for (JsonReaction reaction : reactions) {
			container += "reaction(";
			container += parseClassName(reaction.getOnCollision()[0]) + ".class, ";
			container += parseClassName(reaction.getOnCollision()[1]) + ".class, ";
			container += "(Class<? extends Macromolecule>[]) new Object[]{" + parseMoleculesArray(reaction.getOutput())
					+ "}, ";
			container += Double.toString(reaction.getKm()) + ", ";
			container += reaction.getKcat().toString() + "), ";
		}

		return container.substring(0, container.length() - 2);
	}
}
