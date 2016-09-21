<%@page import="java.util.List"%>
<%@page import="com.ktds.vo.EmployeesVO"%>
<%@page import="com.ktds.dao.HRDao"%>
<%@page import="com.ktds.dao.HRDaoImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	HRDao dao = new HRDaoImpl();
	List<EmployeesVO> employees =dao.getEmployees();

%>
    
    
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HR</title>
</head>
<body>

	<h1>EMPLOYEES</h1>
	<hr/>
	<a href="http://www.naver.com">네이버로가기</a>,
	<a href="http://www.daum.net">다음으로가기</a>,
	<a href="http://www.google.com" target="_blank">구글로가기</a>
	
	
	<a href="./index.jsp?name=jhlee">index.jsp로 가기</a>
	<table style="width: 100%; border-collapse: collapse; border: 1px solid #000000; background-color: #CCCCCC;">
		<tr style="heigtht: 50px;">
			<th>EMPLOYEE_ID</th>
			<th>FIRST_NAME</th>
			<th>LAST_NAME</th>
			<th>EMAIL</th>
			<th>PHONE_NUMBER</th>
			<th>HIRE_DATE</th>
			<th>JOB_ID</th>
			<th>SALARY</th>
			<th>COMMISSION_PCT</th>
			<th>MANAGER_ID</th>
			<th>DEPARTMENT_ID</th>
		</tr>
<%
	int employeesSize = employees.size();
	EmployeesVO employee = null; //사원 하나 받기위한 임시 객체
	for(int i = 0 ; i < employeesSize ; i++) {
		employee = employees.get(i);
%>
		<tr>
			<td><%=employee.getEmployeeId()%></td>
			<td><%=employee.getFirstName()%></td>
			<td><%=employee.getLastName() %></td>
			<td><%=employee.getEmail() %></td>
			<td><%=employee.getPhoneNumber() %></td>
			<td><%=employee.getHireDate() %></td>
			<td><%=employee.getJobId() %></td>
			<td><%=employee.getSalary() %></td>
			<td><%=employee.getCommissionPCT() %></td>
			<td><%=employee.getManagerId() %></td>
			<td><%=employee.getDepartmentId() %></td>
		</tr>
<%
	}
%>
	</table>
</body>
</html>