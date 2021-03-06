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
package net.geoprism.registry.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import org.commongeoregistry.adapter.dataaccess.GeoObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.runwaysdk.dataaccess.ProgrammingErrorException;
import com.runwaysdk.system.gis.geo.Universal;

import net.geoprism.ontology.GeoEntityUtil;
import net.geoprism.registry.model.ServerGeoObjectIF;
import net.geoprism.registry.model.ServerGeoObjectType;
import net.geoprism.registry.model.ServerHierarchyType;
import net.geoprism.registry.model.ServerParentTreeNode;
import net.geoprism.registry.service.ServerGeoObjectService;
import net.geoprism.registry.service.ServiceFactory;

public class ServerParentTreeNodeOverTime
{
  private static class Hierarchy
  {
    private ServerHierarchyType        type;

    private List<ServerParentTreeNode> nodes;

    public Hierarchy(ServerHierarchyType type)
    {
      this.type = type;
      this.nodes = new LinkedList<ServerParentTreeNode>();
    }

    public ServerHierarchyType getType()
    {
      return type;
    }

    public void add(ServerParentTreeNode node)
    {
      this.nodes.add(node);
    }

    public List<ServerParentTreeNode> getNodes()
    {
      return nodes;
    }
  }

  private Map<String, Hierarchy> hierarchies;

  private ServerGeoObjectType    type;

  public ServerParentTreeNodeOverTime(ServerGeoObjectType type)
  {
    this.type = type;
    this.hierarchies = new TreeMap<String, ServerParentTreeNodeOverTime.Hierarchy>();
  }

  public void add(ServerHierarchyType type)
  {
    if (!this.hierarchies.containsKey(type.getCode()))
    {
      this.hierarchies.put(type.getCode(), new Hierarchy(type));
    }
  }

  public void add(ServerHierarchyType type, ServerParentTreeNode node)
  {
    if (!this.hierarchies.containsKey(type.getCode()))
    {
      this.hierarchies.put(type.getCode(), new Hierarchy(type));
    }

    this.hierarchies.get(type.getCode()).add(node);
  }

  public Collection<ServerHierarchyType> getHierarchies()
  {
    final LinkedList<ServerHierarchyType> list = new LinkedList<ServerHierarchyType>();

    this.hierarchies.forEach((key, value) -> {
      list.add(value.getType());
    });

    return list;
  }

  public List<ServerParentTreeNode> getEntries(ServerHierarchyType type)
  {
    return this.hierarchies.get(type.getCode()).getNodes();
  }

  public JsonArray toJSON()
  {
    Universal root = Universal.getRoot();

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));

    final JsonArray response = new JsonArray();

    final Set<Entry<String, Hierarchy>> entrySet = this.hierarchies.entrySet();

    for (Entry<String, Hierarchy> entry : entrySet)
    {
      final Hierarchy hierarchy = entry.getValue();
      final ServerHierarchyType ht = hierarchy.getType();

      final JsonArray entries = new JsonArray();

      Collection<?> uniParents = GeoEntityUtil.getOrderedAncestors(root, this.type.getUniversal(), ht.getUniversalType());

      JsonArray types = new JsonArray();

      for (Object parent : uniParents)
      {
        ServerGeoObjectType pType = ServerGeoObjectType.get((Universal) parent);

        if (!pType.getCode().equals(this.type.getCode()))
        {
          JsonObject pObject = new JsonObject();
          pObject.addProperty("code", pType.getCode());
          pObject.addProperty("label", pType.getLabel().getValue());

          types.add(pObject);
        }
      }

      final List<ServerParentTreeNode> nodes = hierarchy.getNodes();

      for (ServerParentTreeNode node : nodes)
      {
        JsonObject pArray = new JsonObject();

        for (Object parent : uniParents)
        {
          ServerGeoObjectType pType = ServerGeoObjectType.get((Universal) parent);

          if (!pType.getCode().equals(this.type.getCode()))
          {
            final List<ServerParentTreeNode> ptns = node.findParentOfType(pType.getCode());

            if (ptns.size() > 0)
            {
              final ServerGeoObjectIF sGeoObject = ptns.get(0).getGeoObject();
              final GeoObject geoObject = sGeoObject.toGeoObject();
              geoObject.setGeometry(null);

              JsonObject pObject = new JsonObject();
              pObject.add("geoObject", geoObject.toJSON());
              pObject.addProperty("text", sGeoObject.getDisplayLabel().getValue());

              pArray.add(pType.getCode(), pObject);
            }
          }
        }

        JsonObject object = new JsonObject();
        object.addProperty("startDate", format.format(node.getDate()));
        object.addProperty("endDate", format.format(node.getEndDate()));
        object.add("parents", pArray);

        entries.add(object);
      }

      JsonObject object = new JsonObject();
      object.addProperty("code", ht.getCode());
      object.addProperty("label", ht.getDisplayLabel().getValue());
      object.add("types", types);
      object.add("entries", entries);

      response.add(object);
    }

    return response;
  }

  public static ServerParentTreeNodeOverTime fromJSON(ServerGeoObjectType type, String sPtn)
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setTimeZone(TimeZone.getTimeZone("GMT"));

    final ServerParentTreeNodeOverTime node = new ServerParentTreeNodeOverTime(type);

    final JsonParser parser = new JsonParser();
    final JsonArray array = parser.parse(sPtn).getAsJsonArray();

    for (int i = 0; i < array.size(); i++)
    {
      final JsonObject hJSON = array.get(i).getAsJsonObject();
      final String hierarchyCode = hJSON.get("code").getAsString();
      final JsonArray types = hJSON.get("types").getAsJsonArray();
      final ServerHierarchyType ht = ServerHierarchyType.get(hierarchyCode);

      final JsonArray entries = hJSON.get("entries").getAsJsonArray();

      for (int j = 0; j < entries.size(); j++)
      {
        final JsonObject entry = entries.get(j).getAsJsonObject();
        final String startDate = entry.get("startDate").getAsString();
        final JsonObject parents = entry.get("parents").getAsJsonObject();

        try
        {
          Date date = format.parse(startDate);

          final ServerParentTreeNode parent = parseParent(ht, types, parents, date);

          if (parent != null)
          {
            node.add(ht, parent);
          }
        }
        catch (ParseException e)
        {
          throw new ProgrammingErrorException(e);
        }
      }
    }

    return node;
  }

  private static ServerParentTreeNode parseParent(final ServerHierarchyType ht, final JsonArray types, final JsonObject parents, final Date date)
  {
    for (int k = ( types.size() - 1 ); k >= 0; k--)
    {
      final JsonObject type = types.get(k).getAsJsonObject();
      final String code = type.get("code").getAsString();
      final JsonObject parent = parents.get(code).getAsJsonObject();

      if (parent.has("geoObject"))
      {
        final JsonObject go = parent.get("geoObject").getAsJsonObject();

        GeoObject geoObject = GeoObject.fromJSON(ServiceFactory.getAdapter(), go.toString());
        final ServerGeoObjectIF pSGO = new ServerGeoObjectService().getGeoObjectByCode(geoObject.getCode(), code);

        return new ServerParentTreeNode(pSGO, ht, date);
      }
    }

    return null;
  }
}
