

/* First created by JCasGen Thu Oct 06 12:42:57 EDT 2016 */
package org.cleartk.corpus.europarl.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Oct 06 12:51:53 EDT 2016
 * XML source: /Users/majid/Documents/git/europarl/europarl.uima/src/main/resources/org/cleartk/corpus/europarl/type/Speaker.xml
 * @generated */
public class Speaker extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Speaker.class);
  /** @generated
   * @ordered 
   */
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Speaker() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Speaker(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Speaker(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Speaker(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: name

  /** getter for name - gets 
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (Speaker_Type.featOkTst && ((Speaker_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "org.cleartk.corpus.europarl.type.Speaker");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Speaker_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (Speaker_Type.featOkTst && ((Speaker_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "org.cleartk.corpus.europarl.type.Speaker");
    jcasType.ll_cas.ll_setStringValue(addr, ((Speaker_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (Speaker_Type.featOkTst && ((Speaker_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.cleartk.corpus.europarl.type.Speaker");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Speaker_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Speaker_Type.featOkTst && ((Speaker_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.cleartk.corpus.europarl.type.Speaker");
    jcasType.ll_cas.ll_setStringValue(addr, ((Speaker_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: language

  /** getter for language - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLanguage() {
    if (Speaker_Type.featOkTst && ((Speaker_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "org.cleartk.corpus.europarl.type.Speaker");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Speaker_Type)jcasType).casFeatCode_language);}
    
  /** setter for language - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLanguage(String v) {
    if (Speaker_Type.featOkTst && ((Speaker_Type)jcasType).casFeat_language == null)
      jcasType.jcas.throwFeatMissing("language", "org.cleartk.corpus.europarl.type.Speaker");
    jcasType.ll_cas.ll_setStringValue(addr, ((Speaker_Type)jcasType).casFeatCode_language, v);}    
  }

    