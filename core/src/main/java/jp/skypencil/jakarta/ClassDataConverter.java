package jp.skypencil.jakarta;

import java.util.function.Function;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

class ClassDataConverter implements Function<byte[], byte[]> {

  @Override
  public byte[] apply(byte[] bytes) {
    ClassReader reader = new ClassReader(bytes);
    ClassWriter writer = new ClassWriter(reader, 0);
    reader.accept(new MyClassVisitor(Opcodes.ASM7, writer), 0);
    return writer.toByteArray();
  }
}
