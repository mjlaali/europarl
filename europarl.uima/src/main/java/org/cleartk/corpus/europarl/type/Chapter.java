

/* First created by JCasGen Thu Oct 06 12:42:54 EDT 2016 */
package org.cleartk.corpus.europarl.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Oct 06 12:42:54 EDT 2016
 * XML source: /Users/majid/Documents/git/europarl/europarl.uima/src/main/resources/org/cleartk/corpus/europarl/type/Chapter.xml
 * @generated */
public class Chapter extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Chapter.class);
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
  protected Chapter() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Chapter(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Chapter(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Chapter(JCas jcas, int begin, int end) {
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
  //* Feature: id

  /** getter for id - gets 
   * @generated
   * @return value of the feature 
   */
  public String getId() {
    if (Chapter_Type.featOkTst && ((Chapter_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.cleartk.corpus.europarl.type.Chapter");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Chapter_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setId(String v) {
    if (Chapter_Type.featOkTst && ((Chapter_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "org.cleartk.corpus.europarl.type.Chapter");
    jcasType.ll_cas.ll_setStringValue(addr, ((Chapter_Type)jcasType).casFeatCode_id, v);}    
  }

    