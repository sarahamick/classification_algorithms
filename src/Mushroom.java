import java.util.ArrayList;
import java.util.List;
import enums.*;


/**
 * The Mushroom class is used to contain the data for each mushroom found in the datafile.
 * More info on each attribute in agaricus-lepiotaexplanation.txt.
 *
 */
public class Mushroom {
	
	/**
	 * Returns a list (as .class objects) of the different attribute types.
	 * Note that the class label is not added to this list of attributes.
	 * @return
	 */
	public static ArrayList<Object> getAttributeList()
	{
		ArrayList<Object> attributes = new ArrayList<Object>();
		attributes.add(Cap_Shape.class);
		attributes.add(Cap_Surface.class);
		attributes.add(Cap_Color.class);
		attributes.add(Bruises.class);
		attributes.add(Odor.class);
		attributes.add(Gill_Attachment.class);
		attributes.add(Gill_Spacing.class);
		attributes.add(Gill_Size.class);
		attributes.add(Gill_Color.class);
		attributes.add(Stalk_Shape.class);
		attributes.add(Stalk_Root.class);
		attributes.add(Stalk_Surface_Above_Ring.class);
		attributes.add(Stalk_Surface_Below_Ring.class);
		attributes.add(Stalk_Color_Above_Ring.class);
		attributes.add(Stalk_Color_Below_Ring.class);
		attributes.add(Veil_Type.class);
		attributes.add(Veil_Color.class);
		attributes.add(Ring_Number.class);
		attributes.add(Ring_Type.class);
		attributes.add(Spore_Print_Color.class);
		attributes.add(Population.class);
		attributes.add(Habitat.class);
		
		return attributes;
	}

	/**
	 * The attribute to built a classifier for. 
	 * I.e. whether or not the mushroom is poisonous. 
	 */
	public Class_Label m_Class;
	
	public Cap_Shape m_cap_shape;
	
	public Cap_Surface m_cap_surface;
	
	public Cap_Color m_cap_color;
	
	public Bruises m_bruises;
	
	public Odor m_odor;
	
	public Gill_Attachment m_gill_attach;
	
	public Gill_Spacing m_gill_spacing;
	
	public Gill_Size m_gill_size;
	
	public Gill_Color m_gill_color;
	
	public Stalk_Shape m_stalk_shape;
	
	public Stalk_Root m_stalk_root;
	
	public Stalk_Surface_Above_Ring m_stalk_surface_above;
	
	public Stalk_Surface_Below_Ring m_stalk_surface_below;
	
	public Stalk_Color_Above_Ring m_stalk_color_above;
	
	public Stalk_Color_Below_Ring m_stalk_color_below;
	
	public Veil_Type m_veil_type;
	
	public Veil_Color m_veil_color;
	
	public Ring_Number m_ring_number;
	
	public Ring_Type m_ring_type;
	
	public Spore_Print_Color m_spore_color;
		
	public Population m_population;
	
	public Habitat m_habitat;
	
	/***
	 * Returns the value of an Attribute based on its .class type object.
	 * @param Attribute .class type object of its type
	 * @return
	 */
	public Object getAttributeValue(Object Attribute)
	{
		if(Attribute.equals(Class_Label.class))
		{
			return this.m_Class;
		}
		
		if(Attribute.equals(Cap_Shape.class))
		{
			return this.m_cap_shape;
		}
		if(Attribute.equals(Cap_Surface.class))
		{
			return this.m_cap_surface;
		}
		if(Attribute.equals(Cap_Color.class))
		{
			return this.m_cap_color;
		}
		if(Attribute.equals(Bruises.class))
		{
			return this.m_bruises;
		}
		if(Attribute.equals(Odor.class))
		{
			return this.m_odor;
		}
		if(Attribute.equals(Gill_Attachment.class))
		{
			return this.m_gill_attach;
		}
		if(Attribute.equals(Gill_Spacing.class))
		{
			return this.m_gill_spacing;
		}
		if(Attribute.equals(Gill_Size.class))
		{
			return this.m_gill_size;
		}
		if(Attribute.equals(Gill_Color.class))
		{
			return this.m_gill_color;
		}
		if(Attribute.equals(Stalk_Shape.class))
		{
			return this.m_stalk_shape;
		}
		if(Attribute.equals(Stalk_Root.class))
		{
			return this.m_stalk_root;
		}
		if(Attribute.equals(Stalk_Surface_Above_Ring.class))
		{
			return this.m_stalk_surface_above;
		}
		if(Attribute.equals(Stalk_Surface_Below_Ring.class))
		{
			return this.m_stalk_surface_below;
		}
		if(Attribute.equals(Stalk_Color_Above_Ring.class))
		{
			return this.m_stalk_color_above;
		}
		if(Attribute.equals(Stalk_Color_Below_Ring.class))
		{
			return this.m_stalk_color_below;
		}
		if(Attribute.equals(Veil_Type.class))
		{
			return this.m_veil_type;
		}
		if(Attribute.equals(Veil_Color.class))
		{
			return this.m_veil_color;
		}
		if(Attribute.equals(Ring_Number.class))
		{
			return this.m_ring_number;
		}
		if(Attribute.equals(Ring_Type.class))
		{
			return this.m_ring_type;
		}
		if(Attribute.equals(Spore_Print_Color.class))
		{
			return this.m_spore_color;
		}
		if(Attribute.equals(Population.class))
		{
			return this.m_population;
		}
		if(Attribute.equals(Habitat.class))
		{
			return this.m_habitat;
		}
		
		return null; //If we get down here something is wrong;
	}
}
