import enums.Class_Label;

import java.util.ArrayList;
import java.util.List;

public class ID3 {

    public Node root;

    public boolean classifyMushroom(Mushroom mushroom) {
        String result = makePrediction(mushroom, root);

        if(!result.equals("poisonous") && !result.equals("edible")){
//            System.out.println("ERROR WITH RESULT _____________________________________________________________________");
//            System.out.println("Result: " + result);
        }

        if(result.equals("poisonous")) return true;
        else return false;
    }

    public String makePrediction(Mushroom mushroom, Node node){
        //System.out.println("______________________________________________");

        if(node.children.size()>0) {
           // System.out.println("Node test: " + node.nextAttributeToTest);
            Object testedAtributeResult = mushroom.getAttributeValue(node.nextAttributeToTest);
//            System.out.println("Test mushroom result: " + testedAtributeResult);
//            System.out.println("TestingNextAttribute");

            for(Node child : node.children){
                //System.out.println("Child result: " + child.previousTestResult);
                if(child.previousTestResult.equals(testedAtributeResult)){
                    //System.out.println("calling makePrediction again");
                    return makePrediction(mushroom, child);
                }
            }
        }
        //System.out.println("RESULT: " + node.leafClass);
        return node.leafClass;

//        System.out.println("No alike mushrooms found");
//        return "No alike mushrooms found";
    }

    public void buildTree(List<Mushroom> data){
        List<Object> attributes = Mushroom.getAttributeList();
        root = growTree(data, attributes, null);
    }

    private Node growTree(List<Mushroom> data, List<Object> attributes, Object previousTestResult) {

        Node n = new Node();
        //System.out.println("New node created");
        n.previousTestResult = previousTestResult;
        //System.out.println("New node result: " + previousTestResult);

        if (reachedLeaf(data)) {
            n.leafClass = getLeafClass(data);
            //System.out.println("Unanimous data, leaf reached with class " + n.leafClass);
            System.out.println("---------------------");
            n.print();
            return n;
        }
        if (attributes.isEmpty()) {
            n.leafClass = getLeafClass(data).toString();
            //System.out.println("Attribute list is empty, leaf reached with class: " + n.leafClass);
            System.out.println("---------------------");
            n.print();
            return n;
        }

        Object nextAttribute = selectNextAttribute(attributes, data);
       // System.out.println("Next attribute selected: " + nextAttribute);

        n.nextAttributeToTest = nextAttribute;
        attributes.remove(nextAttribute);

        //get all the results for that attribute
        List<Object> attributeResults = getAttributeResults(nextAttribute);
        n.testResults = attributeResults;

        //partitian the mushrooms by attribute result
        for (Object result : attributeResults) {
            List<Mushroom> newData = new ArrayList<>();
            for (Mushroom mushroom : data) {
                Object indivResult = mushroom.getAttributeValue(nextAttribute);
                if (indivResult.equals(result)) newData.add(mushroom);
            }
            n.addChild(growTree(newData, attributes, result));
        }
        System.out.println("---------------------");
        n.print();
        return n;
    }

    private Object selectNextAttribute(List<Object> attributes, List<Mushroom> data){

        InfoGain infoGain = new InfoGain();
        return infoGain.selectNextAttribute(attributes, data);
    }

    private class Node{
        Object previousTestResult;
        Object nextAttributeToTest;
        List<Object> testResults;
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

        public void print(){
            System.out.println("NODE: ");
            System.out.println("Previous test result: " + previousTestResult);
            System.out.println("leafClass: " + leafClass);
            System.out.println("Next attribute to test: " + nextAttributeToTest);
            System.out.println("Attribute results: " + testResults);
            System.out.println("Number of children: " + children.size());
//            System.out.println("Children: ");
//            for(Node child : children) child.print();
        }
    }

    private boolean reachedLeaf(List<Mushroom> data){
        List<Double> result = countClasses(data);

        double edibleCount = result.get(0);
        double poisonousCount = result.get(1);

        if(edibleCount == 0) return true;
        else if(poisonousCount == 0) return true;
        else return false;
    }

    private String getLeafClass(List<Mushroom> data) {
        List<Double> result = countClasses(data);

        double edibleCount = result.get(0);
        double poisonousCount = result.get(1);

        if(edibleCount>poisonousCount) return "edible";
        return "poisonous";
    }

    public List<Object> getAttributeResults(Object attribute){
        List<Object> attributeResults = new ArrayList<>();

        Object[] results = ((Class) attribute).getEnumConstants();

        for(Object result : results){
            attributeResults.add(result);
        }
        return attributeResults;
    }

    //returns a list with index 0 = edible count and index 1 = poisonous count
    public List<Double> countClasses(List<Mushroom> data){

        double edibleCount = 0;
        double poisonousCount = 0;

        for(Mushroom mushroom : data) {
            if (mushroom.getAttributeValue(Class_Label.class).equals(Class_Label.edible)) edibleCount++;
            if (mushroom.getAttributeValue(Class_Label.class).equals(Class_Label.poisonous)) poisonousCount++;
        }

        List<Double> result = new ArrayList<>();
        result.add(edibleCount);
        result.add(poisonousCount);

        return result;
    }

}
