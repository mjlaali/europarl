
/* First created by JCasGen Fri Dec 30 19:13:04 EST 2016 */
package ca.concordia.clac.uima.types.paralleltexts;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Dec 30 19:13:04 EST 2016
 * @generated */
public class Alignment_Type extends Annotation_Type {
  /** @generated */
  public final static int typeIndexID = Alignment.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("ca.concordia.clac.uima.types.paralleltexts.Alignment");
 
  /** @generated */
  final Feature casFeat_comment;
  /** @generated */
  final int     casFeatCode_comment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getComment(int addr) {
        if (featOkTst && casFeat_comment == null)
      jcas.throwFeatMissing("comment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    return ll_cas.ll_getStringValue(addr, casFeatCode_comment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setComment(int addr, String v) {
        if (featOkTst && casFeat_comment == null)
      jcas.throwFeatMissing("comment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    ll_cas.ll_setStringValue(addr, casFeatCode_comment, v);}
    
  
 
  /** @generated */
  final Feature casFeat_wrappedAnnotation;
  /** @generated */
  final int     casFeatCode_wrappedAnnotation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getWrappedAnnotation(int addr) {
        if (featOkTst && casFeat_wrappedAnnotation == null)
      jcas.throwFeatMissing("wrappedAnnotation", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    return ll_cas.ll_getRefValue(addr, casFeatCode_wrappedAnnotation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWrappedAnnotation(int addr, int v) {
        if (featOkTst && casFeat_wrappedAnnotation == null)
      jcas.throwFeatMissing("wrappedAnnotation", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    ll_cas.ll_setRefValue(addr, casFeatCode_wrappedAnnotation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_alignment;
  /** @generated */
  final int     casFeatCode_alignment;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getAlignment(int addr) {
        if (featOkTst && casFeat_alignment == null)
      jcas.throwFeatMissing("alignment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    return ll_cas.ll_getRefValue(addr, casFeatCode_alignment);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAlignment(int addr, int v) {
        if (featOkTst && casFeat_alignment == null)
      jcas.throwFeatMissing("alignment", "ca.concordia.clac.uima.types.paralleltexts.Alignment");
    ll_cas.ll_setRefValue(addr, casFeatCode_alignment, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Alignment_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_comment = jcas.getRequiredFeatureDE(casType, "comment", "uima.cas.String", featOkTst);
    casFeatCode_comment  = (null == casFeat_comment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_comment).getCode();

 
    casFeat_wrappedAnnotation = jcas.getRequiredFeatureDE(casType, "wrappedAnnotation", "uima.tcas.Annotation", featOkTst);
    casFeatCode_wrappedAnnotation  = (null == casFeat_wrappedAnnotation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_wrappedAnnotation).getCode();

 
    casFeat_alignment = jcas.getRequiredFeatureDE(casType, "alignment", "ca.concordia.clac.uima.types.paralleltexts.Alignment", featOkTst);
    casFeatCode_alignment  = (null == casFeat_alignment) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_alignment).getCode();

  }
}



    