package tests.org.designwizard.design.mocks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


public class Anexo {
	
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public void getAnexo(@PathParam("id") Long id) {
		
	}
}
