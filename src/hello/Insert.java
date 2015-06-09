package hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class DataTest
 */
/** リクエストとクラスの関連付けを行う*/
@WebServlet("/Insert")
public class Insert extends HttpServlet {
	/** シリアルUIDの指定 */
	private static final long serialVersionUID = 1L;

	/** Postリクエストに対する応答 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException	{

		/** レスポンスタイプ */
		response.setContentType("text/html; charset=UTF-8");
		/** 各変数の初期化*/
		Connection conn = null;
		PreparedStatement stmt = null;

		/** アウトプットインスタンスの生成 */
		PrintWriter out = response.getWriter();
		/**入力された引数を取得する*/
		String code = request.getParameter("newcode");
	    String company = request.getParameter("newcompany");

	    try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctestdb?user=testuser&password=testpass");

			conn.setAutoCommit(false);

			String sql = "insert into kabukatable (code, company) values ('" + code + "','" + company + "')";

		    stmt = conn.prepareStatement(sql);

		    stmt.execute();

		    stmt.close();
		    conn.commit();

		    out.println("以下のデータを保存しました。");
		    out.println(code);
		    out.println("<br>");
		    out.println(company);
		    out.println("<a href=\"/HelloServlet/Data\">トップへ戻る</a>");

		} catch(SQLException | ClassNotFoundException e) {
			if(conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
					//e1.printStackTrace();
				}
			out.println("<h1>");
			out.println("データベース接続できませんでした。");
			out.println("</h1>");
			out.println(e);

		} finally {
			try {
	            if(conn != null)
	                conn.close();
	        }catch(Exception e){

		}

	}
}
}










