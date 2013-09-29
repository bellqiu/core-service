<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="main-header">
	<div class="row-fluid">
	<div class="logoAndBannerAndSearchBox">
		<div class="span2 header-logo">logo</div>
		<div class="span6 header-banner">banner</div>
		<div class=" span2 offset2 header-searchBox">
			<input title="Search by keyword" id="searchBox" class="ui-autocomplete-input" autocomplete="off">
		</div>
	</div>
	</div>
</div>

<script>
$(function() {
	var availableTags = [
	"ActionScript",
	"AppleScript",
	"Asp",
	"BASIC",
	"C",
	"C++",
	"Clojure",
	"COBOL",
	"ColdFusion",
	"Erlang",
	"Fortran",
	"Groovy",
	"Haskell",
	"Java",
	"JavaScript",
	"Lisp",
	"Perl",
	"PHP",
	"Python",
	"Ruby",
	"Scala",
	"Scheme"
	];
	$( "#searchBox" ).autocomplete({
	source: availableTags
	});
});
</script>