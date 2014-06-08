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
<spring:message code="CDAGenerator.manage_sections"/>
</h4>
<div class="boxHeader"><spring:message code="CDAGenerator.existing.managecdasection" /></div>
<div id="manage_cda_sections_box" class="box">
<div>
<table id="managesctions_table" class="tableStlye">
<thead >
<tr>
<th class="thStyle"><spring:message code="CDAGenerator.section.name"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.section.templateid"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.section.description"/></th>



</tr>
</thead>
<tbody>
<c:forEach var="ls" items="${allCdaSections}" varStatus="status">
<tr class='${status.index % 2 == 0 ? "oddRow" : "evenRow" }'>
<td class="tdStyle" width="20%">
<a href="CdaSectionDetailPage.form?templateid=${ls.templateid}">
${ls.sectionName}
</a>
</td>

<td class="tdStyle">
${ls.templateid}
</td>

<td class="tdStyle" > 
${ls.sectionDescription}
</td>



</tr>
		</c:forEach>
</tbody>

</table>
</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>