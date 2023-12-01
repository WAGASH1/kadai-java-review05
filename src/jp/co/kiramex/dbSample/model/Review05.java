package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Wagashi1121");

            // DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
            String sql = "SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            System.out.print("検索キーワードを入力してください > ");
            int input = keyInNum();

            pstmt.setInt(1, input);

            rs = pstmt.executeQuery();

            // 結果を表示
            while (rs.next()) {
                // Name列の値を取得
                String name = rs.getString("Name");
                // age列の値を取得
                int age = rs.getInt("age");

                System.out.println(name);
                System.out.println(age);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            
            // 8. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }
    //キーボードから数値を受け取る
    private static int keyInNum() {
        int result = 0;

        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            String inputString = key.readLine();
            result = Integer.parseInt(inputString);

        } catch (NumberFormatException | IOException e) {
            System.err.println("数字で入力して下さい");
            e.printStackTrace();
        }
        return result;
    }

}
