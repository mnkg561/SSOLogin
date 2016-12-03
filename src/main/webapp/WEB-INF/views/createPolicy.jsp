<%--
    JBoss, Home of Professional Open Source
    Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
    contributors by the @authors tag. See the copyright.txt in the
    distribution for a full listing of individual contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>

<head>
<title>SSO Integration Policy Store</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/resources/css/screen.css"/>" />
<script>
	$(document).ready(function() {
		var placeholder = null;
		$('input[type=text]').focus(function() {
			placeholder = $(this).attr("placeholder");
			$(this).attr("placeholder", "");
		});
		$('input[type=text]').blur(function() {
			$(this).attr("placeholder", placeholder);
		});
	});
</script>
</head>

<body>
	<div class="main">
		<div class="one">
			<div class="register">
				<h3>SSO Policy Creation</h3>
				<form:form method="post" modelAttribute="newPolicy" id="reg-form">
					<div>
						<form:label path="path">Path</form:label>
						<form:input path="path" placeholder="/MyApp1/sample/protected" />
					</div>
					<div>
						<form:label path="contextRoot">Context Root</form:label>
						<form:input path="contextRoot" placeholder="/MyApp1" />
					</div>
					<div>
						<form:label path="thisProtected">Is Protected</form:label>
						<form:input path="thisProtected" placeholder="true" />
					</div>
					<div>
						<form:label path="protectedBy">Protected By</form:label>
						<form:input path="protectedBy"
							placeholder="http://ssologin.naveen.com:8080/SSOLogin/login" />
					</div>
					<div>
						<form:label path="groupName">Group Name</form:label>
						<form:input path="groupName" placeholder="ALL"/>
					</div>
					<div>
						<form:label path="sessionLevel">Session Level</form:label>
						<form:input path="sessionLevel" />
					</div>
					<div>
						<form:label path="mfaEnabled">Enable MFA</form:label>
						<form:input path="mfaEnabled" placeholder="false"/>
					</div>
					<div>
						<label></label> <input type="submit" value="Create Policy"
							id="create-policy" class="button" />
					</div>
				</form:form>



			</div>
		</div>
	</div>
</body>
</html>
