package com.lec.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.D;

public class NewsDAO {
	    
	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	
	public NewsDAO() {
		try {
			Class.forName(D.DRIVER);
			conn = DriverManager.getConnection(D.URL, D.USERID, D.USERPW);
			System.out.println("NewsDAO() 객체 생성, 데이터베이스 연결");
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
	
	// 뉴스 목록 불러오기 - 수정중
	// 1.
	public NewsDTO [] createNewsArray(ResultSet rs) throws SQLException {
		NewsDTO [] arr = null;
		
		ArrayList<NewsDTO> newsList = new ArrayList<NewsDTO>();
		
		while(rs.next()){

			int news_brd_uid = rs.getInt("news_brd_uid");
			String mb_id = rs.getString("mb_id");
			String news_brd_regdate = rs.getString("news_brd_regdate");
			String news_brd_title = rs.getString("news_brd_title");
			String news_brd_viewcnt = rs.getString("news_brd_viewcnt");
			String news_brd_img = rs.getString("news_brd_img");
			
			NewsDTO dto = new NewsDTO(news_brd_uid, mb_id, news_brd_regdate, news_brd_title, news_brd_viewcnt, news_brd_img);
			newsList.add(dto);			
		}
		
		int size = newsList.size();
		ReviewDTO [] arr = new ReviewDTO[size];
		
		newsList.toArray(arr);
		
		return arr;
	}
	
	// 2.
	public ReviewDTO[] selectReviewList(int option_review, String keyword) throws SQLException {
		
		
		ReviewDTO [] arr = null;
		String selectReview = D.SQL_SELECT_REVIEW;
		
		// 후기 검색 조건 (1)회원ID  (2)후기제목   (3)후기내용
		
		switch(option_review) {
			case 1: 
				selectReview += D.SQL_SELECT_REVIEW_BRD_WHERE_USER_ID;
				break;
			case 2:
				selectReview += D.SQL_SELECT_REVIEW_BRD_WHERE_REVIEW_TITLE;
				break;
			case 3:
				selectReview += D.SQL_SELECT_REVIEW_BRD_WHERE_REVIEW_CONTENT;
				break;
			default:
				break;
		}
		
		// 정렬
		selectReview += D.SQL_ORDER_REVIEW;
		
		try {
			// keyword가 있을 경우 쿼리문에 키워드 넘겨주기
			if(keyword != null && !keyword.equals("")) {
				pstmt = conn.prepareStatement(selectReview);
				pstmt.setString(1, keyword);
			}else { 
				pstmt = conn.prepareStatement(selectReview);
			}

			rs = pstmt.executeQuery();
			arr = createReviewArray(rs);
		} finally {
			close();
		}		
		
		return arr;
	}
	
}