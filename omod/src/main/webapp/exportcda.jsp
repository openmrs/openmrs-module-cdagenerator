<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<style>
.boxbg
{
background-color:Azure ;
}
</style>
<script>
function getType()
{
	var x=document.getElementById("cda_profile_type").value;
	document.getElementById("ChildCDAHandler").value=x;
}
</script>
<h4>
<spring:message code="CDAGenerator.subtitle.exportcda"/>
</h4>
<div class="boxHeader"><spring:message code="CDAGenerator.export_cda" /></div>
<div id="export_cda_box" class="box">
<div class="boxbg">
<form id='export_Patient_cda' method="POST">
<br>
<div id="patient_id_field">
<spring:message code="CDAGenerator.patient.name"/>
<openmrs_tag:personField formFieldName="patientId"  formFieldId="patientId" />
</div>
</br>
<br>
<div id="cda_type_dropdown">
<spring:message code="CDAGenerator.document.cda.type"/>
<select id="cda_profile_type" name="cda_profile_type" onchange="getType()">
<option value="" id=""> <spring:message code="CDAGenerator.choose.document"/> </option>
<c:forEach var="ls" items="${ListCdatypes}">
			<option value="${ls}">${ls.documentFullName}(${ls.documentShortName})</option>
			
</c:forEach>
</select>
<input type="hidden" id="ChildCDAHandler" name="ChildCDAHandler"/>
</div>
</br>
<br>
<div id = "button_div">
<input type="submit" value='<spring:message code="CDAGenerator.document.export" />' />
</div>
</br>
</form>
</div>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>