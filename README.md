<div align=center><h1> SMART CAMPUS 📱 </h1></div>

<div align=center>
NFC를 이용한 모바일 학생증
</div>

- 애플리케이션 설명 영상 보기 : https://www.youtube.com/watch?v=yb5o8MOWexc

# Stacks
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white"> 
<img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=Firebase&logoColor=white">
<img src="https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=SQLite&logoColor=white">

# Main function

### 리더기 기능과 모바일 학생증 로드 기능
- 리더기 페이지에 학생의 모바일 학생증을 태그하면, 모바일 학생증은 Android Beam을 사용해 학번 정보를 담은 NDEF메시지를 리더기에 전송합니다.
- 리더기는 태그로부터 받은 학번을 이용해 서버의 데이터베이스에서 해당 학생 정보를 불러옵니다.
- 만약 NFC가 비활성화 되어있으면, 모바일 학생증 로드가 불가능합니다.

### 학교 행사시, 학생 관리
- ‘학생회비 납부 여부’ 정보를 확인할 수 있고, 앱을 통해 ‘행사 참여 여부’를 관리해 행사에 참여하는 학생들을 관리할 수 있습니다.

### 로그인 정보에 따른 권한 부여
- 학생, 학생회, 교직원 별 데이터 접근 권한을 다르게 주었습니다.

### 출석 확인 및 출입 기록
- 강의실 입구의 리더기에 모바일 학생증을 태그해, 출석하거나 세부적으로 출입을 기록 할 수 있습니다.
- 교수님은 자신의 강의에 한정해 출석한 학생을 확인할 수 있습니다.
- 관리자는 모든 강의에 대해 출석(출입)한 학생을 확인할 수 있습니다.

### 결제시, 페이코 앱으로 이동
- 결제 메뉴를 통해 결제 앱으로 넘어가며, 해당 앱이 설치되어있지 않으면 설치 페이지로 넘어갑니다.
