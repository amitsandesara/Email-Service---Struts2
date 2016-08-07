<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Test Email</title>
<style>
#top {
	position: fixed;
	width: 100%;
	height: 100px; /* See #wrapper bottom value */
	/* 	background: #ddd; */
	padding-left: 20px;
	padding-bottom: 10px;
	padding-left: 20px
}

#bottom {
	background: #fff;
	position: fixed;
	width: 100%; /* (100% cause of fixed) */
	bottom: 0;
	padding-left: 0px;
	height: 40%; /* see #page bottom value */
}
</style>


</head>

<body>
	<div id="top">
		<ul>

			<li><a class="link" href="#about" data-link="first">Compose
			</a></li>
			<li><a class="link" href="#about" data-link="second">Inbox</a></li>
			<li><a class="link" href="#about" data-link="third">Sent</a></li>
			<li><a class="link" href="#about" data-link="fourth">Trash</a></li>
			<li><a class="link" href="#about" data-link="fifth">Search</a></li>
		</ul>
	</div>
	<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
	<script src="http://code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script>
		$(document).ready(function() {
			$('.viewEmails').hide();
			$('.viewEmails[data-link=fifth]').show();

		});

		$('.link').click(function() {
			$('.viewEmails').hide();
			$('.viewEmails[data-link=' + $(this).data('link') + ']').show();
		});
	</script>

	<div id="bottom">
		<div class="viewEmails" data-link="first">
			<pre>
        Compose Button Clicked
        Dummy data:
        123 Street, Freeway,
        SomeState, Country.
      </pre>
		</div>
		<div class="viewEmails" data-link="second">
			<p>Inbox clicked</p>
		</div>
		<div class="viewEmails" data-link="third">
			<p>Sent Email Clicked</p>
		</div>
		<div class="viewEmails" data-link="fourth">
			<pre>
				Trash clicked;
			      But showing some nicely 
			      formated text
			      </pre>
		</div>
		<div class="viewEmails" data-link="fifth">
			<center>
				<h4>Search not yet implemented</h4>
			</center>
		</div>
	</div>
</body>