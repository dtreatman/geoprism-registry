package net.geoprism.registry;

@com.runwaysdk.business.ClassSignature(hash = -4550050)
/**
 * This class is generated automatically.
 * DO NOT MAKE CHANGES TO IT - THEY WILL BE OVERWRITTEN
 * Custom business logic should be added to MasterList.java
 *
 * @author Autogenerated by RunwaySDK
 */
public abstract class MasterListBase extends com.runwaysdk.business.Business
{
  public final static String CLASS = "net.geoprism.registry.MasterList";
  public static java.lang.String ACCESSCONSTRAINTS = "accessConstraints";
  public static java.lang.String ACKNOWLEDGEMENTS = "acknowledgements";
  public static java.lang.String CODE = "code";
  public static java.lang.String CONTACTNAME = "contactName";
  public static java.lang.String CREATEDATE = "createDate";
  public static java.lang.String CREATEDBY = "createdBy";
  public static java.lang.String DISCLAIMER = "disclaimer";
  public static java.lang.String DISPLAYLABEL = "displayLabel";
  private com.runwaysdk.business.Struct displayLabel = null;
  
  public static java.lang.String EMAIL = "email";
  public static java.lang.String ENTITYDOMAIN = "entityDomain";
  public static java.lang.String HIERARCHIES = "hierarchies";
  public static java.lang.String KEYNAME = "keyName";
  public static java.lang.String LASTUPDATEDATE = "lastUpdateDate";
  public static java.lang.String LASTUPDATEDBY = "lastUpdatedBy";
  public static java.lang.String LISTABSTRACT = "listAbstract";
  public static java.lang.String LOCKEDBY = "lockedBy";
  public static java.lang.String MDBUSINESS = "mdBusiness";
  public static java.lang.String OID = "oid";
  public static java.lang.String ORGANIZATION = "organization";
  public static java.lang.String OWNER = "owner";
  public static java.lang.String PROCESS = "process";
  public static java.lang.String PROGRESS = "progress";
  public static java.lang.String PUBLISHDATE = "publishDate";
  public static java.lang.String REPRESENTATIVITYDATE = "representativityDate";
  public static java.lang.String SEQ = "seq";
  public static java.lang.String SITEMASTER = "siteMaster";
  public static java.lang.String TELEPHONENUMBER = "telephoneNumber";
  public static java.lang.String TYPE = "type";
  public static java.lang.String UNIVERSAL = "universal";
  public static java.lang.String USECONSTRAINTS = "useConstraints";
  private static final long serialVersionUID = -4550050;
  
  public MasterListBase()
  {
    super();
    displayLabel = super.getStruct("displayLabel");
  }
  
  public String getAccessConstraints()
  {
    return getValue(ACCESSCONSTRAINTS);
  }
  
