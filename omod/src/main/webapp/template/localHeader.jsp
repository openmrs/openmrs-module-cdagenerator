

<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin">
		<spring:message code="admin.title.short" /></a></li>

	
	<li
	<c:if test='<%= request.getRequestURI().contains("/configureSections") %>'>class="active"</c:if>>
	<a href="${pageContext.request.contextPath}/module/CDAGenerator/configureSections.form">
	<spring:message	code="CDAGenerator.manage_sections"/></a>
				</li>
				
				
	<li
	<c:if test='<%= request.getRequestURI().contains("/exportcda") %>'>class="active"</c:if>>
	<a href="${pageContext.request.contextPath}/module/CDAGenerator/exportcda.form"><spring:message
				code="CDAGenerator.export_cda"/></a>
				</li>
				
			<li
	<c:if test='<%= request.getRequestURI().contains("/managecdatypes") %>'>class="active"</c:if>>
	<a href="${pageContext.request.contextPath}/module/CDAGenerator/managecdatypes.form"><spring:message
				code="CDAGenerator.manage_cda_types"/></a>
				</li>		
				
				
	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="CDAGenerator.title" />
</h2>
