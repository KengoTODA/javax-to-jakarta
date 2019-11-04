package jp.skypencil.jakarta;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

class ClassFileConverter implements ClassFileTransformer {
  private final ClassDataConverter converter = new ClassDataConverter();

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

    return converter.apply(bytes);
  }
}
