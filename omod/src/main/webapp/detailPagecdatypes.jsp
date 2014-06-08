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
<spring:message code="CDAGenerator.subtitle.editcdatypes"/>
</h4>

<form:form modelAttribute="detailcda" method="post" >

<div class="boxHeader"><spring:message code="CDAGenerator.document.DetailPage" /></div>
<div id="detail_page_cda_types" class="box">
<div class="boxbg">
<table>
<tr>
<td>
<label for="documentFullName">
	<spring:message code="CDAGenerator.document.type.Fullname"/></label>	
<input type="text"  name="documentFullName" value="${detailcda.documentFullName}" size="30" readonly />
  </td>
  </tr>
	
	<tr>
<td>
	<label for="documentShortName">
	<spring:message code="CDAGenerator.document.type.shortname"/>
	</label>
	<input type="text"  name="documentShortName" value="${detailcda.documentShortName}" size="20" readonly />
	</td>
  </tr>
	
	<tr>
	<td>
     <label for="documentDescription">
	  <spring:message code="CDAGenerator.document.type.description"/>
	  </label>
	<textarea  name="documentDescription" rows="3" cols="40" disabled> ${detailcda.documentDescription}</textarea>
	</td>
	</tr>
	
	  <tr>
	  <td>
	<label for="formatCode">
	<spring:message code="CDAGenerator.document.type.formatcode"/>
	</label>
	<input type="text"  name="formatCode" value="${detailcda.formatCode}" size="20" readonly />
	</td>
  </tr>

<tr>
<td>
	<label for="templateid">
	<spring:message code="CDAGenerator.document.type.templateid"/>
	</label>
	<input type="text"  name="templateid" value="${detailcda.templateid}" size="25" readonly />
	</td>
  </tr>
	</table>
	</div>
	</div>
	</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>