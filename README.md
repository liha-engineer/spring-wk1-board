# 주특기 입문주차 - Spring으로 게시판을 만들어 봅시다🌱
* Entity를 그대로 반환하지 말고, DTO에 담아서
* 최근의 경향성은 프론트와 백의 '느슨한 결합'
* 서버 쪽만 구현해도 되므로, index.html 대신 PostMan을 이용해서 데이터 송수신 결과 확인

---

## 요구사항 기반의 유즈케이스
![image](https://user-images.githubusercontent.com/122168823/217771183-822110f4-4b60-46a3-91e4-3475c90ced62.png)

---

## API 명세
![image](https://user-images.githubusercontent.com/122168823/217778385-63414999-495d-4019-9863-fe43e7101e1d.png)

---
## 과제 제출시 고민해볼 질문들

### 1. 수정, 삭제 API의 request 방식? (param, query, body)
-> ~~일단 query는 아닌 것 같고~~ param과 body를 사용하였다. <br>
Controller 부분을 보면, return type이 ~ResponseDto로서 JSON형태의 body 형식 데이터를 반환함을 알 수 있다. <br>
또한, 수정과 삭제 API의 매개변수에 @PathVariable Long id가 들어감으로서 param 형식도 사용된 것을 알 수 있다. <br>

### 2. 어떤 상황에 어떤 방식의 request를 써야 하나?
-> query는 RequestParam을 사용. 전달받은 데이터를 URI상에서 찾는 것. 일반적으로 URL에서 ? 뒷부분을 가져올때 사용한다. <br>
-> param은 PathVariable을 사용. URI 경로의 일부를 파라미터로 사용할 때 쓴다. http://babo.com/1234 라면 1234를 메소드 변수에 할당한다. <br>
-> body는 @RequestBody를 사용. HTTP 요청의 body에 담긴 값을 JAVA의 객체로 변환할 때 쓴다. JSON 기반의 메시지를 사용하는 요청에서 유용하다. <br>

### 3. RESTful한 API를 설계했나? 그렇다면 / 그렇지 않다면 어느 부분이?
-> 일단 REST가 뭔지부터 짚고 가자. <br>
* Representational State Transfer : 자원을 이름(자원의 표현)으로 구분하여 해당 자원의 상태(정보)를 주고 받는 모든 것을 의미. <br>
즉, 자원(resource)의 표현(representation) 에 의한 상태 전달. <br>
삭제하려면 DELETE, 게시하려면 POST, 얻어오려면 GET 등의 형태로 주고받고 하는 것.<br>

* RESTful하게 설계한 부분 <br>
-> 자원에 대한 행위는 HTTP Method로 표현했고, URI에 행위에 대한 동사표현이 들어가지 않았다. <br>
-> 자원의 명칭으로 동사보다 명사, 대문자보다 소문자를 사용했다. <br>
-> 자원의 도큐먼트 이름은 단수, 컬렉션 이름은 복수를 사용했다. <br>
-> 경로 중 변하는 부분은 유일한 값으로 대체했다. (id) <br>
-> 슬래시 구분자로 계층 관계를 나타냈고, 마지막 문자로 슬래시를 포함하지 않았다. <br>

* RESTful하게 설계되지 않은 부분 <br>
-> 왠지 있을 것 같은데, 아직 발견하진 못했다. <br>

### 4. 적절한 관심사 분리를 사용했는지?
-> Controller, Repository, Service를 분리했고, 각각 데이터를 주고받을 DTO를 별도 구현했다. <br>