  public void validateAccessConstraints()
  {
    this.validateAttribute(ACCESSCONSTRAINTS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getAccessConstraintsMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(ACCESSCONSTRAINTS);
  }
  
  public void setAccessConstraints(String value)
  {
    if(value == null)
    {
      setValue(ACCESSCONSTRAINTS, "");
    }
    else
    {
      setValue(ACCESSCONSTRAINTS, value);
    }
  }
  
  public String getAcknowledgements()
  {
    return getValue(ACKNOWLEDGEMENTS);
  }
  
  public void validateAcknowledgements()
  {
    this.validateAttribute(ACKNOWLEDGEMENTS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getAcknowledgementsMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(ACKNOWLEDGEMENTS);
  }
  
  public void setAcknowledgements(String value)
  {
    if(value == null)
    {
      setValue(ACKNOWLEDGEMENTS, "");
    }
    else
    {
      setValue(ACKNOWLEDGEMENTS, value);
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
  
  public static com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF getCodeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF)mdClassIF.definesAttribute(CODE);
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
  
  public String getContactName()
  {
    return getValue(CONTACTNAME);
  }
  
  public void validateContactName()
  {
    this.validateAttribute(CONTACTNAME);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getContactNameMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(CONTACTNAME);
  }
  
  public void setContactName(String value)
  {
    if(value == null)
    {
      setValue(CONTACTNAME, "");
    }
    else
    {
      setValue(CONTACTNAME, value);
    }
  }
  
  public java.util.Date getCreateDate()
  {
    return com.runwaysdk.constants.MdAttributeDateTimeUtil.getTypeSafeValue(getValue(CREATEDATE));
  }
  
  public void validateCreateDate()
  {
    this.validateAttribute(CREATEDATE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeDateTimeDAOIF getCreateDateMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeDateTimeDAOIF)mdClassIF.definesAttribute(CREATEDATE);
  }
  
  public com.runwaysdk.system.SingleActor getCreatedBy()
  {
    if (getValue(CREATEDBY).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.SingleActor.get(getValue(CREATEDBY));
    }
  }
  
  public String getCreatedByOid()
  {
    return getValue(CREATEDBY);
  }
  
  public void validateCreatedBy()
  {
    this.validateAttribute(CREATEDBY);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getCreatedByMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(CREATEDBY);
  }
  
  public String getDisclaimer()
  {
    return getValue(DISCLAIMER);
  }
  
  public void validateDisclaimer()
  {
    this.validateAttribute(DISCLAIMER);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getDisclaimerMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(DISCLAIMER);
  }
  
  public void setDisclaimer(String value)
  {
    if(value == null)
    {
      setValue(DISCLAIMER, "");
    }
    else
    {
      setValue(DISCLAIMER, value);
    }
  }
  
  public net.geoprism.registry.MasterListDisplayLabel getDisplayLabel()
  {
    return (net.geoprism.registry.MasterListDisplayLabel) displayLabel;
  }
  
  public void validateDisplayLabel()
  {
    this.validateAttribute(DISPLAYLABEL);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeLocalCharacterDAOIF getDisplayLabelMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeLocalCharacterDAOIF)mdClassIF.definesAttribute(DISPLAYLABEL);
  }
  
  public String getEmail()
  {
    return getValue(EMAIL);
  }
  
  public void validateEmail()
  {
    this.validateAttribute(EMAIL);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getEmailMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(EMAIL);
  }
  
  public void setEmail(String value)
  {
    if(value == null)
    {
      setValue(EMAIL, "");
    }
    else
    {
      setValue(EMAIL, value);
    }
  }
  
  public com.runwaysdk.system.metadata.MdDomain getEntityDomain()
  {
    if (getValue(ENTITYDOMAIN).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.metadata.MdDomain.get(getValue(ENTITYDOMAIN));
    }
  }
  
  public String getEntityDomainOid()
  {
    return getValue(ENTITYDOMAIN);
  }
  
  public void validateEntityDomain()
  {
    this.validateAttribute(ENTITYDOMAIN);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getEntityDomainMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(ENTITYDOMAIN);
  }
  
  public void setEntityDomain(com.runwaysdk.system.metadata.MdDomain value)
  {
    if(value == null)
    {
      setValue(ENTITYDOMAIN, "");
    }
    else
    {
      setValue(ENTITYDOMAIN, value.getOid());
    }
  }
  
  public void setEntityDomainId(java.lang.String oid)
  {
    if(oid == null)
    {
      setValue(ENTITYDOMAIN, "");
    }
    else
    {
      setValue(ENTITYDOMAIN, oid);
    }
  }
  
  public String getHierarchies()
  {
    return getValue(HIERARCHIES);
  }
  
  public void validateHierarchies()
  {
    this.validateAttribute(HIERARCHIES);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getHierarchiesMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(HIERARCHIES);
  }
  
  public void setHierarchies(String value)
  {
    if(value == null)
    {
      setValue(HIERARCHIES, "");
    }
    else
    {
      setValue(HIERARCHIES, value);
    }
  }
  
  public String getKeyName()
  {
    return getValue(KEYNAME);
  }
  
  public void validateKeyName()
  {
    this.validateAttribute(KEYNAME);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF getKeyNameMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF)mdClassIF.definesAttribute(KEYNAME);
  }
  
  public void setKeyName(String value)
  {
    if(value == null)
    {
      setValue(KEYNAME, "");
    }
    else
    {
      setValue(KEYNAME, value);
    }
  }
  
  public java.util.Date getLastUpdateDate()
  {
    return com.runwaysdk.constants.MdAttributeDateTimeUtil.getTypeSafeValue(getValue(LASTUPDATEDATE));
  }
  
  public void validateLastUpdateDate()
  {
    this.validateAttribute(LASTUPDATEDATE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeDateTimeDAOIF getLastUpdateDateMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeDateTimeDAOIF)mdClassIF.definesAttribute(LASTUPDATEDATE);
  }
  
  public com.runwaysdk.system.SingleActor getLastUpdatedBy()
  {
    if (getValue(LASTUPDATEDBY).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.SingleActor.get(getValue(LASTUPDATEDBY));
    }
  }
  
  public String getLastUpdatedByOid()
  {
    return getValue(LASTUPDATEDBY);
  }
  
  public void validateLastUpdatedBy()
  {
    this.validateAttribute(LASTUPDATEDBY);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getLastUpdatedByMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(LASTUPDATEDBY);
  }
  
  public String getListAbstract()
  {
    return getValue(LISTABSTRACT);
  }
  
  public void validateListAbstract()
  {
    this.validateAttribute(LISTABSTRACT);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getListAbstractMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(LISTABSTRACT);
  }
  
  public void setListAbstract(String value)
  {
    if(value == null)
    {
      setValue(LISTABSTRACT, "");
    }
    else
    {
      setValue(LISTABSTRACT, value);
    }
  }
  
  public com.runwaysdk.system.SingleActor getLockedBy()
  {
    if (getValue(LOCKEDBY).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.SingleActor.get(getValue(LOCKEDBY));
    }
  }
  
  public String getLockedByOid()
  {
    return getValue(LOCKEDBY);
  }
  
  public void validateLockedBy()
  {
    this.validateAttribute(LOCKEDBY);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getLockedByMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(LOCKEDBY);
  }
  
  public com.runwaysdk.system.metadata.MdBusiness getMdBusiness()
  {
    if (getValue(MDBUSINESS).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.metadata.MdBusiness.get(getValue(MDBUSINESS));
    }
  }
  
  public String getMdBusinessOid()
  {
    return getValue(MDBUSINESS);
  }
  
  public void validateMdBusiness()
  {
    this.validateAttribute(MDBUSINESS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getMdBusinessMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(MDBUSINESS);
  }
  
  public void setMdBusiness(com.runwaysdk.system.metadata.MdBusiness value)
  {
    if(value == null)
    {
      setValue(MDBUSINESS, "");
    }
    else
    {
      setValue(MDBUSINESS, value.getOid());
    }
  }
  
  public void setMdBusinessId(java.lang.String oid)
  {
    if(oid == null)
    {
      setValue(MDBUSINESS, "");
    }
    else
    {
      setValue(MDBUSINESS, oid);
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
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeUUIDDAOIF)mdClassIF.definesAttribute(OID);
  }
  
  public String getOrganization()
  {
    return getValue(ORGANIZATION);
  }
  
  public void validateOrganization()
  {
    this.validateAttribute(ORGANIZATION);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getOrganizationMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(ORGANIZATION);
  }
  
  public void setOrganization(String value)
  {
    if(value == null)
    {
      setValue(ORGANIZATION, "");
    }
    else
    {
      setValue(ORGANIZATION, value);
    }
  }
  
  public com.runwaysdk.system.Actor getOwner()
  {
    if (getValue(OWNER).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.Actor.get(getValue(OWNER));
    }
  }
  
  public String getOwnerOid()
  {
    return getValue(OWNER);
  }
  
  public void validateOwner()
  {
    this.validateAttribute(OWNER);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getOwnerMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(OWNER);
  }
  
  public void setOwner(com.runwaysdk.system.Actor value)
  {
    if(value == null)
    {
      setValue(OWNER, "");
    }
    else
    {
      setValue(OWNER, value.getOid());
    }
  }
  
  public void setOwnerId(java.lang.String oid)
  {
    if(oid == null)
    {
      setValue(OWNER, "");
    }
    else
    {
      setValue(OWNER, oid);
    }
  }
  
  public String getProcess()
  {
    return getValue(PROCESS);
  }
  
  public void validateProcess()
  {
    this.validateAttribute(PROCESS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getProcessMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(PROCESS);
  }
  
  public void setProcess(String value)
  {
    if(value == null)
    {
      setValue(PROCESS, "");
    }
    else
    {
      setValue(PROCESS, value);
    }
  }
  
  public String getProgress()
  {
    return getValue(PROGRESS);
  }
  
  public void validateProgress()
  {
    this.validateAttribute(PROGRESS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getProgressMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(PROGRESS);
  }
  
  public void setProgress(String value)
  {
    if(value == null)
    {
      setValue(PROGRESS, "");
    }
    else
    {
      setValue(PROGRESS, value);
    }
  }
  
  public java.util.Date getPublishDate()
  {
    return com.runwaysdk.constants.MdAttributeDateUtil.getTypeSafeValue(getValue(PUBLISHDATE));
  }
  
  public void validatePublishDate()
  {
    this.validateAttribute(PUBLISHDATE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeDateDAOIF getPublishDateMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeDateDAOIF)mdClassIF.definesAttribute(PUBLISHDATE);
  }
  
  public void setPublishDate(java.util.Date value)
  {
    if(value == null)
    {
      setValue(PUBLISHDATE, "");
    }
    else
    {
      setValue(PUBLISHDATE, new java.text.SimpleDateFormat(com.runwaysdk.constants.Constants.DATE_FORMAT).format(value));
    }
  }
  
  public java.util.Date getRepresentativityDate()
  {
    return com.runwaysdk.constants.MdAttributeDateUtil.getTypeSafeValue(getValue(REPRESENTATIVITYDATE));
  }
  
  public void validateRepresentativityDate()
  {
    this.validateAttribute(REPRESENTATIVITYDATE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeDateDAOIF getRepresentativityDateMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeDateDAOIF)mdClassIF.definesAttribute(REPRESENTATIVITYDATE);
  }
  
  public void setRepresentativityDate(java.util.Date value)
  {
    if(value == null)
    {
      setValue(REPRESENTATIVITYDATE, "");
    }
    else
    {
      setValue(REPRESENTATIVITYDATE, new java.text.SimpleDateFormat(com.runwaysdk.constants.Constants.DATE_FORMAT).format(value));
    }
  }
  
  public Long getSeq()
  {
    return com.runwaysdk.constants.MdAttributeLongUtil.getTypeSafeValue(getValue(SEQ));
  }
  
  public void validateSeq()
  {
    this.validateAttribute(SEQ);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeLongDAOIF getSeqMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeLongDAOIF)mdClassIF.definesAttribute(SEQ);
  }
  
  public String getSiteMaster()
  {
    return getValue(SITEMASTER);
  }
  
  public void validateSiteMaster()
  {
    this.validateAttribute(SITEMASTER);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF getSiteMasterMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF)mdClassIF.definesAttribute(SITEMASTER);
  }
  
  public String getTelephoneNumber()
  {
    return getValue(TELEPHONENUMBER);
  }
  
  public void validateTelephoneNumber()
  {
    this.validateAttribute(TELEPHONENUMBER);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getTelephoneNumberMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(TELEPHONENUMBER);
  }
  
  public void setTelephoneNumber(String value)
  {
    if(value == null)
    {
      setValue(TELEPHONENUMBER, "");
    }
    else
    {
      setValue(TELEPHONENUMBER, value);
    }
  }
  
  public String getType()
  {
    return getValue(TYPE);
  }
  
  public void validateType()
  {
    this.validateAttribute(TYPE);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF getTypeMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeCharacterDAOIF)mdClassIF.definesAttribute(TYPE);
  }
  
  public com.runwaysdk.system.gis.geo.Universal getUniversal()
  {
    if (getValue(UNIVERSAL).trim().equals(""))
    {
      return null;
    }
    else
    {
      return com.runwaysdk.system.gis.geo.Universal.get(getValue(UNIVERSAL));
    }
  }
  
  public String getUniversalOid()
  {
    return getValue(UNIVERSAL);
  }
  
  public void validateUniversal()
  {
    this.validateAttribute(UNIVERSAL);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF getUniversalMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeReferenceDAOIF)mdClassIF.definesAttribute(UNIVERSAL);
  }
  
