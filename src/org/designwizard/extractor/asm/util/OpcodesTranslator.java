package org.designwizard.extractor.asm.util;

import java.util.ArrayList;
import java.util.Collection;

import org.objectweb.asm.Opcodes;

public class OpcodesTranslator {
	//FIXME: código copiado do asm
	public static final String[] OPCODES;
	public static final String[] TYPES;

	static {
		String s = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,"
			+ "ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,"
			+ "FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,"
			+ "ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,"
			+ "LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,"
			+ "LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,"
			+ "LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,"
			+ "POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,"
			+ "FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,"
			+ "FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,"
			+ "ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,"
			+ "I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,"
			+ "FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,"
			+ "IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,"
			+ "IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,"
			+ "IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,"
			+ "PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,"
			+ "INVOKESTATIC,INVOKEINTERFACE,,NEW,NEWARRAY,ANEWARRAY,"
			+ "ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,"
			+ "MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
		OPCODES = new String[200];
		int i = 0;
		int j = 0;
		int l;
		while ((l = s.indexOf(',', j)) > 0) {
			OPCODES[i++] = j + 1 == l ? null : s.substring(j, l);
			j = l + 1;
		}

		s = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
		TYPES = new String[12];
		j = 0;
		i = 4;
		while ((l = s.indexOf(',', j)) > 0) {
			TYPES[i++] = s.substring(j, l);
			j = l + 1;
		}
	}
	/**
	 * Extracts the opcodes from the given access.
	 * @param int access The access of the entity.
	 * @return A <code>Collection</code> object containing a <code>String</code> representation of the opcodes
	 * extracted from the given access.
	 */
	//FIXME adicionar todos os modifiers aqui.
	public static Collection<String> extractOpcodes(int access) {
		Collection<String> returnValue = new ArrayList<String>();
		if ( (access & Opcodes.ACC_INTERFACE) != 0 ) {
			returnValue.add("INTERFACE");
		}
		if ( (access & Opcodes.ACC_ABSTRACT) != 0 ) {
			returnValue.add("ABSTRACT");
		}
		if ( (access & Opcodes.ACC_STATIC) != 0 ) {
			returnValue.add("STATIC");
		}
		if ( (access & Opcodes.ACC_FINAL) != 0 ) {
			returnValue.add("FINAL");
		}
		if ( (access & Opcodes.ACC_ANNOTATION) != 0 ) {
			returnValue.add("ANNOTATION");
		}
		return returnValue;
	}
}
