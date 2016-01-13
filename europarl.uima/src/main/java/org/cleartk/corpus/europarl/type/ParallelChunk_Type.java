
/* First created by JCasGen Sun Nov 01 19:13:24 EST 2015 */
package org.cleartk.corpus.europarl.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed Jan 13 15:46:06 EST 2016
 * @generated */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ParallelChunk_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (ParallelChunk_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = ParallelChunk_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new ParallelChunk(addr, ParallelChunk_Type.this);
  			   ParallelChunk_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new ParallelChunk(addr, ParallelChunk_Type.this);
  	  }
    };
  /** @generated */
  public final static int typeIndexID = ParallelChunk.typeIndexID;
  /** @generated 
     @modifiable */
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("org.cleartk.corpus.europarl.type.ParallelChunk");



  /** @generated */
  final Feature casFeat_docOffset;
  /** @generated */
  final int     casFeatCode_docOffset;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDocOffset(int addr) {
        if (featOkTst && casFeat_docOffset == null)
      jcas.throwFeatMissing("docOffset", "org.cleartk.corpus.europarl.type.ParallelChunk");
    return ll_cas.ll_getIntValue(addr, casFeatCode_docOffset);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocOffset(int addr, int v) {
        if (featOkTst && casFeat_docOffset == null)
      jcas.throwFeatMissing("docOffset", "org.cleartk.corpus.europarl.type.ParallelChunk");
    ll_cas.ll_setIntValue(addr, casFeatCode_docOffset, v);}
    
  
 
  /** @generated */
  final Feature casFeat_translation;
  /** @generated */
  final int     casFeatCode_translation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTranslation(int addr) {
        if (featOkTst && casFeat_translation == null)
      jcas.throwFeatMissing("translation", "org.cleartk.corpus.europarl.type.ParallelChunk");
    return ll_cas.ll_getRefValue(addr, casFeatCode_translation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTranslation(int addr, int v) {
        if (featOkTst && casFeat_translation == null)
      jcas.throwFeatMissing("translation", "org.cleartk.corpus.europarl.type.ParallelChunk");
    ll_cas.ll_setRefValue(addr, casFeatCode_translation, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public ParallelChunk_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_docOffset = jcas.getRequiredFeatureDE(casType, "docOffset", "uima.cas.Integer", featOkTst);
    casFeatCode_docOffset  = (null == casFeat_docOffset) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_docOffset).getCode();

 
    casFeat_translation = jcas.getRequiredFeatureDE(casType, "translation", "org.cleartk.corpus.europarl.type.ParallelChunk", featOkTst);
    casFeatCode_translation  = (null == casFeat_translation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_translation).getCode();

  }
}



    