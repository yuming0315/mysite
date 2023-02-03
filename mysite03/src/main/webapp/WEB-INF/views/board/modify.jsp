<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
			<c:set var="offset" value="${requestScope.pager.offset }" />
			<c:set var="page" value="${requestScope.pager.page }" />
			<c:set var="link" value="?offset=${offset}" />
			<c:set var="path" value="${pageContext.request.contextPath }/board/" />
			
				<form class="board-form" method="post" 
				action="${path }modify">
					<input type = "hidden" name = "offset" value=${offset } >
					<input type = "hidden" name = "page" value=${page } >
					<input type = "hidden" name = "no" value=${requestScope.vo.no } >
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${requestScope.vo.title}"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td><textarea id="content" name="content">${requestScope.vo.content}</textarea></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${path }view${link }&no=${requestScope.vo.no}">취소</a> 
						<input type="submit" value="수정">
					</div>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>