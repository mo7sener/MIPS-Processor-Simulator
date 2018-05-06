package core;

import java.util.Scanner;
import java.util.regex.Pattern;

import static core.ComponentManager.*;


public class MasterController
{
	private static int PC = 0;
	public static int offsetLines = 0;
	static Scanner scan = new Scanner(System.in);
	public static String codeFile =
									"li $t3, 12\n" +
											"li $t4, 36\n" +
											"sb $t3, 0($s1)\n" +
											"sb $t4, 1($s1)";

	public static void prepareMips()
	{
		Instruction.initialize("src/core/Instructions");
		System.out.println("Instructions initialized");
		RegisterFile.initialize("src/core/Registers");
		System.out.println("Register initialized");
		Memory.initialize();

		ComponentManager.provoke();
	}

	public static void run()
	{
		Assembler.assembleProgram(codeFile);
		ComponentManager.flowControlMux.output.set(SignExtend.extendUnsigned(Integer.toBinaryString(PC), 32));
//		System.out.println(Integer.parseUnsignedInt(Assembler.findLabel("print int").address,2) / 4);
//		InstructionMemory.showMe();
	}

	public static boolean executeStep()
	{
//		System.out.println(Integer.parseUnsignedInt(ProgramCounter.addressIn.get(),2) / 4);
		ProgramCounter.execute();
		if(Integer.parseInt(ProgramCounter.addressOut.get(),2)/4 >= InstructionMemory.inMem.size())
			return false;
		pcIncrementer.execute();
		InstructionMemory.execute();
		if(InstructionMemory.instOut.get().equals("00000000000000000000000000000000"))
		{
			switch (Integer.parseUnsignedInt(RegisterFile.findRegister("$v0").currentValue, 2))
			{
				case 1:
					System.out.print(BinaryParser.parseSigned(RegisterFile.findRegister("$a0").currentValue));
					break;
				case 4:
					System.out.print(Memory.readStringFromAddress(RegisterFile.findRegister("$a0").currentValue));
					break;
				case 5:
					RegisterFile.findRegister("$v0").setValue(Integer.toBinaryString(scan.nextInt()));
					break;
				case 8:
					Memory.saveString(scan.next(Pattern.compile(".{0," + Integer.parseUnsignedInt(RegisterFile.findRegister("$a1").currentValue) + "}?")), "this is a saved string that I donot want anyone to find please", true);
					break;
				case 10:
					return false;
				case 11:
					System.out.print((char) BinaryParser.parseUnsigned(RegisterFile.findRegister("$a0").currentValue));
					break;
				case 12:
					RegisterFile.findRegister("$v0").setValue(String.valueOf(scan.next().charAt(0)));
					break;
			}
		}
		ControlUnit.execute();
		ALUControl.execute();
		signedFlag.execute();
		writeRegisterMux.execute();
		immediateExtend.execute();
		regWriteFlag.execute();
		RegisterFile.execute();
		shamtMux.execute();
		setHiLo.execute();
		DivMultUnit.execute();
		hiLoReader.execute();
		aluSrcMux.execute();
		ALU.execute();
		eqIdentifier.execute();
		eqFlag.execute();
		equalMux.execute();
		jumpRFlag.execute();
		jumpRMux.execute();
		left2BitShifter.execute();
		branchCalculator.execute();
//		System.out.println(flowControlMux.selectBits.toString());
//		System.out.println(flowControlMux.selectBits.get(0).get() + flowControlMux.selectBits.get(1).get());
		flowControlMux.execute();
		Memory.execute();
		WordBreaker.execute();
		memReadDataMux.execute();
		dataOutMux.execute();
		memOrReg.execute();
		WordBuilder.execute();
		memWriteDataMux.execute();
		Memory.execute();
		regWriteDataMux.execute();
		writeRegisterMux.execute();
		RegisterFile.execute();
//		System.out.println(Memory.addressIn.get());
//		System.out.println("------------------------");
//		System.out.println(RegisterFile.regWrite.get());
//		System.out.println(RegisterFile.writeData.get());
//		System.out.println(Memory.readStringFromAddress(Memory.findVariable("newString").address.toString()));
//		System.out.println(Memory.findVariable("string").address);
//		System.out.println(Memory.instantiationPointer.toString());
//		System.out.println(Assembler.codeLines.get(Integer.parseInt(ProgramCounter.addressOut.get(), 2) / 4).trim());
//		System.out.println(ALU.output.get());
//		System.out.println(jumpRFlag.out.get() + equalMux.output.get());
//		System.out.println(InstructionMemory.instOut.get());
//		System.out.println(Memory.addressIn.get());
//		System.out.println(Assembler.findLabel("done").address);
//		System.out.println(Memory.memWriteFlag.get());
//		System.out.println(WordBreaker.wordIn.get());
//		System.out.println(Memory.loadByte(new Pointer(2)));
//		System.out.println(RegisterFile.findRegister("$a0").currentValue);
//		System.out.println(RegisterFile.findRegister("$s1").currentValue);
//		System.out.println(RegisterFile.findRegister("$t4").currentValue);
//		System.out.println("--------------------------------------------------------------");

		return true;
	}

	public static void executeAll()
	{
		boolean state = true;
		while(state)
			state = executeStep();
	}
}
