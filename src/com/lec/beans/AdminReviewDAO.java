package com.lec.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import common.D;

public class AdminReviewDAO {

	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	
	
	
	public AdminReviewDAO() {
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
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}
	
	
	
	
	
	// ResultSet --> DTO 배열로 변환 리턴
	public ReviewDTO [] createReviewArray(ResultSet rs) throws SQLException {
		
		ArrayList<ReviewDTO> reviewList = new ArrayList<ReviewDTO>();
		
		while(rs.next()){
			int review_brd_uid = rs.getInt("review_brd_uid");
			String mb_id = rs.getString("mb_id");
			String ins_name = rs.getString("ins_name");
			String review_brd_title = rs.getString("review_brd_title");
			Date d = rs.getDate("review_brd_regdate");
			Time t = rs.getTime("review_brd_regdate");
			String review_brd_regdate = new SimpleDateFormat("yyyy-MM-dd").format(d) + " | " + new SimpleDateFormat("hh:mm:ss").format(t);
			int review_brd_viewcnt = rs.getInt("review_brd_viewcnt");
			ReviewDTO dto = new ReviewDTO(review_brd_uid, mb_id, ins_name, review_brd_regdate, review_brd_title, review_brd_viewcnt);
			reviewList.add(dto);
		}
		
		int size = reviewList.size();
		ReviewDTO [] arr = new ReviewDTO[size];
		
		reviewList.toArray(arr);
		
		return arr;
	}
	
	
	
	
	
	// 리뷰 목록 
	public ReviewDTO[] selectReviewList(int option_review, String keyword) throws SQLException {
		
		ReviewDTO [] arr = null;
		String selectReview = D.SQL_SELECT_REVIEW;
		
		// 리뷰 검색 조건 (1)회원ID  (2)리뷰제목   (3)리뷰내용

		int setStr1 = 0;
		
		switch(option_review) {
			case 1: 
				selectReview += D.SQL_SELECT_REVIEW_BRD_WHERE_USER_ID;
				keyword = "%" + keyword + "%";
				setStr1 = 1;
				break;
			case 2:
				selectReview += D.SQL_SELECT_REVIEW_BRD_WHERE_REVIEW_TITLE;
				keyword = "%" + keyword + "%";
				setStr1 = 1;
				break;
			case 3:
				selectReview += D.SQL_SELECT_REVIEW_BRD_WHERE_REVIEW_CONTENT;
				keyword = "%" + keyword + "%";
				setStr1 = 1;
				break;
			case 4:
				break;
		}
		
		// 정렬
		selectReview += D.SQL_ORDER_REVIEW;
		
		try {
			pstmt = conn.prepareStatement(selectReview);
			if (setStr1 == 1) pstmt.setString(setStr1, keyword); 
			System.out.println(pstmt);
			rs = pstmt.executeQuery();
			arr = createReviewArray(rs);
		} finally {
			close();
		}		
		
		return arr;
	}
	
	
	
	
	
	// 리뷰 삭제
	public int deleteReview(int review_uid) throws SQLException {
		
		int cnt = 0;
		
		try {
			pstmt = conn.prepareStatement(D.SQL_DELETE_REVIEW_BY_UID);
			System.out.println(pstmt);
			pstmt.setInt(1, review_uid);
			cnt = pstmt.executeUpdate();
		} finally {
			close();
		}
		
		return cnt;
	}
	
	
	
	
	
}
