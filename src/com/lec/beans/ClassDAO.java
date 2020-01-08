package com.lec.beans;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.D;

public class ClassDAO {

	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;

	public ClassDAO() {
		try {
			Class.forName(D.DRIVER);
			conn = DriverManager.getConnection(D.URL, D.USERID, D.USERPW);
			System.out.println("AdminReview 객체 생성, 데이터베이스 연결");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DB 자원 반납 메소드
	public void close() throws SQLException {
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}

	
	public ClassDTO[] createClassArray(ResultSet rs) throws SQLException {

		ArrayList<ClassDTO> list = new ArrayList<ClassDTO>();

		while (rs.next()) {

			String ins_img = rs.getString("ins_img");
			int class_zzimcnt = rs.getInt("class_zzimcnt");
			String ins_name = rs.getString("ins_name");
			String cur_name = rs.getString("cur_name");

			ClassDTO dto = new ClassDTO(ins_name, cur_name, class_zzimcnt, ins_img);
			list.add(dto);
		}

		int size = list.size();
		ClassDTO[] arr = new ClassDTO[size];
		list.toArray(arr);
		return arr;
	}

	
	
	
	
	public ClassDTO[] selectCurList() throws SQLException {

		ClassDTO [] arr = null;
		String selectCur = D.SQL_SELECT_CLASS;

		// 정렬
		selectCur += D.SQL_ORDER_CLASS_UID;

		try {
			// keyword가 있을 경우 쿼리문에 키워드 넘겨주기
			pstmt = conn.prepareStatement(selectCur);
			rs = pstmt.executeQuery();
			arr = createClassArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return arr;
	}
	
	

	public ClassDTO[] createBranchArray(ResultSet rs) throws SQLException {

		ArrayList<ClassDTO> list = new ArrayList<>();

		while (rs.next()) {
			String ins_branch = rs.getString("ins_branch");
			ClassDTO dto = new ClassDTO(ins_branch);
			list.add(dto);
		}

		int size = list.size();
		ClassDTO[] arr = new ClassDTO[size];
		list.toArray(arr);

		return arr;
	}

	
	
	public ClassDTO[] selectBranchList(int option_cur_1) throws SQLException {

		ClassDTO [] arr = null;
		String selectCur = D.SQL_SELECT_BRANCH;
		selectCur += D.SQL_SELECT_BRANCH_WHERE_LOCATION;

		try {
			// keyword가 있을 경우 쿼리문에 키워드 넘겨주기
			pstmt = conn.prepareStatement(selectCur);
			pstmt.setInt(1, option_cur_1);
			rs = pstmt.executeQuery();
			arr = createBranchArray(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return arr;
	}

//	//지역별 학원출력 
//	public ClassDTO[] selectCurListByOptionOne(int option_cur_1,String option_cur_2,int option_cur_3) throws SQLException {
//
//		ClassDTO [] arr = null;
//		String selectCur = D.SQL_SELECT_CLASS;
//		selectCur +=  D.SQL_SELECT_CLASS_WHERE_INS_LOCATION;
//		selectCur +=  D.SQL_SELECT_CLASS_WHERE_INS_BRANCH;
//		selectCur +=  D.SQL_SELECT_CLASS_WHERE_CUR_NAME;
//		String locationTemp = "";
//		String curNameTemp = "";
//		int setOne = 0;
//		int setTwo = 0;
//		int setThree = 0;
//
//		try {
//
//			if(option_cur_1 != 0) {
//				switch(option_cur_1) {
//				case 1: 
//					locationTemp = "서울";
//					break;
//				case 2:
//					locationTemp = "경기";
//					break;
//				case 3:
//					locationTemp = "인천";
//					break;
//				case 4:
//					locationTemp = "대전";
//					break;
//				case 5:
//					locationTemp = "대구";
//					break;
//				case 6:
//					locationTemp = "부산";
//					break;
//				case 7:
//					locationTemp = "광주";
//					break;
//				case 8:
//					locationTemp = "울산";
//					break;
//				case 9:
//					locationTemp = "기타";
//					break;
//				}
//				setOne = 1;
//				pstmt.setString(setOne, locationTemp);
//			} else {
//				setOne = 0;
//				selectCur = selectCur.replace(D.SQL_SELECT_CLASS_WHERE_INS_LOCATION, "");
//			}
//
//			ClassDTO [] branchs = selectBranchList(option_cur_1);
//
//			for(int i = 0; i < branchs.length; i++) {
//				if (branchs == null || branchs.length == 0) {
//					if (setOne == 0) {
//						setTwo = 1;
//					} else if (setOne == 1) {
//						setTwo = 2;
//					}
//					selectCur = selectCur.replace(D.SQL_SELECT_CLASS_WHERE_INS_BRANCH, "");
//				} 
//				if (option_cur_2.equals(branchs[i].getIns_branch())) {
//					pstmt.setString(setTwo, branchs[i].getIns_branch());
//					break;
//				}
//			}
//
//			if(option_cur_3 != 0) {
//				switch(option_cur_3) {
//				case 1: 
//					curNameTemp = "웹/앱";
//					break;
//				case 2:
//					curNameTemp = "네트워크";
//					break;
//				case 3:
//					curNameTemp = "보안";
//					break;
//				case 4:
//					curNameTemp = "AI";
//					break;
//				case 5:
//					curNameTemp = "디자인";
//					break;
//				case 6:
//					curNameTemp = "영상";
//					break;
//				case 7:
//					curNameTemp = "빅데이터";
//					break;
//				case 8:
//					curNameTemp = "게임";
//					break;
//				}
//				if (setOne == 0 && setTwo == 0) {
//					setThree = 1;
//				} else if (setOne == 0 && setTwo == 1) {
//					setThree = 2;
//				} else if (setOne == 1 && setTwo == 0) {
//					setThree = 2;
//				} else if (setOne == 1 && setTwo == 2) {
//					setThree = 3;
//				}
//				pstmt.setString(setThree, curNameTemp);
//			} else {
//				selectCur = selectCur.replace(D.SQL_SELECT_CLASS_WHERE_CUR_NAME, "");
//			}
//
//			// 정렬
//			selectCur += D.SQL_ORDER_CLASS_UID;
//			pstmt = conn.prepareStatement(selectCur);
//			rs = pstmt.executeQuery();
//			arr = createClassArray(rs);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			close();
//		}
//
//		return arr;
//	}
	
	public ClassDTO[] createClassArrayByUid(ResultSet rs) throws SQLException {

		ArrayList<ClassDTO> list = new ArrayList<ClassDTO>();

		while (rs.next()) {
			String cur_name = rs.getString("cur_name");
			int cur_hours = rs.getInt("cur_hours");
			int cur_months = rs.getInt("cur_months");
			String cur_month1 = rs.getString("cur_month1");
			String cur_month2 = rs.getString("cur_month2");
			String cur_month3 = rs.getString("cur_month3");
			String cur_month4 = rs.getString("cur_month4");
			String cur_month5 = rs.getString("cur_month5");
			String cur_month6 = rs.getString("cur_month6");
			String ins_name = rs.getString("ins_name");
			String ins_tel = rs.getString("ins_tel");
			String ins_img = rs.getString("ins_img");
			double ins_x = rs.getDouble("ins_x");
			double ins_y = rs.getDouble("ins_y");
			
			ClassDTO dto = new ClassDTO( cur_name,  cur_hours,  cur_months,  cur_month1,  cur_month2,
					 cur_month3,  cur_month4,  cur_month5,  cur_month6,  ins_name,  ins_tel,  ins_img, ins_x, ins_y);
			list.add(dto);
		}

		int size = list.size();
		ClassDTO[] arr = new ClassDTO[size];
		list.toArray(arr);
		return arr;
	}

	
	
	public ClassDTO[] selectClassByUid(int class_uid) throws SQLException {
		ClassDTO[] arr = null ;
		
		try {
			pstmt = conn.prepareStatement(D.SQL_SELECT_INS_BY_UID);
			pstmt.setInt(1, class_uid);
			rs = pstmt.executeQuery();
			arr = createClassArrayByUid(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return arr;
	}
	
	
	
	public int updateMemberByUid(int mb_uid) throws SQLException {
		int cnt = 0;
		
		try {
			pstmt = conn.prepareStatement(D.SQL_UPDATE_MB_LEVEL);
			pstmt.setInt(1, mb_uid);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return cnt;
	}
	
	
	
	public int insertZZim(int mb_uid, int class_uid) throws SQLException {
		int cnt = 0;
		
		try {
			pstmt = conn.prepareStatement(D.SQL_INSERT_ZZIM);
			pstmt.setInt(1, mb_uid);
			pstmt.setInt(2, class_uid);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return cnt;
	}
	
	public int deleteZZim(int zzim_uid) throws SQLException {
		int cnt = 0;
		
		try {
			pstmt = conn.prepareStatement(D.SQL_DELETE_ZZIM);
			pstmt.setInt(1, zzim_uid);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return cnt;
	}
	
	
	
	//지역별 학원출력 
		public ClassDTO[] selectClassList(String option_location, String option_branch, String option_curName) throws SQLException {
	
			ClassDTO [] arr = null;
			String selectClass = D.SQL_SELECT_CLASS;
			int option = 1; // 조건, 1이 기본
			
			/* ########## 조건 구분 ##########
			 *           
			 * option   지역          지점명         과정명
			 * -----------------------------
			 *   1 :    전체     +  전체     +  X   = 기본
			 *   2 :    전체     +  전체     +  O
			 *  
			 *   3 :    선택    +  전체     +  X
			 *   4 :    선택    +  전체     +  O
			 * 
			 *   5 :    선택    +  선택     +  X
			 *   6 :    선택    +  선택     +  O 
			 *  
			 */
			    
			// 지역 조건이 '전체'가 아니라면, 지점조건을 따질것
			if(!option_location.equals("전체")) {

				// 지점조건이 '전체'가 아니라면! 지점조건을 추가해주고 과정조건을 따지러 가자. 
				if(!option_branch.equals("전체")) {
					
					// 과정명이 입력값을 가졌는가? 값이 있다면 과정명조건 추가
					if(!option_curName.equals("전체과정")) {
						option = 6;
					}else {
						option = 5;
					}
				
				}else {
					// 지점조건이 '전체'라면 바로 과정조건을 따지러가자.
					if(!option_curName.equals("전체과정")) { // 과정조건이 '전체과정'이 아니라면 과정명 조건도 추가 
						option = 4;
					}else {
						option = 3;
					}
				}
			// 지역조건이 '전체'라면 지점은 당연히 전체니까 과정명만 따져주자			
			}else { 
					if(!option_curName.equals("전체과정")) {
						option = 2;
					}else {
						option = 1;
					}
				}

			
			
			try {
				
				switch(option) {
				case 1:
					selectClass += D.SQL_ORDER_CLASS_UID; //정렬
					pstmt = conn.prepareStatement(selectClass);
					break;
				case 2:
					selectClass += D.SQL_SELECT_CLASS_WHERE_CUR_NAME;
					selectClass += D.SQL_ORDER_CLASS_UID; //정렬
					pstmt = conn.prepareStatement(selectClass);
					pstmt.setString(1, option_curName);
					break;
				case 3:
					selectClass += D.SQL_SELECT_CLASS_WHERE_INS_LOCATION; 
					selectClass += D.SQL_ORDER_CLASS_UID; //정렬
					pstmt = conn.prepareStatement(selectClass);
					pstmt.setString(1, option_location);
					break;
				case 4:
					selectClass += D.SQL_SELECT_CLASS_WHERE_INS_LOCATION;
					selectClass += D.SQL_SELECT_CLASS_WHERE_CUR_NAME;
					selectClass += D.SQL_ORDER_CLASS_UID; //정렬
					pstmt = conn.prepareStatement(selectClass);
					pstmt.setString(1, option_location);
					pstmt.setString(2, option_curName);
					break;
				case 5:
					selectClass += D.SQL_SELECT_CLASS_WHERE_INS_LOCATION;
					selectClass += D.SQL_SELECT_CLASS_WHERE_INS_BRANCH; 
					selectClass += D.SQL_ORDER_CLASS_UID; //정렬
					pstmt = conn.prepareStatement(selectClass);
					pstmt.setString(1, option_location);
					pstmt.setString(2, option_branch);
					break;
				case 6: 
					selectClass += D.SQL_SELECT_CLASS_WHERE_INS_LOCATION;
					selectClass += D.SQL_SELECT_CLASS_WHERE_INS_BRANCH; 
					selectClass += D.SQL_SELECT_CLASS_WHERE_CUR_NAME;
					selectClass += D.SQL_ORDER_CLASS_UID; //정렬
					pstmt = conn.prepareStatement(selectClass);
					pstmt.setString(1, option_location);
					pstmt.setString(2, option_branch);
					pstmt.setString(3, option_curName);
					break;
				}

				rs = pstmt.executeQuery();
				arr = createClassArray(rs);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				close();
			}
			
			
			return arr;
		}

	
}