package core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class InstructionMemory
{
	static StringProperty addressIn = new SimpleStringProperty("00000000000000000000000000000000");
	static StringProperty instOut = new SimpleStringProperty("00000000000000000000000000000000");

	static void bindIn(StringProperty stringProperty)
	{
		addressIn.bind(stringProperty);
	}

	static void execute()
	{
		if(inMem.size() - 1 >= Integer.parseUnsignedInt(addressIn.get(), 2))
			instOut.set(read(Integer.parseUnsignedInt(addressIn.get(), 2)));
	}
	public static ArrayList<String> inMem = new ArrayList<>();

	public static void add(String ins)
	{
		inMem.add(ins);
	}
	public static String read(int address)
	{
			return inMem.get(address/4);
	}
	public static void reset()
	{
		inMem.clear();
	}
}
