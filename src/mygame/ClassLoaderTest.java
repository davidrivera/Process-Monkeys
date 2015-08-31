/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author daniellewis
 */
public class ClassLoaderTest extends JavaClassLoader {
	public static void main(String[] args) {
		JavaClassLoader javaClassLoader = new JavaClassLoader();
		javaClassLoader.invokeClassMethod("com.jcg.MyClass", "sayHello");
	}
}