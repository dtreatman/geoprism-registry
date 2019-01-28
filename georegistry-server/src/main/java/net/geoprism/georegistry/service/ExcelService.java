package net.geoprism.georegistry.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.commongeoregistry.adapter.dataaccess.GeoObject;
import org.commongeoregistry.adapter.metadata.GeoObjectType;
import org.json.JSONException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.runwaysdk.RunwayException;
import com.runwaysdk.business.SmartException;
import com.runwaysdk.constants.VaultProperties;
import com.runwaysdk.dataaccess.ProgrammingErrorException;
import com.runwaysdk.dataaccess.transaction.Transaction;
import com.runwaysdk.query.OIterator;
import com.runwaysdk.session.Request;
import com.runwaysdk.session.RequestType;
import com.runwaysdk.system.gis.geo.Universal;

import net.geoprism.data.etl.excel.ExcelDataFormatter;
import net.geoprism.data.etl.excel.ExcelSheetReader;
import net.geoprism.data.etl.excel.InvalidExcelFileException;
import net.geoprism.georegistry.GeoObjectQuery;
import net.geoprism.georegistry.excel.ExcelFieldContentsHandler;
import net.geoprism.georegistry.excel.GeoObjectContentHandler;
import net.geoprism.georegistry.excel.GeoObjectExcelExporter;
import net.geoprism.georegistry.io.GeoObjectConfiguration;
import net.geoprism.georegistry.io.ImportAttributeSerializer;
import net.geoprism.gis.geoserver.SessionPredicate;

public class ExcelService
{

  @Request(RequestType.SESSION)
  public JsonObject getExcelConfiguration(String sessionId, String type, String fileName, InputStream fileStream)
  {
    // Save the file to the file system
    try
    {
      GeoObjectType geoObjectType = ServiceFactory.getAdapter().getMetadataCache().getGeoObjectType(type).get();

      String name = SessionPredicate.generateId();

      File directory = new File(new File(VaultProperties.getPath("vault.default"), "files"), name);
      directory.mkdirs();

      File file = new File(directory, fileName);

      FileUtils.copyInputStreamToFile(fileStream, file);

      ExcelFieldContentsHandler handler = new ExcelFieldContentsHandler();
      ExcelDataFormatter formatter = new ExcelDataFormatter();

      ExcelSheetReader reader = new ExcelSheetReader(handler, formatter);
      reader.process(new FileInputStream(file));

      JsonObject object = new JsonObject();
      object.add("type", this.getType(geoObjectType));
      object.add("sheet", handler.getSheets().get(0).getAsJsonObject());
      object.addProperty("directory", directory.getName());
      object.addProperty("filename", fileName);

      return object;
    }
    catch (InvalidFormatException e)
    {
      InvalidExcelFileException ex = new InvalidExcelFileException(e);
      ex.setFileName(fileName);

      throw ex;
    }
    catch (RunwayException | SmartException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new ProgrammingErrorException(e);
    }
  }

  private JsonObject getType(GeoObjectType geoObjectType)
  {
    JsonObject type = geoObjectType.toJSON(new ImportAttributeSerializer(true));
    JsonArray attributes = type.get("attributes").getAsJsonArray();

    for (int i = 0; i < attributes.size(); i++)
    {
      JsonObject attribute = attributes.get(i).getAsJsonObject();
      String attributeType = attribute.get("type").getAsString();

      attribute.addProperty("baseType", GeoObjectConfiguration.getBaseType(attributeType));
    }

    return type;
  }

  @Request(RequestType.SESSION)
  public JsonObject importExcelFile(String sessionId, String config)
  {
    return this.importExcelFile(config);
  }

  @Transaction
  private JsonObject importExcelFile(String config)
  {
    GeoObjectConfiguration configuration = GeoObjectConfiguration.parse(config, true);

    String dir = configuration.getDirectory();
    String fname = configuration.getFilename();

    File directory = new File(new File(VaultProperties.getPath("vault.default"), "files"), dir);
    directory.mkdirs();

    File file = new File(directory, fname);

    GeoObjectContentHandler handler = new GeoObjectContentHandler(configuration);
    ExcelDataFormatter formatter = new ExcelDataFormatter();

    ExcelSheetReader reader = new ExcelSheetReader(handler, formatter);

    try
    {
      reader.process(new FileInputStream(file));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new ProgrammingErrorException(e);
    }

    return configuration.toJson();
  }

  @Request(RequestType.SESSION)
  public void cancelImport(String sessionId, String config)
  {
    GeoObjectConfiguration configuration = GeoObjectConfiguration.parse(config, false);

    try
    {
      String name = configuration.getDirectory();

      File directory = new File(new File(VaultProperties.getPath("vault.default"), "files"), name);

      FileUtils.deleteDirectory(directory);
    }
    catch (JSONException | IOException e)
    {
      throw new ProgrammingErrorException(e);
    }
  }

  @Request(RequestType.SESSION)
  public InputStream exportSpreadsheet(String sessionId, String code)
  {
    return this.exportSpreadsheet(code);
  }

  @Transaction
  private InputStream exportSpreadsheet(String code)
  {
    GeoObjectType type = ServiceFactory.getAdapter().getMetadataCache().getGeoObjectType(code).get();
    Universal universal = ServiceFactory.getConversionService().geoObjectTypeToUniversal(type);

    GeoObjectQuery query = new GeoObjectQuery(type, universal);
    OIterator<GeoObject> it = null;

    try
    {
      it = query.getIterator();

      GeoObjectExcelExporter exporter = new GeoObjectExcelExporter(type, it);
      InputStream istream = exporter.export();

      return istream;
    }
    catch (IOException e)
    {
      throw new ProgrammingErrorException(e);
    }
    finally
    {
      if (it != null)
      {
        it.close();
      }
    }
  }
}