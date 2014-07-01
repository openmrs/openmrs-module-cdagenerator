<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h1>Errors</h1>
 
	<c:forEach items="${CDA_Errors_Page}" var="error">
		${error}
		<br />
	</c:forEach>
 
 </body>
</html>