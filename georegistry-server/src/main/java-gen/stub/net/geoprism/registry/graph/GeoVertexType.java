/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Geoprism Registry(tm).
 *
 * Geoprism Registry(tm) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Geoprism Registry(tm) is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Geoprism Registry(tm).  If not, see <http://www.gnu.org/licenses/>.
 */
package net.geoprism.registry.graph;

import org.commongeoregistry.adapter.metadata.FrequencyType;

import com.runwaysdk.constants.MdAttributeBooleanInfo;
import com.runwaysdk.gis.constants.MdGeoVertexInfo;
import com.runwaysdk.gis.dataaccess.MdGeoVertexDAOIF;
import com.runwaysdk.gis.dataaccess.metadata.graph.MdGeoVertexDAO;
import com.runwaysdk.system.gis.geo.Universal;
import com.runwaysdk.system.graph.ChangeFrequency;

import net.geoprism.registry.RegistryConstants;

public class GeoVertexType extends GeoVertexTypeBase
{
  private static final long serialVersionUID = 224165207;

  public GeoVertexType()
  {
    super();
  }

  public static MdGeoVertexDAO create(String code, FrequencyType frequency)
  {
    ChangeFrequency cFrequency = ChangeFrequency.valueOf(frequency.name());

    MdGeoVertexDAOIF mdGeoVertexDAO = MdGeoVertexDAO.getMdGeoVertexDAO(GeoVertex.CLASS);

    MdGeoVertexDAO child = MdGeoVertexDAO.newInstance();
    child.setValue(MdGeoVertexInfo.PACKAGE, RegistryConstants.UNIVERSAL_GRAPH_PACKAGE);
    child.setValue(MdGeoVertexInfo.NAME, code);
    child.setValue(MdGeoVertexInfo.SUPER_MD_VERTEX, mdGeoVertexDAO.getOid());
    child.setValue(MdGeoVertexInfo.ENABLE_CHANGE_OVER_TIME, MdAttributeBooleanInfo.TRUE);
    child.setValue(MdGeoVertexInfo.GENERATE_SOURCE, MdAttributeBooleanInfo.FALSE);
    child.addItem(MdGeoVertexInfo.FREQUENCY, cFrequency.getOid());
    child.apply();

    return child;
  }

  public static void remove(String code)
  {
    MdGeoVertexDAO mdGeoVertex = getMdGeoVertex(code);
    mdGeoVertex.delete();
  }

  public static MdGeoVertexDAO getMdGeoVertex(String code)
  {
    if (!code.equals(Universal.ROOT))
    {
      return MdGeoVertexDAO.getMdGeoVertexDAO(RegistryConstants.UNIVERSAL_GRAPH_PACKAGE + "." + code).getBusinessDAO();
    }

    return null;
  }
}
