package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class MUX
{
	ArrayList<StringProperty> inputs = new ArrayList<>();
	ArrayList<StringProperty> selectBits = new ArrayList<>();
	StringProperty output = new SimpleStringProperty("00000000000000000000000000000000");
	public MUX(int n)
	{
		for(int i = 0; i < n; i ++)
		{
			selectBits.add(new SimpleStringProperty("0"));
		}
		for(int i = 0; i < Math.pow(2,n); i++)
		{
			inputs.add(new SimpleStringProperty("00000000000000000000000000000000"));
		}
	}

	void reset()
	{

	}

	public void bindInputs(StringProperty... stringProperties)
	{
		for(int i = 0; i < stringProperties.length; i++)
		{
			inputs.get(i).bind(stringProperties[i]);
		}
	}

	public void execute()
	{
		String selection = "0";
		for(StringProperty stringProperty : selectBits)
			selection += stringProperty.get().charAt(stringProperty.get().length()-1);
		output.set(inputs.get(BinaryParser.parseUnsigned(selection)).get());
	}
}
