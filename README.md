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
    <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfMjcw/MDAxNTU3MjAzMjMzNTIz.ZvnKTGgOzd1KIudYt--pfKkR3-U1DQOptrdE9jT2xX4g.bPEXwqcROZ5gF5CBwALsjZIiaroFv4RLbl1-yhwF6mMg.PNG.phh_92/airhockey_startPage.png?type=w2" width="250px"/>
    </td>
    <td>
      ▶ AirHockey Main Page<br>
      <ul>
        <li>Start Button Click 시 Game Main Page 로 이동!</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
    <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfODkg/MDAxNTU3MjAzMjMzMDA2.cosE2Y1wqiRG4Hi37uRoTzd8CoECTQRdOH4zCxBZXvcg.g4WxPlb2rx4cX-HDbMsKpWV5Nqi4kVDFwknwc9xML_Ug.PNG.phh_92/airhockey_gameMain.png?type=w2" width="250px"/>
    </td>
    <td>
      ▶ Game Main Page<br>
      <ul>
        <li>Game 의 서브권을 결정함</li>
        <li>select Button Click 시 내장객체 Math에 의해 추출된 난수값 (0, 1) 에 의해 서브권이 결정됨 </li>
        <table>
          <tr>
            <td colspan="2">서브권 결정 화면</td>
          </tr>
          <tr>
            <td>
            <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfNzcg/MDAxNTU3MjAzMjM0MDEz.Deu40I5FTo9dZi1hK5EPrW8c68Q4ap5WUBqVIOnc8_4g.f9F-IvDcFoyBo9fXKeFmfwg8ADSMq-RPuqtuqBZ_zxkg.PNG.phh_92/serve_com.png?type=w2" width="250px"/> 
            </td>
            <td>
              <img src="https://blogfiles.pstatic.net/MjAxOTA1MDdfMjgw/MDAxNTU3MjAzMjM0MjMx.at0j4EGPCXbpzIbuMMjNDT1v23JQO4mBbyhLIudW-AUg.kTPOxtkQScOToO8feXLyrrRbbCoNn-AkOaxf2FrbrC4g.PNG.phh_92/serve_player.png?type=w2" width="250px"/>
            </td>
          </tr>
        </table>
        <li>이 후에는 setTimeOut() Method 를 통해 div 을 제거 및 게임 시작/li>
        <li>Puck 의 위치는 서브권에 따라 다르게 설정됨</li>
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

