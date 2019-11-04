package jp.skypencil.jakarta;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

final class MyMethodVisitor extends MethodVisitor {
  public MyMethodVisitor(int api, MethodVisitor mv) {
    super(api, mv);
  }

  @Override
  public void visitMethodInsn(
      final int opcode,
      String owner,
      final String name,
      final String descriptor,
      final boolean isInterface) {
    if (owner.startsWith("javax/")) {
      owner = "jakarta/" + owner.substring(6);
    }
    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
  }

  @Override
  public void visitTypeInsn(final int opcode, String type) {
    if (type.startsWith("javax/")) {
      type = "jakarta/" + type.substring(6);
    }
    mv.visitTypeInsn(opcode, type);
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
