<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("newline", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		
		<c:set var="offset" value="${requestScope.pager.offset }" />
		<c:set var="page" value="${requestScope.pager.page }" />
		<c:set var="link" value="?offset=${offset}" />
		<c:set var="plink" value="${link }&page=${page }" />
		<c:set var="path" value="${pageContext.request.contextPath }/board/" />
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title}</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${fn:replace(vo.content, newline, "<br>")}
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${path }${link }">글목록</a>
					<c:if test="${vo.user_no == authUser.no }">
						<a href="${path}modify${plink}&no=${vo.no}">글수정</a>
					</c:if>
					<c:if test="${not empty authUser.no }">
						<a href="${path }reply${plink }&no=${vo.no}">댓글</a>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>