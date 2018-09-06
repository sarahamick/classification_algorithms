import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import enums.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

/**
 * Main class to run program from.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// First step - Load data and convert to Mushroom objects.
		ArrayList<Mushroom> trainingMushrooms = DataManager.LoadData();
		System.out.println("DataManager loaded "+trainingMushrooms.size() + " mushrooms");

		ArrayList<Mushroom> testingMushrooms = new ArrayList<>();
		for(int i = 0; i < 1000; i++) {
			testingMushrooms.add(trainingMushrooms.get(i));
			trainingMushrooms.remove(i);
		}

		List<Mushroom> smallTrainingSet = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			smallTrainingSet.add(trainingMushrooms.get(i));
		}

		boolean useKNN = false;
		boolean useID3 = true;

		int successes = 0;
		int classifiedCorrectlyEdible = 0;
		int classifiedCorrectlyPoisonous = 0;
		int failures = 0;
		int classifiedPoisonousAndWasEdible = 0;
		int classifiedEdibleAndWasPoisonous = 0;

		int poisonousCount = 0;
		int edibleCount = 0;

		if(useKNN){
			kNN kNN = new kNN(trainingMushrooms);

			for(Mushroom mushroom : testingMushrooms){

				boolean actual = getMushroomClassification(mushroom);
				boolean result = kNN.classifyMushroom(mushroom);

				if(result==true) poisonousCount++;
				if(result==false) edibleCount++;

				if(actual == result) successes++;
				else failures++;
			}
		}

		if(useID3){
			ID3 ID3 = new ID3();
			ID3.buildTree(smallTrainingSet);

			for(Mushroom mushroom : testingMushrooms){

				boolean actual = getMushroomClassification(mushroom);
				boolean result = ID3.classifyMushroom(mushroom);

				if(result==true) {
					poisonousCount++;
					if(actual == result) {
						successes++;
						classifiedCorrectlyPoisonous++;
					}
					else {
						classifiedPoisonousAndWasEdible++;
						failures++;
					}
				}

				if(result==false) {
					edibleCount++;
					if(actual == result){
						successes++;
						classifiedCorrectlyEdible++;
					}
					else {
						classifiedEdibleAndWasPoisonous++;
						failures++;
					}

				}
			}
		}

		int poisonousMushes = 0;
		int edibleMushes = 0;

		for(Mushroom mushroom : testingMushrooms){
			if(mushroom.getAttributeValue(Class_Label.class).toString().equals("edible")) edibleMushes++;
			if(mushroom.getAttributeValue(Class_Label.class).toString().equals("poisonous")) poisonousMushes++;
		}

		System.out.println("# of poisonous testing mushrooms: " + poisonousMushes);
		System.out.println("# of edible testing mushrooms: " + edibleMushes);
		System.out.println();

		System.out.println("Predicted poisonous mushrooms (true): " + poisonousCount);
		System.out.println("Prected edible mushrooms (false): " + edibleCount);
		System.out.println();

		System.out.println("SUCCESSES:");
		System.out.println("Total successes: " + successes);
		System.out.println("Successfully classfied poisonous: " + classifiedCorrectlyPoisonous);
		System.out.println("Successfully classified edible: " + classifiedCorrectlyEdible);
		System.out.println();

		System.out.println("FAILURES:");
		System.out.println("Total failures : " + failures);
		System.out.println("falsely classified: poisonous but were edible: " + classifiedPoisonousAndWasEdible);
		System.out.println("falsely classified: edible but were poisonous: " + classifiedEdibleAndWasPoisonous);
		System.out.println();

	}

	private static boolean getMushroomClassification(Mushroom mushroom) {
		if(mushroom.getAttributeValue(Class_Label.class).toString().equals("edible")) return false;
		else return true;
	}
}
