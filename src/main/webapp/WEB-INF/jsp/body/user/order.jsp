<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tag/HBTag.tld" prefix="hb"%>
<div>
<div class="padding10">
	<legend>View your orders</legend>
</div>
<div>
<table class="table table-striped">
        <thead>
          <tr>
            <th>#</th>
            <th>Order SN</th>
            <th>Price</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td><a href="">Order #1</a></td>
            <td>$10</td>
            <td>PENDING</td>
          </tr>
          <tr>
            <td>2</td>
            <td><a href="">Order #2</a></td>
            <td>$20</td>
            <td>PAID</td>
          </tr>
          <tr>
            <td>3</td>
            <td><a href="">Order #3</a></td>
            <td>$120</td>
            <td>COMPLETED</td>
          </tr>
        </tbody>
      </table>
</div>
</div>