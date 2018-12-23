package GIS;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GIS_project_class implements GIS_project{


    Set<GIS_layer> Gis_pro = new HashSet<GIS_layer>();
	private Meta_data_class data;
	public GIS_project_class(Meta_data_class data) {
		super();
		this.data = data;
	}
	@Override
	public boolean add(GIS_layer arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.addAll(arg0);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		Gis_pro.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return Gis_pro.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		// TODO Auto-generated method stub
		return Gis_pro.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.retainAll(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return Gis_pro.size();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return Gis_pro.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return Gis_pro.toArray(arg0);
	}

	@Override
	public Meta_data get_Meta_data() {
		// TODO Auto-generated method stub
		return data;
	}

    
}
