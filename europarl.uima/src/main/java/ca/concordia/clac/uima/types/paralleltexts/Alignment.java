

/* First created by JCasGen Fri Dec 30 19:13:04 EST 2016 */
package ca.concordia.clac.uima.types.paralleltexts;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Dec 30 19:13:04 EST 2016
 * XML source: /Users/majid/Documents/git/europarl/europarl.uima/src/main/resources/org/cleartk/corpus/europarl/type/Alignment.xml
 * @generated */
public class Alignment extends Annotation {
  /** @generated
   * @ordered 
   */
  public final static int typeIndexID = JCasRegistry.register(Alignment.class);
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
  protected Alignment() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Alignment(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Alignment(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Alignment(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  public Alignment(JCas jcas, Annotation annotation) {
	    super(jcas);
	    setBegin(annotation.getBegin());
	    setEnd(annotation.getEnd());
	    readObject();
	    setWrappedAnnotation(annotation);
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
  //* Feature: comment

  /** getter for comment - gets 
   * @generated
   * @return value of the feature 
   */
  public String getComment() {
    if (Alignment_Type.featOkTst && ((Alignment_Type)jcasType).casFeat_comment == null)
      jcasType.jcas.throwFeatMissing("comment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Alignment_Type)jcasType).casFeatCode_comment);}
    
  /** setter for comment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setComment(String v) {
    if (Alignment_Type.featOkTst && ((Alignment_Type)jcasType).casFeat_comment == null)
      jcasType.jcas.throwFeatMissing("comment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    jcasType.ll_cas.ll_setStringValue(addr, ((Alignment_Type)jcasType).casFeatCode_comment, v);}    
   
    
  //*--------------*
  //* Feature: wrappedAnnotation

  /** getter for wrappedAnnotation - gets 
   * @generated
   * @return value of the feature 
   */
  public Annotation getWrappedAnnotation() {
    if (Alignment_Type.featOkTst && ((Alignment_Type)jcasType).casFeat_wrappedAnnotation == null)
      jcasType.jcas.throwFeatMissing("wrappedAnnotation", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    return (Annotation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Alignment_Type)jcasType).casFeatCode_wrappedAnnotation)));}
    
  /** setter for wrappedAnnotation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWrappedAnnotation(Annotation v) {
    if (Alignment_Type.featOkTst && ((Alignment_Type)jcasType).casFeat_wrappedAnnotation == null)
      jcasType.jcas.throwFeatMissing("wrappedAnnotation", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    jcasType.ll_cas.ll_setRefValue(addr, ((Alignment_Type)jcasType).casFeatCode_wrappedAnnotation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: alignment

  /** getter for alignment - gets 
   * @generated
   * @return value of the feature 
   */
  public Alignment getAlignment() {
    if (Alignment_Type.featOkTst && ((Alignment_Type)jcasType).casFeat_alignment == null)
      jcasType.jcas.throwFeatMissing("alignment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    return (Alignment)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Alignment_Type)jcasType).casFeatCode_alignment)));}
    
  /** setter for alignment - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAlignment(Alignment v) {
    if (Alignment_Type.featOkTst && ((Alignment_Type)jcasType).casFeat_alignment == null)
      jcasType.jcas.throwFeatMissing("alignment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    jcasType.ll_cas.ll_setRefValue(addr, ((Alignment_Type)jcasType).casFeatCode_alignment, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    