  public void setUniversal(com.runwaysdk.system.gis.geo.Universal value)
  {
    if(value == null)
    {
      setValue(UNIVERSAL, "");
    }
    else
    {
      setValue(UNIVERSAL, value.getOid());
    }
  }
  
  public void setUniversalId(java.lang.String oid)
  {
    if(oid == null)
    {
      setValue(UNIVERSAL, "");
    }
    else
    {
      setValue(UNIVERSAL, oid);
    }
  }
  
  public String getUseConstraints()
  {
    return getValue(USECONSTRAINTS);
  }
  
  public void validateUseConstraints()
  {
    this.validateAttribute(USECONSTRAINTS);
  }
  
  public static com.runwaysdk.dataaccess.MdAttributeTextDAOIF getUseConstraintsMd()
  {
    com.runwaysdk.dataaccess.MdClassDAOIF mdClassIF = com.runwaysdk.dataaccess.metadata.MdClassDAO.getMdClassDAO(net.geoprism.registry.MasterList.CLASS);
    return (com.runwaysdk.dataaccess.MdAttributeTextDAOIF)mdClassIF.definesAttribute(USECONSTRAINTS);
  }
  
  public void setUseConstraints(String value)
  {
    if(value == null)
    {
      setValue(USECONSTRAINTS, "");
    }
    else
    {
      setValue(USECONSTRAINTS, value);
    }
  }
  
  protected String getDeclaredType()
  {
    return CLASS;
  }
  
  public static MasterListQuery getAllInstances(String sortAttribute, Boolean ascending, Integer pageSize, Integer pageNumber)
  {
    MasterListQuery query = new MasterListQuery(new com.runwaysdk.query.QueryFactory());
    com.runwaysdk.business.Entity.getAllInstances(query, sortAttribute, ascending, pageSize, pageNumber);
    return query;
  }
  
  public static MasterList get(String oid)
  {
    return (MasterList) com.runwaysdk.business.Business.get(oid);
  }
  
  public static MasterList getByKey(String key)
  {
    return (MasterList) com.runwaysdk.business.Business.get(CLASS, key);
  }
  
  public static MasterList lock(java.lang.String oid)
  {
    MasterList _instance = MasterList.get(oid);
    _instance.lock();
    
    return _instance;
  }
  
  public static MasterList unlock(java.lang.String oid)
  {
    MasterList _instance = MasterList.get(oid);
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