package com.wang.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {

    private static String URL = "";
    private static String USER = "";
    private static String PASSWORD = "";

    static {


        try {
            Properties properties = new Properties();
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
            String DRIVER = properties.getProperty("DRIVER");
            URL = properties.getProperty("URL");
            USER = properties.getProperty("USER");
            PASSWORD = properties.getProperty("PASSWORD");

            Class.forName(DRIVER);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @return 数据库连接对象
     * @throws Exception 抛出异常
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     *
     * @param sql sql语句
     * @param parameters 可变参数
     * @return boolean 是否更新成功
     */
    public static boolean update(String sql, Object... parameters) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return false;
    }

    /**
     *
     * @param sql sql语句
     * @param c 封装的对象
     * @param parameters 可变参数
     * @param <T> 泛型
     * @return 返回对应的一个对象
     */
    public static <T> T queryBean(String sql, Class<T> c, Object... parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T object = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i+1, parameters[i]);
            }

            rs = ps.executeQuery();
            //结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //查询结果集的列数
            int columnCount = metaData.getColumnCount();

            if(rs.next()) {
                object = c.newInstance();
                encapsulation(rs, object, metaData, columnCount);

            }

        } catch (Exception ignored) {}

        return object;
    }

    private static <T> void encapsulation(ResultSet rs, T object,
                                          ResultSetMetaData metaData, int columnCount) throws SQLException {
        for (int i = 1; i <= columnCount; i++) {
            //表的字段名 列名
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(columnName);
            if(value instanceof Timestamp){
                value=String.valueOf(value);
            }
            createBean(object, columnName, value);
        }
    }

    /**
     *
     * @param sql sql语句
     * @param c 封装的对象
     * @param parameters 可变参数
     * @param <T> 泛型
     * @return 返回对应的对象列表
     */
    public static <T> List<T> queryList(String sql, Class<T> c, Object... parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i+1, parameters[i]);
            }

            rs = ps.executeQuery();
            //结果集元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //查询结果集的列数
            int columnCount = metaData.getColumnCount();
            while(rs.next()) {

                T object = c.newInstance();
                encapsulation(rs, object, metaData, columnCount);
                list.add(object);
            }

        } catch (Exception ignored) {}
        finally {
            close(conn, ps, rs);
        }

        return list;
    }

    public static void createBean(Object o, String key, Object value) {

        try {
            Class c = o.getClass();
            // 获取对应属性
            Field field = c.getDeclaredField(key);

            // 设置可访问私有属性
            field.setAccessible(true);
            field.set(o, value);

        } catch (Exception e) {
        }

    }

    public static <T> T querySingle(String sql, Object... parameters) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T object = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i+1, parameters[i]);
            }

            rs = ps.executeQuery();
            if(rs.next()) {
                object = (T) rs.getObject(1);

            }

        } catch (Exception ignored) {}

        return object;
    }

    public static boolean commitTransaction(String sql, String... parameters) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            System.out.println(sql);

            ps = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            for (String s : parameters) {
                String[] s_ = s.split("\\|");
                ps.setObject(1, Double.parseDouble(s_[0]));
                ps.setObject(2, s_[1]);
                ps.addBatch();
            }
            int[] count = ps.executeBatch();
            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if(conn != null) {
                conn.rollback();
            }

        } finally {
            close(conn, ps, null);
        }

        return false;

    }

    /**
     *
     * @param conn 数据库连接对象
     * @param ps sql预编译对象
     * @param rs 结果集对象
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {

        try{
            if(rs != null) {
                rs.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(conn != null) {
                 conn.close();
            }
        } catch (Exception e) {
            System.out.println("关闭资源失败");
        }

    }

}
