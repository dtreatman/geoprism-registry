<%@ taglib uri="/WEB-INF/tlds/runwayLib.tld" prefix="mjl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="page_title" scope="request" value="Edit an existing Dashboard"/>
<dl>
  <mjl:form method="POST" name="net.geoprism.dashboard.Dashboard.form.name" id="net.geoprism.dashboard.Dashboard.form.id">
    <%@include file="form.jsp" %>
    <mjl:command name="net.geoprism.dashboard.Dashboard.form.update.button" action="net.geoprism.dashboard.DashboardController.update.mojo" value="Update" />
    <mjl:command name="net.geoprism.dashboard.Dashboard.form.delete.button" action="net.geoprism.dashboard.DashboardController.delete.mojo" value="Delete" />
    <mjl:command name="net.geoprism.dashboard.Dashboard.form.cancel.button" action="net.geoprism.dashboard.DashboardController.cancel.mojo" value="Cancel" />
  </mjl:form>
</dl>
