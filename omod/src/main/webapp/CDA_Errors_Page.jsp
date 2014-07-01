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
<spring:message code="CDAGenerator.validation.results"/>
</h4>
<div class="boxHeader"><spring:message code="CDAGenerator.validation.results"/></div>
<div id="validation_box" class="box">
<div>
<table id="validation_results_table" class="tableStlye">
<thead >
<tr>
<th class="thStyle"><spring:message code="CDAGenerator.validation.severity"/></th>
<th class="thStyle"><spring:message code="CDAGenerator.validation.message"/></th>

 </tr>
</thead>
<tbody>

<c:forEach items="${CDA_Errors_Page}" var="data">
<tr class='${status.index % 2 == 0 ? "oddRow" : "evenRow" }'>
	<c:choose>
    <c:when test="${fn:contains(data, 'Error')}">
     <td class="tdStyle"> 
		  ERROR
	</td>
    </c:when>
    <c:when test="${fn:contains(data, 'WARNING')}">
    <td class="tdStyle">     
          WARNING
     </td>   
    </c:when>
    <c:when test="${fn:contains(data, 'Info')}">
       <td class="tdStyle"> 
          INFO
      </td> 
    </c:when>
    <c:otherwise>
    </c:otherwise>
 </c:choose>
	 <td width="90%">
    ${data}
    </td>
</c:forEach>
</tbody>

</table>
</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>