<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	안녕하세요
	src/main/webapp 아래에 index.jsp를 만들어 넣으면,<br/>
	주소에 index.jsp를 적지 않아도 바로 볼 수 있다.<br/>
	다른 file 이름을 사용한다면, 주소에 파일 명과 .jsp를 적어야 한다.<br/>
	jsp 파일이 추가/수정/삭제했을 때 서버를 재시작 하지 않아도 브라우저에서 브라우저를 확인할 수 있습니다.<br/>
	
	<table border="1">
		<tr>
			<td>1-1</td>
			<td>1-2</td>
		</tr>
		<tr>
			<td>2-1</td>
			<td>2-2</td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td>1</td>
			<td colspan="5">2</td>
		<!-- 	<td>3</td>
			<td>4</td>
			<td>5</td>
			<td>6</td> -->
		</tr>
		<tr>
			<td rowspan="8">7</td>
			<td>8</td>
			<td>9</td>
			<td>10</td>
			<td>11</td>
			<td>12</td>
		</tr>
		<tr>
			<!-- <td>13</td> -->
			<td>14</td>
			<td>15</td>
			<td>16</td>
			<td>17</td>
			<td>18</td>
		</tr>
		<tr>
			<!-- <td>19</td> -->
			<td>20</td>
			<td colspan="2" rowspan="2">21</td>
			<!-- <td>22</td> -->
			<td>23</td>
			<td>24</td>
		</tr>
		<tr>
			<!-- <td>25</td> -->
			<td>26</td>
			<!-- <td>27</td> -->
			<!-- <td>28</td> -->
			<td>29</td>
			<td>30</td>
		</tr>
		<tr>
			<!-- <td>31</td> -->
			<td>32</td>
			<td>33</td>
			<td>34</td>
			<td>35</td>
			<td>36</td>
		</tr>
		<tr>
			<!-- <td>37</td> -->
			<td>38</td>
			<td>39</td>
			<td>40</td>
			<td>41</td>
			<td>42</td>
		</tr>
		<tr>
			<!-- <td>43</td> -->
			<td>44</td>
			<td>45</td>
			<td>46</td>
			<td>47</td>
			<td>48</td>
		</tr>
		<tr>
			<!-- <td>49</td> -->
			<td>50</td>
			<td>51</td>
			<td>52</td>
			<td>53</td>
			<td>54</td>
		</tr>
	</table>
	
	<table border="1">
		<tr>
			<td colspan="3"><b>LOGIN</b></td>
			<!-- <td></td>
			<td></td> -->
		</tr>
		<tr>
			<td>ID</td>
			<td colspan="2">아이디를입력해주세요</td>
		<!-- 	<td></td> -->
		</tr>
		<tr>
			<td>PWD</td>
			<th colspan="2">비밀번호를 입력하세요</th>
		<!-- 	<td></td> -->
		</tr>
		<tr>
			<td>취소</td>
			<td>회원가입</td>
			<td>로그인</td>
		</tr>
	</table>
</body>
</html>