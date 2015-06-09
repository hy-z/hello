package hello;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class DataTest
 */
//リクエストとクラスの関連付けを行う
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	//行いたい処理
	private static final long serialVersionUID = 1L;

	//そのクラスがgetリクエストに応答するには次のようなメソッドを用意する。
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException	{

		//送信する情報のコンテンツタイプを設定する,これはtext/htmlのデータで、キャラクタセットはUTF-8を使っています.
		response.setContentType("text/html; charset=UTF-8");

		Connection conn = null;
		PreparedStatement stmt = null;

		/*データを送り返す準備,Printwriterはdbに送信するためのストリーム,
		response.getWriter();インスタンスを格納した、変数outを利用することにより、
		webページが表示される。*/
		PrintWriter out = response.getWriter();
		//入力された引数を取得する
		String delete_code = request.getParameter("newcode");

	    try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctestdb?user=testuser&password=testpass");

			conn.setAutoCommit(false);

			String sql = "delete from  kabukatable where code = (" + delete_code + ")";

		    stmt = conn.prepareStatement(sql);

		    stmt.execute();

		    stmt.close();
		    conn.commit();
		    out.println("以下のcodeを削除しました。");
		    out.println(delete_code);
		    out.println("<a href=\"/HelloServlet/Data\">トップへ戻る</a>");
	    }catch(Exception e) {
			out.println("<h1>");
			out.println("データベース接続できませんでした。");
			out.println("</h1>");
			out.println(e);
		} finally {
			out.close();
	}
}
}
