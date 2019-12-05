package net.geoprism.registry.conversion;

import java.util.Date;

import org.commongeoregistry.adapter.dataaccess.GeoObject;
import org.commongeoregistry.adapter.dataaccess.GeoObjectOverTime;

import net.geoprism.registry.model.ServerGeoObjectIF;
import net.geoprism.registry.model.ServerGeoObjectType;
import net.geoprism.registry.query.ServerGeoObjectQuery;

public interface ServerGeoObjectStrategyIF
{
  public ServerGeoObjectType getType();

  public ServerGeoObjectIF constructFromGeoObject(GeoObject geoObject, boolean isNew);
  
  public ServerGeoObjectIF constructFromGeoObjectOverTime(GeoObjectOverTime geoObject, boolean isNew);

  public ServerGeoObjectIF constructFromDB(Object dbObject);

  public ServerGeoObjectIF getGeoObjectByCode(String code);

  public ServerGeoObjectIF newInstance();

  public ServerGeoObjectQuery createQuery(Date date);

}
