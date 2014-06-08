<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<style>
.oddRow { background-color: Snow; }
.evenRow { background-color:LightGray   ; }
.tableStlye
	{
border-collapse:collapse;
border:2px solid black;
width:100%;
table-layout: fixed;
}
.thStyle
{
 background-color:#00CC99; 
 font-family:"Tahoma";
 font-style:normal;
 font-weight:thick;
 font-size:small;
}
.tdStyle
	{
text-align:left;
vertical-align:top;
height:30px;
font-size:small;
}
</style>
<%@ include file="template/localHeader.jsp"%>
<h4>
<spring:message code="CDAGenerator.manage_cda_types"/>
</h4>
<div class="boxHeader"><spring:message code="CDAGenerator.existing.managecdatypes"/></div>
<div id="manage_cda_types_box" class="box">
<div>
<table id="managetypes_table" class="tableStlye">
<thead >
<tr>
<th class="thStyle"><spring:message code="CDAGenerator.document.type.Fullname"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.document.type.shortname"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.document.type.formatcode"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.document.type.description"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.document.type.templateid"/></th>

</tr>
</thead>
<tbody>
<c:forEach var="ls" items="${ListCdatypes}" varStatus="status">
<tr class='${status.index % 2 == 0 ? "oddRow" : "evenRow" }'>
<td class="tdStyle" width="20%">
<a href="detailPagecdatypes.form?templateid=${ls.templateid}">
${ls.documentFullName}
</td>
</a>
<td class="tdStyle" > 
${ls.documentShortName}
</td>
<td>
${ls.formatCode }
</td>
<td class="tdStyle" width="35%">
${ls.documentDescription }
</td>
<td class="tdStyle" width="40%">
${ls.templateid}
</td>
</tr>
		</c:forEach>
</tbody>

</table>
</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>