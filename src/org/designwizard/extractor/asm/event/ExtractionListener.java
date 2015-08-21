package org.designwizard.extractor.asm.event;



public interface ExtractionListener  {
	
	public void classExtracted(FactEvent factEvent);
	public void relationExtracted(FactEvent factEvent);
	public void signatureExtracted(FactEvent factEvent);
	public void visibilityExtracted(FactEvent factEvent);
	public void modifiersExtracted(FactEvent factEvent);
	public void packageExtracted(FactEvent factEvent);
	public void annotationExtracted(FactEvent factEvent);
	
}