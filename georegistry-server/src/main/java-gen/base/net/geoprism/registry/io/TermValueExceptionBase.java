package net.geoprism.registry.io;

@com.runwaysdk.business.ClassSignature(hash = 120198479)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to TermValueException.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class TermValueExceptionBase extends com.runwaysdk.business.SmartException
{
  public final static String CLASS = "net.geoprism.registry.io.TermValueException";
  public static java.lang.String ATTRIBUTELABEL = "attributeLabel";
  public static java.lang.String CODE = "code";
  public static java.lang.String OID = "oid";
  private static final long serialVersionUID = 120198479;
  
  public TermValueExceptionBase()
  {
    super();
  }
  
  public TermValueExceptionBase(java.lang.String developerMessage)
  {
    super(developerMessage);
  }
  
  public TermValueExceptionBase(java.lang.String developerMessage, java.lang.Throwable cause)
  {
    super(developerMessage, cause);
  }
  
  public TermValueExceptionBase(java.lang.Throwable cause)
  {
    super(cause);
  }
  
  public String getAttributeLabel()
  {
    return getValue(ATTRIBUTELABEL);
  }
  
  public void validateAttributeLabel()
  {
    this.validateAttribute(ATTRIBUTELABEL);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getAttributeLabelMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.io.TermValueException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(ATTRIBUTELABEL);
  }
  
  public void setAttributeLabel(String value)
  {
    if(value == null)
    {
      setValue(ATTRIBUTELABEL, "");
    }
    else
    {
      setValue(ATTRIBUTELABEL, value);
    }
  }
  
  public String getCode()
  {
    return getValue(CODE);
  }
  
  public void validateCode()
  {
    this.validateAttribute(CODE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getCodeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.io.TermValueException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(CODE);
  }
  
  public void setCode(String value)
  {
    if(value == null)
    {
      setValue(CODE, "");
    }
    else
    {
      setValue(CODE, value);
    }
  }
  
  public String getOid()
  {
    return getValue(OID);
  }
  
  public void validateOid()
  {
    this.validateAttribute(OID);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeUUIDDAOIF getOidMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.io.TermValueException.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeUUIDDAOIF)mdClassIF.definesAttribute(OID);
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public java.lang.String localize(java.util.Locale locale)
  {
    java.lang.String message = super.localize(locale);
    message = replace(message, "{attributeLabel}", this.getAttributeLabel());
    message = replace(message, "{code}", this.getCode());
    message = replace(message, "{oid}", this.getOid());
    return message;
  }
  
}
