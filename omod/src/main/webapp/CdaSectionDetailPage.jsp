<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>
<style>
label {
    font-size: 12px;
    font-weight: bold;
    width: 150px;
    float: left;
    margin-right: 50px;
    text-align: left;
   	height: 30px;
}

input {
    height: 30px;
    background-color: #fafafa; 
    border: 1px solid #abaaaa;
    width: 300px;
    margin: 5px 0 5px 0;
    float: right;
}
textarea{
    background-color: #fafafa; 
    border: 1px solid #abaaaa;
    width: 300px
    margin: 5px 0 5px 0;
    float: right;
}
.boxbg
{
background-color:Azure ;
}
</style>
<h4>
<spring:message code="CDAGenerator.subtitle.editcdasection"/>
</h4>

<form:form modelAttribute="detailcda" method="post" >

<div class="boxHeader"><spring:message code="CDAGenerator.section.DetailPage" /></div>
<div id="detail_page_cda_types" class="box">
<div class="boxbg">
<table>
<tr>
<td>
<label for="documentFullName">
	<spring:message code="CDAGenerator.section.name"/></label>	
<input type="text"  name="sectionName" value="${sectionDetails.sectionName}" size="30" readonly />
  </td>
  </tr>
	
	<tr>
<td>
	<label for="documentShortName">
	<spring:message code="CDAGenerator.section.templateid"/>
	</label>
	<input type="text"  name="templateid" value="${sectionDetails.templateid}" size="20" readonly />
	</td>
  </tr>
	
	<tr>
	<td>
     <label for="documentDescription">
	  <spring:message code="CDAGenerator.section.description"/>
	  </label>
	<textarea  name="sectionDescription" rows="3" cols="40" disabled> ${sectionDetails.sectionDescription}</textarea>
	</td>
	</tr>
	
	
	</table>
	</div>
	</div>
	</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>