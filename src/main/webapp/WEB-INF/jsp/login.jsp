<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign in | OnlineBookStore</title>
</head>
<body>
	<div class="container">
		<form class="needs-validation" method="POST" action="${user-login}" novalidate>
			<div class="form-row">
				<div class="col-md-4 mb-3">
					<label for="validationCustom01"></label> <input type="text"
						class="form-control" id="validationCustom01" placeholder="Email"
						name="email" required>
					<div class="invalid-tooltip">Please enter a valid email!</div>
				</div>

			</div>
			<div class="form-row">
				<div class="col-md-4 mb-3">
					<label for="validationCustom01"></label> <input type="text"
						class="form-control" id="validationCustom01"
						placeholder="Password" name="password" required>
					<div class="invalid-tooltip">Please enter a valid password!</div>
				</div>
			</div>
			<div class="form-row">
				<div class="col-md-4 mb-3 mt-4">
					<button class="btn btn-primary btn-block" type="submit">Sign
						in</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>