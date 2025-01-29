<%@page import="kr.or.iei.aside.model.vo.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.iei.weather.model.vo.Weather"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Weather weather = (Weather)session.getAttribute("weather");
	ArrayList<Product> ProductList = (ArrayList<Product>)session.getAttribute("productList");
%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
.weather-box{
	width : 200px;
	border: 1px solid black;
	background-color: black;
}
.weather-title{
	text-align: center;
	color:white;
}
.weather-body{
	display: flex;
}
.weather-img{
	width : 50%;
}
.weather-text{
	font-size : 20px;
	width : 50%;
	text-align: center;
	color:white;
}
/* Container for the aside content */
.aside-container {
    border: 1px solid black;
    width: 280px;
    background-color: gray;
    padding: 10px;
    box-sizing: border-box; /* Includes padding in the width */
    color: white; /* Text color for better contrast */
}

/* Title styling */
.aside-container h2 {
    font-size: 18px;
    margin-bottom: 15px;
    text-align: center;
    border-bottom: 2px solid white; /* Add a separator under the title */
    padding-bottom: 5px;
}

/* Each product item container */
.aside-content {
    display: flex;
    align-items: flex-start;
    margin-bottom: 15px;
    border: 1px solid white;
    padding: 10px;
    border-radius: 5px;
    background-color: #444; /* Slightly darker background for items */
    color: #fff;
}

/* Product image styling */
.aside-image-box {
    width: 80px;
    height: 80px;
    border-radius: 5px;
    object-fit: cover; /* Ensures the image fits neatly */
    margin-right: 10px;
}

/* Product details container */
.aside-tags {
    display: flex;
    flex-direction: column; /* Arrange tags vertically */
    justify-content: space-between; /* Evenly distribute space */
    flex: 1;
    margin-left: 10px;
}

/* Individual tag styling */
.aside-tag {
    font-size: 14px;
    margin-bottom: 5px;
    color: #ddd; /* Softer text color for tags */
}

.aside-tag:nth-child(1) {
    font-weight: bold;
    color: #ffd700; /* Highlight price in gold */
}
</style>

