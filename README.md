# DATAMAP
DataMap use HashMap. 

###### `VO`를 대신 하여 간단한 프로젝트 사용시 `HashMap`을 쓸때 계속적으로 중복되는 코드를 모아서 만든 유틸성 클래스 입니다.

###### `Map`과 `Serializable`을 구현 하였고 내부 변수로 `HashMap`을 가지고 있습니다.

- 추가된 내역 정리
 + 생성자에 `Map`을 인자로 받아 복제 
 + `getKeys` 키 정보를 `String[]`로 전달
 + `getString`, 'getInt`, 'getDouble` 추가
 + `toString` 문자열로 해당 Key와 Value를 전달
 
 
