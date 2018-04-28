package core;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigInteger;

public class DivMultUnit
{
	static StringProperty input1 = new SimpleStringProperty();
	static StringProperty input2 = new SimpleStringProperty();
	static StringProperty inHi = new SimpleStringProperty();
	static StringProperty inLo = new SimpleStringProperty();
	static StringProperty write = new SimpleStringProperty();
	static StringProperty div = new SimpleStringProperty();
	static StringProperty outHi = new SimpleStringProperty();
	static StringProperty outLo = new SimpleStringProperty();
	static StringProperty active = new SimpleStringProperty();
	static StringProperty signed = new SimpleStringProperty();
	static StringProperty hi = new SimpleStringProperty();
	static StringProperty lo = new SimpleStringProperty();
	static String hiLo = null;

	static void execute()
	{
		if(write.get().equals("1"))
		{

			if(inHi.get()!=null)
				hi.set(inHi.get());

			if(inLo.get()!=null)
				lo.set(inLo.get());

		}

		if(active.get().equals("1"))
		{
			if(div.get().equals("1"))
			{
				if(signed.get().equals("1"))
				{
					hi.set(Integer.toBinaryString(Integer.parseInt(input1.get(), 2)%Integer.parseInt(input2.get(), 2)));
					lo.set(Integer.toBinaryString(Integer.parseInt(input1.get(), 2)/Integer.parseInt(input2.get(), 2)));
				}
				else
				{
					hi.set(Integer.toBinaryString(Integer.parseUnsignedInt(input1.get(), 2)%Integer.parseUnsignedInt(input2.get(), 2)));
					lo.set(Integer.toBinaryString(Integer.parseUnsignedInt(input1.get(), 2)/Integer.parseUnsignedInt(input2.get(), 2)));
				}
			}
			else
			{
				if(signed.get().equals("1"))
				{
					BigInteger multResult = new BigInteger(input1.get(),2).multiply(new BigInteger(input2.get(),2));
					if(multResult.compareTo(new BigInteger(String.valueOf(0)))==1)
						hiLo = SignExtend.extendUnsigned(multResult.toString(2), 64);
					else
						hiLo = SignExtend.extendSigned(multResult.toString(2), 64);

					hi.set(hiLo.substring(0, 32));
					lo.set(hiLo.substring(32));
				}
				else
				{
					BigInteger multResult = new BigInteger(String.valueOf(Integer.parseUnsignedInt(input1.get(),2))).multiply(new BigInteger(String.valueOf(Integer.parseUnsignedInt(input2.get(),2))));
					hiLo = SignExtend.extendUnsigned(multResult.toString(2), 64);
					hi.set(hiLo.substring(0, 32));
					lo.set(hiLo.substring(32));
				}
			}
		}
		if(!outHi.isBound())
			outHi.bind(hi);
		if(!outLo.isBound())
			outLo.bind(lo);
	}
}
