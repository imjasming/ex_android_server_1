import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 在Servlet中直接连接数据库，并查询显示信息
 * 每个application都应该有自己的驱动包，放在各自项目的WEB-INF/lib/目录下
 */

public class LoginServer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("login-doget");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        //调用数据库
        // 直接调用DB中的静态方法来为本类服务
        Connection conn = DatabaseManager.getConnection();
        Statement stmt = DatabaseManager.getStatement(conn);
        String sql = "select * from users where username='" + username
                + "' and password='" + password + "'";
        ResultSet rs = DatabaseManager.getResultSet(stmt, sql);

        String url;
        try {
            if (rs.next()) {
                System.out.println("login sucess");
               out.print("true");
            } else {
                System.out.println("login failed");
                out.print("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("login-doPost");
        doGet(request, response);
    }

}