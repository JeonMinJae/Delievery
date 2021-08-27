# Delievery - 배달앱
![main_launcher](https://user-images.githubusercontent.com/38930501/128635946-7e9f5c9a-c97f-447c-8cfd-11f497fb95fc.png)

# 아키텍처

 <img width="266" alt="화면 캡처 2021-07-12 105719" src="https://user-images.githubusercontent.com/38930501/125222021-2aa89e00-e304-11eb-9352-a4144083fd1a.png">

# 사용될 기술 및 라이브러리
* Presentation Layer
  - Activity
  - Fragment
  - ViewModel
  - LiveData
  - GOF State Pattern

* Repository Layer
  - Remote Data Source(Retrofit2)
  - Local Data Source(Room)

* Common
  - Coroutines
  - Android X
  - Koin (for Light-Weight DI)
  - View-Binding

* Third-Party Library
  - Glide, Glide-Transformation
  - Retrofit2
  - OkHttp3
  - Google Maps, Location
  - Firebase Auth, Firebase Store




# 실제화면

|메인화면(식당 리스트)|위치 변경 화면|정렬 기능 > 별점 높은 순|장바구니 추가 > 홈화면에 반영 된 장바구니 버튼|
|---|---|---|---|
|![Screenshot_20210817-115632](https://user-images.githubusercontent.com/38930501/129658036-916671cd-0807-48d2-983f-e92a889ca84b.png)|![Screenshot_20210817-115648](https://user-images.githubusercontent.com/38930501/129658058-3373917c-661f-4026-9fec-94d22b9a2489.png)|![Screenshot_20210817-115915](https://user-images.githubusercontent.com/38930501/129658095-7aa2f9dc-d5ea-4b1f-91ef-fd45cde057bb.png)|![Screenshot_20210817-120256](https://user-images.githubusercontent.com/38930501/129659067-255ce87c-5260-410b-957e-5dba2ad694f0.png)|

|식당 상세화면|찜 버튼 클릭|찜 탭| 스크롤 시 툴바 제목 애니메이션|
|---|---|---|---|
|![Screenshot_20210817-115950](https://user-images.githubusercontent.com/38930501/129658449-dd2b47e2-9141-478c-9e09-01af88513159.png)|![Screenshot_20210817-120005](https://user-images.githubusercontent.com/38930501/129658458-46f823bf-3613-4c4f-b55f-854a71605f3f.png)|![Screenshot_20210823-105927](https://user-images.githubusercontent.com/38930501/130381666-4ba3d77e-fdf9-4a2e-8799-dbf19e11b482.png)|![Screenshot_20210817-120036](https://user-images.githubusercontent.com/38930501/129658535-5d2c15e7-3cc4-4584-9b72-e58d920637de.png)|

|식당 상세화면 > 전화|식당 상세화면 > 공유|식당 상세화면 > 장바구니 추가|장바구니>주문화면|
|---|---|---|---|
|![Screenshot_20210817-120054](https://user-images.githubusercontent.com/38930501/129658876-7d489585-ad8d-4561-a8b0-554aa5489cdb.png)|![Screenshot_20210817-120159](https://user-images.githubusercontent.com/38930501/129658894-0f2ff8e1-ff16-41c3-9757-fb99ea3a3604.png)|![Screenshot_20210817-120238](https://user-images.githubusercontent.com/38930501/129658968-cbbcb15b-83ab-4034-8b26-ff37b95f0777.png)|![Screenshot_20210823-110010](https://user-images.githubusercontent.com/38930501/130381808-f0f1d222-9ce8-4ba9-a8fd-6e7a08910cc4.png)|

|리뷰탭|다른 식당 상세화면 > 장바구니 담을 때 기존 비우기|장바구니 담기 > 로그인 되어 있지 않으면 프로필 탭 이동|주문내역 리스트|
|---|---|---|---|
|![Screenshot_20210823-110206](https://user-images.githubusercontent.com/38930501/130381951-93996b04-e2e0-4060-903a-da8a96711a3a.png)|![Screenshot_20210817-120316](https://user-images.githubusercontent.com/38930501/129659080-6e4bab65-ddef-4ecc-a929-557f344263eb.png)|![Screenshot_20210817-120344](https://user-images.githubusercontent.com/38930501/129659098-1d1a0d7b-f8e1-4f17-bd30-6d84baf894da.png)|![Screenshot_20210827-110237](https://user-images.githubusercontent.com/38930501/131060821-3e4fe834-6b46-43cf-915c-8f86ca242497.png)|
