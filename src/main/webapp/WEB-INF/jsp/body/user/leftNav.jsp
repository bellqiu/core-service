<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="list-group"> 
	 <a href="/od/myOrder" id="myOrder" class="list-group-item ${(empty page || page=='order')?'active':''}">My Order</a>
	 <a href="/ac/changePwd" id="changePwd" class="list-group-item ${(not empty page && page=='password')?'active':''}">Change Password</a>
	 <a href="/ac/address" id="manageAddr" class="list-group-item ${(not empty page && page=='address')?'active':''}">Manage Address</a>
</div>