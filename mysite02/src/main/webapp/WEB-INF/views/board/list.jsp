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
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>

				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${requestScope.list }" var="vo"
						varStatus="status">
						<tr>
							<td>${vo.no }</td>
							<td style="text-align: left; padding-left: ${vo.depth * 5}px">
								<c:if test="${vo.depth > 0 }">
									<img
										src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if> <a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td><a href="" class="del"> <c:if
										test="${vo.user_no == session.authUser.no }">
										<img
											src="${pageContext.request.contextPath }/assets/images/recycle.png">
									</c:if></a></td>
						</tr>
					</c:forEach>
				</table>
				<c:set var="page" value="${requestScope.page.page }" />
				<c:set var="offset" value="${requestScope.page.offset }" />
				<c:set var="pages" value="${requestScope.page.pages }" />
				
				<c:set var="alink" value="${pageContext.request.contextPath }/board?offset=${offset }" />
				<c:set var="link" value="${alink }&page=${page }" />
				
				<c:set var="begin" value="${page >= 3 ? (page+2 < pages ? page-2 : pages-4 ) : 1 }" />
				<c:set var="end" value="${page < 3 ? 5 : (page + 2 > pages ? pages : page+2 ) }" />
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="${link }&move=-1">◀</a></li>
						<c:forEach begin="${begin}" end="${end  }" step="1" var="i">
							<li ${i == page ? 'class="selected"':"" }><a href="${alink }&page=${i}">${i }</a></li>
						</c:forEach>
						<li><a href="${link }&move=1">▶</a></li>
					</ul>
				</div>
				<!-- pager 추가 -->

				<c:if test="${not empty authUser }">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=new-book&page=${page}&offset=${offset}"
							id="new-book">글쓰기</a>
					</div>
				</c:if>

			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>