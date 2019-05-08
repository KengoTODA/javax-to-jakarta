package jp.skypencil.jakarta;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class ClassFileConverter implements ClassFileTransformer {
	@Override
	public byte[] transform(ClassLoader classLoader, String slashedClassName, Class<?> aClass,
			ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
		if (slashedClassName.startsWith("java/") || slashedClassName.startsWith("com/sun/")) {
			return null;
		}

		ClassReader reader = new ClassReader(bytes);
		ClassWriter writer = new ClassWriter(reader, 0);
		reader.accept(new MyClassVisitor(Opcodes.ASM7, writer), 0);
		return writer.toByteArray();
	}

	static final class MyClassVisitor extends ClassVisitor {
		public MyClassVisitor(int api, ClassVisitor another) {
			super(api, another);
		}

		@Override
		public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
				final String signature, final String[] exceptions) {
			MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
			return new MyMethodVisitor(api, mv);
		}
	}

	static final class MyMethodVisitor extends MethodVisitor {
		public MyMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}

		@Override
		public void visitLdcInsn(Object value) {
			if (value instanceof Type) {
				Type type = (Type) value;
				String internalName = type.getInternalName();
				if (internalName.startsWith("javax/")) {
					value = Type.getObjectType("jakarta/" + type.getInternalName().substring(6));
				}
			}
			mv.visitLdcInsn(value);
		}
	}
}
