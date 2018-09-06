import java.util.ArrayList;
import java.util.List;

public class InfoGain {

    ID3 ID3 = new ID3();

    public Object selectNextAttribute(List<Object> attributes, List<Mushroom> data){

        //compute entry of data:
        List<Double> count = ID3.countClasses(data);
        double dataSize = (double) data.size();

        double entropy = -((count.get(0)/dataSize)*(calcBase2(count.get(0)/dataSize))) -
                ((count.get(1)/dataSize)*(calcBase2(count.get(1)/dataSize)));
        //System.out.println("Entropy: " + entropy);

        //compute information gain for each attribute:
        //TODO: fix calculation of indivInformationGain (returns craziness)

        double highestInfoGain = 0;
        Object attributeWithHighestInfoGain = null;

        for(Object attribute : attributes){

            List<Double> listOfIndivInfoReq = new ArrayList<>();
            List<Object> results = ID3.getAttributeResults(attribute);

            for(Object result : results){

                //partitian mushrooms with that result:
                List<Mushroom> resultMushrooms = new ArrayList<>();
                for(Mushroom mushroom : data){
                    Object indivResult = mushroom.getAttributeValue(attribute);
                    if (indivResult.equals(result)) resultMushrooms.add(mushroom);
                }
                double numberOfResultMushrooms = (double) resultMushrooms.size();

                if(numberOfResultMushrooms>0.0){

                    List<Double> classCounts = ID3.countClasses(resultMushrooms);
                    double edibleCount = classCounts.get(0);
                    double poisonousCount = classCounts.get(1);

                    //number of mushrooms with this result (i.e. "bell" result for Cap_Shape attribute), divided by all mushrooms in data
                    double sizePortion = (numberOfResultMushrooms/dataSize);
                    double informationRequirement = 0.0;

                    if(poisonousCount>0 && edibleCount>0) {
                        //portion of edible mushrooms divided by total mushrooms with this result (i.e. "bell), multiplied by the log2 of that number
                        double ediblePortion = (-(edibleCount/numberOfResultMushrooms)) * (calcBase2(edibleCount/numberOfResultMushrooms));

                        //portion of poisonous mushrooms divided by total mushrooms with this result (i.e. "bell), multiplied by the log2 of that number
                        double poisonousPortion = (-(poisonousCount/numberOfResultMushrooms)) * (calcBase2(poisonousCount/numberOfResultMushrooms));

                        //calculation of infoGain for the overall attribute (i.e. "Cap_Shape")
                        informationRequirement = sizePortion * (ediblePortion + poisonousPortion);
                    }

                    //System.out.println("Attribute: " + attribute);
                    //System.out.println("Information gain for " + result + ": " + informationRequirement);

                    listOfIndivInfoReq.add(informationRequirement);
                }
            }
            double attributeInfoGain = 0;
            for(Double indivInfoGain : listOfIndivInfoReq){
                attributeInfoGain = attributeInfoGain + indivInfoGain;
            }
            //added this check because I had cases of entropy = attribute information gain. Not sure if bug...
            if(entropy == attributeInfoGain){
                highestInfoGain = attributeInfoGain;
                attributeWithHighestInfoGain = attribute;
            }
            if((entropy - attributeInfoGain)>highestInfoGain){
                highestInfoGain = attributeInfoGain;
                attributeWithHighestInfoGain = attribute;
            }
        }
        return attributeWithHighestInfoGain;
    }

    public static double calcBase2(double number) {
        return Math.log(number)/Math.log(2)+1e-10;
    }
}