<aside>
	<div class="weather-box">
		<div class="weather-title">
			<span>서울</span>
		</div>
		<div class="weather-body">
			<div class="weather-img">
				<%-- 강수 유형에 따른 이미지 출력 --%>
				<c:choose>
					<c:when test="${weather.prep == 1}">
						<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50"
							fill="currentColor" class="bi bi-cloud-rain-heavy-fill"
							viewBox="0 0 16 16">
					  <path	d="M4.176 11.032a.5.5 0 0 1 .292.643l-1.5 4a.5.5 0 0 1-.936-.35l1.5-4a.5.5 0 0 1 .644-.293m3 0a.5.5 0 0 1 .292.643l-1.5 4a.5.5 0 0 1-.936-.35l1.5-4a.5.5 0 0 1 .644-.293m3 0a.5.5 0 0 1 .292.643l-1.5 4a.5.5 0 0 1-.936-.35l1.5-4a.5.5 0 0 1 .644-.293m3 0a.5.5 0 0 1 .292.643l-1.5 4a.5.5 0 0 1-.936-.35l1.5-4a.5.5 0 0 1 .644-.293m.229-7.005a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 10H13a3 3 0 0 0 .405-5.973" fill="gray"/>
					</svg>
					</c:when>
					<c:when test="${weather.prep == 2}">
						<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50"
							fill="currentColor" class="bi bi-cloud-sleet-fill"
							viewBox="0 0 16 16">
					  <path
								d="M2.375 13.5a.25.25 0 0 1 .25.25v.57l.501-.287a.25.25 0 0 1 .248.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 0 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zM6.375 13.5a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 0 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zm2.151 2.447a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 0 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 1 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zm1.181-7.026a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 10H13a3 3 0 0 0 .405-5.973" />
					</svg>
					</c:when>
					<c:when test="${weather.prep == 3}">
						<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50"
							fill="currentColor" class="bi bi-cloud-snow-fill"
							viewBox="0 0 16 16">
					  <path
								d="M2.625 11.5a.25.25 0 0 1 .25.25v.57l.501-.287a.25.25 0 0 1 .248.434l-.495.283.495.283a.25.25 0 0 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 0 1-.248-.434l.495-.283-.495-.283a.25.25 0 0 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m2.75 2a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 0 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 0 1-.248-.434l.495-.283-.495-.283a.25.25 0 0 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m5.5 0a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 0 1-.248.434l-.501-.286v.569a.25.25 0 0 1-.5 0v-.57l-.501.287a.25.25 0 0 1-.248-.434l.495-.283-.495-.283a.25.25 0 0 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m-2.75-2a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 0 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 0 1-.248-.434l.495-.283-.495-.283a.25.25 0 0 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m5.5 0a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 0 1-.248.434l-.501-.286v.569a.25.25 0 0 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 0 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m-.22-7.223a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 10.25H13a3 3 0 0 0 .405-5.973" />
					</svg>
					</c:when>
					<c:when test="${weather.prep == 4}">
						<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50"
							fill="currentColor" class="bi bi-cloud-sleet-fill"
							viewBox="0 0 16 16">
					  <path
								d="M2.375 13.5a.25.25 0 0 1 .25.25v.57l.501-.287a.25.25 0 0 1 .248.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 0 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zM6.375 13.5a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 0 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zm2.151 2.447a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 0 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25m1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 1 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zm1.181-7.026a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 10H13a3 3 0 0 0 .405-5.973" />
					</svg>
					</c:when>

					<%-- prep이 0일 때 wf 값에 따른 이미지 출력 --%>
					<c:otherwise>
						<c:choose>
							<%-- 흐림 --%>
							<c:when test="${weather.sky == 'DB04'}"> //
								<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"
									fill="currentColor" class="bi bi-cloud-sun-fill"
									viewBox="0 0 16 16">
										<%-- 구름을 회색으로 변경 --%>
							  			<path d="M11.473 11a4.5 4.5 0 0 0-8.72-.99A3 3 0 0 0 3 16h8.5a2.5 2.5 0 0 0 0-5z" fill="gray" />
							  
							  			<%-- 해를 노란색으로 변경 --%>
							  			<path d="M10.5 1.5a.5.5 0 0 0-1 0v1a.5.5 0 0 0 1 0zm3.743 1.964a.5.5 0 1 0-.707-.707l-.708.707a.5.5 0 0 0 .708.708zm-7.779-.707a.5.5 0 0 0-.707.707l.707.708a.5.5 0 1 0 .708-.708zm1.734 3.374a2 2 0 1 1 3.296 2.198q.3.423.516.898a3 3 0 1 0-4.84-3.225q.529.017 1.028.129m4.484 4.074c.6.215 1.125.59 1.522 1.072a.5.5 0 0 0 .039-.742l-.707-.707a.5.5 0 0 0-.854.377M14.5 6.5a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1z"
										fill="yellow" />
							</svg>
							</c:when>
							
							<%-- 맑음 --%>
							<c:when test="${weather.sky == 'DB01'}">
								<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100"
									fill="currentColor" class="bi bi-brightness-high-fill"
									viewBox="0 0 16 16">
							  		<path d="M12 8a4 4 0 1 1-8 0 4 4 0 0 1 8 0M8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0m0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13m8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5M3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8m10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0m-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0m9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707M4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708" fill="yellow"/>
								</svg>
							</c:when>
							
							<%-- 구름조금 --%>
							<c:when test="${weather.sky == 'DB02'}"> 
								<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="gray" class="bi bi-cloud-fill" viewBox="0 0 16 16">
  									<path d="M4.406 3.342A5.53 5.53 0 0 1 8 2c2.69 0 4.923 2 5.166 4.579C14.758 6.804 16 8.137 16 9.773 16 11.569 14.502 13 12.687 13H3.781C1.708 13 0 11.366 0 9.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383"/>
								</svg>
							</c:when>
							
							<%-- 구름많음 --%>
							<c:when test="${weather.sky == 'DB03'}">
								<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" fill="gray" class="bi bi-clouds-fill" viewBox="0 0 16 16">
								  <path d="M11.473 9a4.5 4.5 0 0 0-8.72-.99A3 3 0 0 0 3 14h8.5a2.5 2.5 0 1 0-.027-5"/>
								  <path d="M14.544 9.772a3.5 3.5 0 0 0-2.225-1.676 5.5 5.5 0 0 0-6.337-4.002 4.002 4.002 0 0 1 7.392.91 2.5 2.5 0 0 1 1.17 4.769z"/>
								</svg>
							</c:when>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<%-- 온도 및 날씨 상태 출력 --%>
			</div>
			<div class="weather-text">
				<span>${weather.ta}°C</span><br> <span>${weather.wf}</span> <%-- ta= 기온, wf=날씨 (ex.흐림,맑음, 흐리고 비 등등) --%>
			</div>
		</div>
	</div>
	<div class="aside-container">
    <h2>네이버 쇼핑</h2>
    <% for(int i = 0; i < ProductList.size(); i++) { %>
    <div>
        <a href="<%= ProductList.get(i).getShopLink() %>">
            <%= ProductList.get(i).getShopTitle() %>
        </a>
    </div>
    <div class="aside-content">
        <img src="<%= ProductList.get(i).getShopImg() %>" class="aside-image-box" alt="Product Image">
        <div class="aside-tags">
            <div class="aside-tag">
                <%= ProductList.get(i).getShopLowPrice() %>원!!
            </div>
            <div class="aside-tag">
                판매처: <%= ProductList.get(i).getShopName() %>
            </div>
            <div class="aside-tag">
                제품 카테고리: <%= ProductList.get(i).getShopCategory1() %>
            </div>
        </div>
    </div>
    <% } %>
</div>
</aside>