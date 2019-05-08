<h1>SQLearn</h1>
<ul>
  <li>개발기간 : 2019-02-17 ~ 2019-02-24</li>
  <li>인원 : 1명</li>
  <li>개발환경 : JavaSE, Oracle</li>
</ul>

<table style="text-align:center;">
  <tr>
    <td>UI</td>
    <td>상세 설명</td>
  </tr>
  <tr>
    <td>
    <img src="https://user-images.githubusercontent.com/47166170/57373230-d90ba280-71d2-11e9-8a9a-80f43b0d92a5.PNG" width="250px"/>
    </td>
    <td>
      ▶ 프로그램 최초 실행 메인화면<br>
      <ul>
        <li>JDBC를 이용해 Connection을 얻어온 후 파일의 로그인정보를 눌러 오라클 계정접속 확인</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
    <img src="https://user-images.githubusercontent.com/47166170/57373270-f2145380-71d2-11e9-8666-97fea778726b.PNG" width="250px"/>
    </td>
    <td>
      ▶ Login Page<br>
      <ul>
        <li>오라클 내에 등록되어있는 계정들을 확인 할 수 있음</li>
        <li>계정선택후 알맞은 비밀번호를 입력하면 해당 계정으로 오라클 접속수행, 계정테이블을 확인 할 수 있음</li>
        <table>
          <tr>
            <td colspan="2">로그인 후 계정테이블 창</td>
          </tr>
          <tr>
            <td>
            <img src="https://user-images.githubusercontent.com/47166170/57373449-546d5400-71d3-11e9-8189-45c62968c1ad.PNG" width="250px"/> 
            </td>
            <td>
              <img src="https://user-images.githubusercontent.com/47166170/57373571-aa41fc00-71d3-11e9-8c49-026491756efa.PNG" width="250px"/>
            </td>
          </tr>
        </table>
        <li>이 후 원하는 테이블을 누르면 preparestatement에 select문을 담아 쿼리 수행 + 메타데이터를 담아 resultset.next()를 통해/li>
        <li>데이터를 끌고옴, 이후 Swing에 정의한 테이블 모델에 덮어 씌워서 보여줌</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
    <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfMTAz/MDAxNTU3MjA0MjExMTU0.J1VdMLUXK7_cHZ8GoewasLqA537RdOzVjLcSLhszzggg.MgHbPQXdWDZKP0gxXtXNfQKTrCAmiJaQ5OzQ3RP0LzUg.PNG.phh_92/game_main.png?type=w2" width="250px"/>
    </td>
    <td>
      ▶ Game Page<br>
      <ul>
        <li>서브권이 결정되고 나서의 화면</li>
        <li>붉은색 Stick 은 Com, 파란색 Stick 은 Player 이다.</li>
        <li>Com 의 Stick 은 setInterval() Method 를 통해 일정한 속도로 좌우로 움직임.</li>
        <li>각 Stick 에는 충돌 Check Method 가 적용되어있는 Sensor 들로 감싸져 있음</li>
        <li>각 Puck 은 이동시 Stage 의 벽과 Puck 에 충돌시 속도가 조금씩 줄어듦</li>
        <li>Puck 뒤 Goal Line 에 또한 충돌 Check 가 적용된 Sensor 가 있어서 충돌시 Goal 처리 및 Score 변경됨</li>
        <table>
          <tr>
            <td colspan="2">Goal 처리 이후 Score 변경 처리 화면</td>
          </tr>
          <tr>
            <td>
            <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfNjMg/MDAxNTU3MjA0MzIyMTUz.C7PsSgY83hRLyoJNnIoM65yz_4eLkZDwVFiInLft68Ig.LpP4bbai4nRF1ohsNC9hveK8Gk-iXtRp3CXJFVTY_cEg.PNG.phh_92/Goal.png?type=w2" width="250px"/> 
            </td>
            <td>
              <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfMjA2/MDAxNTU3MjA0MzIyNDE1.aA54p_XHn0uEOcoyRq7W1vATWhX4FZc0da-pKmy1evQg.Nx-kMfIMtE5Swk_RYfPxP-ijBQ3y1ZsTjN-CgeNu-KEg.PNG.phh_92/socre.png?type=w2" width="250px"/>
            </td>
          </tr>
        </table>
      </ul>
    </td>
  </tr>
</table>
<img src="https://user-images.githubusercontent.com/47166170/57341817-c82d4380-7176-11e9-8332-0e5baca43bbb.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341859-e98e2f80-7176-11e9-8c7a-519278c68185.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341868-f3179780-7176-11e9-9f71-b3d254d2a41b.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341874-00348680-7177-11e9-8e0c-e15dbd211ad9.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341880-0c204880-7177-11e9-9b13-902b2435a3e9.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341891-1b06fb00-7177-11e9-9560-83db02e854f7.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341905-28bc8080-7177-11e9-975f-64945a9d6755.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341922-340fac00-7177-11e9-8161-be288e693b9d.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341934-44278b80-7177-11e9-926a-0f07a8f5f60c.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341945-4d185d00-7177-11e9-9578-005415f6eca5.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341958-5bff0f80-7177-11e9-8eb4-c9d46f44e346.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341966-68836800-7177-11e9-9ad1-872e7ace70ed.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341975-720cd000-7177-11e9-9a23-7770477567db.PNG" width="800px" height="400px">
<img src="https://user-images.githubusercontent.com/47166170/57341983-7933de00-7177-11e9-856d-c9fd5efd50ee.PNG" width="800px" height="400px">

