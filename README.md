<h1>SQLearn</h1>
<ul>
  <li>개발기간 : 2019-02-17 ~ 2019-02-24</li>
  <li>인원 : 1명</li>
  <li>개발환경 : JavaSE, Oracle</li>
  <li>개발목적 : 오라클의 편리한 접속 및 계정과 테이블 간편조회, CRUD의 흐름에 대해 알아봄</li>
</ul>

<table style="text-align:center;">
  <tr>
    <td>UI</td>
    <td>상세 설명</td>
  </tr>
  <tr>
    <td>
    <img src="https://user-images.githubusercontent.com/47166170/57341905-28bc8080-7177-11e9-975f-64945a9d6755.PNG" width="250px"/>
    </td>
    <td>
      ▶ 프로그램 구조<br>
    </td>
  </tr>
  <tr>
    <td>
    <img src="https://user-images.githubusercontent.com/47166170/57374572-28070700-71d6-11e9-8335-12cf55df9a4f.PNG" width="250px"/>
    </td>
    <td>
      ▶ 프로그램 최초 실행 메인화면<br>
      <ul>
        <li>JDBC를 이용해 Connection을 얻어온 후 파일의 로그인정보를 눌러 오라클 계정접속 확인</li>
        <table>
          <tr>
            <td colspan="1">오라클 접속을 담당하는 ConnectionManager</td>
          </tr>
          <tr>
            <td>
            <img src="https://user-images.githubusercontent.com/47166170/58018809-d0a25880-7b3e-11e9-9805-0020d06eae64.PNG" width="400px"/> 
            </td>
  </tr>
        </table>
      </ul>
    </td>
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
        <li>이 후 원하는 테이블을 누르면 preparestatement에 select문을 담아 쿼리 수행 + 메타데이터를 담아 resultset.next()를 통해 데이터를 끌고옴</li>
        <table>
          <tr>
            <td colspan="1">오라클 계정 내의 테이블 자료를 출력</td>
          </tr>
          <tr>
            <td>
            <img src="https://user-images.githubusercontent.com/47166170/58018811-d1d38580-7b3e-11e9-86da-e2cf339fc827.PNG" width="400px"/> 
            </td>
          </tr>
        </table>
        <li>이후 Swing에 정의한 테이블 모델에 덮어 씌워서 보여줌, 계정접속 후에는 쿼리문 입력후 수행도 가능.(단축키 ctrl+enter)</li>
        <table>
          <tr>
            <td colspan="1">쿼리문 수행</td>
          </tr>
          <tr>
            <td>
            <img src="https://user-images.githubusercontent.com/47166170/58018817-d304b280-7b3e-11e9-81f8-bda5122a3848.PNG" width="400px"/> 
            </td>
          </tr>
        </table>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
    <img src="https://user-images.githubusercontent.com/47166170/57373746-0e64c000-71d4-11e9-9d9d-31aa436c8e95.PNG" width="250px"/>
    </td>
    <td>
      ▶ 단축키를 활용한 엑셀 저장<br>
      <ul>
        <li>단축키를 지정해 명령 수행 가능.</li>
        <li>그중 Ctrl+S로 엑셀파일로 빼내 원하는 위치에 저장이 가능(엑셀저장은 poi.jar 라이브러리를 가져와서 수행).</li>
        <li>HSSFWorkbook 객체로 엑셀파일 생성후 ResultSetMetaData를 통해 DB테이블의 컬럼조사, 자료들을 가져온다.</li>
        <li>ResultSet객체로 DB의 줄 조사 이후 각 줄마다 Swing에서 사전에 정의한 테이블에 덮어 씌움</li>
        <li>이후 저장경로를 생성하여 File객체를 만들어 해당파일을 엑셀로 저장</li>
        <table>
          <tr>
            <td colspan="2">저장 처리가 된 이후 나오는 파일</td>
          </tr>
          <tr>
            <td>
            <img src="https://user-images.githubusercontent.com/47166170/57374152-2c7ef000-71d5-11e9-9cd5-929792cf0119.PNG" width="250px"/> 
            </td>
            <td>
              <img src="https://user-images.githubusercontent.com/47166170/57374166-36085800-71d5-11e9-8f87-b7fe7a6f85d0.PNG" width="250px"/>
            </td>
          </tr>
        </table>
      </ul>
    </td>
  </tr>
  <tr>
    <td>
    후기
    </td>
    <td>
      ▶ 단순 cmd창에서의 번거로운 접속 및 테이블 조회과정을 생략하고 오라클에 자동 접속 및 계정확인이 가능하다.<br>
      ▶ 테이블 컬럼 자동조회 및 CRUD의 반영결과를 즉시 확인 할 수 있다.<br>
      ▶ Oracle에 대해 보다 깊은 이해에 도움이 됐고, DB쿼리 관련공부에 집중 할 수 있는 프로젝트였다.<br>
      ▶ 차후 쿼리문 용어 입력시 자동완성 기능 추가.<br>
    </td>
  </tr>
</table>
