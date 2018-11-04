import enums.Class_Label;
import java.util.ArrayList;

**/
@author: Sarah Amick
\*
public class kNN {

    private ArrayList<Mushroom> data;
    private ArrayList<Object> attributes;
    private ArrayList<Mushroom> similarMushrooms;
    private static final int THRESHOLD = 20;

    public kNN(ArrayList<Mushroom> data){
        attributes = Mushroom.getAttributeList();
        this.data = data;

    }
    public boolean classifyMushroom(Mushroom mushroom){
      similarMushrooms = new ArrayList<>();
      for(Mushroom shroom : data){
        int similarity = 0;
            for(Object attribute : attributes){
                if(mushroom.getAttributeValue(attribute).equals(shroom.getAttributeValue(attribute))){
                    similarity++;
                }
            }
            if(similarity>=THRESHOLD){
                similarMushrooms.add(shroom);
            }
        }
        return compareSimilarMushrooms();
    }

    //returns true if edible, false if poisonous
    private boolean compareSimilarMushrooms(){
        int poisonousCount = 0;
        int edibleCount = 0;

        for(Mushroom mushroom : similarMushrooms){
            String classification = mushroom.getAttributeValue(Class_Label.class).toString();
            if(classification.equals("edible")) edibleCount++;
            if(classification.equals("poisonous")) poisonousCount++;
        }
        if(poisonousCount>=edibleCount) return false;
        else return true;
    }
}
