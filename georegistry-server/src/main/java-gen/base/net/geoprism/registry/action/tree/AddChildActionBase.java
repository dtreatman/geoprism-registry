package net.geoprism.registry.action.tree;

@com.runwaysdk.business.ClassSignature(hash = 917577070)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to AddChildAction.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class AddChildActionBase extends net.geoprism.registry.action.AbstractAction
{
  public final static String CLASS = "net.geoprism.registry.action.tree.AddChildAction";
  public static java.lang.String CHILDID = "childId";
  public static java.lang.String CHILDTYPECODE = "childTypeCode";
  public static java.lang.String HIERARCHYTYPECODE = "hierarchyTypeCode";
  public static java.lang.String PARENTID = "parentId";
  public static java.lang.String PARENTTYPECODE = "parentTypeCode";
  private static final long serialVersionUID = 917577070;
  
  public AddChildActionBase()
  {
    super();
  }
  
  public String getChildId()
  {
    return getValue(CHILDID);
  }
  
  public void validateChildId()
  {
    this.validateAttribute(CHILDID);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getChildIdMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.tree.AddChildAction.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(CHILDID);
  }
  
  public void setChildId(String value)
  {
    if(value == null)
    {
      setValue(CHILDID, "");
    }
    else
    {
      setValue(CHILDID, value);
    }
  }
  
  public String getChildTypeCode()
  {
    return getValue(CHILDTYPECODE);
  }
  
  public void validateChildTypeCode()
  {
    this.validateAttribute(CHILDTYPECODE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getChildTypeCodeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.tree.AddChildAction.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(CHILDTYPECODE);
  }
  
  public void setChildTypeCode(String value)
  {
    if(value == null)
    {
      setValue(CHILDTYPECODE, "");
    }
    else
    {
      setValue(CHILDTYPECODE, value);
    }
  }
  
  public String getHierarchyTypeCode()
  {
    return getValue(HIERARCHYTYPECODE);
  }
  
  public void validateHierarchyTypeCode()
  {
    this.validateAttribute(HIERARCHYTYPECODE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getHierarchyTypeCodeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.tree.AddChildAction.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(HIERARCHYTYPECODE);
  }
  
  public void setHierarchyTypeCode(String value)
  {
    if(value == null)
    {
      setValue(HIERARCHYTYPECODE, "");
    }
    else
    {
      setValue(HIERARCHYTYPECODE, value);
    }
  }
  
  public String getParentId()
  {
    return getValue(PARENTID);
  }
  
  public void validateParentId()
  {
    this.validateAttribute(PARENTID);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getParentIdMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.tree.AddChildAction.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(PARENTID);
  }
  
  public void setParentId(String value)
  {
    if(value == null)
    {
      setValue(PARENTID, "");
    }
    else
    {
      setValue(PARENTID, value);
    }
  }
  
  public String getParentTypeCode()
  {
    return getValue(PARENTTYPECODE);
  }
  
  public void validateParentTypeCode()
  {
    this.validateAttribute(PARENTTYPECODE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getParentTypeCodeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.action.tree.AddChildAction.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(PARENTTYPECODE);
  }
  
  public void setParentTypeCode(String value)
  {
    if(value == null)
    {
      setValue(PARENTTYPECODE, "");
    }
    else
    {
      setValue(PARENTTYPECODE, value);
    }
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public static AddChildAction get(String oid)
  {
    return (AddChildAction) com.runwaysdk.business.Business.get(oid);
  }
  
  public static AddChildAction getByKey(String key)
  {
    return (AddChildAction) com.runwaysdk.business.Business.get(CLASS, key);
  }
  
  public static AddChildAction lock(java.lang.String oid)
  {
    AddChildAction _instance = AddChildAction.get(oid);
    _instance.lock();
    
    return _instance;
  }
  
  public static AddChildAction unlock(java.lang.String oid)
  {
    AddChildAction _instance = AddChildAction.get(oid);
    _instance.unlock();
    
    return _instance;
  }
  
  public String toString()
  {
    if (this.isNew())
    {
      return "New: "+ this.getClassDisplayLabel();
    }
    else
    {
      return super.toString();
    }
  }
}