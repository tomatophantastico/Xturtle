package de.itemis.tooling.xturtle.ui.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;

public class TurtleEObjectHover extends DispatchingEObjectTextHover {

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Inject
	private ILocationInFileProvider locationInFileProvider;

	@Override
	protected Pair<EObject, IRegion> getXtextElementAt(XtextResource resource,
			int offset) {
		Pair<EObject, IRegion> temp = super.getXtextElementAt(resource, offset);
		if(temp==null){
			EObject crossLinkedEObject = eObjectAtOffsetHelper.resolveCrossReferencedElementAt(resource, offset);
			if (crossLinkedEObject != null) {
				if (crossLinkedEObject.eIsProxy()) {
					EObject o = eObjectAtOffsetHelper.resolveContainedElementAt(resource, offset);
					if (o != null) {
						ICompositeNode node = NodeModelUtils.getNode(o);
						IRegion region = new Region(node.getOffset(),node.getLength());
						if (TextUtilities.overlaps(region, new Region(offset, 0)))
							return Tuples.create(o, region);
					}
				}
			}
		}
		return temp;
	}
}
