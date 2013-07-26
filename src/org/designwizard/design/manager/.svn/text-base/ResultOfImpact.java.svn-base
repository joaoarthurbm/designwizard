package org.designwizard.design.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Jo�o Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public class ResultOfImpact {
	
	private Map<String,List<String[]>> result;
	
	/**
	 * Creats a new <code>ResultOfImpact</code>
	 *
	 */
	public ResultOfImpact() {
		result = new HashMap<String, List<String[]>>();
	}

	/**
	 * @param entity
	 * @param trace
	 */
	void addTrace(String entity, List<String[]> trace) {
		this.result.put(entity, trace);
	}
	
	/**
	 * Returns the result of impact analisis for an <code>Entity</code> with
	 * the specified entitiName.
	 * @param entityName the name of the entity.
	 * @return the result of impact analisis for an <code>Entity</code> with
	 * the specified entitiName. If there is no result for the specified entity, an empty trace is returned.
	 */
	public List<String[]> getResult(String entityName) {
		if (this.result.get(entityName) == null) return new ArrayList<String[]>();		
		return this.result.get(entityName);
	}
	
	/**
	 * A <code>String</code> representation of the impact analisis for several numbers 
	 * of entities readed from entities file(./entities.txt).
	 */
	@Override
	public String toString() {
		StringBuffer re = new StringBuffer();
		for (String key: result.keySet()) {
			re.append("Trace for: "+key+"\n\n");
			ArrayList<String[]> l = (ArrayList<String[]>) this.result.get(key);
			
			for (String[] array: l) {
				re.append(array[0]+ " <is called by> " + array[1]+"\n");
			}
			re.append("\n\n");
		}
		return re.toString();
	}
	
}
