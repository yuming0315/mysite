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

				<c:set var="path" value="${pageContext.request.contextPath }/board/" />
				<c:set var="page" value="${requestScope.pager.page }" />
				<c:set var="offset" value="${requestScope.pager.offset }" />
				<c:set var="kwd" value="${requestScope.kwdVo.kwd }" />
				<c:set var="kwdOption" value="${requestScope.kwdVo.kwdOption }" />

				<c:set var="alink" value="?offset=${offset }&kwd=${kwd }&kwdOption=${kwdOption }" />
				<c:set var="link" value="${alink }&page=${page }" />
				
				<form id="search_form" action="${path }" method="post">
					<input type="hidden" id="page" name="page" value="${page }">

					<select id="kwdOption" name="kwdOption" size="1">
						<option value="title">제목</option>
						<option value="contents">내용</option>
						<option value="b.name">작성자</option>
					</select> <input type="text" id="kwd" name="kwd"
						value="${kwd }"> <input type="submit"
						value="찾기">
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
									</c:if> <a href="${path }view?no=${vo.no }&offset=${offset }&page=${page }">${vo.title }</a>
								</td>
								<td>${vo.name }</td>
								<td>${vo.hit }</td>
								<td>${vo.regDate }</td>
								<td><c:if test="${vo.user_no == authUser.no }">
										<a href="${path }delete${link }&no=${vo.no }" class="del"> <img
											src="${pageContext.request.contextPath }/assets/images/recycle.png">
										</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</table>

					<!-- pager 추가 -->
					<div class="pager">
						<ul>
							<select onchange="this.form.submit()" id="offset" name="offset"
								size="1">
								<option value="${offset }">${offset }</option>
								<c:forEach begin="5" end="10" step="1" var="i">
									<c:if test="${i!=offset }">
										<option value="${i }">${i }</option>
									</c:if>
								</c:forEach>
							</select>
							<li><a href="${path }${link }&move=-1">◀</a></li>
							<c:forEach begin="${requestScope.pager.begin }"
								end="${requestScope.pager.end }" step="1" var="i">
								<c:if test="${i>0 }">
									<li ${i == page ? 'class="selected"':"" }><a
										href="${path }${alink }&page=${i}">${i }</a></li>
								</c:if>
							</c:forEach>
							<li><a href="${path }${link }&move=1">▶</a></li>
						</ul>
					</div>

					<c:if test="${not empty authUser }">
						<div class="bottom">
							<a
								href="${path }write?page=${page }&offset=${offset }"
								id="new-book">글쓰기</a>
						</div>
					</c:if>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>