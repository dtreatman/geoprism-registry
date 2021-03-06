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
package net.geoprism.registry;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.runwaysdk.constants.ClientRequestIF;

import net.geoprism.ClientConfigurationIF;
import net.geoprism.DefaultClientConfiguration;
import net.geoprism.GeoprismApplication;
import net.geoprism.GeoprismPatcher;
import net.geoprism.RoleConstants;
import net.geoprism.localization.LocalizationFacadeDTO;

public class RegistryClientConfiguration extends DefaultClientConfiguration implements ClientConfigurationIF
{

  public GeoprismPatcher getPatcher()
  {
    return new GeoregistryPatcher();
  }

  @Override
  public List<GeoprismApplication> getApplications(ClientRequestIF request)
  {
    GeoprismApplication hierarchies = new GeoprismApplication();
    hierarchies.setId("hierarchies");
    hierarchies.setLabel(LocalizationFacadeDTO.getFromBundles(request, "hierarchies.landing"));
    hierarchies.setSrc("net/geoprism/images/hierarchy-icon-modified.svg");
    hierarchies.setUrl("cgr/manage#/registry/hierarchies");
    hierarchies.setDescription(LocalizationFacadeDTO.getFromBundles(request, "hierarchies.landing.description"));
    hierarchies.addRole(RoleConstants.ADIM_ROLE);
    hierarchies.addRole(RegistryConstants.REGISTRY_ADMIN_ROLE);
    hierarchies.addRole(RegistryConstants.REGISTRY_MAINTAINER_ROLE);
    hierarchies.addRole(RegistryConstants.API_CONSUMER_ROLE);

    GeoprismApplication masterLists = new GeoprismApplication();
    masterLists.setId("lists");
    masterLists.setLabel(LocalizationFacadeDTO.getFromBundles(request, "masterlists.landing"));
    masterLists.setSrc("net/geoprism/images/masterlist-icon-modified.svg");
    masterLists.setUrl("cgr/manage#/registry/master-lists");
    masterLists.setDescription(LocalizationFacadeDTO.getFromBundles(request, "masterlists.landing.description"));
    masterLists.addRole(RoleConstants.ADIM_ROLE);
    masterLists.addRole(RegistryConstants.REGISTRY_ADMIN_ROLE);
    masterLists.addRole(RegistryConstants.REGISTRY_CONTRIBUTOR_ROLE);
    masterLists.addRole(RegistryConstants.REGISTRY_MAINTAINER_ROLE);
    masterLists.addRole(RegistryConstants.API_CONSUMER_ROLE);

    GeoprismApplication requests = new GeoprismApplication();
    requests.setId("requests");
    requests.setLabel(LocalizationFacadeDTO.getFromBundles(request, "requests.landing"));
    requests.setSrc("net/geoprism/images/update-icon-modified.svg");
    requests.setUrl("cgr/manage#/registry/change-requests");
    requests.setDescription(LocalizationFacadeDTO.getFromBundles(request, "requests.landing.description"));
    requests.addRole(RoleConstants.ADIM_ROLE);
    requests.addRole(RegistryConstants.REGISTRY_ADMIN_ROLE);
    requests.addRole(RegistryConstants.REGISTRY_CONTRIBUTOR_ROLE);
    requests.addRole(RegistryConstants.REGISTRY_MAINTAINER_ROLE);
    requests.addRole(RegistryConstants.API_CONSUMER_ROLE);

    GeoprismApplication uploads = new GeoprismApplication();
    uploads.setId("uploads");
    uploads.setLabel(LocalizationFacadeDTO.getFromBundles(request, "uploads.landing"));
    uploads.setSrc("net/geoprism/images/dm_icon.svg");
    uploads.setUrl("cgr/manage#/registry/data");
    uploads.setDescription(LocalizationFacadeDTO.getFromBundles(request, "uploads.landing.description"));
    uploads.addRole(RoleConstants.ADIM_ROLE);
    uploads.addRole(RegistryConstants.REGISTRY_ADMIN_ROLE);
    uploads.addRole(RegistryConstants.REGISTRY_MAINTAINER_ROLE);
    uploads.addRole(RegistryConstants.API_CONSUMER_ROLE);

    // GeoprismApplication management = new GeoprismApplication();
    // management.setId("management");
    // management.setLabel(LocalizationFacadeDTO.getFromBundles(request,
    // "geoprismLanding.dataManagement"));
    // management.setSrc("net/geoprism/images/dm_icon.svg");
    // management.setUrl("prism/home#data");
    // management.addRole(RoleConstants.ADIM_ROLE);
    // management.addRole(RoleConstants.BUILDER_ROLE);

    GeoprismApplication management = new GeoprismApplication();
    management.setId("locations");
    management.setLabel(LocalizationFacadeDTO.getFromBundles(request, "navigator.landing"));
    management.setSrc("net/geoprism/images/map_icon.svg");
    management.setUrl("nav/management#locations");
    management.setDescription(LocalizationFacadeDTO.getFromBundles(request, "navigator.landing.description"));
    management.addRole(RoleConstants.ADIM_ROLE);
    management.addRole(RegistryConstants.REGISTRY_ADMIN_ROLE);
    management.addRole(RegistryConstants.REGISTRY_MAINTAINER_ROLE);
    management.addRole(RegistryConstants.REGISTRY_CONTRIBUTOR_ROLE);

    List<GeoprismApplication> applications = new LinkedList<GeoprismApplication>();
    applications.add(hierarchies);
    applications.add(management);
    applications.add(masterLists);
    applications.add(requests);
    applications.add(uploads);

    return applications;
  }

  /*
   * Expose public endpoints to allow non-logged in users to hit controller
   * endpoints
   */
  @Override
  public Set<String> getPublicEndpoints()
  {
    Set<String> endpoints = super.getPublicEndpoints();
    endpoints.add("cgr/manage");
    return endpoints;
  }

  @Override
  public String getHomeUrl()
  {
    return "/cgr/manage";
  }

  @Override
  public String getLoginUrl()
  {
    return "/cgr/manage#login";
  }

}
