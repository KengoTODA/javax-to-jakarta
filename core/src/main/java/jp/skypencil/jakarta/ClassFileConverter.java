package jp.skypencil.jakarta;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

class ClassFileConverter implements ClassFileTransformer {
  @Override
  public byte[] transform(
      ClassLoader classLoader,
      String slashedClassName,
      Class<?> aClass,
      ProtectionDomain protectionDomain,
      byte[] bytes)
      throws IllegalClassFormatException {
    if (slashedClassName.startsWith("java/") || slashedClassName.startsWith("com/sun/")) {
      return null;
    }

    ClassReader reader = new ClassReader(bytes);
    ClassWriter writer = new ClassWriter(reader, 0);
    reader.accept(new MyClassVisitor(Opcodes.ASM7, writer), 0);
    return writer.toByteArray();
  }
}
