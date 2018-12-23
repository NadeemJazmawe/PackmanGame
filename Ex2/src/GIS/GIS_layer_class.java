package GIS;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GIS_layer_class implements GIS_layer{

    Set<GIS_element> Gis_layer = new HashSet<GIS_element>();
    private Meta_data_class data;

	@Override
	public boolean add(GIS_element arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.add(arg0);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_element> arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.addAll(arg0);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		Gis_layer.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return Gis_layer.isEmpty();
	}

	@Override
	public Iterator<GIS_element> iterator() {
		// TODO Auto-generated method stub
		return Gis_layer.iterator();
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.retainAll(arg0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return Gis_layer.size();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return Gis_layer.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return Gis_layer.toArray(arg0);
	}

	@Override
	public Meta_data get_Meta_data() {
		// TODO Auto-generated method stub
		return data;
	}
	
}
