import java.util.Collection;
import java.util.Set;

public interface IF extends Comparable{
	
	/**
	 * Method that returns the classes and interfaces that are'nt from java api's.
	 * @return a collection representing all classes and interfaces that are'nt from java api's.
	 */
	public Collection getNonJavaClassesAndInterfaces();
	
	/**
	 * Adds a class that was extrated from code.
	 * @param className the name of the class extracted.
	 */
	public void addClassExtracted(String className);

	/**
	 * Returns a set containing all classes extracted.
	 * @return a set containing all classes extracted.
	 * * If no classe was exttracted, this method will return a empty Set<String>;
	 */
	public Set<String> getAllClassesExtracted();
}
