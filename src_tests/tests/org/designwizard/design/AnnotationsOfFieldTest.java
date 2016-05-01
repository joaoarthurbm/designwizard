package tests.org.designwizard.design;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotationsOfFieldTest {

	DesignWizard dw;

	@Before
	public void setUp() throws Exception {
		String arquivoJar = "resources/sigest.jar";
		dw = new DesignWizard(arquivoJar);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGetFieldsAnnotations() throws InexistentEntityException {
		ClassNode curso;
		FieldNode fieldNode;

		curso = dw.getClass("br.ufrn.cerescaico.bsi.sigest.model.Curso");

		fieldNode = curso.getField("codigo");

		Set<ClassNode> annotations = fieldNode.getAnnotations();

		ClassNode id = new ClassNode("javax.persistence.Id");

		Assert.assertTrue("1", annotations.contains(id));
		Assert.assertEquals("2", 4, annotations.size());

		fieldNode = curso.getField("serialVersionUID");
		annotations = fieldNode.getAnnotations();

		Assert.assertEquals("3", 0, annotations.size());
	}
}
