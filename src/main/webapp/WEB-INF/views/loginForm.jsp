<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>Employees Login</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

<link rel='stylesheet prefetch'
	href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>

<link rel="stylesheet" href="static/resources/css/login.css">


</head>

<body>
	<div class="logmod">
		<div class="logmod__wrapper">
			<div class="logmod__container">
				<ul class="logmod__tabs">
					<li data-tabtar="lgm-2"><a href="#">Login</a></li>
					<li data-tabtar="lgm-1"><a href="#">Sign Up</a></li>
				</ul>
				<div class="logmod__tab-wrapper">
					<div class="logmod__tab lgm-1">
						<div class="logmod__heading">
							<span class="logmod__heading-subtitle">You can now sync up
								your account with social networks!!</span>
						</div>
						<div class="logmod__alter">
							<div class="logmod__alter-container">
								<a href="#" class="connect facebook">
									<div class="connect__icon">
										<i class="fa fa-facebook"></i>
									</div>
									<div class="connect__context">
										<span>Link with <strong>Facebook</strong></span>
									</div>
								</a> <a href="#" class="connect googleplus">
									<div class="connect__icon">
										<i class="fa fa-google-plus"></i>
									</div>
									<div class="connect__context">
										<span>Link with <strong>Google+</strong></span>
									</div>
								</a>
							</div>
						</div>
					</div>
					<div class="logmod__tab lgm-2">
						<div class="logmod__heading">
							<span class="logmod__heading-subtitle">Enter your user
								name and password <strong>to sign in</strong>
							</span>
						</div>
						<div class="logmod__form">
							<form:form method="post" modelAttribute="userCredentials"
								class="simform">
								<div class="sminputs">
									<div class="input full">
										<form:label class="string optional" path="userName">User Name*</form:label>
										<form:input path="userName" placeholder="Username"
											class="string optional" maxlength="255" size="50" />

									</div>
								</div>
								<div class="sminputs">
									<div class="input full">
										<form:label class="string optional" path="password">Password*</form:label>
										<form:input path="password" type="password"
											placeholder="Password" class="string optional"
											maxlength="255" size="50" />
										<span class="hide-password">Show</span>
									</div>
								</div>
								<div class="simform__actions">
									<input class="sumbit" name="commit" type="submit"
										value="Log In" />
								</div>
							</form:form>
						</div>
						<div class="logmod__alter">
							<div class="logmod__alter-container">
								<a href="#" class="connect facebook">
									<div class="connect__icon">
										<i class="fa fa-facebook"></i>
									</div>
									<div class="connect__context">
										<span>Sign in with <strong>Facebook</strong></span>
									</div>
								</a> <a href="https://accounts.google.com/o/oauth2/v2/auth?client_id=349455219287-nsba8isi6qnspmttjalgiet1vvprus0h.apps.googleusercontent.com&response_type=code&scope=openid%20email&redirect_uri=http://ssologin.naveen.com:8080/SSOLogin/google/redirectUrl&state=${userCredentials.target}" class="connect googleplus">
									<div class="connect__icon">
										<i class="fa fa-google-plus"></i>
									</div>
									<div class="connect__context">
										<span>Sign in with <strong>Google+</strong></span>
									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script>
		var LoginModalController = {
			tabsElementName : ".logmod__tabs li",
			tabElementName : ".logmod__tab",
			inputElementsName : ".logmod__form .input",
			hidePasswordName : ".hide-password",

			inputElements : null,
			tabsElement : null,
			tabElement : null,
			hidePassword : null,

			activeTab : null,
			tabSelection : 0, // 0 - first, 1 - second

			findElements : function() {
				var base = this;

				base.tabsElement = $(base.tabsElementName);
				base.tabElement = $(base.tabElementName);
				base.inputElements = $(base.inputElementsName);
				base.hidePassword = $(base.hidePasswordName);

				return base;
			},

			setState : function(state) {
				var base = this, elem = null;

				if (!state) {
					state = 0;
				}

				if (base.tabsElement) {
					elem = $(base.tabsElement[state]);
					elem.addClass("current");
					$("." + elem.attr("data-tabtar")).addClass("show");
				}

				return base;
			},

			getActiveTab : function() {
				var base = this;

				base.tabsElement.each(function(i, el) {
					if ($(el).hasClass("current")) {
						base.activeTab = $(el);
					}
				});

				return base;
			},

			addClickEvents : function() {
				var base = this;

				base.hidePassword.on("click", function(e) {
					var $this = $(this), $pwInput = $this.prev("input");

					if ($pwInput.attr("type") == "password") {
						$pwInput.attr("type", "text");
						$this.text("Hide");
					} else {
						$pwInput.attr("type", "password");
						$this.text("Show");
					}
				});

				base.tabsElement.on("click", function(e) {
					var targetTab = $(this).attr("data-tabtar");

					e.preventDefault();
					base.activeTab.removeClass("current");
					base.activeTab = $(this);
					base.activeTab.addClass("current");

					base.tabElement.each(function(i, el) {
						el = $(el);
						el.removeClass("show");
						if (el.hasClass(targetTab)) {
							el.addClass("show");
						}
					});
				});

				base.inputElements.find("label").on("click", function(e) {
					var $this = $(this), $input = $this.next("input");

					$input.focus();
				});

				return base;
			},

			initialize : function() {
				var base = this;

				base.findElements().setState().getActiveTab().addClickEvents();
			}
		};

		$(document).ready(function() {
			LoginModalController.initialize();
		});
	</script>

</body>
</html>
