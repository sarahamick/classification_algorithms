import enums.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class Main {
	public static void main(String[] args) {

		boolean useKNN = false;
		if(args[0].equals("knn")) useKNN = true; //else use ID3

		//Load data and convert to Mushroom objects.
		ArrayList<Mushroom> trainingMushrooms = DataManager.LoadData();
		System.out.println("\nDataManager loading "+ trainingMushrooms.size() + " mushrooms...\n\nPartitioning into training and test sets...\n\nClassifying test set...\n");

		//partition into test and train sets
		ArrayList<Mushroom> testingMushrooms = new ArrayList<>();
		for(int i = 0; i < 1000; i++) {
			testingMushrooms.add(trainingMushrooms.get(i));
			trainingMushrooms.remove(i);
		}

		int poisonousMushes = 0;
		int edibleMushes = 0;

		for(Mushroom mushroom : testingMushrooms){
			if(mushroom.getAttributeValue(Class_Label.class).toString().equals("edible")) edibleMushes++;
			if(mushroom.getAttributeValue(Class_Label.class).toString().equals("poisonous")) poisonousMushes++;
		}

		//accuracy data
		int successes = 0;
		int failures = 0;

		if(useKNN){
			kNN kNN = new kNN(trainingMushrooms);
			for(Mushroom mushroom : testingMushrooms){
				boolean actual = getMushroomClassification(mushroom);
				boolean result = kNN.classifyMushroom(mushroom);
				if(actual == result) successes++;
				else failures++;
			}
		}
		else{ //use ID3
			ID3 ID3 = new ID3(trainingMushrooms);

			for(Mushroom mushroom : testingMushrooms){
				boolean actual = getMushroomClassification(mushroom);
				boolean result = ID3.classifyMushroom(mushroom);

				if(actual == result) successes++;
				else failures++;
			}
		}
		System.out.println("RESULTS:");
		System.out.println("Num poisonous mushrooms in test set: " + poisonousMushes + "\nNum edible mushrooms test set: " + edibleMushes + "\n");
		System.out.println("Num correctly classified: " + successes + "/" + testingMushrooms.size() + "\nNum incorrectly classified: " + failures + "/" + testingMushrooms.size() + "\n");

	}
	//returns true if edible, false if poisonous
	private static boolean getMushroomClassification(Mushroom mushroom) {
		if(mushroom.getAttributeValue(Class_Label.class).toString().equals("edible")) return true;
		else return false;
	}
}
