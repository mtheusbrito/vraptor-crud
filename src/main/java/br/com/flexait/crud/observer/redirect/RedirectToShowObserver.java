package br.com.flexait.crud.observer.redirect;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.flexait.crud.model.IModel;

public class RedirectToShowObserver extends AbstractRedirect {

	/**
	 * @deprecated CDI eyes only
	 */
	public RedirectToShowObserver() {
	}
	
	@Inject
	public RedirectToShowObserver(Result result) {
		super(result);
	}
	
	@Override
	public boolean accepts(ControllerMethod method) {
		return method.containsAnnotation(RedirectToShow.class);
	}

	@Override
	public void redirect(MethodInfo methodInfo) {
		IModel model = (IModel) methodInfo.getResult();
		result().redirectTo(getController(methodInfo)).show(model.getId());
	}

}
