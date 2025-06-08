//package com.ssafy.project.model.dao;
//
//import com.ssafy.live.model.dto.Guguns;
//import com.ssafy.live.util.DBUtil;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GugunsDao {
//    private static DBUtil util = DBUtil.getUtil();
//    
//    private static GugunsDao gugunsDao = new GugunsDao();
//    private GugunsDao() {}
//    public static GugunsDao getInstance() {
//    	return gugunsDao;
//    }
//
//    // 특정 시도의 구군 정보를 가져오는 메소드
//    public List<Guguns> selectGugunsBySido(Connection conn, int sidoCode) {
//        List<Guguns> gugunsList = new ArrayList<>();
//        String sql = "SELECT * FROM guguns WHERE sido_code = ?";
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, sidoCode);  // 시도코드를 조건으로 구군을 조회
//            try (ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    Guguns gugun = new Guguns(
//                        rs.getInt("no"),
//                        rs.getInt("sido_code"),
//                        rs.getInt("gugun_code"),
//                        rs.getString("gugun_name")  // 구군 이름을 제대로 가져옵니다.
//                    );
//                    gugunsList.add(gugun);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return gugunsList;  // 해당 시도의 모든 구군을 반환
//    }
//}


