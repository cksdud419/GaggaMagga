//package com.ssafy.project.model.dao;
//
//import com.ssafy.live.model.dto.Sidos;
//import com.ssafy.live.util.DBUtil;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SidosDao {
//    private static DBUtil util = DBUtil.getUtil();
//   
//    private static SidosDao sidosDao = new SidosDao();
//    private SidosDao() {}
//    public static SidosDao getInstance() {
//    	return sidosDao;
//    }
//
//    // 지역 코드와 지역 이름을 가져오는 메소드
//    public List<Sidos> selectAllSidos(Connection conn) {
//        List<Sidos> sidosList = new ArrayList<>();
//        String sql = "SELECT * FROM sidos";
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                Sidos sido = new Sidos(
//                    rs.getInt("no"),
//                    rs.getInt("sido_code"),
//                    rs.getString("sido_name")
//                );
//                sidosList.add(sido);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return sidosList;
//    }
//}

