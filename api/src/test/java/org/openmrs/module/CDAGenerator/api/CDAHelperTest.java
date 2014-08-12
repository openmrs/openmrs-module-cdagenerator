package org.openmrs.module.CDAGenerator.api;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.vocab.NullFlavor;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class CDAHelperTest extends BaseModuleContextSensitiveTest 
{

	@Test
	public void shouldFailIfReturnsNull() 
	{
		SimpleDateFormat sdf=CDAHelper.getDateFormat();
		Assert.assertNotNull(sdf);
	}
	
	@Test
	public void shouldPassIfBuildEffectiveTimeinIVLIsCalledNullValues()
	{
		IVL_TS time=CDAHelper.buildEffectiveTimeinIVL(null, null);
		IVXB_TS low=time.getLow();
		IVXB_TS high=time.getHigh();
		NullFlavor nf=low.getNullFlavor();
		NullFlavor nf1=high.getNullFlavor();
				
		Assert.assertNotNull(nf);
		Assert.assertNotNull(nf1);
	}
	
	@Test
	public void shouldPassIfBuildEffectiveTimeinIVLIsCalledBothValues()
	{
		IVL_TS time=CDAHelper.buildEffectiveTimeinIVL(new Date(), new Date());
		IVXB_TS low=time.getLow();
		IVXB_TS high=time.getHigh();
		
		String value=low.getValue();
		String value1=high.getValue();
				
		Assert.assertNotNull(value);
		Assert.assertNotNull(value1);
	}
	
	@Test
	public void shouldPassIfBuildEffectiveTimeinIVLIsCalledOneValues()
	{
		IVL_TS time=CDAHelper.buildEffectiveTimeinIVL(new Date(), null);
		IVL_TS time1=CDAHelper.buildEffectiveTimeinIVL(null, new Date());
		
		IVXB_TS low=time.getLow();
		IVXB_TS high=time.getHigh();
		
		IVXB_TS low1=time1.getLow();
		IVXB_TS high1=time1.getHigh();
		
		
		String value=low.getValue();
		NullFlavor nf=high.getNullFlavor();
		
		NullFlavor nf1=low1.getNullFlavor();
		String value1=high1.getValue();
		
		Assert.assertNotNull(value);
		Assert.assertNotNull(nf);
		
		Assert.assertNotNull(nf1);
		Assert.assertNotNull(value1);
	}
	
	@Test
	public void shouldContainEmptyStringIfTitleIsNull()
	{
		
		ST title=CDAHelper.buildTitle(null);
		String value=title.getText();
		Assert.assertEquals(value,"");
	}
	@Test
	public void shouldPassIfTitleIsHasValue()
	{
		
		ST title=CDAHelper.buildTitle("Testing");
		String value=title.getText();
		Assert.assertEquals(value,"Testing");
	}
	
	@Test
	public void shouldPassIfValueInEDTextisNull()
	{
	         ED edtext=CDAHelper.buildEDText(null);
	          String value=edtext.getText();
	          Assert.assertEquals(value,"<reference value=\""+null+"\"/>");
	}
	
	@Test
	public void shouldContainEmptyValueIfGetDatatypesValueIsPassedWithNullValues()
	{
		String value1=CDAHelper.getDatatypesValue(null, null);
		Assert.assertEquals(value1, "");
		
	}
	@Test
	public void shouldreturnNullIfgetLatestObsIsCalledWithNull()
	{
	Obs latestobs=CDAHelper.getLatestObs(null);
	Assert.assertEquals(latestobs, null);
	}
	
	@Test
	public void shouldCreateIDIfParametersareNull()
	{
		II id=CDAHelper.buildID(null, null);
		String root=id.getRoot();
		String extension=id.getExtension();
		
		Assert.assertEquals(root, null);
		Assert.assertEquals(extension, null);
	}
	
	@Test
	public void shouldCreateTemplateIDIfParametersareNull()
	{
		II templateid=CDAHelper.buildTemplateID(null, null, null);
		
		String root=templateid.getRoot();
		String extension=templateid.getExtension();
		String authority=templateid.getAssigningAuthorityName();
		
		Assert.assertEquals(root, null);
		Assert.assertEquals(extension, null);
		Assert.assertEquals(authority, null);
	}
	
	@Test
	public void shouldBuildCE_Element_IfParametersareNull()
	{
		CE ce=CDAHelper.buildCodeCE(null, null, null,null);
		
		String code=ce.getCode();
		String codeSystem=ce.getCodeSystem();
		String codeSystemName=ce.getCodeSystemName();
		String displayName=ce.getDisplayName();
		
		Assert.assertEquals(code, null);
		Assert.assertEquals(codeSystem, null);
		Assert.assertEquals(codeSystemName, null);
		Assert.assertEquals(displayName, null);
	}
	
	
	@Test
	public void shouldReturnCompletedIfStatusCodeisNull()
	{
		CS cs=CDAHelper.getStatusCode(null);
		String value=cs.getCode();
		Assert.assertEquals(value, "completed");
	}
	
}
