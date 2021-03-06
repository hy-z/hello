package hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;
/**
 * Servlet implementation class DataTest
 */
/**リクエストとクラスの関連付けを行う*/
@WebServlet("/DataTest")
public class DataTest extends HttpServlet {
	/** シリアルUIDの指定 */
	private static final long serialVersionUID = 1L;

	/** Postリクエストに対する応答 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException	{

		 /** レスポンスタイプ */
		response.setContentType("text/html; charset=UTF-8");

		/** アウトプットインスタンスの生成 */
		PrintWriter out = response.getWriter();
		/** 各変数の初期化*/
		Connection conn = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctestdb?user=testuser&password=testpass");

			Statement stmt = (Statement) conn.createStatement();

			String sql = "select * from kabukatable";

			ResultSet rs = stmt.executeQuery(sql);

			String tableHTML = "<table border=1>";
			tableHTML += "<tr bgcolor=\"000080\"><td><font color=\"white\">code</font></td>"
					  + "<td><font color=\"white\">company</font></td>";

			while(rs.next()) {
			    int code = rs.getInt("code");
			    String company = rs.getString("company");

			    tableHTML += "<tr><td align=\"right\">" + code + "</td>"
			      + "<td>" + company + "</td>"

			      + "<td>" + "<form action=\"update.html\" method=\"POST\">"

			      + "<input type = \"hidden\" name = \"code_id\" value= \" " +  code  + " \" >"

			      + "<input type = \"submit\" value=\"取得\"></form>" + "</td>";
			  }
			tableHTML += "</table>";


		    out.println(tableHTML);
		    out.println("<a href=\"insert.html\">追加</a>");
		    out.println("<a href=\"delete.html\">削除</a>");
		    rs.close();
		    stmt.close();

		} catch(Exception e) {
			out.println("<h1>");
			out.println("データベース接続できませんでした。");
			out.println("</h1>");
			out.println(e);
		} finally {
			out.close();

		}

	}
}

