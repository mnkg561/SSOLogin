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
					<li data-tabtar="lgm-2"><a href="#">Open OTP</a></li>
				</ul>
				<div class="logmod__tab-wrapper">
					<div class="logmod__tab lgm-2">
						<div class="logmod__heading">
							<span class="logmod__heading-subtitle"> <strong>Please enter your one time password</strong>
							</span>
						</div>
						<div class="logmod__form">
							<form:form method="post" modelAttribute="oneTimePassword"
								class="simform">
								<div class="sminputs">
									<div class="input full">
										<form:label class="string optional" path="oneTimePassword">Open OTP*</form:label>
										<form:input path="oneTimePassword" placeholder="oneTimePassword"
											class="string optional" maxlength="255" size="50" />
									</div>
								</div>
								<div class="simform__actions">
									<input class="sumbit" name="commit" type="submit"
										value="Submit" />
								</div>
							</form:form>
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
