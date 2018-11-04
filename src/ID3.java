import enums.Class_Label;
import java.util.ArrayList;
import java.util.*;

**/
@author: Sarah Amick
\*
public class ID3 {

  private Node root;
  private List<Object> attributes;

  public ID3(List<Mushroom> data){
    attributes = Mushroom.getAttributeList();
    root = growTree(data, attributes);
  }

  public boolean classifyMushroom(Mushroom mushroom) {
    String result = makePrediction(mushroom, root);
    if(!result.equals("poisonous") && !result.equals("edible")) System.out.println(result);
    if(result.equals("poisonous")) return false;
    else return true;
  }

  private String makePrediction(Mushroom mushroom, Node node){
    if(node.nextAttributeToTest != null){
      Object mushroomAtributeResult = mushroom.getAttributeValue(node.nextAttributeToTest);
      for(Node child : node.children){
        if(child.previousTestResult.equals(mushroomAtributeResult)) return makePrediction(mushroom, child);
      }
    }
    return node.leafClass;
  }

  private Node growTree(List<Mushroom> data, List<Object> attributes) {
    Node n = new Node();

    if(attributes.isEmpty()) { //run out of attributes to split on
      n.leafClass = getLeafClass(data);
      return n;
    }
    else if(calculateEntropyForSet(data)==0) { //all tuples have the same label
      n.leafClass = getLeafClass(data);
      return n;
    }
    else {
      Object nextAttribute = selectNextAttribute(data, attributes);
      n.nextAttributeToTest = nextAttribute;
      attributes.remove(nextAttribute);
      //get all the different possible results for that attribute
      List<Object> nextAttributeValues = getAttributeResults(nextAttribute);
      // n.testResults = attributeResults;

        for (Object value : nextAttributeValues) {
          List<Mushroom> newData = new ArrayList<>();
          for (Mushroom mushroom : data) {
            Object indivValue = mushroom.getAttributeValue(nextAttribute);
            if (indivValue.equals(value)) newData.add(mushroom);
          }
          if(newData.isEmpty()){
            Node leaf = new Node();
            leaf.leafClass = getLeafClass(data);
            leaf.previousTestResult = value;
            leaf.parent = n;
            n.addChild(leaf);
          }
          else{
            Node child = growTree(newData, attributes);
            child.previousTestResult = value;
            child.parent = n;
            n.addChild(child);
          }
        }
        return n;
    }
  }

  private boolean reachedLeaf(List<Mushroom> data){
    int[] result = countClasses(data);
    int edibleCount = result[0];
    int poisonousCount = result[1];
    if(edibleCount == 0) return true;
    else if(poisonousCount == 0) return true;
    else return false;
  }

  private String getLeafClass(List<Mushroom> data) {
    int[] result = countClasses(data);
    int edibleCount = result[0];
    int poisonousCount = result[1];

    if(edibleCount>poisonousCount) return "edible";
    else return "poisonous";
  }

  private List<Object> getAttributeResults(Object attribute){
    List<Object> attributeResults = new ArrayList<>();
    Object[] results = ((Class) attribute).getEnumConstants();

    for(Object result : results)  attributeResults.add(result);

    return attributeResults;
  }

  //returns a list with index 0 = edible count and index 1 = poisonous count
  private int[] countClasses(List<Mushroom> data){

    int edibleCount = 0;
    int poisonousCount = 0;

    for(Mushroom mushroom : data) {
      if (mushroom.getAttributeValue(Class_Label.class).equals(Class_Label.edible)) edibleCount++;
      else poisonousCount++;
    }

    int[] result = new int[2];
    result[0] = edibleCount;
    result[1] = poisonousCount;

    return result;
  }

  private class Node{
    Object previousTestResult;
    Object nextAttributeToTest;
    List<Object> testResults;
    Node parent;
    List<Node> children;
    String leafClass;

    public Node(){
      testResults = new ArrayList<>();
      children = new ArrayList<>();
      leafClass = "";
      nextAttributeToTest = null;
    }

    public void addChild(Node node){
      children.add(node);
    }
  }

  //IMPLEMENTATION OF INFORMATION GAIN-----------------------------------------

  private Object selectNextAttribute(List<Mushroom> data, List<Object> attributes){

    double entropy = calculateEntropyForSet(data);

    //compute information gain for each attribute:
    double highestInfoGain = 0;
    Object attributeWithHighestInfoGain = null;

    for(Object attribute : attributes){

        double infoForAttribute = 0;
        List<Object> results = getAttributeResults(attribute);

        for(Object result : results){
            //partitian mushrooms with that result:
            List<Mushroom> resultMushrooms = new ArrayList<>();
            for(Mushroom mushroom : data){
                Object indivResult = mushroom.getAttributeValue(attribute);
                if (indivResult.equals(result)) resultMushrooms.add(mushroom);
              }

            int[] classCounts = countClasses(resultMushrooms);
            double edibleCount = (double) classCounts[0];
            double poisonousCount = (double) classCounts[1];
            double total = edibleCount + poisonousCount;

            //number of mushrooms with this result (i.e. "bell" result for Cap_Shape attribute), divided by all mushrooms in data
            double sizePortion = (total/(double)data.size());
            double informationRequirement = 0.0;

            double ediblePortion = 0;
            double poisonousPortion = 0;
            if(edibleCount>0) {
              //portion of edible mushrooms divided by total mushrooms with this result (i.e. "bell), multiplied by the log2 of that number
              ediblePortion = (-(edibleCount/total)) * (calcBase2(edibleCount/total));
            }
            if(poisonousCount>0){
              //portion of poisonous mushrooms divided by total mushrooms with this result (i.e. "bell), multiplied by the log2 of that number
              poisonousPortion = (-(poisonousCount/total)) * (calcBase2(poisonousCount/total));
            }
            informationRequirement = sizePortion * (ediblePortion + poisonousPortion);
            infoForAttribute += informationRequirement;
        }
        double infoGain = entropy - infoForAttribute;

        if(infoGain>highestInfoGain){
            highestInfoGain = infoGain;
            attributeWithHighestInfoGain = attribute;
        }
    }
    return attributeWithHighestInfoGain;
  }

  private double calculateEntropyForSet(List<Mushroom> dataset){
    //compute entropy of entire data set:
    double size = (double) dataset.size();
    int[] count = countClasses(dataset);
    double edible = (double) count[0];
    double poisonous = (double) count[1];

    if(poisonous == 0.0 || edible == 0.0) return 0.0;

    return (-(poisonous / size) * calcBase2(poisonous / size)) +
            (-(edible / size) * calcBase2(edible / size));
  }

  private static double calcBase2(double number) {
      return Math.log(number) / Math.log(2);
  }

}
