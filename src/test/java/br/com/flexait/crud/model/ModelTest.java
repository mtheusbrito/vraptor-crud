package br.com.flexait.crud.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.ContextController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.flexait.cdi.integration.Jpa;

@RunWith(CdiRunner.class)
public class ModelTest {
	
	@Inject ContextController ctx;
	@Inject Jpa jpa;
	
	@Before
	public void setUp() throws Exception {
		ctx.openRequest();
	}

	private ModelImpl buildModel() {
		ModelImpl model = new ModelImpl();
		model.name = "Foo";
		return model;
	}

	@After
	public void tearDown() throws Exception {
		ctx.closeRequest();
	}
	
	@Test
	public void shouldSaveAModelImplementation() throws Exception {
		ModelImpl merged = jpa.begin().merge(buildModel());
		jpa.commit();
		
		assertThat(merged.getId(), notNullValue());
	}
	
	@Test
	public void shouldConfigDateOnInsert() {
		ModelImpl merged = jpa.begin().merge(buildModel());
		jpa.commit();
		
		assertThat(merged.getCreatedAt(), notNullValue());
	}
	
	@Test
	public void shouldConfigDateOnUpdate() {
		ModelImpl merged = jpa.begin().merge(buildModel());
		Calendar createdAt = merged.getCreatedAt();
		jpa.commit();
		assertThat(merged.getUpdatedAt(), notNullValue());
		assertThat(createdAt, notNullValue());
		
		merged.name = "Bar";
		merged = jpa.begin().merge(merged);
		jpa.commit();
		
		assertThat(merged.getUpdatedAt(), notNullValue());
		assertThat(merged.getCreatedAt(), equalTo(createdAt));
	}
}