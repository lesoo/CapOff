<div align="right">
<img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/capoff_logo.png?raw=true" width="500px" >
</div>

# Cap-Off
### 경매 방식의 중고거래 앱
<br>

## 개발환경, 언어
 ![AndroidStudio](https://img.shields.io/badge/AndroidStudio-%5044ff44?style=for-the-badge&logo=Android&logoColor=white)
 ![VisualStudio](https://img.shields.io/badge/VisualStudio-%23777BB4?style=for-the-badge&logo=VisualStudio&logoColor=white)

 ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
 ![PHP](https://img.shields.io/badge/php-%23777BB4.svg?style=for-the-badge&logo=php&logoColor=white) 
 ![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54)
  ![Kotlin](https://img.shields.io/badge/kotlin-%23ED8B00.svg?style=for-the-badge&logo=kotlin&logoColor=white)

 ![MySQL](https://img.shields.io/badge/MySQL-%23666699.svg?style=for-the-badge&logo=MySQL&logoColor=white)
  ![DynamoDB](https://img.shields.io/badge/Amazon%20DynamoDB-4053D6?style=for-the-badge&logo=Amazon%20DynamoDB&logoColor=white)
  ![API](https://img.shields.io/badge/Amazon%20APIGateway-%23ED8B00.svg?style=for-the-badge&logo=Amazom%20APIGateway&logoColor=white)
  ![Lambda](https://img.shields.io/badge/Amazom%20lambda-%23ED8B00.svg?style=for-the-badge&logo=Amazom%20Lambda&logoColor=white)
 ![EC2](https://img.shields.io/badge/Amazom%20EC2-%232222bb.svg?style=for-the-badge&logo=Amazom%20EC2&logoColor=white)
  ![Node.js](https://img.shields.io/badge/nodejs-%2344f8844?style=for-the-badge&logo=nodejs&logoColor=white)
![FCM](https://img.shields.io/badge/FirebaseCloudeMessaging-%23fd8B00?style=for-the-badge&logo=fcm&logoColor=white)
  
<br>

## 데이터 구조

### SQL 데이터 베이스 ERD

<img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/SQL_ERD.png?raw=true" width="100%">

### Dynamo 채팅 데이터 베이스 구성

<img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/DynamoDB.PNG?raw=true" width="100%">
<br>

## 시나리오
<table>
	<tr><td>시작화면</td><td>로그인</td><td>메인</td></tr>
	<tr><td>
		<img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/initial.PNG?raw=true" width="100%"></td>
	<td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/login.PNG?raw=true" width="100%">
		</td><td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/main.PNG?raw=true" width="100%">
		</td></tr>	
	<tr><td>리스트</td><td>물품 등록</td><td>경매</td>
	</td></tr>
	<tr><td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/list.PNG?raw=true" width="100%"></td>
	<td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/regist.PNG?raw=true" width="100%"></td>
		<td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/oction.PNG?raw=true" width="100%"></td>
	<tr><td>채팅</td><td>위치전송</td>
	</tr><tr>
	<td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/chat.PNG?raw=true" width="100%"></td>
	<td><img src="https://github.com/lesoo/CapOff/blob/main/Cap-Off_img/map.PNG?raw=true" width="100%"></td>
	</tr>
</table>


<br>

## 역할
- 데이터베이스 설계/구현/쿼리
	- 설계/ERD작성
	- 구현, RDS 연결
	- Android Studio 연동
	- 입/출력/색인 쿼리 
	- 
<br>

-  경매기능 구현
	- 상품 상세 출력
	- 경매 기능 구현
	- 모든 쿼리 php/java 파일 작성

<br>

- 디자인 레이아웃 xml파일 수정

<br>

## 개발 시 어려웠던 점
쿼리를 할 때마다 대응하는 php를 만들고, 불러와야 해서 불편했다.
 - 입/출력, 업데이트별로 하나의 php파일로 통일하는 방법을 찾게되면 좋을 것 같다.

<br>

파일 공유를 자주 하지 않아 각자 작성하는 파일마다 변수명이 달라 불편했다.
 - git을 통해 2학기에는 파일 공유가 빨라 변수명을 통일할 수 있었다. 

<br>
 
디자인을 초기에 정해두지 않아 계속해서 변경사항이 생기고 결국은 다시 디자인 하느라 인력과 시간이 낭비됐다
 - 처음부터 확실하게 디자인을 픽스하고 진행했어야 했다.

<br>
 
계획이나 구현 계획이 확실하지 않아, 계속해서 변경 사항이 생겨 초기 기획보다 부실한 결과물이 나왔다.
 - 처음부터 구체적인 계획이나 구현방식을 정하지 않고 진행해서 생긴 일이라고 생각한다.
 - 계획을 수립할 때 구체적인 세부 구성이나 구현방식을 생각해놔야겠다. 

<br>

앱에서의 이미지 업로드/다운로드, 입/출력이 어렵다는 이야기만 듣고 해보지도 않고 차선책을 생각했다
 - 쉬웠다. 코딩은 역시 해봐야 알수 있다.

<br>

상세페이지를 출력할 때 쿼리결과를 한줄만 뽑아서 표시하도록 쿼리를 했는데, 결과값을 가져오는 것이 리스트 형식밖에 안됐다.
 - 어차피 한줄만 뽑아오니까 상세페이지를 한 블록으로 만들어 리스트 출력을 했다. 
 - 리스트 말고 배열로 가져와서 페이지에 출력하는 것이 가능한 방법이 있는지 아직도 알 수 없다. 
 - 시간이 난다면 프로젝트를 디벨롭하면서 찾아봐야겠다

<br>
 
디자인이 정말정말 심각하게 구리다
- 디벨롭할때 1순위 수정대상이다. 
- 흰배경에 검은테두리만 했어도 이렇게까지는 안 구릴텐데. 
- 조장님이 3달동안 디자인해온거라 차마 되돌려보낼수가 없었다...
- 내가 레퍼런스를 엄청나게 보냈는데 결과물이 이거라니.. 
- 믿기지 않았지만, 내가 고치면 그만이다
- 졸업작품은 끝났지만 고쳐봐야겠다.
