

/* First created by JCasGen Sun Nov 01 19:13:24 EST 2015 */
package org.cleartk.corpus.europarl.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jan 13 15:46:06 EST 2016
 * XML source: /Users/majid/Documents/git/europarl/europarl.uima/src/main/resources/org/cleartk/corpus/europarl/type/ParallelChunk.xml
 * @generated */
public class ParallelChunk extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(ParallelChunk.class);
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
  protected ParallelChunk() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ParallelChunk(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ParallelChunk(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ParallelChunk(JCas jcas, int begin, int end) {
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
  //* Feature: docOffset

  /** getter for docOffset - gets 
   * @generated
   * @return value of the feature 
   */
  public int getDocOffset() {
    if (ParallelChunk_Type.featOkTst && ((ParallelChunk_Type)jcasType).casFeat_docOffset == null)
      jcasType.jcas.throwFeatMissing("docOffset", "org.cleartk.corpus.europarl.type.ParallelChunk");
    return jcasType.ll_cas.ll_getIntValue(addr, ((ParallelChunk_Type)jcasType).casFeatCode_docOffset);}
    
  /** setter for docOffset - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocOffset(int v) {
    if (ParallelChunk_Type.featOkTst && ((ParallelChunk_Type)jcasType).casFeat_docOffset == null)
      jcasType.jcas.throwFeatMissing("docOffset", "org.cleartk.corpus.europarl.type.ParallelChunk");
    jcasType.ll_cas.ll_setIntValue(addr, ((ParallelChunk_Type)jcasType).casFeatCode_docOffset, v);}    
   
    
  //*--------------*
  //* Feature: translation

  /** getter for translation - gets 
   * @generated
   * @return value of the feature 
   */
  public ParallelChunk getTranslation() {
    if (ParallelChunk_Type.featOkTst && ((ParallelChunk_Type)jcasType).casFeat_translation == null)
      jcasType.jcas.throwFeatMissing("translation", "org.cleartk.corpus.europarl.type.ParallelChunk");
    return (ParallelChunk)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ParallelChunk_Type)jcasType).casFeatCode_translation)));}
    
  /** setter for translation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTranslation(ParallelChunk v) {
    if (ParallelChunk_Type.featOkTst && ((ParallelChunk_Type)jcasType).casFeat_translation == null)
      jcasType.jcas.throwFeatMissing("translation", "org.cleartk.corpus.europarl.type.ParallelChunk");
    jcasType.ll_cas.ll_setRefValue(addr, ((ParallelChunk_Type)jcasType).casFeatCode_translation, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    