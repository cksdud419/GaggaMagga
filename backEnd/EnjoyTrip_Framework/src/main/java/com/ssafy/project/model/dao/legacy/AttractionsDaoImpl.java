//package com.ssafy.project.model.dao;
//
//import com.ssafy.live.model.dto.Attractions;
//import com.ssafy.live.util.DBUtil;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AttractionsDao {
//    private static DBUtil util = DBUtil.getUtil();
//    
//    private static AttractionsDao attractionDao = new AttractionsDao();
//    private AttractionsDao() {}
//    public static AttractionsDao getInstance() {
//    	return attractionDao;
//    }
//    
//    private List<Attractions> mapResultSetToList(ResultSet rs) throws SQLException {
//        List<Attractions> list = new ArrayList<>();
//        while (rs.next()) {
//            list.add(new Attractions(
//                rs.getInt("no"),
//                rs.getString("title"),
//                rs.getString("addr1"),
//                rs.getString("addr2"),
//                rs.getInt("area_code"),
//                rs.getInt("si_gun_gu_code"),
//                rs.getInt("content_id"),
//                rs.getInt("content_type_id"),
//                rs.getString("first_image1"),
//                rs.getString("first_image2"),
//                rs.getString("homepage"),
//                rs.getBigDecimal("latitude"),
//                rs.getBigDecimal("longitude"),
//                rs.getInt("map_level"),
//                rs.getString("overView"),
//                rs.getString("tel")
//            ));
//        }
//        return list;
//    }
//    
//    // ================================ F01 ================================ //
//    
//    public List<Attractions> selectAttraction_All(Connection conn) {
//        // 랜덤 정렬을 적용하여 결과를 조회
//        String sql = "SELECT * FROM attractions ORDER BY RAND() LIMIT 100"; // 100개 관광지를 랜덤하게 조회
//        try (PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//            return mapResultSetToList(rs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public List<Attractions> selectAttraction_Area(Connection conn, int areaCode) {
//        // 랜덤 정렬을 적용하여 결과를 조회
//        String sql = "SELECT * FROM attractions WHERE area_code = ? ORDER BY RAND() LIMIT 100";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, areaCode);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return mapResultSetToList(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public List<Attractions> selectAttraction_Sigungu(Connection conn, int areaCode, int si_gun_gu_code) {
//        // 랜덤 정렬을 적용하여 결과를 조회
//        String sql = "SELECT * FROM attractions WHERE area_code = ? AND si_gun_gu_code = ? ORDER BY RAND() LIMIT 100";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, areaCode);
//            stmt.setInt(2, si_gun_gu_code);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return mapResultSetToList(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//    
//    // ================================ F02~03 ================================ //
//    
//    public List<Attractions> selectAttraction_Category(Connection conn, int content_type_id) {
//        // 랜덤 정렬을 적용하여 결과를 조회
//        String sql = "SELECT * FROM attractions WHERE content_type_id = ? ORDER BY RAND() LIMIT 100";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, content_type_id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return mapResultSetToList(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public List<Attractions> selectAttraction_Area_Category(Connection conn, int areaCode, int content_type_id) {
//        // 랜덤 정렬을 적용하여 결과를 조회
//        String sql = "SELECT * FROM attractions WHERE area_code = ? AND content_type_id = ? ORDER BY RAND() LIMIT 100";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, areaCode);
//            stmt.setInt(2, content_type_id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return mapResultSetToList(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    public List<Attractions> selectAttraction_Sigungu_Category(Connection conn, int areaCode, int si_gun_gu_code, int content_type_id) {
//        // 랜덤 정렬을 적용하여 결과를 조회
//        String sql = "SELECT * FROM attractions WHERE area_code = ? AND si_gun_gu_code = ? AND content_type_id = ? ORDER BY RAND() LIMIT 100";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, areaCode);
//            stmt.setInt(2, si_gun_gu_code);
//            stmt.setInt(3, content_type_id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                return mapResultSetToList(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//}

