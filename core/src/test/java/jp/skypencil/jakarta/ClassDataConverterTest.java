package jp.skypencil.jakarta;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class ClassDataConverterTest {
  @Test
  public void test()
      throws IllegalClassFormatException, IOException, NoSuchMethodException, SecurityException,
          IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    MyClassLoader classLoader = new MyClassLoader();
    byte[] classData =
        Files.readAllBytes(
            Paths.get(
                "build",
                "classes",
                "java",
                "test",
                "jp",
                "skypencil",
                "jakarta",
                "CallingJavax.class"));
    byte[] converted = new ClassDataConverter().apply(classData);
    Class<?> clazz = classLoader.defineClass("jp.skypencil.jakarta.CallingJavax", converted);
    Method method = clazz.getMethod("method");
    method.setAccessible(true);
    assertThrows(
        NoClassDefFoundError.class,
        () -> {
          try {
            method.invoke(null);
          } catch (InvocationTargetException e) {
            throw e.getCause();
          }
        });
  }
}

final class CallingJavax {
  public static String method() {
    return javax.annotation.Generated.class.getPackage().getName();
  }
}

final class MyClassLoader extends ClassLoader {
  public Class<?> defineClass(String dottedClassName, byte[] b) {
    return defineClass(dottedClassName, b, 0, b.length);
  }
}
