package jp.skypencil.jakarta;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

final class MyClassVisitor extends ClassVisitor {
  public MyClassVisitor(int api, ClassVisitor another) {
    super(api, another);
  }

  @Override
  public MethodVisitor visitMethod(
      final int access,
      final String name,
      final String descriptor,
      final String signature,
      final String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
    return new MyMethodVisitor(api, mv);
  }
}
