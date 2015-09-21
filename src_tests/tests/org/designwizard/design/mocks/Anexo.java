package tests.org.designwizard.design.mocks;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


public class Anexo {
	String anexo;
	
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public void getAnexo(@PathParam("id") Long id) {
		
	}
	
	@GET
	public void getAnexoMultipleParams(@PathParam("id") Long id, @QueryParam("key") Long key) {
		
	}
	
	@GET
	@PathParam("id")
	public void getCaso(@PathParam("id") Long id) {
		
	}
	
	public void getNome(String nome) {
		
	}
	
	@POST
	@Path("/{id}")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
}
