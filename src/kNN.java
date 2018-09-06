import enums.Class_Label;

import java.util.ArrayList;

public class kNN {

    private ArrayList<Mushroom> data;
    private ArrayList<Object> attributes;
    private ArrayList<Mushroom> similarMushrooms;
    private static final int THRESHOLD = 22;

    public kNN(ArrayList<Mushroom> data){
        attributes = Mushroom.getAttributeList();
        this.data = data;
        similarMushrooms = new ArrayList<>();
    }
    public boolean classifyMushroom(Mushroom mushroom){
        int similarity = 0;
        for(Mushroom shroom : data){
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

    private boolean compareSimilarMushrooms(){
        int poisonousCount = 0;
        int edibleCount = 0;

        for(Mushroom mushroom : similarMushrooms){
            String classification = mushroom.getAttributeValue(Class_Label.class).toString();
            if(classification.equals("edible")) edibleCount++;
            if(classification.equals("poisonous")) poisonousCount++;
        }
        if(poisonousCount>=edibleCount) return true;
        else return false;
    }
}
