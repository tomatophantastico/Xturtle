/*
* generated by Xtext
*/
package de.itemis.tooling.xturtle.ui.labeling;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import de.itemis.tooling.xturtle.resource.TurtleResourceService;
import de.itemis.tooling.xturtle.xturtle.Base;
import de.itemis.tooling.xturtle.xturtle.PrefixId;
import de.itemis.tooling.xturtle.xturtle.QNameDef;
import de.itemis.tooling.xturtle.xturtle.Resource;
import de.itemis.tooling.xturtle.xturtle.UriDef;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class XturtleLabelProvider extends DefaultEObjectLabelProvider {
	@Inject 
	TurtleResourceService service;

	@Inject
	IQualifiedNameProvider nameProvider;
	@Inject
	public XturtleLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String image(Resource res){
		return "definition.gif";
	}
	public String image(PrefixId prefix){
		return "import.gif";
	}
	public String image(Base base){
		return "import.gif";
	}
	
	private String getUriText(EObject obj, String prefix){
		String uri=service.getUriString(obj);
		return prefix+(uri!=null&&!prefix.equals(uri)?": "+uri:"");
	}
	
	public String text(QNameDef def){
		return getUriText(def, Optional.fromNullable(def.getId()).or(""));
	}
	public String text(UriDef def){
		return getUriText(def, Optional.fromNullable(def.getUri()).or(""));
	}
	
	public String text(Base def){
		return getUriText(def, "@base");
	}
	public String text(PrefixId def){
		return getUriText(def, "@prefix "+Optional.fromNullable(def.getId()).or(""));
	}
}
