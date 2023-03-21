<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-sql.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script>
	var listItemTemplate = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
			});
	var listTemplate = new EJS({
		url : "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
	})
	var render = function(vo, mode) {
		/* var htmls = 
			"<li data-no='" + vo.no + "'>" +
			"	<strong>" + vo.name + "</strong>" +
			"	<p>" + vo.content + "</p>" +
			"	<strong></strong>" +
			"	<a href='' data-no='" + vo.no + "'>삭제</a>" + 
			"</li>";
		 */
		//자바스크립트 공식문법에서도 el을 쓸 수 있다고 설명
		var no = 10;
		var s = '번호는 ${no}입니다.'; 
		var htmls = listItemTemplate.render(vo);
		$("#list-guestbook")[mode ? "prepend" : "append"](htmls);
	}

	var messageBox = function(title, message, callback) {
		$("#dialog-message p").text(message);
		$("#dialog-message").attr("title", title).dialog({
			width : 340,
			height : 170,
			modal : true,
			buttons : {
				"확인" : function() {
					$(this).dialog('close');
				}
			},
			close : callback
		});
	}

	$(function() {

		$("#add-form").submit(function(event) {
			event.preventDefault();

			// form serialization
			var vo = {};
			vo.name = $("#input-name").val();
			vo.password = $("#input-password").val();
			vo.content = $("#tx-content").val();

			/* validation & messagebox */
			if ($("#input-name").val() === '') {
				messageBox("방명록", "이름이 비어 있습니다.", function() {
					$("#input-name").focus();
				});
				return;
			}

			if ($("#input-password").val() === '') {
				messageBox("방명록", "비밀번호가 비어 있습니다.", function() {
					$("#input-password").focus();
				});
				return;
			}

			if ($("#tx-content").val() === '') {
				messageBox("방명록", "내용이 비어 있습니다.", function() {
					$("#tx-content").focus();
				});
				return;
			}

			$.ajax({
				url : "${pageContext.request.contextPath}/guestbook/api",
				type : "post",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(vo),
				success : function(response) {
					if (response.result === 'fail') {
						console.error(response.message);
						return;
					}

					render(response.data, true);
				}
			});
			$('#add-form')[0].reset();
		});

		// 삭제 다이알로그 jQuery 객체 미리 만들기
		var $dialogDelete = $("#dialog-delete-form")
				.dialog(
						{
							autoOpen : false,
							modal : true,
							buttons : {
								"삭제" : function() {
									var vo = {};
									vo.no = $("#hidden-no").val();
									vo.password = $("#password-delete").val();

									$
											.ajax({
												url : "${pageContext.request.contextPath}/guestbook/api",
												type : "delete",
												dataType : "json",
												contentType : "application/json",
												data : JSON.stringify(vo),
												success : function(response) {
													if (response.result === 'fail') {
														$(".validateTips-error")
																.show();
														console
																.error(response.message);
														return;
													}

													$(
															'[data-no='
																	+ response.data.no
																	+ ']')
															.remove();
													$("#password-delete").val(
															'');
												}
											});
									$(this).dialog('close');
								},
								"취소" : function() {
									$(this).dialog('close');
								}
							}
						});

		$(document).on('click', "#list-guestbook li", function(event) {
			event.preventDefault();

			$("#hidden-no").val($(this).data("no"));
			$dialogDelete.dialog('open');
		});

		var fetch = function() {
			$.ajax({
				url : "${pageContext.request.contextPath}/guestbook/api?sno="
						+ ($("#list-guestbook li").length + 5),
				type : "get",
				dataType : "json",
				success : function(response) {
					if (response.result === 'fail') {
						console.error(response.message);
						return;
					}

					/* response.data.forEach(function(vo) {
						render(vo);
					}) */
					
					var htmls = listTemplate.render(response);
					$("#list-guestbook").append(htmls);
				}
			});
			console.log($("#list-guestbook li").length + 5)
		}

		fetch();

		$(window).scroll(function() {
			var $window = $(this);
			var $document = $(document);

			var windowHeight = $window.height();
			var documentHeight = $document.height();
			var scrollTop = $window.scrollTop();

			if (documentHeight < windowHeight + scrollTop + 10) {
				fetch();
			}
		});
	})
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips-error" style="display: none; color: red">비밀번호가
					틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<div id="dialog-message" title="" style="display: none">
			<p style="line-height: 60px"></p>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>